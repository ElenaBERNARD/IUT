import LocalSource from "@/datasource/controller";

async function shopLoginFromLocalSource(data) {
  // rÃ©cupÃ©ration auprÃ¨s de la source locale
  return LocalSource.shopLogin(data)
}
/*
async function shopLoginFromAPI(data) {
  // a ecrire quand l'API est fonctionnelle
  return {}
}
 */

async function getAllVirusesFromLocalSource() {
  // recuperation aupres de la source locale
  return LocalSource.getAllViruses()
}

/*
async function getAllVirusesFromAPI() {
  // a ecrire quand l'API est fonctionnelle
  return {}
}
*/

async function getBasketByIdFromLocalSource(id) {
  // recuperation aupres de la source locale
  return LocalSource.getBasketById(id)
}

async function updateBasketByIdFromLocalSource(data) {
  // recuperation aupres de la source locale
  return LocalSource.updateBasketById(data)
}

async function addOrderByUserIdFromLocalSource(data) {
  // recuperation aupres de la source locale
  return LocalSource.addOrderByUserId(data)
}

async function payOrderFromLocalSource(data) {
  // recuperation aupres de la source locale
  return LocalSource.payOrder(data)
}

async function getOrdersByUserIdFromLocalSource(data) {
  // recuperation aupres de la source locale
  return LocalSource.getOrdersByUserId(data)
}

async function shopLogin(data) {
  let response = null;
  try {
    // changer la mÃ©thode appelÃ©e quand cette fonctionnalitÃ© l'API est prÃªte
    response = await shopLoginFromLocalSource(data)
  }
  // NB: le catch n'aura lieu que pour des requÃªte vers l'API, s'il y a une erreur rÃ©seau
  catch (err) {
    response = { error: 1, status: 404, data: 'erreur rÃ©seau, impossible de se loguer' }
  }
  return response
}


async function getAllViruses() {
  let response = null;
  try {
    // changer la méthode appelee quand cette fonctionnalite de l'API est prete
    response = await getAllVirusesFromLocalSource()
  }
  // NB: le catch n'aura lieu que pour des requete vers l'API, s'il y a une erreur reseau
  catch (err) {
    response = { error: 1, status: 404, data: 'erreur rÃ©seau, impossible de rÃ©cupÃ©rer la liste des viruses' }
  }
  return response
}

async function getBasketById(id) {
  let response = null;
  try {
    // changer la méthode appelee quand cette fonctionnalite de l'API est prete
    response = await getBasketByIdFromLocalSource(id)
  }
  // NB: le catch n'aura lieu que pour des requete vers l'API, s'il y a une erreur reseau
  catch (err) {
    response = { error: 1, status: 404, data: 'erreur reseau, impossible de rÃ©cupÃ©rer le panier' }
  }
  return response
}

async function updateBasketById(data) {
  let response = null;
  try {
    // changer la méthode appelee quand cette fonctionnalite de l'API est prete
    response = await updateBasketByIdFromLocalSource(data)
  }
  // NB: le catch n'aura lieu que pour des requete vers l'API, s'il y a une erreur reseau
  catch (err) {
    response = { error: 1, status: 404, data: 'erreur reseau, impossible de rÃ©cupÃ©rer le panier' }
  }
  return response
}

async function cancelOrderByIdFromLocalSource(data) {
  // recuperation aupres de la source locale
  return LocalSource.cancelOrderById(data)
}




async function addOrderByUserId(data) {
  let response = null;
  try {
    // changer la méthode appelee quand cette fonctionnalite de l'API est prete
    response = await addOrderByUserIdFromLocalSource(data)

  }
  // NB: le catch n'aura lieu que pour des requete vers l'API, s'il y a une erreur reseau
  catch (err) {
    response = { error: 1, status: 404, data: 'erreur reseau, impossible de recuperer l\'uuid' }
  }
  return response
}

async function payOrder(data) {
  let response = null;
  try {
    // changer la méthode appelee quand cette fonctionnalite de l'API est prete
    response = await payOrderFromLocalSource(data)
  }
  // NB: le catch n'aura lieu que pour des requete vers l'API, s'il y a une erreur reseau
  catch (err) {
    response = { error: 1, status: 404, data: 'erreur reseau, impossible de recuperer la commande' }
  }
  return response  
}

async function getOrdersByUserId(data) {
  let response = null;
  try {
    // changer la méthode appelee quand cette fonctionnalite de l'API est prete
    response = await getOrdersByUserIdFromLocalSource(data)
  }
  // NB: le catch n'aura lieu que pour des requete vers l'API, s'il y a une erreur reseau
  catch (err) {
    response = { error: 1, status: 404, data: 'erreur reseau, impossible de recuperer les commandes' }
  }
  return response
}

async function cancelOrderById(data) {
  let response = null;
  try {
    // changer la méthode appelee quand cette fonctionnalite de l'API est prete
    response = await cancelOrderByIdFromLocalSource(data)
  }
  // NB: le catch n'aura lieu que pour des requete vers l'API, s'il y a une erreur reseau
  catch (err) {
    response = { error: 1, status: 404, data: 'erreur reseau, impossible de recuperer la commande' }
  }
  return response
}

export default {
  shopLogin,
  getAllViruses,
  getBasketById,
  updateBasketById,
  addOrderByUserId,
  payOrder,
  getOrdersByUserId,
  cancelOrderById
}