<template>
  <div>
    <h1>I am App.vue</h1>
    <p>the value of my variable val is {{val}}</p>
    <button @click="toggleShowMsg">toggle the value [T/F] of showMsg</button>
    <hr>
    <TD3Demo title="my first props" :show="showMsg" :initVal="val" />
  </div>
</template>

<script>
import TD3Demo from './components/TD3Demo.vue'

export default {
  name: 'App',
  components: {
    TD3Demo
  },
  data: () => {
    return {
      val: 10,
      showMsg: true,
    }
  },
  methods: {
    toggleShowMsg() {
      this.showMsg = !this.showMsg
    },
  }
}
</script>
