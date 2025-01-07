<template>
    <div class="operation-wrapper">
        <h1>
            <slot name="title">Débit / Virement</slot>
        </h1>

        <div class="form-group">
            <label for="amount">Montant :</label>
            <input type="number" id="amount" v-model="amount" min="0" placeholder="Entrez le montant" />
        </div>

        <div class="form-group">
            <label>
                <input type="checkbox" v-model="isRecipient" />
                Destinataire
            </label>
        </div>

        <div v-if="isRecipient" class="form-group">
            <label for="recipient">Numero de compte du destinataire :</label>
            <input type="text" id="recipient" v-model="recipient" placeholder="Entrez le numero de compte du destinataire" />
        </div>

        <div class="form-group">
            <button @click="validateOperation({
                currentAccount: currentAccount,
                amount: amount,
                isRecipient: isRecipient,
                recipient: recipient,
            })">Valider</button>
        </div>

        <p v-if="successMessage" class="success">{{ successMessage }}</p>
    </div>
</template>

<script>
import { mapActions, mapState } from 'vuex';

export default {
    name: 'BankOperation',

    data: () => ({
        amount: null,
        isRecipient: false,
        recipient: '',
    }),
    methods: {
        ...mapActions('bank', ['validateOperation']),
    },
    computed: {
        ...mapState('bank', ['currentAccount', 'successMessage']),
    },
};
</script>

<style scoped>
.operation-wrapper {
    padding: 24px;
    background-color: #f9f9f9;
    border: 1px solid #ddd;
    border-radius: 8px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    max-width: 1200px;
    margin: 20px auto;
    transition: ease-in-out 0.1s;
}

.operation-wrapper:hover {
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.3);
    transition: ease-in-out 0.1s;
}

.form-group {
    margin-bottom: 15px;
}

input[type="number"],
input[type="text"],
button {
    width: 100%;
    padding: 10px;
    margin-top: 5px;
    border: 1px solid #ccc;
    border-radius: 5px;
}

button {
    background-color: #007bff;
    color: #fff;
    font-weight: bold;
    cursor: pointer;
}

button:hover {
    background-color: #0056b3;
}

.success {
    color: green;
    font-weight: bold;
}
</style>
