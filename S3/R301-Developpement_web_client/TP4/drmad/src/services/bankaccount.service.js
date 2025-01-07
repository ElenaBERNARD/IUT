import LocalSource from "@/datasource/controller";

async function getAccountAmountFromLocalSource(number) {
  return LocalSource.getAccountAmount(number)
}

async function getAccountAmount(number) {
  let response = null;
  try {
    // changer la methode appelee quand cette fonctionnalite l'API est prete
    response = await getAccountAmountFromLocalSource(number)
  }
  // NB: le catch n'aura lieu que pour des requete vers l'API, s'il y a une erreur reseau
  catch(err) {
    response = {error: 1, status: 404, data: 'erreur reseau, impossible de recuperer le detail du compte'  }
  }
  return response
}

async function getAccountTransactionsFromLocalSouce(number) {
  return LocalSource.getAccountTransactions(number)
}

async function getAccountTransactions(number) {
  let response = null;
  try {
    // changer la methode appelee quand cette fonctionnalite l'API est prete
    response = await getAccountTransactionsFromLocalSouce(number)
  }
  // NB: le catch n'aura lieu que pour des requete vers l'API, s'il y a une erreur reseau
  catch(err) {
    response = {error: 1, status: 404, data: 'erreur reseau, impossible de recuperer le detail du compte'  }
  }
  return response
}

export default {
  getAccountAmount,
  getAccountTransactions,
}