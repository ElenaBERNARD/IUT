import LocalSource from "@/datasource/controller";

async function getTransactionsByNumberFromLocalSouce(data) {
  return LocalSource.getTransactionsByNumber(data)
}

async function getTransactionsByNumber(data) {
  let response = null;
  try {
    // changer la methode appelee quand cette fonctionnalite l'API est prete
    response = await getTransactionsByNumberFromLocalSouce(data)
  }
  // NB: le catch n'aura lieu que pour des requete vers l'API, s'il y a une erreur reseau
  catch(err) {
    response = {error: 1, status: 404, data: 'erreur reseau, impossible de recuperer le detail du compte'  }
  }
  return response
}

async function getAccountFromLocalSource(data) {
  return LocalSource.getAccount(data)
}

async function getAccount(data) {
  let response = null;
  try {
    // changer la methode appelee quand cette fonctionnalite l'API est prete
    response = await getAccountFromLocalSource(data)
  }
  // NB: le catch n'aura lieu que pour des requete vers l'API, s'il y a une erreur reseau
  catch(err) {
    response = {error: 1, status: 404, data: 'erreur reseau, impossible de recuperer le detail du compte'  }
  }
  return response
}

async function getTransactionsFromLocalSource(data) {
  return LocalSource.getTransactions(data)
}

async function getTransactions(data) {
  let response = null;
  try {
    // changer la methode appelee quand cette fonctionnalite l'API est prete
    response = await getTransactionsFromLocalSource(data)
  }
  // NB: le catch n'aura lieu que pour des requete vers l'API, s'il y a une erreur reseau
  catch(err) {
    response = {error: 1, status: 404, data: 'erreur reseau, impossible de recuperer le detail du compte'  }
  }
  return response
}

async function createWithdrawFromLocalSource(data) {
  return LocalSource.createWithdraw(data)
}

async function createWithdraw(data) {
  let response = null;
  try {
    // changer la methode appelee quand cette fonctionnalite l'API est prete
    response = await createWithdrawFromLocalSource(data)
  }
  // NB: le catch n'aura lieu que pour des requete vers l'API, s'il y a une erreur reseau
  catch(err) {
    response = {error: 1, status: 404, data: 'erreur reseau, impossible de recuperer le detail du compte'  }
  }
  return response
}

async function createPaymentFromLocalSource(data) {
  return LocalSource.createDeposit(data)
}

async function createPayment(data) {
  let response = null;
  try {
    // changer la methode appelee quand cette fonctionnalite l'API est prete
    response = await createPaymentFromLocalSource(data)
  }
  // NB: le catch n'aura lieu que pour des requete vers l'API, s'il y a une erreur reseau
  catch(err) {
    response = {error: 1, status: 404, data: 'erreur reseau, impossible de recuperer le detail du compte'  }
  }
  return response
}

async function validateOperationFromLocalSource(data) {
  return LocalSource.validateOperation(data)
}

async function validateOperation(data) {
  let response = null;
  try {
    // changer la methode appelee quand cette fonctionnalite l'API est prete
    response = await validateOperationFromLocalSource(data)
  }
  // NB: le catch n'aura lieu que pour des requete vers l'API, s'il y a une erreur reseau
  catch(err) {
    response = {error: 1, status: 404, data: 'erreur reseau, impossible de recuperer le detail du compte'  }
  }
  return response
}

export default {
  getTransactionsByNumber,
  getAccount,
  getTransactions,
  createWithdraw,
  createPayment,
  validateOperation
}