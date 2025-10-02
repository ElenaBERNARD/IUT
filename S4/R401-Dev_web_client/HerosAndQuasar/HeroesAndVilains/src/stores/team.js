import { defineStore } from 'pinia'
import {
  getAllTeamsService,
  createTeamService,
  teamAddHeroesService,
  teamRemoveHeroesService,
} from '../services/team.service.js'
import { useErrorStore } from './error.js'

export const useTeamStore = defineStore('team', {
  state: () => ({
    teams: [],
    currentTeam: null,
  }),
  actions: {
    setError(message) {
      const errorStore = useErrorStore()
      errorStore.setError(message)
    },

    async getTeams() {
      let result = null
      try {
        result = await getAllTeamsService()
        if (result.error === 0) {
          this.teams = result.data
        } else {
          this.setError(result.data.data)
        }
      } catch (err) {
        this.setError('Cas anormal dans getTeams() : ' + err)
      }
    },

    async createTeam(name) {
      let result = null
      try {
        result = await createTeamService(name)
        if (result.error === 0) {
          this.currentTeam = result.data
        } else {
          this.setError(result.data.data)
        }
      } catch (err) {
        this.setError('Cas anormal dans createTeam() : ' + err)
      }
    },

    async teamAddHeroes(data) {
      let result = null
      try {
        result = await teamAddHeroesService(data)
        if (result.error === 0) {
          this.currentTeam = result.data
        } else {
          this.setError(result.data.data)
        }
      } catch (err) {
        this.setError('Cas anormal dans teamAddHeroes() : ' + err)
      }
    },

    async teamRemoveHeroes(data) {
      let result = null
      try {
        result = await teamRemoveHeroesService(data)
        if (result.error === 0) {
          this.currentTeam = result.data
        } else {
          this.setError(result.data.data)
        }
      } catch (err) {
        this.setError('Cas anormal dans teamRemoveHeroes() : ' + err)
      }
    },
  },
})
