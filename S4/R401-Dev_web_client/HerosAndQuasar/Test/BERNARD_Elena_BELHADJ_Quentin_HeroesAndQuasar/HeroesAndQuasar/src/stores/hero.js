import { defineStore } from 'pinia'
import {
  getAllAliasesService,
  createHeroService,
  updateHeroService,
  getHeroByIdService,
  updateHeroNoSecretService,
} from '../services/hero.service.js'
import { useErrorStore } from './error.js'

export const useHeroStore = defineStore('hero', {
  state: () => ({
    heroAliases: [],
    currentHero: null,
  }),
  actions: {
    setError(message) {
      const errorStore = useErrorStore()
      errorStore.setError(message)
    },

    async getHeroAliases() {
      let result = null
      try {
        result = await getAllAliasesService()
        if (result.error === 0) {
          this.heroAliases = result.data
          return result.data
        } else {
          this.setError(result.data.data)
        }
      } catch (err) {
        this.setError('Cas anormal dans getHeroAliases() : ' + err)
      }
    },

    async createHero(hero) {
      let result = null
      try {
        result = await createHeroService(hero)
        if (result.error === 0) {
          this.currentHero = result.data
          return result.data
        } else {
          this.setError(result.data.data)
        }
      } catch (err) {
        this.setError('Cas anormal dans createHero() : ' + err)
      }
    },

    async updateHero(data) {
      let result = null
      try {
        result = await updateHeroService(data)
        if (result.error === 0) {
          this.currentHero = result.data
          return result.data
        } else {
          this.setError(result.data.data)
        }
      } catch (err) {
        this.setError('Cas anormal dans updateHero() : ' + err)
      }
    },

    async updateHeroNoSecret(data) {
      let result = null
      try {
        result = await updateHeroNoSecretService(data)
        if (result.error === 0) {
          this.currentHero = result.data
          return result.data
        } else {
          this.setError(result.data.data)
        }
      } catch (err) {
        this.setError('Cas anormal dans updateHeroNoSecret() ' + err)
      }
    },

    async getHero(data) {
      let result = null
      try {
        result = await getHeroByIdService(data)
        if (result.error === 0) {
          this.currentHero = result.data[0]
          return result.data[0]
        } else {
          this.setError(result.data.data)
        }
      } catch (err) {
        this.setError('Cas anormal dans getHero() : ' + err)
      }
      return result?.data?.[0]
    },
  },
})
