import { getAllOrganisationsService, createOrganisationService, addTeamService, removeTeamService, getOrganisationByIdService } from "@/services/org.service";

export default {
    namespaced: true,
    state: {
        organisationSecret: '',
        organisationNames: [],
        currentOrganisation: null
    },
    mutations: {
        setOrganisationSecret(state, secret) {
            state.organisationSecret = secret
        },
        setOrganisationNames(state, names) {
            state.organisationNames = names
        },
        setCurrentOrganisation(state, org) {
            state.currentOrganisation = org
        },
    },
    actions: {
        async getOrganisations({ commit }) {
            let result = null;
            try {
                result = await getAllOrganisationsService()
                if (result.error === 0) {
                    commit("setOrganisationNames", result.data)
                    return result.data
                } else {
                    console.log(result.data)
                }

            } catch (err) {
                console.log("Cas anormal dans getOrganisations()")
            }
            return result
        },
        async getOrganisation({ commit, state }, id) {
            let result = null;
            try {
                result = await getOrganisationByIdService(id, state.organisationSecret)
                if (result.error === 0) {
                    commit("setCurrentOrganisation", result.data[0])
                    return result.data[0]
                } else {
                    console.log(result.data)
                }

            } catch (err) {
                console.log("Cas anormal dans getOrganisation()")
            }
        },
        async addTeam({ commit, state }, idTeam) {
            let result = null;
            try {
                result = await addTeamService(idTeam, state.organisationSecret)
                if (result.error === 0) {
                    commit("setCurrentOrganisation", result.data)
                    return result.data
                } else {
                    console.log(result.data)
                }

            } catch (err) {
                console.log("Cas anormal dans addTeam()")
            }
        },
        async removeTeam({ commit, state }, idTeam) {
            let result = null;
            try {
                result = await removeTeamService(idTeam, state.organisationSecret)
                if (result.error === 0) {
                    commit("setCurrentOrganisation", result.data)
                    return result.data
                } else {
                    console.log(result.data)
                }

            } catch (err) {
                console.log("Cas anormal dans removeTeam()")
            }
        },
        async createOrganisation({ commit }, data) {
            let result = null;
            try {
                result = await createOrganisationService(data)
                if (result.error === 0) {
                    commit("setCurrentOrganisation", result.data)
                    return result.data
                } else {
                    console.log(result.data)
                }

            } catch (err) {
                console.log("Cas anormal dans createOrganisation()")
            }
        },
    }
}