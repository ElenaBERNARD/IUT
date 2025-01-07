import { items, shopusers, transactions } from './data'
import { v4 as uuidv4 } from 'uuid'
import { bankaccounts } from './data'
/* controllers: les fonctions ci-dessous doivent mimer ce que renvoie l'API en fonction des requêtes possibles.

  Dans certains cas, ces fonctions vont avoir des paramètres afin de filtrer les données qui se trouvent dans data.js
  Ces paramètres sont généralement les mêmes qu'ils faudrait envoyer à l'API, mais pas forcément.

  Exemple 1 : se loguer auprès de la boutique
 */

function shopLogin(data) {
  if ((!data.login) || (!data.password)) return { error: 1, status: 404, data: 'aucun login/pass fourni' }
  // pour simplifier : test uniquement le login
  let user = shopusers.find(e => e.login === data.login)
  if (!user) return { error: 1, status: 404, data: 'login/pass incorrect' }
  // générer un uuid de session pour l'utilisateur si non existant
  if (!user.session) {
    user.session = uuidv4()
  }
  // retourne uniquement les champs nécessaires
  let u = {
    _id: user._id,
    name: user.name,
    login: user.login,
    email: user.email,
    session: user.session
  }
  return { error: 0, status: 200, data: u }
}

function getAllViruses() {
  return { error: 0, data: items }
}

function getAccountAmount(number) {
  if (number == null)
    return
  if (number == "")
    return
  for (let i = 0; i < bankaccounts.length; i++) {
    if (bankaccounts[i]["number"] === number) {
      return { error: 0, status: 200, data: bankaccounts[i]["amount"] }
    }
  }
  return { error: 1, status: 404, data: 'nw' }
}

function getAccountId(number) {
  if (number == "")
    return
  for (let i = 0; i < bankaccounts.length; i++) {
    if (bankaccounts[i]["number"] === number) {
      return { error: 0, status: 200, data: bankaccounts[i]["_id"] }
    }
  }
  return { error: 1, status: 404, data: 'nw' }
}

function getAccountTransactions(number) {
  if (number == null) {
    return
  }
  if (number == "") {
    return
  }
  let id = getAccountId(number)
  if(id.error === 1){
    return { error: 1, status: 404, data: 'nw' }
  }
  let AccountTransactions = [];
  for (let i = 0; i < transactions.length; i++){
    if (transactions[i]["account"] === id["data"]) {
      AccountTransactions.push(transactions[i])
    }
  }
  console.log(id)
  console.log(AccountTransactions)
  return { error: 0, status: 200, data: AccountTransactions }
}

export default {
  shopLogin,
  getAllViruses,
  getAccountAmount,
  getAccountTransactions
}