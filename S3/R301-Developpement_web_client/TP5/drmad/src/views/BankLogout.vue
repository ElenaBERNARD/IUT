<template>
    <div>
        <h2>Logging out...</h2>
    </div>
</template>

<script>
import { mapActions } from 'vuex';
import router from '../router';

export default {
    name: 'BankLogout',
    methods: {
        ...mapActions('bank', ['bankLogout']),

        async logout() {
            try {
                await new Promise(resolve => setTimeout(resolve, 1000));

                await this.bankLogout();
                
                router.push('/bank/home');
            } catch (error) {
                console.error('Bank logout failed:', error);
                router.push('/bank/home');
            }
        },
    },
    mounted() {
        this.logout();
    },
};
</script>
