import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import NavBar from '@/components/NavBar.vue';

Vue.component('NavBar', NavBar);

Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
