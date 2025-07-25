import { defineStore } from 'pinia'
import { loginUserService, getUserInfoService, registerUserService } from '../services/auth.service.js'
import { useHeroStore } from './hero.js'
import { useErrorStore } from './error.js'

export const useAuthStore = defineStore('auth', {
  state: () => ({
    currentUser: null,
    currentPassword: null,
  }),
  actions: {
    setError(message) {
      const errorStore = useErrorStore()
      errorStore.setError(message)
    },

    // Connexion de l'utilisateur
    async login(info) {
      let result = null
      try {
        result = await loginUserService(info)
        if (result.error === 0) {
          this.currentUser = result.data.name
          return result.data
        } else {
          this.setError(result.data.data)
        }
      } catch (err) {
        this.setError('Erreur dans la connexion : ' + err)
      }
    },

    // Inscription de l'utilisateur
    async register(userInfo) {
      let result = null
      try {
        result = await registerUserService(userInfo)
        if (result.error === 0) {
          return result.data
        } else {
          this.setError(result.data.data)
        }
      } catch (err) {
        this.setError("Erreur dans l'inscription : " + err)
      }
    },

    // Déconnexion de l'utilisateur
    logout() {
      this.currentUser = null
      this.currentPassword = null
      const heroStore = useHeroStore() // Interagir avec le store hero pour supprimer le héros actuel
      heroStore.currentHero = null
    },

    // Récupérer les informations utilisateur
    async getUser() {
      let result = null
      try {
        result = await getUserInfoService(this.currentUser)
        if (result.error === 0) {
          this.currentUser = result.data.login
          this.currentPassword = result.data.password

          // Mettre à jour le héros dans le store 'hero'
          const heroStore = useHeroStore()
          heroStore.currentHero = result.data.hero

          return result.data
        } else {
          this.setError(result.data.data)
        }
      } catch (err) {
        this.setError('Erreur dans la récupération des informations utilisateur : ' + err)
      }
    },
  },
})
