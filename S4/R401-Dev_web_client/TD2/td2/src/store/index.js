import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

import errors from "@/store/errors";
import auth from "@/store/auth";

export default new Vuex.Store({
  modules: {
    errors,
    auth,
  }
})
