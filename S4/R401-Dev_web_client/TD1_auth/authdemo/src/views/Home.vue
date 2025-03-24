<template>
  <v-container>
    <p v-if="authState.login === false">
      Welcome to authentication demonstration app.
      You must login.
    </p>
    <p v-else>
      You are logged. You can list users by opening left drawer
    </p>

  </v-container>
</template>

<script>

import {mapState} from 'vuex'

export default {
  name: 'Home',
  computed: {
    ...mapState('auth',['authState']),
  },
}
</script>
