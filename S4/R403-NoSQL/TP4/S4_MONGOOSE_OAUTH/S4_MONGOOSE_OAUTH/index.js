// Importation des modules nécessaires
const passport = require('passport');
const session = require('express-session');
const http = require('http');
const express = require('express');
const cookieParser = require('cookie-parser');
const bodyParser = require('body-parser');
const cors = require('cors');
const mongoose = require('mongoose');

require('./models/Message');
require('./models/User');

require('./services/passport');

const keys = require('./config/keys');
const Message = mongoose.model('Message');

const app = express();

// Redis
const redis = require('redis');
const connectRedis = require('connect-redis');


app.use(cookieParser());


const MongoStore = require('connect-mongo');

// Configure la session avec un store MongoDB
const sessionMiddleware = session({
    secret: keys.cookieKey,
    resave: false,
    saveUninitialized: false,
    cookie: { maxAge: 30 * 24 * 60 * 60 * 1000 },
    store: MongoStore.create({
        mongoUrl: keys.mongoURI,
        collectionName: 'sessions',
    })
});

app.use(sessionMiddleware);


app.use(passport.initialize());
app.use(passport.session())

// CORS configuration
app.use(cors({
    origin: 'http://localhost:5173',  // Frontend Vue.js
    credentials: true
}));

// Utilisation du body parser
app.use(bodyParser.json());

// Connexion à MongoDB
mongoose.connect(keys.mongoURI, {
    useNewUrlParser: true,
    useUnifiedTopology: true
})
    .then(() => console.log('MongoDB connected'))
    .catch(err => console.error('MongoDB connection error:', err));

// Importation des routes (si tu en as besoin pour d'autres fonctionnalités)
const messageRoutes = require('./routes/messageRoutes');

// Utilisation des routes
app.use('/api', messageRoutes);

// Route de base
require('./routes/authRoutes')(app);
app.get('/', (req, res) => {
    res.send('Server is running');
});

// Configuration de Socket.io
const server = http.createServer(app);
const { Server } = require('socket.io');
const io = new Server(server, {
    cors: {
        origin: "http://localhost:5173", // Frontend Vue.js
        credentials: true
    }
});

io.use((socket, next) => {
    sessionMiddleware(socket.request, {}, next);
});

const User = mongoose.model('User');

io.use(async (socket, next) => {
    sessionMiddleware(socket.request, {}, async (err) => {
        if (err) return next(err);

        if (socket.request.session && socket.request.session.passport && socket.request.session.passport.user) {
            try {
                const user = await User.findById(socket.request.session.passport.user);
                if (!user) {
                    return next(new Error('Utilisateur non trouvé'));
                }
                socket.request.user = {
                    id: user._id,
                    username: user.displayName,
                };
                next();
            } catch (error) {
                next(error);
            }
        } else {
            next(new Error('Non autorisé'));
        }
    });
});

io.on('connection', (socket) => {
    console.log('Un utilisateur est connecté à Socket.io');
    console.log('Utilisateur connecte :', socket.request.user);

    socket.on('sendMessage', async (messageContent) => {
        if (socket.request.user) {
            try {
                const user = socket.request.user;
                console.log('Utilisateur :', user);
                const message = new Message({
                    content: messageContent.content,
                    sender: messageContent.sender._id,
                    senderName: messageContent.sender.profilePicture,
                    date: messageContent.date
                });

                const newMessage = await message.save();

                console.log('Message sauvegardé :', newMessage);
                console.log('Contenu du message :', messageContent);
                console.log('User :', user);

                io.emit('newMessage', {
                    content: newMessage.content,
                    sender : {
                        _id: messageContent.sender._id,
                        displayName: messageContent.sender.displayName,
                        profilePicture: messageContent.sender.profilePicture
                    },
                    date: newMessage.date
                });
            } catch (err) {
                console.error('Erreur lors de la sauvegarde du message :', err);
            }
        } else {
            console.log('Utilisateur non authentifié');
        }
    });

    socket.on('disconnect', () => {
        console.log('Un utilisateur s\'est déconnecté');
    });
});

// Démarrage du serveur
const PORT = process.env.PORT || 5000;
server.listen(PORT, () => {
    console.log(`Server running on port ${PORT}`);
});