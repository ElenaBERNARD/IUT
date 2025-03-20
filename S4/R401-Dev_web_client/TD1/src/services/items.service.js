import {getRequest, patchRequest} from "@/services/axios.service"
//import LocalSource from "@/datasource/controller"

async function getAllItemsFromAPI() {
  return getRequest('/items/get', 'GETALLITEMS')
}

/*
async function getAllItemsFromLocalSource() {
 return LocalSource.getAllItemsService()
}
 */

async function getItemByIdFromAPI(id) {
  return getRequest('/items/getbyid/'+id, 'GETITEMBYID')
}

async function getItemByNameFromAPI(name) {
  return getRequest('/items/getbyname/'+name, 'GETITEMBYNAME')
}

async function updateItemPriceFromAPI(id, price) {
  let data = {
    id: id,
    price: price,
  }
  return patchRequest('/items/updateprice', data, 'UPDATEITEMPRICE')
}

async function getAllItemsService() {
  let answer = await getAllItemsFromAPI()
  //let answer = await getAllItemsFromLocalSource()
  return answer
}

async function getItemByIdService(id) {

  let answer = await getItemByIdFromAPI(id)
  return answer
}

async function getItemByNameService(name) {

  let answer = await getItemByNameFromAPI(name)
  return answer
}

async function updateItemPriceService(id, price) {

  let answer = await updateItemPriceFromAPI(id, price)
  return answer
}

/* NB sur le nommage:
  Ajouter Service à la ifn de chaque fonction permet ensuite de les identifier
  facilement lorsqu'elles sont importées dans d'autres modules.
  Cela évite également de donner le même nom à une fonciton qui serait définit dans ces modules,
  notamment dans le store.
 */
export {
  getAllItemsService,
  getItemByIdService,
  getItemByNameService,
  updateItemPriceService,
}