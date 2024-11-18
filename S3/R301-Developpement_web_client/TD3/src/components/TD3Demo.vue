<template>
  <div>
    <h1>I am TD3Demo</h1>
    <hr>
    Title provided by my parent as a props: <h2>{{title}}</h2>
    <hr>
    <span v-if="show">This message appears if show props provided by my parent is true</span>
    <hr>

    <button @click="doReset">Reset directly props initVal => Warning in console</button>
    <span>props initVal = {{initVal}}</span>
    <hr>    
    <label for="val">Value initialized from initVal props : </label>
    <input v-model="value" id="val"><br />
    <span>variable value = {{value}}</span>
  </div>
</template>

<script>
export default {
  name: 'TD3Demo',
  props: {
    title: String,
    show: Boolean,
    initVal: Number,
  },
  data: function() {
    return {
      value: this.initVal,
    }
  },
  methods: {
    doReset() {
      this.initVal = 0;
    },
  },
}
</script>