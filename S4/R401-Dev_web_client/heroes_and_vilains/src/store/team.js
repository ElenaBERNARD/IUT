import { getAllTeamsService, createTeamService, teamAddHeroesService, teamRemoveHeroesService } from '@/services/team.service';

export default {
    namespaced: true,
    state: {
        teams: [],
        currentTeam: null,
    },
    mutations: {
        setTeams(state, teams) {
            state.teams = teams
        },
        setCurrentTeam(state, team) {
            state.currentTeam = team
        },
    },
    actions: {
        async getTeams({ commit }) {
            let result = null;
            try {
                result = await getAllTeamsService()
                if (result.error === 0) {
                    commit("setTeams", result.data)
                } else {
                    console.log(result.data)
                }

            } catch (err) {
                console.log("Cas anormal dans getTeams()")
            }
        },
        async createTeam({ commit }, name) {
            let result = null;
            try {
                result = await createTeamService(name)
                if (result.error === 0) {
                    commit("setCurrentTeam", result.data)
                } else {
                    console.log(result.data)
                }

            } catch (err) {
                console.log("Cas anormal dans createTeam()")
            }
        },
        async teamAddHeroes({ commit }, data) {
            let result = null;
            try {
                result = await teamAddHeroesService(data)
                if (result.error === 0) {
                    commit("setCurrentTeam", result.data)
                } else {
                    console.log(result.data)
                }

            } catch (err) {
                console.log("Cas anormal dans teamAddHeroes()")
            }
        },
        async teamRemoveHeroes({ commit }, data) {
            let result = null;
            try {
                result = await teamRemoveHeroesService(data)
                if (result.error === 0) {
                    commit("setCurrentTeam", result.data)
                } else {
                    console.log(result.data)
                }

            } catch (err) {
                console.log("Cas anormal dans teamRemoveHeroes()")
            }
        }
    }
}