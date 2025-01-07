<template>
    <div class="bank-account-wrapper">
        <h1>Compte</h1>
        <span>Numero de compte : </span><input v-model="number"> <br>
        <button @click="login(number)">Valider</button>
        <p v-if="accountNumberError == -1">
            Numero de compte invalide
        </p>
    </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import router from '../router'

export default {
    name: 'BankAccountView',
    data: () => ({
        number: '',
    }),
    computed: {
        ...mapState('bank', ['currentAccount', 'accountNumberError']),
    },
    methods: {
        ...mapActions('bank', ['getAccount']),
        async login(number) {
            await this.getAccount(number)
            if (this.currentAccount)
                router.push('/bank/amount')
        }
    }
}
</script>

<style scoped>
div {
    padding: 20px;
    max-width: 600px;
    margin: 0 auto;
}

h1 {
    text-align: center;
    color: #4CAF50;
}

input {
    padding: 10px;
    width: calc(100% - 22px);
    margin-bottom: 15px;
    border: 1px solid #ccc;
    border-radius: 4px;
    font-size: 16px;
}

button {
    padding: 10px 15px;
    margin: 10px 0;
    background-color: #4CAF50;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 16px;
}

button:hover {
    background-color: #45a049;
}

p {
    color: red;
    font-weight: bold;
}
</style>