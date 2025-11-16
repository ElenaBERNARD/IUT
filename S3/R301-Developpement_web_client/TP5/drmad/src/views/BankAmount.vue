<template>
    <div class="bank-amount">
        <slot name="account-amount">
            <span class="amount">
                {{ currentAccount.amount }}
            </span>
        </slot>
    </div>
</template>

<script>
import { mapState } from 'vuex';

export default {
    name: 'BankAmount',

    computed: {
        ...mapState('bank', ['currentAccount']),
    },
};
</script>

<style scoped>
.bank-amount {
    padding: 24px;
    background-color: #f9f9f9;
    border: 1px solid #ddd;
    border-radius: 8px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    max-width: 1200px;
    margin: 20px auto;
    transition: ease-in-out 0.1s;
}

.bank-amount:hover {
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.3);
    transition: ease-in-out 0.1s;
}

.amount {
  color: #1890ff;
  font-weight: bold;
}
</style>
