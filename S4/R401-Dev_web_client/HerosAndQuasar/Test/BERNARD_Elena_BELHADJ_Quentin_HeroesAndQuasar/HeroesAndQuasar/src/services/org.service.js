import { getRequest, postRequest, patchRequest } from '../services/axios.service.js'

async function getAllOrganisationsFromAPI() {
  console.log('getAllOrganisationsFromAPI')
  return getRequest('/orgs/get', 'getAllOrganisations')
}

async function createOrganisationFromAPI(data) {
  return postRequest('/orgs/create', data, 'createOrganisation')
}

async function addTeamFromAPI(idTeam) {
  let data = {
    idTeam: idTeam,
  }
  return patchRequest('/orgs/addteam', data, 'addTeam')
}

async function removeTeamFromAPI(idTeam) {
  let data = {
    idTeam: idTeam,
  }
  return patchRequest('/orgs/removeteam', data, 'removeTeam')
}

async function getOrganisationByIdAPI(id) {
  return getRequest('/orgs/getbyid/' + id, 'getOrganisationById')
}

async function getAllOrganisationsService() {
  let answer = await getAllOrganisationsFromAPI()
  console.log('getAllOrganisationsService : ' + answer)
  return answer
}

async function createOrganisationService(data) {
  let answer = await createOrganisationFromAPI(data)
  return answer
}

async function addTeamService(idTeam) {
  let answer = await addTeamFromAPI(idTeam)
  return answer
}

async function removeTeamService(idTeam) {
  let answer = await removeTeamFromAPI(idTeam)
  return answer
}

async function getOrganisationByIdService(id) {
  let answer = await getOrganisationByIdAPI(id)
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
