import {getRequest} from "@/services/axios.service";
//import LocalSource from "@/datasource/controller"

async function getAllTownsFromAPI() {
  return getRequest('/towns/get', 'GETALLTOWNS')
}

/*
async function getAllTownsFromLocalSource() {
  return LocalSource.getAllTownsService()
}
*/

async function getAllTownsService() {
  let answer = await getAllTownsFromAPI()
  //let answer = await getAllTownsFromLocalSource()
  return answer
}

/* NB sur le nommage:
  Ajouter Service à la ifn de chaque fonction permet ensuite de les identifier
  facilement lorsqu'elles sont importées dans d'autres modules.
  Cela évite également de donner le même nom à une fonciton qui serait définit dans ces modules,
  notamment dans le store.
 */
export {
  getAllTownsService
}