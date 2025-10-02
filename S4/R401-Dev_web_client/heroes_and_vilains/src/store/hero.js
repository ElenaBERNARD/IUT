import { getAllAliasesService, createHeroService, updateHeroService, getHeroByIdService } from '@/services/hero.service';

export default {
    namespaced: true,
    state: {
        heroAliases: [],
        currentHero: null,
    },
    getters: {
    },
    mutations: {
        setHeroAliases(state, aliases) {
            state.heroAliases = aliases
        },
        setCurrentHero(state, hero) {
            state.currentHero = hero[0]
        },
    },
    actions: {
        async getHeroAliases({ commit }) {
            let result = null;
            try {
                result = await getAllAliasesService()
                if (result.error === 0) {
                    commit("setHeroAliases", result.data)
                    return result.data
                } else {
                    console.log(result.data)
                }

            } catch (err) {
                console.log("Cas anormal dans getHeroAliases()")
            }
        },
        async createHero({ commit }, hero) {
            let result = null;
            try {
                result = await createHeroService(hero)
                if (result.error === 0) {
                    commit("setCurrentHero", result.data)
                    return result.data
                } else {
                    console.log(result.data)
                }
            } catch (err) {
                console.log("Cas anormal dans createHero()")
            }
        },
        async updateHero({ commit }, data) {
            let result = null;
            try {
                result = await updateHeroService(data)
                if (result.error === 0) {
                    commit("setCurrentHero", result.data)
                    return result.data
                } else {
                    console.log(result.data)
                }

            } catch (err) {
                console.log("Cas anormal dans updateHero()")
            }
        },
        async getHero({ commit }, data) {
            let result = null;
            try {
                result = await getHeroByIdService(data)
                if (result.error === 0) {
                    commit("setCurrentHero", result.data[0])
                    return result.data[0]
                } else {
                    console.log(result.data)
                }

            } catch (err) {
                console.log("Cas anormal dans getHero()")
            }
            return result.data[0]
        },
    }
}