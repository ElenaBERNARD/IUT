<template>
    <div class="home-container">
        <div class="home-content">
            <h1 class="welcome-title">Bienvenue sur notre plateforme !</h1>
            
            <div v-if="userStore.user" class="user-section">
                <div class="user-info">
                    <div class="user-details">
                        <p>Connecté en tant que <strong>{{ userStore.user.username }}</strong></p>
                        <p class="user-email">{{ userStore.user.email }}</p>
                    </div>
                </div>
                
                <button @click="userStore.logout" class="logout-btn">
                    <span>Se déconnecter</span>
                </button>

                <div class="integrated-chat">
                    <h2>Chat en direct</h2>
                    <ChatComponent />
                </div>

                <router-link to="/chat" class="open-chat-btn">
                    Ouvrir le chat en plein écran
                </router-link>
            </div>

            <div v-else class="login-section">
                <h2>Connectez-vous pour accéder au chat</h2>
                <button @click="loginWithGoogle" class="google-login-btn">
                    <img src="https://upload.wikimedia.org/wikipedia/commons/5/53/Google_%22G%22_Logo.svg" alt="Google logo" class="google-logo">
                    <span>Se connecter avec Google</span>
                </button>
            </div>
        </div>
    </div>
</template>

<script setup>
import { onMounted } from 'vue';
import { useUserStore } from '../stores/user';
import ChatComponent from '../components/ChatComponent.vue';

const userStore = useUserStore();

onMounted(() => {
    userStore.fetchUser();
});

function loginWithGoogle() {
    window.location.href = 'http://localhost:5000/auth/google';
}
</script>

<style scoped>
.home-container {
    display: flex;
    justify-content: center;
    align-items: center;
    min-height: 100vh;
    background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
    padding: 20px;
}

.home-content {
    background-color: white;
    border-radius: 16px;
    box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
    padding: 40px;
    width: 100%;
    max-width: 800px;
    text-align: center;
}

.welcome-title {
    color: #2c3e50;
    margin-bottom: 30px;
    font-size: 2.2rem;
}

.user-section {
    display: flex;
    flex-direction: column;
    gap: 20px;
}

.user-info {
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 15px;
    margin-bottom: 15px;
}

.user-details {
    text-align: left;
}

.user-email {
    color: #666;
    font-size: 0.9rem;
    margin-top: 5px;
}

button {
    padding: 12px 24px;
    border: none;
    border-radius: 50px;
    font-weight: 600;
    cursor: pointer;
    transition: all 0.3s ease;
    display: inline-flex;
    align-items: center;
    justify-content: center;
}

.google-login-btn {
    background-color: white;
    color: #5f6368;
    border: 1px solid #dadce0;
    font-size: 1rem;
    margin-top: 20px;
}

.google-login-btn:hover {
    background-color: #f8f9fa;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.google-logo {
    width: 20px;
    margin-right: 12px;
}

.logout-btn {
    background-color: #f8f9fa;
    color: #dc3545;
    border: 1px solid #dc3545;
    align-self: center;
}

.logout-btn:hover {
    background-color: #dc3545;
    color: white;
}

.open-chat-btn {
    display: inline-block;
    background-color: #007bff;
    color: white;
    text-decoration: none;
    padding: 12px 24px;
    border-radius: 50px;
    margin-top: 20px;
    transition: background-color 0.3s;
}

.open-chat-btn:hover {
    background-color: #0056b3;
}

.integrated-chat {
    margin-top: 40px;
    border-top: 1px solid #eee;
    padding-top: 20px;
}

.login-section {
    padding: 20px 0;
}

.login-section h2 {
    color: #4a5568;
    margin-bottom: 20px;
    font-size: 1.3rem;
}

@media (max-width: 768px) {
    .home-content {
        padding: 20px;
    }
    
    .welcome-title {
        font-size: 1.8rem;
    }
    
    .user-info {
        flex-direction: column;
        text-align: center;
    }
}
</style>