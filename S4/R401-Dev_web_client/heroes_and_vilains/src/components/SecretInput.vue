<template>
    <v-container>
        <v-text-field v-model="secretPhrase" label="Input a secret phrase" @input="updateSecretPhrase"></v-text-field>
    </v-container>
</template>

<script>
import { mapMutations, mapState } from 'vuex';

export default {
    data() {
        return {
            secretPhrase: this.orgainisationSecret
        };
    },
    methods: {
        ...mapMutations('org', ['setOrganisationSecret']),
        updateSecretPhrase() {
            this.setOrganisationSecret(this.secretPhrase);
        }
    },
    computed: {
        ...mapState('org', ['orgainisationSecret'])
    },
};
</script>