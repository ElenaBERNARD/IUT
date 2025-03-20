import { getRequest, postRequest, putRequest } from "@/services/axios.service"

async function getAllAliasesFromAPI() {
    return getRequest('/heroes/getaliases', 'getAllAliases')
}

async function createHeroFromAPI(hero) {
    return postRequest('/heroes/create', hero, 'createHero')
}

async function updateHeroFromAPI(data) {
    let hero = data.hero
    let secret = data.secret
    return putRequest('/heroes/update', hero, 'updateHero', { "params": { "org-secret": secret } })
}

async function getHeroByIdFromAPI(data) {
    let id = data.id
    let secret = data.secret
    return getRequest('/heroes/getbyid/' + id, 'getHeroById', { "params": { "org-secret": secret } })
}

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

/* NB sur le nommage:
  Ajouter Service à la fin de chaque fonction permet ensuite de les identifier
  facilement lorsqu'elles sont importées dans d'autres modules.
  Cela évite également de donner le même nom à une fonction qui serait définit dans ces modules,
  notamment dans le store.
 */
export {
    getAllAliasesService,
    createHeroService,
    updateHeroService,
    getHeroByIdService,
}