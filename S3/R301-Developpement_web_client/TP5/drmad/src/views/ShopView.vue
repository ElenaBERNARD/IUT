<template>
    <div>
        <NavBar :links="links" class="navbar">
            <template v-slot:button-Buy="{ label }">
                <span style="color: blue;">{{ label }}</span>
            </template>
            <template v-slot:button-Pay="{ label }">
                <span style="color: green;">{{ label }}</span>
            </template>
            <template v-slot:button-Orders="{ label }">
                <span style="color: purple;">{{ label }}</span>
            </template>
            <template v-slot:button-Logout="{ label }">
                <span style="color: red;">{{ label }}</span>
            </template>
            <template v-slot:button-Login="{ label }">
                <span style="color: blue;">{{ label }}</span>
            </template>
            <template v-slot:button-Register="{ label }">
                <span style="color: green;">{{ label }}</span>
            </template>
        </NavBar>
        <h1>Boutique</h1>
        <router-view></router-view>
    </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
// NavBar is now a global component, so we don't need to import it here

export default {
    name: 'ShopView',

    methods: {
        ...mapActions('shop', ['shopLogout', 'getAllViruses']),
    },
    computed: {
        ...mapState('shop', ['shopUser']),
        //Data for the navbar :
        links() {
            return this.shopUser
                ? [
                    { label: 'Items', to: '/shop/items' },
                    { label: 'Buy', to: '/shop/buy' },
                    { label: 'Pay', to: '/shop/pay/' },
                    { label: 'Orders', to: '/shop/orders' },
                    { label: 'Logout', to: '/shop/logout' },
                ]
                : [
                    { label: 'Login', to: '/shop/login' },
                    { label: 'Register', to: '/shop/register' },
                ];
        },
    },
    mounted() {
        this.getAllViruses()
    }
};
</script>