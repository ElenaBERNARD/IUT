import Vue from 'vue'
import Vuex from 'vuex'

import hero from './hero'
import team from './team'
import org from './org'

Vue.use(Vuex)

export default new Vuex.Store({
  modules: { 
    hero, 
    team, 
    org 
  }
})