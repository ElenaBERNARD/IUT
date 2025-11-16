import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex);

import auth from './auth'
import user from './user'
import errors from "./errors";

export default new Vuex.Store({
  modules: {
    auth,
    user,
    errors,
  }
})
