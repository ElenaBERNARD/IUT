import { getRequest, postRequest, putRequest } from '../services/axios.service.js'

// Fonctions de base pour les requêtes API
// TP 1
async function getAllAliasesFromAPI() {
  return getRequest('/heroes/getaliases', 'getAllAliases')
}

async function createHeroFromAPI(hero) {
  return postRequest('/heroes/create', hero, 'createHero')
}

async function updateHeroFromAPI(data) {
  let hero = data.hero
  return putRequest('/heroes/update', hero, 'updateHero')
}

async function getHeroByIdFromAPI(data) {
  let id = data.id
  return getRequest('/heroes/getbyid/' + id, 'getHeroById')
}

// Fonction suplementaire pour les requêtes API
// TP 2
async function updateHeroNoSecretFromAPI(data) {
  let hero = data.hero
  return putRequest('/heroes/authupdate', hero, 'updateHeroNoSecret', 'herocorp', true)
}

// Fonctions de base pour les services
// TP 1
async function getAllAliasesService() {
  let answer = await getAllAliasesFromAPI()
  return answer
}

async function createHeroService(hero) {
  let answer = await createHeroFromAPI(hero)
  return answer
}

async function updateHeroService(data) {
  let answer = await updateHeroFromAPI(data)
  return answer
}

async function getHeroByIdService(data) {
  let answer = await getHeroByIdFromAPI(data)
  return answer
}

// Fonction suplementaire pour les services
// TP 2
async function updateHeroNoSecretService(data) {
  let answer = await updateHeroNoSecretFromAPI(data)
  return answer
}

export {
  getAllAliasesService,
  createHeroService,
  updateHeroService,
  getHeroByIdService,
  updateHeroNoSecretService,
}
