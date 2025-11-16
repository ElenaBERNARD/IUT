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