import BankService from '../services/bankaccount.service';

export default {
  namespaced: true,
  state: () => ({
    currentAccount: null,
    accountTransactions: [],
    accountNumberError: 0,
    successMessage: "",
  }),
  mutations: {
    updateAccount(state, account) {
      state.currentAccount = account;
    },
    updateAccountTransactions(state, transactions) {
      state.accountTransactions = transactions;
    },
    updateAccountNumberError(state, status) {
      state.accountNumberError = status;
    },
    updateSuccessMessage(state, message) {
      state.successMessage = message;
    },
    reset(state) {
      state.currentAccount = null;
      state.accountTransactions = [];
      state.accountNumberError = 0;
    },
  },
  actions: {
    async getAccount({ commit }, number) {
      const response = await BankService.getAccount({ number: number });
      if (response.error === 0) {
        commit('updateAccountNumberError', 1);
        commit('updateAccount', response.data);
      } else {
        commit('updateAccountNumberError', -1);
        console.error(response.data);
      }
    },
    async getTransactionsByNumber({ commit }, number) {
      let data = { number: number };
      const response = await BankService.getTransactionsByNumber(data);

      if (response.error === 0) {
        commit('updateAccountNumberError', 1);
        commit('updateAccountTransactions', response.data);
      } else {
        commit('updateAccountNumberError', -1);
        console.error(response.data);
      }
    },
    async getTransactions({ commit }, data) {
      const response = await BankService.getTransactions(data);
      if (response.error === 0) {
        commit('updateAccountTransactions', response.data);
      } else {
        commit('updateAccountNumberError', -1);
        console.error(response.data);
      }
    },
    async createWithdraw({ commit }, data) {
      const response = await BankService.createWithdraw(data);
      if (response.error === 0) {
        commit('updateAccount', response.data);
      } else {
        console.error(response.data);
      }
    },
    async createPayment({ commit }, data) {
      const response = await BankService.createPayment(data);
      if (response.error === 0) {
        commit('updateAccount', response.data);
      } else {
        console.error(response.data);
      }
    },
    async validateOperation({ commit }, data) {
      const response = await BankService.validateOperation(data);
      if (response.error === 0) {
        let message = "L'opération est validée avec le n° : " + response.data + ". Vous pouvez la retrouver dans l'historique";
        commit('updateSuccessMessage', message);
        setTimeout(() => {
          commit('updateSuccessMessage', "");
        }, 5000);
      } else {
        console.error(response.data);
      }
    },
    async bankLogout({ commit }) {
      try {
        commit('reset'); // Add mutation to reset bank-related data
      } catch (error) {
        console.error('Error in bankLogout:', error);
      }
    },
  }
}
