<template>
    <v-container>
        <v-row v-if="currentTeam">
            <v-col>
                <v-card>
                    <v-card-title>{{ currentTeam.name }}</v-card-title>
                </v-card>
            </v-col>

        </v-row>
        <v-row v-if="currentTeam" justify="center" class="mb-6">
            <v-btn color="primary" @click="openAddDialog">
                Add Hero
            </v-btn>
        </v-row>

        <v-row v-if="currentTeam && members.length" justify="center">
            <v-col v-for="member in members" :key="member._id" cols="12" md="6">
                <v-card class="pa-3">
                    <v-card-title>{{ member.publicName }}</v-card-title>
                    <v-card-subtitle v-if="member.realName">({{ member.realName }})</v-card-subtitle>
                    <v-divider></v-divider>
                    <v-card-text>
                        <p v-if="member.powers.length"><strong>Powers:</strong></p>
                        <v-list v-if="member.powers.length">
                            <v-list-item v-for="power in member.powers" :key="power.name">
                                <v-list-item-content>
                                    <v-list-item-title>
                                        {{ power.name }} ({{ powerTypes[power.type - 1] }}): Level {{ power.level }}
                                    </v-list-item-title>
                                </v-list-item-content>
                            </v-list-item>
                        </v-list>
                        <p v-else>No powers</p>
                    </v-card-text>
                    <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn color="error" @click="openDeleteDialog(member)">Remove</v-btn>
                    </v-card-actions>
                </v-card>
            </v-col>
        </v-row>
        <DeleteDialog ref="deleteHeroRef" />
        <AddHero ref="addHeroRef" />
    </v-container>
</template>

<script>
import DeleteDialog from '@/components/DeleteDialog.vue';
import AddHero from '@/components/AddHero.vue';
import { mapState, mapActions } from 'vuex';

export default {
    name: 'TeamView',

    data() {
        return {
            members: [],
            powerTypes: ["Force", "Vitesse", "Endurance", "Magie", "Effrayant", "Furtivité", "Stupidité"],
            dialog: false,
            deleteDialog: false,
            heroToRemove: null,
            tab: null,
            selectedHeroes: [],
            newHero: {
                publicName: '',
                realName: '',
                powers: [],
            },
        };
    },

    components: {
        DeleteDialog,
        AddHero,
    },

    computed: {
        ...mapState('team', ['currentTeam']),
        ...mapState('org', ['organisationSecret']),
    },

    methods: {
        ...mapActions('hero', ['getHero', 'addHero']),
        ...mapActions('team', ['teamRemoveHeroes']),

        async fetchTeamMembers() {
            if (!this.currentTeam || !this.currentTeam.members) return;

            const members = [];
            for (const memberId of this.currentTeam.members) {
                const hero = await this.getHero({ id: memberId, secret: this.organisationSecret });
                if (hero) {
                    members.push(hero);
                }
            }
            this.members = members;
        },
        openAddDialog() {
            this.$refs.addHeroRef.openDialog(() => {
                this.fetchTeamMembers();
            }, this.currentTeam.members
            );
        },
        openDeleteDialog(hero) {
            this.$refs.deleteHeroRef.openDialog(
                () => {
                    this.teamRemoveHeroes({ idHeroes: [hero._id], idTeam: this.currentTeam._id });
                },
            );
        },
    },

    watch: {
        currentTeam() {
            this.fetchTeamMembers();
        },
    },

    mounted() {
        if (this.currentTeam) {
            this.fetchTeamMembers();
        }
    },
};
</script>