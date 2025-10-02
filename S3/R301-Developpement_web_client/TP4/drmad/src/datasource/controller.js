import { items, shopusers, transactions } from './data'
import { v4 as uuidv4 } from 'uuid'
import { bankaccounts } from './data'
import bcrypt from 'bcryptjs'
/* controllers: les fonctions ci-dessous doivent mimer ce que renvoie l'API en fonction des requêtes possibles.

  Dans certains cas, ces fonctions vont avoir des paramètres afin de filtrer les données qui se trouvent dans data.js
  Ces paramètres sont généralement les mêmes qu'ils faudrait envoyer à l'API, mais pas forcément.

  Exemple 1 : se loguer auprès de la boutique
 */

function shopLogin(data) {
  if ((!data.login) || (!data.password)) return { error: 1, status: 404, data: 'aucun login/pass fourni' }
  // test du login
  let user = shopusers.find(e => e.login === data.login)
  if (!user) return { error: 1, status: 404, data: 'login/pass incorrect' }

  // test du mot de passe
  const passwordMatches = bcrypt.compareSync(data.password, user.password); // `user.password` is the stored hashed password
  if (!passwordMatches) {
    return { error: 1, status: 404, data: 'login/pass incorrect' };
  }

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
  console.log("in controller : " + items)

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
  if (id.error === 1) {
    return { error: 1, status: 404, data: 'nw' }
  }
  let AccountTransactions = [];
  for (let i = 0; i < transactions.length; i++) {
    if (transactions[i]["account"] === id["data"]) {
      AccountTransactions.push(transactions[i])
    }
  }
  console.log(id)
  console.log(AccountTransactions)
  return { error: 0, status: 200, data: AccountTransactions }
}

function updateBasketById(data) {
  let id = data.id
  let bakset = data.basket
  if (id == null || id == "") {
    return
  }
  if (id == "") {
    return
  }
  for (let i = 0; i < shopusers.length; i++) {
    if (shopusers[i]["_id"] === id) {
      shopusers[i]["basket"] = bakset
      return { error: 0, status: 200, data: shopusers[i]["basket"] }
    }
  }
  return { error: 1, status: 404, data: 'no users found' }
}

function getBasketById(id) {
  if (id == null) {
    return
  }
  if (id == "") {
    return
  }
  for (let i = 0; i < shopusers.length; i++) {
    if (shopusers[i]["_id"] === id) {
      if(shopusers[i]["basket"] == null){
        shopusers[i]["basket"] = []
      }
      return { error: 0, status: 200, data: shopusers[i]["basket"] }
    }
  }
  console.log("no users found")
  return { error: 1, status: 404, data: 'no users found' }
}

function addOrderByUserId(data) {
  let id = data.user_id
  let order = {"items": data.items}
  if(id == null || id == ""){
    console.log("id is null")
    return
  }
  let total = 0
  for(let i = 0; i < data.items.length; i++){
    let item = data.items[i]
    // On cherche si l'item a une promotion
    let reduction = 0
    for(let j = 0; j < item.item.promotion.length; j++){
      // On cherche la promotion la promotion la plus importante par rapport au nombre d'items achetés
      if(item.item.promotion[j].amount <= item.amount){
        reduction = item.item.promotion[j].discount
      } 
    }
    // On ajoute le prix de l'item au total en fonction de la réduction
    total += (item.item.price * item.amount) * (1 - reduction / 100)
  }
  console.log("total :" + total)
  order["date"] = new Date()
  order["total"] = total
  order["status"] = "waiting_payment"
  order["uuid"] = uuidv4()

  for (let i = 0; i < shopusers.length; i++) {
    if (shopusers[i]["_id"] === id) {
      shopusers[i]["orders"].push(order)
      return { error: 0, status: 200, data: order["uuid"] }
    }
  }
  console.log("no users found")
  return { error: 1, status: 404, data: 'no users found' }
}

function validateOrderByID(data) {
  let user_id = data.user_id
  let order_id = data.order_id
  if(user_id == null || user_id == ""){
    return
  }
  if(order_id == null || order_id == ""){
    return
  }
  for(let i = 0; i < shopusers.length; i++){
    if(shopusers[i]["_id"] === user_id){
      for(let j = 0; j < shopusers[i]["orders"].length; j++){
        if(shopusers[i]["orders"][j]["uuid"] === order_id){
          shopusers[i]["orders"][j]["status"] = "finalized"
          return { error: 0, status: 200, data: shopusers[i]["orders"][j] }
        }
      }
    }
  }
  console.log("no corresponding user/order found")
  return { error: 1, status: 404, data: 'no users found' }
}

function getOrdersByUserId(data){
  let user_id = data.user_id
  if(user_id == null || user_id == ""){
    return
  }
  for(let i = 0; i < shopusers.length; i++){
    if(shopusers[i]["_id"] === user_id){
      return { error: 0, status: 200, data: shopusers[i]["orders"] }
    }
  }
  console.log("no corresponding user found")
  return { error: 1, status: 404, data: 'no users found' }
  
}

function cancelOrderById(data){
  let user_id = data.user_id
  let order_id = data.order_id
  if(user_id == null || user_id == ""){
    return
  }
  if(order_id == null || order_id == ""){
    return
  }
  for(let i = 0; i < shopusers.length; i++){
    if(shopusers[i]["_id"] === user_id){
      for(let j = 0; j < shopusers[i]["orders"].length; j++){
        if(shopusers[i]["orders"][j]["uuid"] === order_id){
          shopusers[i]["orders"][j]["status"] = "cancelled"
          return { error: 0, status: 200, data: shopusers[i]["orders"][j] }
        }
      }
    }
  }
  console.log("no corresponding order found")
  return { error: 1, status: 404, data: 'no order found' }
}

export default {
  shopLogin,
  getAllViruses,
  getAccountAmount,
  getAccountTransactions,
  getBasketById,
  updateBasketById,
  addOrderByUserId,
  validateOrderByID,
  getOrdersByUserId,
  cancelOrderById
}