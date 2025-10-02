<template>
  <v-container>
    <v-card>
      <v-card-title>I am a view named TD2Demo2A</v-card-title>

      <v-card-text>
        <v-text-field v-model="msg" label="Type your first name (must be != toto)"></v-text-field>
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn @click="checkInput">Confirm</v-btn>
      </v-card-actions>

    </v-card>
  </v-container>
</template>

<script>

import {mapMutations} from 'vuex'

export default {
  name: 'TD2Demo2A',
  data: () =>({
    msg: ''
  }),
  methods: {
    ...mapMutations('errors',['pushError']),
    checkInput() {
      if (this.msg === 'toto') {
        this.pushError("I said: NOT TOTO !!!")
      }
      else {
        this.$router.push('checklastname')
      }
    }
  }
}
</script>

