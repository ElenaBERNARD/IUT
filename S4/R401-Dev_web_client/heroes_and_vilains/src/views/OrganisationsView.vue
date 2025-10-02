<template>
    <v-container>
        <SecretInput />

        <v-row class="mb-6" justify="center">
            <v-col cols="auto">
                <v-btn color="primary" @click="openCreateOrganisationDialog">
                    Create Organisation
                </v-btn>
            </v-col>
        </v-row>

        <v-row>
            <v-col v-for="org in organisationNames" :key="org._id" cols="12" md="4">
                <v-card class="pa-3" @click="fetchOrganisation(org._id)">
                    <v-card-title>{{ org.name }}</v-card-title>

                    <v-alert v-if="errorOrgId === org._id" type="error" dense text>
                        {{ errorMessage }}
                    </v-alert>
                </v-card>
            </v-col>
        </v-row>
        <CreateOrganisationDialog ref="createOrganisationRef" />
    </v-container>
</template>

<script>
import SecretInput from '@/components/SecretInput.vue';
import CreateOrganisationDialog from '@/components/CreateOrganisationDialog.vue';
import { mapState, mapActions } from 'vuex';

export default {
    name: 'OrganisationView',

    components: {
        SecretInput,
        CreateOrganisationDialog,
    },

    data() {
        return {
            selectedOrganisationId: null,
            errorOrgId: null,
            errorMessage: "",
        };
    },

    computed: {
        ...mapState('org', ['organisationSecret', 'organisationNames', 'currentOrganisation']),
    },

    methods: {
        ...mapActions('org', ['getOrganisation', 'getOrganisations', 'createOrganisation']),

        async fetchOrganisation(orgId) {
            this.errorOrgId = null;
            this.errorMessage = "";

            const response = await this.getOrganisation(orgId, this.organisationSecret);

            if (response && response.error) {
                this.errorOrgId = orgId;
                this.errorMessage = "Impossible d'accéder à l'organisation";
            }
            else {
                this.$router.push('/organisation');
            }
        },

        async openCreateOrganisationDialog() {
            this.$refs.createOrganisationRef.openDialog(
                (name, secret) =>{
                    this.handleOrganisationCreation(name, secret);
                }
            );
        },

        async handleOrganisationCreation(name, secret) {
            const data = {
                name: name,
                secret: secret,
            };
            await this.createOrganisation(data);
            this.getOrganisations();
        },
    },

    mounted() {
        this.getOrganisations();
    },
};
</script>