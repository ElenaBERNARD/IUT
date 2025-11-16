<template>
    <v-container>
        <v-row class="mb-6" justify="center">
            <v-col cols="auto">
                <v-btn color="primary" @click="openCreateTeamDialog">
                    Create Team
                </v-btn>
            </v-col>
        </v-row>

        <v-row>
            <v-col v-for="team in teams" :key="team._id" cols="12" md="4">
                <v-card class="pa-3">
                    <v-card-title>{{ team.name }}</v-card-title>

                    <v-alert v-if="errorTeamId === team._id" type="error" dense text>
                        {{ errorMessage }}
                    </v-alert>
                </v-card>
            </v-col>
        </v-row>
        <CreateTeamDialog ref="createTeamRef" />
    </v-container>
</template>

<script>
import { mapState, mapActions, mapMutations } from 'vuex';
import CreateTeamDialog from '@/components/CreateTeamDialog.vue';

export default {
    name: 'TeamsView',

    components: {
        CreateTeamDialog,
    },

    data() {
        return {
            errorTeamId: null,
            errorMessage: "",
        };
    },

    computed: {
        ...mapState('team', ['teams']),
    },

    methods: {
        ...mapActions('team', ['getTeams', 'createTeam']),
        ...mapMutations('team', ['setCurrentTeam']),

        async openCreateTeamDialog() {
            this.$refs.createTeamRef.openDialog(
                (name) =>{
                    this.handleTeamCreation(name);
                }
            );
        },

        async handleTeamCreation(name) {
            const data = {
                name: name,
            };
            await this.createTeam(data);
            this.getTeams();
        },
    },

    mounted() {
        this.getTeams();
    },
};
</script>
