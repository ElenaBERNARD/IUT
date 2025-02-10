const express = require('express');
const keys = require('./config/keys');
const bodyParser = require('body-parser');
const cors = require('cors');
const cookieSeesion = require('cookie-session');
const mongoose = require('mongoose');
const passport = require('passport');

require('./models/User');
require('./models/Blog');
require('./services/passport');

mongoose.connect(keys.mongoURI, {
  useNewUrlParser: true,
  useUnifiedTopology: true
});


const app = express();
// CORS configuration
const corsOptions = {
  origin: 'http://localhost:3000',
  methids: 'GET, HEAD, PATCH, POST, PUT, DELETE',
  credentials: true,
  optionsSuccessStatus: 204
};
app.use(cors(corsOptions));
app.use(bodyParser.json());
app.use(cookieSeesion({
  maxAge: 30 * 24 * 60 * 60 * 1000,
  keys: [keys.cookieKey]
}));
app.use(passport.initialize());
app.use(passport.session());

// TODO SWAGGER DOC

require('./routes/authRoutes')(app);
require('./routes/blogRoutes')(app);


app.get('/', (req, res) => {
  res.send('Salut!');
});

const PORT = process.env.PORT || 5000;
app.listen(PORT, () => {
  console.log(`Le serveur ecoute sur le port: `, PORT);
});