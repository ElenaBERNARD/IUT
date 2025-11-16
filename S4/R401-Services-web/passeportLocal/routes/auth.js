// auth.js
const authcontroller = require('../controllers/authcontroller.js');
module.exports = function (app, passport) {
    function isLoggedIn(req, res, next) {
        if (req.isAuthenticated())
            return next();
        res.redirect('/signin');
    }
    app.get('/signup', authcontroller.signup);
    app.get('/signin', authcontroller.signin);
    app.get('/logout', authcontroller.logout);
    app.get('/home', authcontroller.home);
    app.post('/signup', passport.authenticate('local-signup',
        { succesRedirect: '/home', failureRedirect: '/signup' }
    ));
    app.post('/signin', passport.authenticate('local-signin',
        { succesRedirect: '/home', failureRedirect: '/signin' }
    ));
}