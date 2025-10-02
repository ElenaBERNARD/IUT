// routes/messageRoutes.js
const express = require('express');
const messageService = require('../services/message.service');
const router = express.Router();

// Route pour récupérer l'utilisateur courant
router.get('/current_user', (req, res) => {
    if (req.user) {
        res.json({
            id: req.user._id,
            username: req.user.displayName,
            profilePicture: req.user.profilePicture
        });
    } else {
        res.status(401).send('Utilisateur non connecté');
    }
});

// Route pour récupérer l'historique des messages
router.get('/messages', async (req, res) => {
    try {
        const messages = await messageService.getMessages();
        res.json(messages);
    } catch (err) {
        console.error('Erreur lors de la récupération des messages:', err);
        res.status(500).send('Erreur serveur');
    }
});

module.exports = router;
