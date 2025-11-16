import BankService from '../services/bankaccount.service';

export default {
  namespaced : true,
  state: () => ({
    accountAmount: 0,
    accountTransactions: [],
    accountNumberError: 0,
  }),
  mutations: {
    updateAccountAmount(state, amount) {
      state.accountAmount = amount;
    },
    updateAccountTransactions(state, transactions) {
      state.accountTransactions = transactions;
    },
    updateAccountNumberError(state, status) {
      state.accountNumberError = status;
    },
    reset(state) {
      state.accountAmount = 0;
      state.accountTransactions = [];
      state.accountNumberError = 0;
    },
  },
  actions: {
    async getAccountAmount({ commit }, number) {
      const response = await BankService.getAccountAmount(number);
      if (response.error === 0) {
        commit('updateAccountNumberError', 1);
        commit('updateAccountAmount', response.data);
      } else {
        commit('updateAccountNumberError', -1);
        console.error(response.data);
      }
    },
    async getAccountTransactions({ commit }, number) {
      const response = await BankService.getAccountTransactions(number);
      if (response.error === 0) {
        commit('updateAccountNumberError', 1);
        commit('updateAccountTransactions', response.data);
      } else {
        commit('updateAccountNumberError', -1);
        console.error(response.data);
      }
    },
  },
}
