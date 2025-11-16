import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default {
  namespaced: true,
  state: {
    auth: false,
  },
  mutations: {
    setLogin(state) {
      state.auth = true
    },
    setLogout(state) {
      state.auth = false
    },
  },
  actions: {
    async login({commit}, credentials) {
      /*
      Normally, this check should be done remotely.
      Simulate the answer delay with setTimeout()
       */
      await new Promise( resolve => {
        setTimeout(resolve, 1000)
      })

      if ((credentials.login === 'toto') && (credentials.passwd === 'azer')) {
        commit('setLogin')
      }
      else {
        commit('setLogout')
        commit('pushError','Invalid credentials')
      }
    },
    logout({commit}) {
      commit('setLogout')
    }

  },
}
