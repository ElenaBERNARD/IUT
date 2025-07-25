import { defineStore } from 'pinia';

export const useErrorStore = defineStore('error', {
    state: () => ({
        isError: false,
        errorMessage: ''
    }),
    actions: {
        setError(message) {
            this.isError = true;
            this.errorMessage = message;
        },
        clearError() {
            this.isError = false;
            this.errorMessage = '';
        }
    }
});
