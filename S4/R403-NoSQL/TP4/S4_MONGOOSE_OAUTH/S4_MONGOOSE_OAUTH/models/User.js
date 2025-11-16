const mongoose = require('mongoose');
const { Schema } = mongoose;

const userSchema = new Schema({
    googleId: String,
    displayName: String,
    profilePicture: {
        type: String,
        required: false
    }
});
mongoose.model('User', userSchema);