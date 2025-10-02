<template>
    <div class="chat-page-container">
        <div class="chat-page-header">
            <button @click="goBack" class="back-btn">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" viewBox="0 0 16 16">
                    <path fill-rule="evenodd"
                        d="M15 8a.5.5 0 0 0-.5-.5H2.707l3.147-3.146a.5.5 0 1 0-.708-.708l-4 4a.5.5 0 0 0 0 .708l4 4a.5.5 0 0 0 .708-.708L2.707 8.5H14.5A.5.5 0 0 0 15 8z" />
                </svg>
                Retour
            </button>
            <div class="user-info" v-if="userStore.user">
                <div class="user-details">
                    <h2>Chat en temps réel</h2>
                    <div class="user-info">
                        <span>Logged in as: <strong>{{ userStore.user.username }}</strong> (ID: {{ userStore.user.id
                            }})</span>
                    </div>
                </div>
            </div>
            <button @click="userStore.logout" class="logout-btn">
                <span>Se déconnecter</span>
            </button>
        </div>

        <div class="chat-content">
            <ChatComponent />
        </div>
    </div>
</template>

<script setup>
import { useRouter } from 'vue-router';
import { useUserStore } from '../stores/user';
import ChatComponent from '../components/ChatComponent.vue';

const router = useRouter();
const userStore = useUserStore();

function goBack() {
    router.push('/');
}
</script>

<style scoped>
.chat-page-container {
    min-height: 100vh;
    background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
    padding: 20px;
    display: flex;
    flex-direction: column;
}

.chat-page-header {
    background-color: white;
    border-radius: 16px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
    padding: 20px;
    margin-bottom: 20px;
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.back-btn {
    padding: 8px 16px;
    background-color: #f8f9fa;
    color: #2c3e50;
    border: 1px solid #ddd;
    border-radius: 50px;
    font-weight: 600;
    cursor: pointer;
    transition: all 0.3s ease;
    display: flex;
    align-items: center;
    gap: 8px;
}

.back-btn:hover {
    background-color: #e9ecef;
    border-color: #adb5bd;
}

.back-btn svg {
    margin-right: 5px;
}


.user-info {
    display: flex;
    align-items: center;
    gap: 15px;
}

.user-details h2 {
    
    margin: 0;
    color: #2c3e50;
    font-size: 1.5rem;
}

.user-details p {
    margin: 5px 0 0;
    color: #666;
    font-size: 0.9rem;
}

.logout-btn {
    padding: 8px 16px;
    background-color: #f8f9fa;
    color: #dc3545;
    border: 1px solid #dc3545;
    border-radius: 50px;
    font-weight: 600;
    cursor: pointer;
    transition: all 0.3s ease;
}

.logout-btn:hover {
    background-color: #dc3545;
    color: white;
}

.chat-content {
    flex: 1;
    background-color: white;
    border-radius: 16px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
    padding: 20px;
    display: flex;
    flex-direction: column;
}

@media (max-width: 768px) {
    .chat-page-header {
        flex-direction: column;
        align-items: flex-start;
        gap: 15px;
    }

    .logout-btn {
        align-self: flex-end;
    }
}
</style>