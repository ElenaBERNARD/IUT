<template>
    <div class="chat-container">
        <div id="messages">
            <div v-for="message in messages" :key="message._id"
                :class="['message', { 'my-message': message.sender._id === currentUserId, 'other-message': message.sender._id !== currentUserId }]">
                <!-- Structure pour les messages des autres utilisateurs -->
                <template v-if="message.sender._id !== currentUserId">
                    <img v-if="message.sender.profilePicture" 
                         :src="message.sender.profilePicture" 
                         alt="Profile" 
                         class="profile-picture other-profile-picture" />
                    
                    <div class="message-content other-message-content">
                        <span class="sender-name">{{ message.sender.displayName }}</span>
                        <p class="message-text">{{ message.content }}</p>
                        <span class="message-time">{{ formatTime(message.date) }}</span>
                    </div>
                </template>

                <!-- Structure pour mes messages -->
                <template v-else>
                    <div class="message-content my-message-content">
                        <p class="message-text">{{ message.content }}</p>
                        <span class="message-time">{{ formatTime(message.date) }}</span>
                    </div>
                    
                    <img v-if="currentUserProfilePicture" 
                         :src="currentUserProfilePicture" 
                         alt="Profile" 
                         class="profile-picture my-profile-picture" />
                </template>
            </div>
        </div>
        <input v-model="newMessage" @keyup.enter="sendMessage" placeholder="Tapez votre message..."
            class="message-input" />
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import socket from '../socket';

const newMessage = ref('');
const messages = ref([]);
const currentUserId = ref('');
const currentUsername = ref('');
const currentUserProfilePicture = ref('');

async function loadMessages() {
    try {
        const response = await axios.get('http://localhost:5000/api/messages', { withCredentials: true });
        messages.value = response.data;
    } catch (err) {
        console.error('Erreur lors du chargement des messages:', err);
    }
}

async function loadCurrentUser() {
    try {
        const response = await axios.get('http://localhost:5000/api/current_user', { withCredentials: true });
        currentUserId.value = response.data.id;
        currentUsername.value = response.data.username;
        currentUserProfilePicture.value = response.data.profilePicture;
        console.log("Profile picture:", currentUserProfilePicture.value);
        console.log('Utilisateur courant:', response.data);
    } catch (err) {
        console.error('Erreur lors de la récupération de l\'utilisateur courant:', err);
    }
}

function formatTime(timestamp) {
    if (!timestamp) return '';
    const date = new Date(timestamp);
    return date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
}

onMounted(() => {
    loadMessages();
    loadCurrentUser();

    socket.on('newMessage', (message) => {
        console.log('Nouveau message reçu:', message);
        // Vérifie si le message est déjà présent dans la liste
        messages.value.push(message);
        setTimeout(() => {
            const messagesContainer = document.getElementById('messages');
            messagesContainer.scrollTop = messagesContainer.scrollHeight;
        }, 50);
    });
});

function sendMessage() {
    if (newMessage.value.trim() !== '') {
        const message = {
            content: newMessage.value,
            sender: {
                _id: currentUserId.value,
                displayName: currentUsername.value,
                profilePicture: currentUserProfilePicture.value
            },
            date: new Date().toISOString()
        };

        socket.emit('sendMessage', message);

        loadMessages(); 
        newMessage.value = '';
    }
}
</script>

<style scoped>
.chat-container {
    width: 100%;
    max-width: 900px;
    margin: 0 auto;
    padding: 20px;
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

#messages {
    max-height: 500px;
    overflow-y: auto;
    padding: 15px;
    background-color: #f9f9f9;
    border-radius: 12px;
    margin-bottom: 20px;
    display: flex;
    flex-direction: column;
    gap: 15px;
}

.message {
    display: flex;
    width: 100%;
}

/* Style pour les messages des autres utilisateurs */
.other-message {
    justify-content: flex-start;
    align-items: flex-end;
}

/* .other-profile-picture {
    order: 1;
    margin-right: 10px;
} */

.other-message-content {
    order: 2;
    background-color: #e9ecef;
    color: #333;
    border-bottom-left-radius: 4px;
    border-top-left-radius: 18px;
    border-top-right-radius: 18px;
    border-bottom-right-radius: 18px;
}

/* Style pour mes messages */
.my-message {
    justify-content: flex-end;
    align-items: flex-end;
}

.my-profile-picture {
    order: 2;
    margin-left: 5px;
}

.other-profile-picture {
    order: 1;
    margin-right: 5px;
}

.my-message-content {
    order: 1;
    background-color: #007bff;
    color: white;
    border-bottom-right-radius: 4px;
    border-top-left-radius: 18px;
    border-top-right-radius: 18px;
    border-bottom-left-radius: 18px;
}

.profile-picture {
    width: 36px;
    height: 36px;
    border-radius: 50%;
    object-fit: cover;
    flex-shrink: 0;
    transform: translateY(30px);
}

.message-content {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    padding: 12px 18px;
    max-width: 75%;
    min-width: 120px;
    position: relative;
}

.sender-name {
    font-weight: bold;
    font-size: 0.85em;
    margin-bottom: 6px;
    color: #555;
}

.my-message-content .sender-name {
    display: none; /* Cache le nom pour mes propres messages */
}

.message-text {
    margin: 0;
    word-break: break-word;
    font-size: 1em;
    line-height: 1.4;
}

.message-time {
    font-size: 0.75em;
    margin-top: 6px;
    opacity: 0.8;
    align-self: flex-end;
}

.my-message-content .message-time {
    color: rgba(255, 255, 255, 0.8);
}

.other-message-content .message-time {
    color: rgba(0, 0, 0, 0.6);
}

.message-input {
    width: 100%;
    padding: 14px 20px;
    border: 1px solid #ddd;
    border-radius: 24px;
    font-size: 1em;
    outline: none;
    transition: border 0.3s;
    box-sizing: border-box;
}

.message-input:focus {
    border-color: #007bff;
    box-shadow: 0 0 0 2px rgba(0, 123, 255, 0.25);
}

@media (max-width: 768px) {
    .chat-container {
        padding: 15px;
    }

    .message-content {
        max-width: 80%;
        padding: 10px 15px;
    }

    .profile-picture {
        width: 32px;
        height: 32px;
    }
}

@media (max-width: 480px) {
    .message-content {
        max-width: 85%;
    }
}
</style>