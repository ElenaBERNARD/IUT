<template>
  <v-container>
    <v-card>
      <v-card-title>The login is toto and password is azer</v-card-title>

      <v-card-text>
        <v-text-field v-model="name" label="login"></v-text-field>
        <v-text-field v-model="passwd" label="password"></v-text-field>
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn :loading="loading" @click="checkInput">Ok</v-btn>
      </v-card-actions>

    </v-card>
  </v-container>
</template>

<script>

import {mapActions, mapState} from 'vuex'

export default {
  name: 'LoginForm',
  data: () =>({
    name: '',
    passwd: '',
    loading: false,
  }),
  computed: {
    ...mapState('auth',['auth'])
  },
  methods: {
    ...mapActions('auth',['login']),
    async checkInput() {
      this.loading = true
      await this.login({login: this.name, passwd: this.passwd})
      this.loading = false
      if (this.auth) {
        this.$router.push('/')
      }
    }
  }
}
</script>

