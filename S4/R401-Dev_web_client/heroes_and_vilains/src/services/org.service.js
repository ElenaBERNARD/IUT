import { getRequest, postRequest, patchRequest } from "@/services/axios.service"

async function getAllOrganisationsFromAPI() {
    return getRequest('/orgs/get', 'getAllOrganisations')
}

async function createOrganisationFromAPI(data) {
    return postRequest('/orgs/create', data, 'createOrganisation')
}

async function addTeamFromAPI(idTeam, secret) {
    let data = {
        idTeam: idTeam
    }
    return patchRequest('/orgs/addteam', data, 'addTeam', { "params": { "org-secret": secret } })
}

async function removeTeamFromAPI(idTeam, secret) {
    let data = {
        idTeam: idTeam
    }
    return patchRequest('/orgs/removeteam', data, 'removeTeam', { "params": { "org-secret": secret } })
}

async function getOrganisationByIdAPI(id, secret) {
    return getRequest('/orgs/getbyid/' + id, 'getOrganisationById', { "params": { "org-secret": secret } })
}


async function getAllOrganisationsService() {
    let answer = await getAllOrganisationsFromAPI()
    return answer
}

async function createOrganisationService(data) {
    let answer = await createOrganisationFromAPI(data)
    return answer
}

async function addTeamService(idTeam, secret) {
    let answer = await addTeamFromAPI(idTeam, secret)
    return answer
}

async function removeTeamService(idTeam, secret) {
    let answer = await removeTeamFromAPI(idTeam, secret)
    return answer
}

async function getOrganisationByIdService(id, secret) {
    let answer = await getOrganisationByIdAPI(id, secret)
    return answer
}

/* NB sur le nommage:
  Ajouter Service à la fin de chaque fonction permet ensuite de les identifier
  facilement lorsqu'elles sont importées dans d'autres modules.
  Cela évite également de donner le même nom à une fonction qui serait définit dans ces modules,
  notamment dans le store.
 */
export {
    getAllOrganisationsService,
    createOrganisationService,
    addTeamService,
    removeTeamService,
    getOrganisationByIdService,
}