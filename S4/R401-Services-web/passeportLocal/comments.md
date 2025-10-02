Initialiser un projet
```
npm init --y
```
Ajouter les modules :
```
npm i express pg sequelize body-parser passport passport-local
npm i dotenv bcrypt express-session express-handlebars
```
Initialiser nodemon
```
npm i --save-dev nodemon
```
Creer un fichier server.js
```
touch server.js
```
Ajouter a "scripts" dans /package.json
```
"start": "nodemon server.js",
```
Se rendre sur le site passportjs.org

Ajouter les repertoirs :
```
mkdir config
mkdir controllers
mkdir routes
mkdir models
mkdir views
touch .env
```

Ajouter et ouvrir le fichier config dans config.json. Le remplir avec vos donnes PGSQL ou MySQL
```
touch config/config.json
```
Ajouter les varialbles suivante dans .env
```
NODE_ENV="development"
SECRET='abcd'
```
Dans le fichier  server.js, ajouter les dependances : :
```
const express = require('express');
const app = express();
const passport = require('passport');
const session = require('express-session');
const bodyParser = require('body-parser');

// Encodage
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

const dotenv = require('dotenv');
dotenv.config();
console.log(process.env.SECRET);
```
Tester le programme avec :
```
node server.js
```
Ajouter ensuite dans server.js
```
// Handlebars
const exphbs = require('express-handlebars');
app.set('views', './views');
app.set('view engine', '.hbs');
app.engine(
    'hbs',
    exphbs({extname:'.hbs',
        defaultLayout:'',
        layoutDir: ''
    })
)
```
Creer les fichiers
```
touch models/index.js
touch models/users.js
```
Dans le fichier /models/users.js
```
const { DataTypes } = require('sequelize');
module.exports = (sequelize, Sequelize) => {
    const User = sequelize.define('users', {
        id: {
            type: DataTypes.INTEGER,
            primaryKey: true,
            autoIncrement: true,
            allowNull: false
        },
        firstName: {
            type: DataTypes.STRING,
            notEmpty: true
        },
        lastName: {
            type: DataTypes.STRING,
            notEmpty: true
        },
        emailId: {
            type: DataTypes.STRING,
            notEmpty: true,
            validate: {
                isEmail: true
            }
        },
        password: {
            type: DataTypes.STRING,
            allowNull: false
        }
    },{
        tableName: 'users'
    });
    return User;
}
```
Dans le fichier /models/index.js :
```
const fs = require('fs');
const path = require('path');
const Sequelize = require('sequelize');
const env = process.env.NODE_ENV || 'development';
const config = require(path.join(__dirname, '..', 'config', 'config.json'))[env];
const sequelize = new Sequelize(
    config.database,
    config.username,
    config.password,
    config
);
```