<template>
    <div class="bank-wrapper">
        <NavBar :links="links" class="navbar">
            <template v-slot:button-Login="{ label }">
                <span style="color: green; font-weight: bold;">{{ label }}</span>
            </template>
            <template v-slot:button-Logout="{ label }">
                <span style="color: red; font-weight: bold;">{{ label }}</span>
            </template>
        </NavBar>
        <VerticalMenu :items="menu" class="vertical-menu">
            <template v-slot:menu-title="{ label }">
                <div class="menu-title">{{ label }}</div>
            </template>
            <template v-slot:menu-link="{ label }">
                <button class="menu-button" :disabled="!currentAccount">{{ label }}</button>
            </template>
        </VerticalMenu>
        <div class="content">
            <h1>Banque</h1>
            <label>Show ID's</label>
            <input type="checkbox" :showIds="showIds" @change="showIds = !showIds">
            <p v-if="showIds" class="account-id" >FRDRMAD578901234567890-0000666</p>
            <p v-if="showIds" class="account-id">FRSHOP4578901234567890-0000999</p>
            <router-view>
                <template v-slot:account-amount>
                    Account Balance:
                    <input v-if="0 > currentAccount.amount" type="text" :value="currentAccount.amount" style="color: red; font-weight: bold;" disabled>
                    <input v-else type="text" :value="currentAccount.amount" style="color: green; font-weight: bold;" disabled>
                </template>
            </router-view>
        </div>
    </div>
</template>

<script>
import { mapState } from 'vuex'
import VerticalMenu from "@/components/VerticalMenu.vue";


export default {
    name: 'BankView',

    components: {
        VerticalMenu,
    },

    data: () => ({
        menu: [
            { type: "title", label: "Opérations" },
            { type: "link", label: "Solde", to: "/bank/amount" },
            { type: "link", label: "Débit/Virement", to: "/bank/operation" },
            { type: "title", label: "États" },
            { type: "link", label: "Historique", to: "/bank/history" }
        ],
        showIds: false,
    }),

    computed: {
        ...mapState('bank', ['currentAccount']),
        links() {
            return this.currentAccount
                ? [
                    { label: 'Logout', to: '/bank/logout' },
                ]
                : [
                    { label: 'Login', to: '/bank/account' },
                ];
        },
    },
};
</script>

<style scoped>
.bank-wrapper {
    display: grid;
    grid-template-rows: auto 1fr;
    grid-template-columns: 20% 1fr;
    grid-template-areas:
        "navbar navbar"
        "menu content";
    height: 90vh;
}

.navbar {
    grid-area: navbar;
    background-color: #f8f9fa;
    padding: 10px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.vertical-menu {
    grid-area: menu;
}

.content {
    grid-area: content;
    padding: 20px;
    background-color: #fff;
    overflow-y: auto;
}
.ids-list {
    padding: 10px;
    background-color: #fff;
    border: 1px solid #ddd;
    border-radius: 5px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}
.account-id {
    margin: 0;
    font-family: monospace;
    color: #555;
}
</style>
