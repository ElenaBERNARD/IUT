<template>
    <v-container>
        <SecretInput />

        <v-row>
            <v-col>
                <v-card>
                    <v-card-title>Secret</v-card-title>
                </v-card>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
import SecretInput from '@/components/SecretInput.vue';
import { mapState } from 'vuex';

export default {
    name: 'HomeView',

    components: {
        SecretInput,
    },
    computed: {
        ...mapState('org', ['organisationSecret']),
    },
}
</script>
