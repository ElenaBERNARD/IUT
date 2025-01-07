<template>
    <div>
      <h2>Logging out...</h2>
    </div>
  </template>
  
  <script>
  import { mapActions } from 'vuex';
  import router from '../router';
  
  export default {
    name: 'ShopLogout',
    methods: {
      ...mapActions('shop', ['shopLogout']),
  
      async logout() {
        try {
          await this.shopLogout();
          router.push('/shop/login');
        } catch (error) {
          console.error('Shop logout failed:', error);
          router.push('/shop/home');
        }
      },
    },
    created() {
      this.logout();
    },
  };
  </script>
  