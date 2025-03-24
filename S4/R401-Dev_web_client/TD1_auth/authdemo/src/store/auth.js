import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex);

import AuthService from '../services/auth.service'

/* When the app. is closed or when browser is stopped, the local storage is "saved" in the user's local config.
   Thus, the userdata item can be retrieved when the app./browser is started again.
    But during dev., in some cases, it may lead to a messy behavior that prevents user to correctly log in.
    In such a case, just uncomment the next line and restart the dev. server.
 */
localStorage.removeItem('userdata');

const userdata = JSON.parse(localStorage.getItem('userdata'));
// by default, we consider that an existing userdata item means that user is logged
// BUT his jwt token can be expired
const initialState = userdata
  ? { login: true , user: userdata }
  : { login: false , user: null };

export default {
  namespaced: true,
  state: () => ({
    authState: initialState,
  }),
  getters: {
    isLoggedIn(state) {
      return state.authState.login
    }
  },
  mutations: {
    loginSuccess(state, user) {
      state.authState.login = true;
      state.authState.user = user;
      localStorage.setItem('userdata', JSON.stringify(user));
    },
    loginFailure(state) {
      state.authState.login = false;
      state.authState.user = null;
    },
    logout(state) {
      state.authState.login = false;
      state.authState.user = null;
      localStorage.removeItem('userdata');
    },
    refresh(state, data) {
      state.authState.user.refreshToken = data.refreshToken;
      // replace all the content of the storage
      localStorage.setItem('userdata', JSON.stringify(state.authState.user));
    }
  },
  actions: {
    async login({ commit }, user) {

      try {

        let response = await AuthService.loginService(user);
        if (response.error === 0) {
          //console.log("SUCCESS login, user = "+JSON.stringify(response.data));
          commit('loginSuccess', response.data);
          console.log(localStorage)
        }
        else {
          commit('loginFailure');
        }
        return response;
      }
      catch(err) {
        console.log("ABNORMAL CASE : ERROR while waiting for AuthService.login()");
        commit('loginFailure');
        return err.response; // pass the whole object from server (err+data)
      }
    },
    async logout({ commit }) {
      commit('logout');
    },
  }
}

