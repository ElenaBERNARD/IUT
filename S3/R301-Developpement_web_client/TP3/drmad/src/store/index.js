import Vue from 'vue'
import Vuex from 'vuex'


Vue.use(Vuex)

import ShopService from '../services/shop.service'
import BankService from '../services/bankaccount.service'

export default new Vuex.Store({
  // state = les données centralisées
  state: () => ({
    viruses: [],
    shopUser: null,
    accountAmount: 0,
    accountTransactions: [],
    accountNumberError: 0
  }),
  // mutations = fonctions synchrones pour mettre à jour le state (!!! interdit de modifier directement le state)
  mutations: {
    updateViruses(state, viruses) {
      state.viruses = viruses
    },
    updateShopUser(state, user) {
      state.shopUser = user
    },
    updateAccountAmount(state, amount) {
      state.accountAmount = amount
    },
    updateAccountTransactions(state, transactions){
      state.accountTransactions  = transactions
    },
    updateAccountNumberError(state, status){
      state.accountNumberError = status
    },
    reset(state){
      state.accountAmount = 0
      state.accountTransactions = []
      state.accountNumberError = 0
    }
  },
  // actions = fonctions asynchrone pour mettre à jour le state, en faisant appel aux mutations, via la fonction commit()
  actions: {
    async shopLogin({commit}, data) {
      console.log('login');
      let response = await ShopService.shopLogin(data)
      if (response.error === 0) {
        commit('updateShopUser', response.data)
      }
      else {
        console.log(response.data)
      }
    },
    async getAccountAmount({commit}, number) {
      let response = await BankService.getAccountAmount(number)
      if (response.error === 0) {
        commit('updateAccountNumberError', 1)
        commit('updateAccountAmount', response.data)
      }
      else {
        commit('updateAccountNumberError', -1)
        console.log(response.data)
      }
    },
    async getAccountTransactions({commit}, number) {
      let response = await BankService.getAccountTransactions(number)
      if (response.error === 0) {
        commit('updateAccountNumberError', 1)
        commit('updateAccountTransactions', response.data)
      }
      else {
        commit('updateAccountNumberError', -1)
        console.log(response.data)
      }
    },
    async getAllViruses({commit}) {
      console.log('récupération des viruses');
      let response = await ShopService.getAllViruses()
      if (response.error === 0) {
        commit('updateViruses', response.data)
      }
      else {
        console.log(response.data)
      }
    }
  }
})
