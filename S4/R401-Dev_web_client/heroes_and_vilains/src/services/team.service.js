import { getRequest, patchRequest, postRequest } from "@/services/axios.service"

async function getAllTeamsFromAPI() {
    return getRequest('/teams/get', 'getAllTeams')
}

async function createTeamFromAPI(name) {
    return postRequest('/teams/create', name, 'createTeam')
}

async function teamAddHeroesFromAPI(data) {
    return patchRequest('/teams/addheroes', data, 'teamAddHeroes')
}

async function teamRemoveHeroesFromAPI(data) {
    return patchRequest('/teams/removeheroes', data, 'teamRemoveHeroes')
}


async function getAllTeamsService() {
    let answer = await getAllTeamsFromAPI()
    return answer
}

async function createTeamService(name) {
    let answer = await createTeamFromAPI(name)
    return answer
}

async function teamAddHeroesService(data) {
    let answer = await teamAddHeroesFromAPI(data)
    return answer
}

async function teamRemoveHeroesService(data) {
    let answer = await teamRemoveHeroesFromAPI(data)
    return answer
}

/* NB sur le nommage:
  Ajouter Service à la fin de chaque fonction permet ensuite de les identifier
  facilement lorsqu'elles sont importées dans d'autres modules.
  Cela évite également de donner le même nom à une fonction qui serait définit dans ces modules,
  notamment dans le store.
 */
export {
    getAllTeamsService,
    createTeamService,
    teamAddHeroesService,
    teamRemoveHeroesService,
}