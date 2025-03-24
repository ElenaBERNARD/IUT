import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex);

import UserService from '../services/user.service'

export default {
  namespaced: true,
  state: () => ({
    user: null, // the current user
  }),
  mutations: {
    updateUser(state, user) {
      state.user = user;
    },
  },
  actions: {
    async getUser({ commit }, login) {
      console.log('get an user by login');
      let response = null;
      try {
        response = await UserService.getUser(login);
        console.log("answer for getuser"+JSON.stringify(response))
        if (response.error === 0) {
          commit('updateUser', response.data);
        }
      } catch (err) {
        console.log("ABNORMAL CASE: ERROR while getting an user");
        return err.response; // pass the whole object from server (err+data)
      }
    },
    async register({ commit },data) {
      console.log('register a new user');
      let response = null;
      try {
        response = await UserService.registerUser(data);
        console.log("answer for registeruser "+JSON.stringify(response))
        if (response.error === 0) {
          commit('updateUser', response.data);
        }
        return response
      } catch (err) {
        console.log("ABNORMAL CASE: ERROR while registering a new user");
        return err.response; // pass the whole object from server (err+data)
      }
    },
  }
}

