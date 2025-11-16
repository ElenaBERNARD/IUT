import axios from 'axios'
import { useOrganisationStore } from '../stores/org.js'

const urls = {
  auth: 'https://apidemo.iut-bm.univ-fcomte.fr/authapi',
  herocorp: 'https://apidemo.iut-bm.univ-fcomte.fr/herocorp',
}

function createAxiosInstance(service = 'herocorp', useXsrf = false) {
  const baseURL = urls[service] || urls.herocorp

  const instance = axios.create({
    baseURL,
    withCredentials: true, // Permet au navigateur d'envoyer automatiquement le cookie JWT
  })

  // Intercepteur pour le token XSRF
  instance.interceptors.request.use(
    (config) => {
      if (useXsrf) {
        const xsrfToken = localStorage.getItem('xsrfToken')
        if (xsrfToken) {
          config.headers['x-xsrf-token'] = xsrfToken
        }
      }
      return config
    },
    (error) => Promise.reject(error),
  )

  // Intercepteur pour la phrase secrète si service = "herocorp"
  if (service === 'herocorp') {
    const orgStore = useOrganisationStore()
    const secretPhrase = orgStore.organisationSecret
    console.log('SecretPhrase : ' + secretPhrase)

    instance.interceptors.request.use(
      (config) => {
        if (secretPhrase) {
          config.headers['org-secret'] = secretPhrase
        }
        return config
      },
      (error) => Promise.reject(error)
    )
  }


  return instance
}

/**
 * Gestion des erreurs pour les requêtes API
 */
function handleError(serviceName, err) {
  if (err.response) {
    console.error(`ERROR in ${serviceName}:`, err.response)
    return { data: { error: 1, data: err.response.data } }
  } else if (err.request) {
    console.error(`NETWORK ERROR in ${serviceName}:`, err.request)
    return { data: { error: 1, data: "Le serveur est injoignable ou l'URL demandee n'existe pas" } }
  } else {
    console.error(`UNKNOWN ERROR in ${serviceName}`)
    return { data: { error: 1, data: 'Erreur inconnue' } }
  }
}

/**
 * Fonction generique pour les requêtes GET
 */
async function getRequest(uri, name, service = 'herocorp', useXsrf = false, config = {}) {
  let response = null
  try {
    const axiosInstance = createAxiosInstance(service, useXsrf)
    console.log(
      'URI : ' +
        uri +
        ' Name : ' +
        name +
        ' Service : ' +
        service +
        ' UseXsrf : ' +
        useXsrf +
        ' Config : ' +
        config,
    )
    response = await axiosInstance.get(uri, config)

    // Sauvegarde du token JWT si necessaire
    if (response.data?.data?.refreshToken) {
      localStorage.setItem('refreshToken', response.data.data.refreshToken)
      console.log('JWT saved : ' + response.data.data.refreshToken)
    }
  } catch (err) {
    response = handleError(name, err)
  }
  return response.data
}

/**
 * Fonction generique pour les requêtes POST
 */
async function postRequest(uri, data, name, service = 'herocorp', useXsrf = false, config = {}) {
  let response = null
  try {
    const axiosInstance = createAxiosInstance(service, useXsrf)
    console.log(
      'URI : ' +
        uri +
        ' Name : ' +
        name +
        ' Service : ' +
        service +
        ' UseXsrf : ' +
        useXsrf +
        ' Config : ' +
        config,
    )

    response = await axiosInstance.post(uri, data, config)

    console.log('Response : ' + response.data)
    console.log('Response : ' + response.data.data)
    console.log('data data keys')
    for (let key in response.data.data) {
      console.log('Key : ' + key)
      console.log('Value : ' + response.data.data[key])
    }

    // Sauvegarde du token JWT si necessaire
    if (response.data?.data?.refreshToken) {
      localStorage.setItem('refreshToken', response.data.data.refreshToken)
      console.log('JWT saved : ' + response.data.data.refreshToken)
    }

    // Sauvegarde du token XSRF si necessaire (uniquement sur auth)
    if (service === 'auth' && response.data?.data?.xsrfToken) {
      localStorage.setItem('xsrfToken', response.data.data.xsrfToken)
    }
  } catch (err) {
    response = handleError(name, err)
  }
  return response.data
}

/**
 * Fonction generique pour les requêtes PUT
 */
async function putRequest(uri, data, name, service = 'herocorp', useXsrf = false, config = {}) {
  let response = null
  try {
    const axiosInstance = createAxiosInstance(service, useXsrf)
    console.log(
      'URI : ' +
        uri +
        ' Name : ' +
        name +
        ' Service : ' +
        service +
        ' UseXsrf : ' +
        useXsrf +
        ' Config : ' +
        config,
    )

    response = await axiosInstance.put(uri, data, config)

    // Sauvegarde du token JWT si necessaire
    if (response.data?.data?.refreshToken) {
      localStorage.setItem('refreshToken', response.data.data.refreshToken)
      console.log('JWT saved : ' + response.data.data.refreshToken)
    }
  } catch (err) {
    response = handleError(name, err)
  }
  return response.data
}

/**
 * Fonction generique pour les requêtes PATCH
 */
async function patchRequest(uri, data, name, service = 'herocorp', useXsrf = false, config = {}) {
  let response = null
  try {
    const axiosInstance = createAxiosInstance(service, useXsrf)
    console.log(
      'URI : ' +
        uri +
        ' Name : ' +
        name +
        ' Service : ' +
        service +
        ' UseXsrf : ' +
        useXsrf +
        ' Config : ' +
        config,
    )

    response = await axiosInstance.patch(uri, data, config)

    // Sauvegarde du token JWT si necessaire
    if (response.data?.data?.refreshToken) {
      localStorage.setItem('refreshToken', response.data.data.refreshToken)
      console.log('JWT saved : ' + response.data.data.refreshToken)
    }
  } catch (err) {
    response = handleError(name, err)
  }
  return response.data
}

export { getRequest, postRequest, putRequest, patchRequest }
