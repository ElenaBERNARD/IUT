<template>
    <v-dialog v-model="dialog" max-width="600px">
        <v-card>
            <v-card-title>Add a Hero</v-card-title>
            <v-tabs v-model="tab">
                <v-tab>Choose Existing</v-tab>
                <v-tab>Create New</v-tab>
            </v-tabs>
            <v-card-text>
                <v-tabs-items v-model="tab">
                    <v-tab-item>
                        <v-list>
                            <v-list-item v-for="hero in filteredHeroAliases" :key="hero._id">
                                <v-list-item-content>
                                    <v-list-item-title>{{ hero.publicName }}</v-list-item-title>
                                </v-list-item-content>
                                <v-list-item-action>
                                    <v-checkbox v-model="selectedHeroes" :value="hero._id"></v-checkbox>
                                </v-list-item-action>
                            </v-list-item>
                        </v-list>
                        <v-btn color="primary" :disabled="!selectedHeroes.length" @click="addHeroesToTeam">Add</v-btn>
                    </v-tab-item>

                    <v-tab-item>
                        <v-text-field label="Display Name" v-model="newHero.publicName"></v-text-field>
                        <v-text-field label="Real Name" v-model="newHero.realName"></v-text-field>

                        <v-container>
                            <v-row v-for="(power, index) in newHero.powers" :key="index">
                                <v-col cols="5">
                                    <v-text-field label="Power Name" v-model="power.name"></v-text-field>
                                </v-col>
                                <v-col cols="4">
                                    <v-select label="Type" v-model="power.type"
                                        :items="powerTypes.map((type, i) => ({ text: type, value: i + 1 }))"
                                        item-text="text" item-value="value"></v-select>
                                </v-col>
                                <v-col cols="3">
                                    <v-btn color="red" @click="removePower(index)">x</v-btn>
                                </v-col>
                            </v-row>
                        </v-container>

                        <v-btn color="green" @click="addPower">+ Add Power</v-btn>
                        <v-btn color="primary" @click="createAndAddHero">Create Hero</v-btn>
                    </v-tab-item>
                </v-tabs-items>
            </v-card-text>
            <v-card-actions>
                <v-btn color="grey" @click="closeDialog">Cancel</v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>
<script>
import { mapState, mapActions } from 'vuex';

export default {
    name: 'AddHero',
    data() {
        return {
            dialog: false,
            tab: null,
            selectedHeroes: [],
            powerTypes: ["Force", "Vitesse", "Endurance", "Magie", "Effrayant", "Furtivité", "Stupidité"],
            newHero: {
                publicName: '',
                realName: '',
                powers: [],
            },
            members: [],
            onConfirm: Function,
        };
    },
    computed: {
        ...mapState('team', ['currentTeam']),
        ...mapState('hero', ['heroAliases']),
        filteredHeroAliases() {
            return this.heroAliases.filter(hero => 
                !this.members.some(member => member === hero._id)
            );
        }
    },
    methods: {
        ...mapActions('team', ['teamAddHeroes']),
        ...mapActions('hero', ['createHero']),
        openDialog(onConfirm, members) {
            this.members = members;
            this.onConfirm = onConfirm;
            // Reset on open
            this.dialog = true;
            this.selectedHeroes = [];
            this.tab = 0;
            this.newHero = {
                publicName: '',
                realName: '',
                powers: [],
            };
        },
        closeDialog() {
            this.dialog = false;
            this.newHero = {
                publicName: '',
                realName: '',
                powers: [],
            };
        },
        confirm() {
            this.onConfirm();
            this.closeDialog();
        },
        addHeroesToTeam() {
            if (!this.currentTeam || !this.selectedHeroes.length) return;

            this.teamAddHeroes({
                idHeroes: this.selectedHeroes,
                idTeam: this.currentTeam._id
            });

            this.selectedHeroes = [];
            this.dialog = false;
        },
        addPower() {
            this.newHero.powers.push({ name: 'newPower', type: 0, level: 1 });
        },
        removePower(index) {
            this.newHero.powers.splice(index, 1);
        },
        async createAndAddHero() {
            const createdHero = await this.createHero(this.newHero);
            console.log('createdHero', createdHero);
            if (createdHero) {
                this.teamAddHeroes({
                    idHeroes: [createdHero._id],
                    idTeam: this.currentTeam._id
                });
                this.dialog = false;
            }
        },
    }
}
</script>