<template>
    <v-container>
        <v-row v-if="currentOrganisation">
            <v-col>
                <v-card>
                    <v-card-title>{{ currentOrganisation.name }}</v-card-title>
                </v-card>
            </v-col>
        </v-row>

        <v-row v-if="currentOrganisation" justify="center" class="mb-6">
            <v-col cols="auto">
                <v-btn color="primary" @click="openAddTeamDialog">
                    Add Team to Organisation
                </v-btn>
            </v-col>
        </v-row>

        <v-row v-if="currentOrganisation && currentOrganisation.teams && currentOrganisation.teams.length">
            <v-col v-for="team in currentOrganisation.teams" :key="team._id" cols="12" md="6">
                <v-card class="pa-3">
                    <v-card-title>{{ team.name || "Team name" }}</v-card-title>

                    <v-divider></v-divider>

                    <v-list>
                        <v-list-item v-for="memberId in team.members" :key="memberId">
                            <v-list-item-content>
                                <v-list-item-title>{{ getHeroName(memberId) }}</v-list-item-title>
                            </v-list-item-content>
                        </v-list-item>
                    </v-list>
                    <v-card-actions>
                        <v-btn color="primary" @click="editTeam(team._id)">
                            Edit Team
                        </v-btn>
                        <v-btn color="error" @click="deleteTeam(team._id)">
                            Delete Team
                        </v-btn>
                    </v-card-actions>

                </v-card>
            </v-col>
        </v-row>
        <DeleteDialog ref="deleteTeamRef" />
        <AddDialog ref="addTeamRef" />

    </v-container>
</template>

<script>
import DeleteDialog from '@/components/DeleteDialog';
import AddDialog from '@/components/AddDialog';
import { mapState, mapActions, mapMutations } from 'vuex';

export default {
    name: 'OrganisationView',

    data() {
        return {
            deleteDialog: false,
            addTeamDialog: false,
            selectedTeam: null,
        };
    },

    components: {
        DeleteDialog,
        AddDialog,
    },

    computed: {
        ...mapState('org', ['currentOrganisation']),
        ...mapState('hero', ['heroAliases']),
        ...mapState('team', ['teams']),

        availableTeams() {
            if (!this.currentOrganisation || !this.currentOrganisation.teams || !this.teams) {
                return [];
            }
            const organisationTeamIds = this.currentOrganisation.teams.map(team => team._id);
            return this.teams.filter(team => !organisationTeamIds.includes(team._id));
        },
    },

    methods: {
        ...mapActions('org', ['removeTeam', 'addTeam', 'getOrganisation']),
        ...mapActions('team', ['getTeams']),
        ...mapMutations('team', ['setCurrentTeam']),

        getHeroName(memberId) {
            const hero = this.heroAliases.find(hero => hero._id === memberId);
            return hero ? hero.publicName : "Unknown Hero";
        },

        editTeam(teamId) {
            if (!teamId) {
                this.$toast.error('Invalid team ID');
                return;
            }
            let team = this.currentOrganisation.teams.find(team => team._id === teamId);
            if (!team) {
                this.$toast.error('Team not found');
                return;
            }
            this.setCurrentTeam(team);
            this.$router.push(`/team`);
        },

        deleteTeam(teamId) {
            this.$refs.deleteTeamRef.openDialog(() => { this.removeTeam(teamId); });
        },

        openAddTeamDialog() {
            this.$refs.addTeamRef.openDialog((team) => {
                this.addTeam(team);
                
            }, 
            this.availableTeams,
            'Add Team to Organisation');
        },
    },

    mounted() {
        this.getTeams();
    },

    watch: {
        currentOrganisation() {
            this.getOrganisation(this.currentOrganisation._id);
        },
    }
};
</script>