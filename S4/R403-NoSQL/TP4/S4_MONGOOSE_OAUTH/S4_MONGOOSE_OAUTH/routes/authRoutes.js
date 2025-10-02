const passport = require('passport');

module.exports = app => {
    app.get(
        '/auth/google',
        passport.authenticate('google', {
            scope: ['profile', 'email']
        })
    );

    app.get(
        '/auth/google/callback',
        passport.authenticate('google'),
        (req, res) => {
            res.redirect('http://localhost:5173/');
        }
    );

    app.get('/auth/logout', (req, res) => {
        req.logout();
        res.redirect('http://localhost:5173/'); // Updated redirect URL
    });

    app.get('/api/current_user', (req, res) => {
        res.send(req.user);
    });
};