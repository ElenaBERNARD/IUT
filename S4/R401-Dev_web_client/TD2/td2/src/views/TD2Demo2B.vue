<template>
  <v-container>
    <v-card>
      <v-card-title>I am a view named TD2Demo2B</v-card-title>

      <v-card-text>
        <v-checkbox v-model="checked" label="check the box to agree"></v-checkbox>
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
  name: 'TD2Demo2B',
  data: () =>({
    checked: false ,
  }),
  methods: {
    ...mapMutations('errors',['pushError']),
    checkInput() {
      if (!this.checked) {
        this.pushError("I said: check the box !!!")
      }
      else {
        this.$router.push('/')
      }
    }
  }
}
</script>

