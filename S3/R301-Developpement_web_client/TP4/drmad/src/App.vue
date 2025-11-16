<template>
  <div id="app">
    <NavBar :items=items @menu-clicked="redirect($event)" class="navbar" />
    <router-view></router-view>
  </div>

</template>

<script>

import { mapActions } from 'vuex'
import NavBar from './components/NavBar.vue';
import router from './router';

export default {
  name: 'App',

  components: {
    NavBar,
  },
  data: () => ({
    //Data for the navbar :
    items: [
      { text: 'Viruses', color: 'red' },
      { text: 'Compte Bancaire', color: 'blue' },
      { text: 'Shop', color: 'purple' },
      { text: 'Login', color: 'green' }
    ]
  }),
  methods: {
    ...mapActions('shop', ['getAllViruses']),
    redirect(index) {
      let redirections = ["/shop/items", "/bank/account", "/shop/buy", "/shop/login"]
      router.push(redirections[index]).catch(() => { })
    }
  },
  mounted() {
    this.getAllViruses()
  }
};
</script>

<style scoped>
/* General Styles */
* {
  font-family: Arial, sans-serif;
}

body {
  background-color: #ccc;
  color: #333;
  margin: 0;
  padding: 0;
}

/* App Container */
#app {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}

h1 {
  font-size: 2rem;
  color: #007bff;
  margin-bottom: 20px;
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.1);
  border-bottom: 2px solid #0056b3;
}
</style>