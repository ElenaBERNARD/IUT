// models/Message.js
const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const messageSchema = new Schema({
    content: {
        type: String,
        required: true
    },
    sender: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'User',
        required: true
    },
    senderName: {
        type: String,
        required: false,
        default: 'Anonyme' 
    },
    date: {
        type: Date,
        default: Date.now
    }
});

mongoose.model('Message', messageSchema);