import { defineStore } from 'pinia';
import axios from 'axios';

export const useUserStore = defineStore('user', {
  state: () => ({
    user: null,
  }),
  getters: {
    isLoggedIn: (state) => !!state.user,  // Retourne true si un utilisateur est connecté
  },
  actions: {
    async fetchUser() {
      try {
        const res = await axios.get('http://localhost:5000/api/current_user', { withCredentials: true });
        this.user = res.data || null;
      } catch (err) {
        this.user = null;
      }
    },
    logout() {
      window.location.href = 'http://localhost:5000/auth/logout';
    }
  }
});
