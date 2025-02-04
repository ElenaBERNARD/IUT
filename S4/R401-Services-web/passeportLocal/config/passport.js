// config/passport/passport.js
const bCrypt = require('bcrypt');
module.exports = function (passport, user) {
    const User = user;
    const LocalStrategy = require('passport-local').Strategy;
    passport.serializeUser(function (user, done) {
        done(null, user.id);
    });
    passport.deserializeUser(function (id, done) {
        User.findByPk(id).then(function (user) {
            if (user) {
                done(null, user.get());
            } else {
                done(user.errors, null);
            }
        });
    });
    passport.use('local-signup', new LocalStrategy(
        {
            usernameField: 'email',
            passwordField: 'password',
        }, function (req, email, password, done) {
            let generateHash = function (password) {
                return bCrypt.hashSync(password, bCrypt.genSaltSync(8), null);
            }
            User.findOne({
                where: {
                    emailId: email
                }
            }).then(function (user) {
                if (user) {
                    return done(null, false, {
                        message: 'That email is already taken'
                    });
                } else {
                    let userPassword = generateHash(password);
                    let data = {
                        emailId: email,
                        password: userPassword,
                        firstName: req.body.firstName,
                        lastName: req.body.lastName
                    };
                    User.create(data).then(function (newUser, created) {
                        if (!newUser) {
                            return done(null, false);
                        }
                        if (newUser) {
                            return done(null, newUser);
                        }
                    });
                }
            });
        })
    );
    passport.use('local-signin', new LocalStrategy(
        {
            usernameField: 'email',
            passwordField: 'password',
            passReqToCallback: true
        }, function (req, email, password, done) {
            let User = user;
            let isValidPassword = function (userpass, password) {
                return bCrypt.compareSync(password, userpass);
            }
            User.findOne({
                where: {
                    emailId: email
                }
            }).then(function (user) {
                if (!user) {
                    return done(null, false, {
                        message: 'Email does not exist'
                    });
                }
                if (!isValidPassword(user.password, password)) {
                    return done(null, false, {
                        message: 'Incorrect password'
                    });
                }
                let userinfo = user.get();
                return done(null, userinfo);
            }).catch(function (err) {
                console.log('Error:', err);
                return done(null, false, {
                    message: 'Something went wrong with your Signin'
                });
            });
        })
    );
}