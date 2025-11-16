<template>
    <div>
        <h1>Account</h1>

        <span>Account number : </span><input @input="reset()" v-model="number"> <br>
        <button @click="getAccountAmount(number)">Get amount</button>
        
        <button @click="getAccountTransactions(number)">Get transactions</button>
        
        <p v-if="accountNumberError==1">
            <span v-if="accountAmount">{{ accountAmount }}</span>
            <ul v-if="accountTransactions">
                <li v-for="(accountTransaction, index) in accountTransactions" :key="index">{{ accountTransaction.amount }} on the {{ accountTransaction.date.$date }}</li>
            </ul>
        </p>
        <p v-if="accountNumberError==0">
            
        </p>
        <p v-if="accountNumberError==-1">
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
        ...mapState(['accountAmount', 'accountTransactions', 'accountNumberError']),
    },
    methods: {
        ...mapActions(['getAccountAmount', 'getAccountTransactions']),
        ...mapMutations(['updateAccountNumberError', 'reset']),
    }
}
</script>