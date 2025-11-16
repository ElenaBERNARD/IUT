<template>
  <div id="app">
    <NavBar :items=items @menu-clicked="redirect($event)"/>
    <hr>
    <h1 style="display:flex;justify-content:center">Welcome to DrMad app</h1>
    <hr>
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
      { text: 'Viruses', color: 'red'},
      { text: 'Compte Bancaire', color: 'blue' },
      { text: 'Login', color: 'green' }
    ]
  }),
  methods: {
    ...mapActions(['getAllViruses']),
    redirect(index){
      let redirections = [ "/shop/items", "/bank/account", "/shop/login" ]
      router.push(redirections[index]).catch(()=>{})
    }
  },
  mounted() {
    this.getAllViruses()
  }
};
</script>
