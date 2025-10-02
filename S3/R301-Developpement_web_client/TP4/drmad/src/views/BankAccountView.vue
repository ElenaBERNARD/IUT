<template>
    <div>
        <h1>Account</h1>

        <span>Account number : </span><input @input="reset()" v-model="number"> <br>
        <button @click="getAccountAmount(number)">Get amount</button>

        <button @click="getAccountTransactions(number)">Get transactions</button>

        <p v-if="accountNumberError == 1">
            <span v-if="accountAmount">{{ accountAmount }}</span>
        <ul v-if="accountTransactions">
            <li v-for="(accountTransaction, index) in accountTransactions" :key="index">{{ accountTransaction.amount }}
                on the {{ accountTransaction.date.$date }}</li>
        </ul>
        </p>
        <p v-if="accountNumberError == 0">

        </p>
        <p v-if="accountNumberError == -1">
            Invalid account number
        </p>

        <button @click="reset()">Reset</button>

    </div>

</template>

<script>

import { mapState, mapActions, mapMutations } from 'vuex'

export default {
    name: 'BankAccountView',
    data: () => ({
        number: '',
    }),
    computed: {
        ...mapState('bank', ['accountAmount', 'accountTransactions', 'accountNumberError']),
    },
    methods: {
        ...mapMutations('bank', ['updateAccountNumberError', 'reset']),
        ...mapActions('bank', ['getAccountAmount', 'getAccountTransactions']),
    }
}
</script>

<style scoped>
div {
    font-family: Arial, sans-serif;
    padding: 20px;
    max-width: 600px;
    margin: 0 auto;
}

/* Title */
h1 {
    text-align: center;
    color: #4CAF50;
}

/* Form Input */
input {
    padding: 10px;
    width: calc(100% - 22px); /* Full width with padding */
    margin-bottom: 15px;
    border: 1px solid #ccc;
    border-radius: 4px;
    font-size: 16px;
}

/* Buttons */
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

/* Error Message */
p {
    font-size: 16px;
    color: #333;
}

p span {
    font-weight: bold;
}


/* Invalid Account Error */
p[v-if="accountNumberError == -1"] {
    color: red;
    font-weight: bold;
}

/* Transaction List */
ul {
    padding-left: 20px;
    margin-top: 10px;
}

li {
    list-style-type: none;
    font-size: 14px;
}

li::before {
    content: "• ";
    color: #4CAF50;
    font-size: 18px;
}
</style>