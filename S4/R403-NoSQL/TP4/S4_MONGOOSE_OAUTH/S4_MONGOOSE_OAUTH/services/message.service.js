// services/messageService.js
const mongoose = require('mongoose');
const Message = mongoose.model('Message');

const getMessages = async () => {
    try {
        const messages = await Message.find()
            .sort({ date : 1 })
            .limit(50)
            .populate('sender', 'sender displayName profilePicture');
        return messages;
    } catch (err) {
        throw new Error('Erreur lors de la récupération des messages : ' + err.message);
    }
};

module.exports = { getMessages };
