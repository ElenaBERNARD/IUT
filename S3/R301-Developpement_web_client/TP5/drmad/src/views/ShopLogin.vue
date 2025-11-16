<template>
  <div class="login-wrapper">
    <h1>Login</h1>
    <div class="form-group">
      <label for="login">Username</label>
      <input
        id="login"
        v-model="login"
        :placeholder="'Enter your username'"
        class="form-input"
        type="text"
      />
    </div>
    <div class="form-group">
      <label for="password">Password</label>
      <input
        id="password"
        v-model="password"
        class="form-input"
        :placeholder="'Enter your password'"
        type="password"
      />
    </div>
    <button class="login-button" @click="handleLogin(login, password)">Login</button>
    <p v-if="shopUser" class="user-info">{{ shopUser }}</p>
    <div v-if="flashMessage" class="flash-message">
      {{ flashMessage }}
    </div>
  </div>
</template>

<script>

import {mapState, mapActions} from 'vuex'
import router from '../router';

export default {
  name: 'ShopLoginView',
  data: () => ({
    login: '',
    password:'',
    flashMessage: ''
  }),
  computed: {
    ...mapState('shop', ['shopUser'])
  },
  methods: {
    ...mapActions('shop', ['shopLogin']),
    async handleLogin(login, password) {
      let response = await this.shopLogin({login, password})
      if (response.error) {
        this.flashMessage = response.data
      } else {
        this.flashMessage = "Login successful"
        router.push('/shop/buy')
      }
    }
  }
}
</script>
<style scoped>
.login-wrapper {
  max-width: 400px;
  margin: 50px auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 10px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  background-color: #f9f9f9;
  transition: ease-in-out 0.1s;
}

.login-wrapper:hover {
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.3);
  transition: ease-in-out 0.1s;
}

h1 {
  text-align: center;
  margin-bottom: 20px;
  font-size: 24px;
  color: #333;
}

.form-group {
  margin-bottom: 15px;
  padding-right: 20px;
}

label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
  color: #555;
}

.form-input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 16px;
}

.form-input:focus {
  border-color: #007bff;
  outline: none;
  box-shadow: 0 0 5px rgba(0, 123, 255, 0.5);
}

.login-button {
  width: 100%;
  padding: 10px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  font-size: 16px;
  cursor: pointer;
}

.login-button:hover {
  background-color: #0056b3;
}

.user-info {
  margin-top: 20px;
  font-size: 14px;
  color: #333;
  text-align: center;
}

.flash-message {
  margin-top: 15px;
  padding: 10px;
  background-color: #ffefc4;
  color: #856404;
  border: 1px solid #ffeeba;
  border-radius: 5px;
  text-align: center;
}
</style>
