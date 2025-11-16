import { defineStore } from 'pinia'
import {
  getAllOrganisationsService,
  createOrganisationService,
  addTeamService,
  removeTeamService,
  getOrganisationByIdService,
} from '../services/org.service.js'
import { useErrorStore } from './error.js'

export const useOrganisationStore = defineStore('organisation', {
  state: () => ({
    organisationSecret: '',
    organisationNames: [],
    currentOrganisation: null,
  }),
  actions: {
    // Gestion des erreurs
    setError(message) {
      const errorStore = useErrorStore()
      errorStore.setError(message)
    },

    // Actions asynchrones
    async getOrganisations() {
      let result = null
      try {
        result = await getAllOrganisationsService()
        if (result.error === 0) {
          this.organisationNames = result.data
          console.log('getOrganisations success:', result.data)
          return result.data
        } else {
          console.log("data : " + result.data)

          this.setError(result.data)
        }
      } catch (err) {
        this.setError('Cas anormal dans getOrganisations() : ' + err)
      }
    },

    async getOrganisation(id) {
      let result = null
      try {
        result = await getOrganisationByIdService(id)
        if (result.error === 0) {
          this.currentOrganisation = result.data[0]
          return result.data[0]
        } else {
          this.currentOrganisation = null
          this.setError(result.data.data)
        }
      } catch (err) {
        this.setError('Cas anormal dans getOrganisation() : ' + err)
      }
      return result?.data?.[0]
    },

    async addTeam(idTeam) {
      let result = null
      try {
        result = await addTeamService(idTeam)
        if (result.error === 0) {
          this.currentOrganisation = result.data
          return result.data
        } else {
          this.setError(result.data.data)
        }
      } catch (err) {
        this.setError('Cas anormal dans addTeam() : ' + err)
      }
    },

    async removeTeam(idTeam) {
      let result = null
      try {
        result = await removeTeamService(idTeam)
        if (result.error === 0) {
          this.currentOrganisation = result.data
          return result.data
        } else {
          this.setError(result.data.data)
        }
      } catch (err) {
        this.setError('Cas anormal dans removeTeam() : ' + err)
      }
    },

    async createOrganisation(data) {
      let result = null
      try {
        result = await createOrganisationService(data)
        if (result.error === 0) {
          this.currentOrganisation = result.data
          return result.data
        } else {
          this.setError(result.data.data)
        }
      } catch (err) {
        this.setError('Cas anormal dans createOrganisation() : ' + err)
      }
    },
  },
})
