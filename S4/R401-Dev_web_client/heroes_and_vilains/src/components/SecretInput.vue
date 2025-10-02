<template>
    <v-container>
        <v-text-field
            v-model="secretPhrase"
            :type="showSecret ? 'text' : 'password'"
            label="Input a secret phrase"
            @input="updateSecretPhrase"
        >
            <template v-slot:append>
                <v-btn icon @click="toggleShowSecret">
                    <v-icon>{{ showSecret ? 'mdi-eye-off' : 'mdi-eye' }}</v-icon>
                </v-btn>
            </template>
        </v-text-field>
    </v-container>
</template>

<script>
import { mapMutations, mapState } from 'vuex';

export default {
    data() {
        return {
            secretPhrase: '',
            showSecret: false,
        };
    },
    computed: {
        ...mapState('org', ['organisationSecret']),
    },
    methods: {
        ...mapMutations('org', ['setOrganisationSecret']),
        updateSecretPhrase() {
            this.setOrganisationSecret(this.secretPhrase);
        },
        toggleShowSecret() {
            this.showSecret = !this.showSecret;
        },
    },
    watch: {
        organisationSecret: {
            immediate: true,
            handler(newValue) {
                if (newValue) {
                    this.secretPhrase = newValue;
                }
            },
        },
    },
};
</script>
