<template>
    <v-app>
        <v-navigation-drawer v-model="drawer" app>
            <v-list>
                <v-list-item-group>
                    <v-list-item to="/organisations">
                        <v-list-item-icon>
                            <v-icon>mdi-domain</v-icon>
                        </v-list-item-icon>
                        <v-list-item-content>
                            <v-list-item-title>Organisation</v-list-item-title>
                        </v-list-item-content>
                    </v-list-item>

                    <v-list-item to="/teams">
                        <v-list-item-icon>
                            <v-icon>mdi-account-group</v-icon>
                        </v-list-item-icon>
                        <v-list-item-content>
                            <v-list-item-title>Teams</v-list-item-title>
                        </v-list-item-content>
                    </v-list-item>
                </v-list-item-group>
            </v-list>
        </v-navigation-drawer>


        <v-app-bar app color="primary" dark>
            <v-app-bar-nav-icon @click="drawer = !drawer"></v-app-bar-nav-icon>

            <div class="d-flex align-center">
                <v-img alt="Vuetify Logo" class="shrink mr-2" contain
                    src="https://cdn.vuetifyjs.com/images/logos/vuetify-logo-dark.png" transition="scale-transition"
                    width="40" />

                <v-img alt="Vuetify Name" class="shrink mt-1 hidden-sm-and-down" contain min-width="100"
                    src="https://cdn.vuetifyjs.com/images/logos/vuetify-name-dark.png" width="100" />
            </div>

            <v-spacer></v-spacer>

            <v-btn href="https://github.com/vuetifyjs/vuetify/releases/latest" target="_blank" text>
                <span class="mr-2">Latest Release</span>
                <v-icon>mdi-open-in-new</v-icon>
            </v-btn>
        </v-app-bar>

        <v-main>
            <router-view />
        </v-main>
    </v-app>
</template>

<script>
import { mapActions } from 'vuex';

export default {
    name: 'App',

    data() {
        return {
            drawer: false,
        };
    },

    methods: {
        ...mapActions('org', ['getOrganisations']),
        ...mapActions('hero', ['getHeroAliases']),
    },

    mounted() {
        this.getOrganisations();
        this.getHeroAliases();
    },
};
</script>
