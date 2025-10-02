import {getRequest} from "@/services/axios.service";
//import LocalSource from "@/datasource/controller"

async function getAllCharactersFromAPI() {
  return getRequest('/persos/get', 'GETALLPERSOS')
}

/*
async function getAllCharactersFromLocalSource() {
  return LocalSource.getAllCharactersService()
}
*/

async function getAllCharactersService() {
  let answer = await getAllCharactersFromAPI()
  //let answer = await getAllCharactersFromLocalSource()
  return answer
}

/* NB sur le nommage:
  Ajouter Service à la ifn de chaque fonction permet ensuite de les identifier
  facilement lorsqu'elles sont importées dans d'autres modules.
  Cela évite également de donner le même nom à une fonciton qui serait définit dans ces modules,
  notamment dans le store.
 */
export {
  getAllCharactersService
}