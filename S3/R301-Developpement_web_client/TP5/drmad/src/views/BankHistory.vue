<template>
    <div class="history-wrapper">
        <h1>
            <slot name="title">Operations passées</slot>
        </h1>
        <label for="periodFilter">Filtrer par période : </label>
        <input type="checkbox" v-model="periodFilter" id="periodFilter">
        <div v-if="periodFilter">
            <label for="start">Du :</label>
            <input type="date" id="start" v-model="start" />
            <label for="end">Au :</label>
            <input type="date" id="end" v-model="end" />
        </div>
        <DataTable :items="filteredTransactions" :headers="headers" :itemCheck="true" :itemButton="true"
            :tableButton="true" @itemClicked="showTransactionDetails" @tableClicked="showSelectedTransactions">
            <template #item-button>
                Details
            </template>
            <template #table-button>
                Voir
            </template>
        </DataTable>
    </div>
</template>

<script>
import { mapState, mapActions } from 'vuex';
import DataTable from '@/components/DataTable.vue';

export default {
    name: 'BankHistory',
    components: {
        DataTable,
    },
    data: () => ({
        periodFilter: false,
        start: '',
        end: '',
        headers: [
            { label: "Montant", name: "amount" },
            { label: "Date", name: "date" },
            { label: "Source/Destinataire", name: "direction" },
        ],
    }),
    computed: {
        ...mapState('bank', ['currentAccount', 'accountTransactions']),
        filteredTransactions() {
            if (!Array.isArray(this.formatedTransactions)) {
                return [];
            }

            if (this.periodFilter) {
                return this.formatedTransactions.filter(transaction => {
                    return (!this.start || transaction.date >= this.start) &&
                           (!this.end || transaction.date <= this.end);
                });
            }

            return this.formatedTransactions;
        },
        formatedTransactions() {
            if (!Array.isArray(this.accountTransactions)) {
                return [];
            }
            return this.accountTransactions.map(transaction => ({
                ...transaction,
                date: transaction.date.$date,
                direction: transaction.amount < 0 ? 'S (source)' : 'D (destinataire)',
            }));
        },
        account_id() {
            return this.currentAccount?._id || '';
        },
    },
    methods: {
        ...mapActions('bank', ['getTransactions']),
        showTransactionDetails(item) {
            navigator.clipboard.writeText(item.uuid)
            alert(`Transaction UUID: ${item.uuid} \n(l'UUID a été copié dans le presse-papier)`);
        },
        showSelectedTransactions(selectedItems) {
            alert(`Selected Transactions UUIDs: ${selectedItems.map(item => item.uuid).join(', ')}`);
        },
    },
    mounted() {
        this.getTransactions({ account_id: this.account_id });
    },
};
</script>
<style scoped>
.history-wrapper {
    padding: 24px;
    background-color: #f9f9f9;
    border: 1px solid #ddd;
    border-radius: 8px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    max-width: 1200px;
    margin: 20px auto;
    transition: ease-in-out 0.1s;
}

.history-wrapper:hover {
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.3);
    transition: ease-in-out 0.1s;
}

.history-wrapper h1 {
    font-size: 24px;
    font-weight: bold;
    margin-bottom: 16px;
    color: #333;
}

label {
    font-size: 14px;
    color: #555;
    margin-right: 8px;
}

input[type="checkbox"] {
    width: 16px;
    height: 16px;
    margin-left: 8px;
    cursor: pointer;
    accent-color: #007bff;
}

input[type="date"] {
    padding: 6px 12px;
    border: 1px solid #ddd;
    border-radius: 4px;
    font-size: 14px;
    color: #333;
    margin-right: 8px;
    margin-bottom: 12px;
    transition: border-color 0.3s;
}

input[type="date"]:focus {
    border-color: #007bff;
    outline: none;
}

.history-wrapper .period-filters {
    margin: 16px 0;
    padding: 16px;
    background-color: #fff;
    border: 1px solid #ddd;
    border-radius: 8px;
}

button {
    padding: 8px 16px;
    background-color: #007bff;
    color: #fff;
    border: none;
    border-radius: 4px;
    font-size: 14px;
    cursor: pointer;
    transition: background-color 0.3s;
}

button:hover {
    background-color: #0056b3;
}

button:focus {
    outline: 2px solid #80bdff;
    outline-offset: 2px;
}

button:disabled {
    background-color: #ccc;
    cursor: not-allowed;
}
</style>

