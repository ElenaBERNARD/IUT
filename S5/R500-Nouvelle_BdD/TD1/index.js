import express from 'express';
import mongoose from 'mongoose';
import bodyParser from 'body-parser';
import routes from './src/routes/routes.js';

const app = express();
const port = 3000;

mongoose.Promise = global.Promise;
mongoose.connect('mongodb://127.0.0.1:27017/S5_TD1_Productsdb', {
    useNewUrlParser: true,
    useUnifiedTopology: true,
});

// Middleware
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());
routes(app);

app.get('/', (req, res) => {
    res.send('TD1 Express Server running');
});

app.listen(port, () => {
    console.log(`Server is running at http://localhost:${port}`);
});