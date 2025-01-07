import { items, shopusers, transactions } from './data'
import { v4 as uuidv4 } from 'uuid'
import { bankaccounts } from './data'
import bcrypt from 'bcryptjs'
/* controllers: les fonctions ci-dessous doivent mimer ce que renvoie l'API en fonction des requêtes possibles.

  Dans certains cas, ces fonctions vont avoir des paramètres afin de filtrer les données qui se trouvent dans data.js
  Ces paramètres sont généralement les mêmes qu'ils faudrait envoyer à l'API, mais pas forcément.

  Exemple 1 : se loguer auprès de la boutique
 */

// ####################
// SHOP FUNCTIONS BELOW
// ####################

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
  return { error: 0, data: items }
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
      if (shopusers[i]["basket"] == null) {
        shopusers[i]["basket"] = []
      }
      return { error: 0, status: 200, data: shopusers[i]["basket"] }
    }
  }
  return { error: 1, status: 404, data: 'no users found' }
}

function addOrderByUserId(data) {
  let id = data.user_id
  let order = { "items": data.items }
  if (id == null || id == "") {
    return { error: 1, status: 404, data: 'ID is null' }
  }
  let total = 0
  for (let i = 0; i < data.items.length; i++) {
    let item = data.items[i]
    // On cherche si l'item a une promotion
    let reduction = 0
    for (let j = 0; j < item.item.promotion.length; j++) {
      // On cherche la promotion la promotion la plus importante par rapport au nombre d'items achetés
      if (item.item.promotion[j].amount <= item.amount) {
        reduction = item.item.promotion[j].discount
      }
    }
    // On ajoute le prix de l'item au total en fonction de la réduction
    total += (item.item.price * item.amount) * (1 - reduction / 100)
  }
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
  return { error: 1, status: 404, data: 'no users found' }
}

function payOrder(data) {
  let user_id = data.user_id
  let order_id = data.order_id
  let transaction_id = data.transaction_id
  if (user_id == null || user_id == "") {
    return
  }
  if (order_id == null || order_id == "") {
    return
  }
  if (transaction_id == null || transaction_id == "") {
    return
  }

  // Recherche de l'utilateur
  for (let i = 0; i < shopusers.length; i++) {
    // Si l'utilisateur est trouvé
    if (shopusers[i]["_id"] === user_id) {
      // Recherche de la commande
      for (let j = 0; j < shopusers[i]["orders"].length; j++) {
        // Si la commande est trouvée
        if (shopusers[i]["orders"][j]["uuid"] === order_id) {
          // Si la commande est en attente de paiement
          if (shopusers[i]["orders"][j]["status"] === "waiting_payment") {
            // Recherche de la transaction
            for (let k = 0; k < transactions.length; k++) {
              // Si la transaction est trouvée
              if (transactions[k]["uuid"] === transaction_id) {
                // Si le montant en negatif de la transaction correspond au montant de la commande
                // et que la destination de la transaction est le compte de la boutique
                if (-transactions[k]["amount"] === shopusers[i]["orders"][j]["total"] && transactions[k]["destination"] === '65d721c44399ae9c8321832c') {
                  shopusers[i]["orders"][j]["status"] = "finalized"
                  return { error: 0, status: 200, data: shopusers[i]["orders"][j] }
                }
                // Si le montant de la transaction ne correspond pas au montant de la commande
                else {
                  return { error: 1, status: 404, data: 'Le montant ou le destinataire de la transaction ne correspond pas a la valeur attendu' }
                }
              }
            }
            // Si la transaction n'est pas trouvée
            return { error: 1, status: 404, data: 'Aucune transaction ne correspond a l\'id de transaction' }
          }
          // Si la commande n'est pas en attente de paiement
          else if (shopusers[i]["orders"][j]["status"] === "cancelled") {
            return { error: 1, status: 404, data: 'Cette commande a ete annulee' }
          }
          else if (shopusers[i]["orders"][j]["status"] === "finalized") {
            return { error: 1, status: 404, data: 'Cette commande a deja ete payee' }
          }
          else {
            return { error: 1, status: 404, data: 'Cette commande n\'attend pas de payement' }
          }
        }
      }
      // Si la commande n'est pas trouvée
      return { error: 1, status: 404, data: 'Aucune commande ne correspond a l\'id de commande' }
    }
  }
  // Si l'utilisateur n'est pas trouvé
  return { error: 1, status: 404, data: 'Aucun utilisateur ne correspond a l\'id d\'utilisateur' }
}

function getOrdersByUserId(data) {
  let user_id = data.user_id
  if (user_id == null || user_id == "") {
    return
  }
  for (let i = 0; i < shopusers.length; i++) {
    if (shopusers[i]["_id"] === user_id) {
      return { error: 0, status: 200, data: shopusers[i]["orders"] }
    }
  }
  return { error: 1, status: 404, data: 'no users found' }

}

function cancelOrderById(data) {
  let user_id = data.user_id
  let order_id = data.order_id
  if (user_id == null || user_id == "") {
    return
  }
  if (order_id == null || order_id == "") {
    return
  }
  for (let i = 0; i < shopusers.length; i++) {
    if (shopusers[i]["_id"] === user_id) {
      for (let j = 0; j < shopusers[i]["orders"].length; j++) {
        if (shopusers[i]["orders"][j]["uuid"] === order_id) {
          shopusers[i]["orders"][j]["status"] = "cancelled"
          return { error: 0, status: 200, data: shopusers[i]["orders"][j] }
        }
      }
    }
  }
  return { error: 1, status: 404, data: 'no order found' }
}

// ####################
// BANK FUNCTIONS BELOW
// ####################

function getAccount(data) {
  let number = data.number
  if (number == null || number == "")
    return
  let id = getAccountId(number)
  if (id.error === 1) {
    return { error: 1, status: 404, data: 'Error in recovering account id' }
  }
  return getAccountById({ account_id: id.data })
}

function getAccountById(data) {
  let account_id = data.account_id
  if (account_id == null || account_id == "")
    return
  for (let i = 0; i < bankaccounts.length; i++) {
    if (bankaccounts[i]["_id"] === account_id) {
      return { error: 0, status: 200, data: bankaccounts[i] }
    }
  }
  return { error: 1, status: 404, data: 'No matching accounts found' }
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

function getTransactions(data) {
  let account_id = data.account_id
  if (account_id == null || account_id == "")
    return
  let account_transactions = []
  for (let i = 0; i < transactions.length; i++) {
    if (transactions[i]["account"] === account_id) {
      account_transactions.push(transactions[i])
    }
  }
  if (account_transactions.length > 0) {
    return { error: 0, status: 200, data: account_transactions }
  }
  return { error: 1, status: 404, data: 'No matching transactions found' }

}

function getTransactionsByNumber(data) {
  let number = data.number
  if (number == null, number == "") {
    return
  }
  let account_id = getAccountId(number)
  if (account_id.error === 1) {
    return { error: 1, status: 404, data: 'Error in recovering account id' }
  }
  return getTransactions({ account_id: account_id.data })
}

function createWithdraw(data) {
  let account_id = data.idAccount
  let amount = data.amount
  if (account_id == null || account_id == "") {
    return { error: 1, status: 404, data: 'No account id provided' }
  }
  if (amount == null || amount == "") {
    return { error: 1, status: 404, data: 'No amount provided' }
  }
  let account = getAccountById({ account_id: account_id })
  if (account.error === 1) {
    return { error: 1, status: 404, data: 'Error in recovering account' }
  }

  if (account.data.amount < amount) {
    let transaction = { '_id': account_id, 'amount': -amount, 'account': account_id, 'date': { $date: new Date() }, 'uuid': uuidv4() }
    transactions.push(transaction)
    account.amount -= amount
    return { error: 0, status: 200, data: { uuid: transaction.uuid, amount: account.amount } }
  }
  return { error: 1, status: 404, data: 'Not enough money on the account' }
}

function createPayment(data) {
  let payer_id = data.idAccount
  let amount = data.amount
  let destNumber = data.destNumber
  if (payer_id == null || payer_id == "") {
    return { error: 1, status: 404, data: 'No account id provided' }
  }
  if (amount == null || amount == "") {
    return { error: 1, status: 404, data: 'No amount provided' }
  }
  // On récupère le compte de l'utilisateur payant
  let payer_account = getAccountById({ account_id: payer_id })
  if (payer_account.error === 1) {
    return { error: 1, status: 404, data: 'Error in recovering account' }
  }
  // On récupère l'id et le compte du destinataire
  let dest_id = getAccountId(destNumber)
  if (dest_id.error === 1) {
    return { error: 1, status: 404, data: 'Error in recovering destination account id' }
  }
  let dest_account = getAccountById({ account_id: dest_id.data })
  if (dest_account.error === 1) {
    return { error: 1, status: 404, data: 'Error in recovering destination account' }
  }

  // Si le montant est supérieur à la somme sur le compte du payant
  if (payer_account.data.amount < amount) {
    let date = new Date()
    // Transaction du payant : montant négatif, compte du payant, compte du destinataire, date, uuid
    let payer_transaction = { '_id': payer_id, 'amount': -amount, 'account': payer_id, 'destination': dest_id, 'date': { $date: date }, 'uuid': uuidv4() }
    // Transaction du destinataire : montant positif, compte du destinataire, date, uuid
    let dest_transaction = { '_id': dest_id, 'amount': amount, 'account': dest_id, 'date': { $date: date }, 'uuid': uuidv4() }
    // On ajoute les transactions
    transactions.push(payer_transaction)
    transactions.push(dest_transaction)
    // On met à jour les montants des comptes
    payer_account.amount -= amount
    dest_account.amount += amount
    // On retourne l'uuid de la transaction et le montant restant sur le compte du payant
    return { error: 0, status: 200, data: { uuid: payer_transaction.uuid, amount: payer_account.amount } }
  }
  return { error: 1, status: 404, data: 'Not enough money on the account' }
}

function validateOperation(data) {
  let currentAccount = data.currentAccount
  let amount = data.amount
  let isRecipient = data.isRecipient

  if (!amount) {
    alert("Erreur : Le montant doit être renseigné.");
    return { error: 1, status: 404, data: 'Le montant doit être renseigné.' };
  }

  if (amount <= 0) {
    alert("Erreur : Le montant doit être supérieur à 0.");
    return { error: 1, status: 404, data: 'Le montant doit être supérieur à 0.' };
  }

  let transaction = {
    '_id': currentAccount._id,
    'amount': -amount,
    'account': currentAccount._id,
    'date': { $date: new Date() },
    'uuid': uuidv4()
  }
  if (isRecipient) {
    // Si le destinataire est renseigné
    let recipient_number = data.recipient
    if (!recipient_number) {
      alert("Erreur : Le destinataire doit être renseigné.");
      return { error: 1, status: 404, data: 'Le destinataire doit être renseigné.' };
    }

    // On récupère le compte du destinataire
    let recipient = null;
    for (let i = 0; i < bankaccounts.length; i++) {
      if (bankaccounts[i].number === recipient_number) {
        recipient = bankaccounts[i];
        break;
      }
    }

    if (!recipient) {
      alert("Erreur : Le destinataire n'existe pas.");
      return { error: 1, status: 404, data: 'Le destinataire n\'existe pas.' };
    }

    // On ajout l'argent sur le compte du destinataire (si il existe)
    recipient.amount += amount;
    transaction['destination'] = recipient._id;
  }
  // On retire l'argent du compte du payeur
  currentAccount.amount -= amount;

  // On ajoute la transaction
  transactions.push(transaction);

  return { error: 0, status: 200, data: transaction.uuid };
}

export default {
  // SHOP FUNCTIONS
  shopLogin,
  getAllViruses,
  getBasketById,
  updateBasketById,
  addOrderByUserId,
  payOrder,
  getOrdersByUserId,
  cancelOrderById,
  // BANK FUNCTIONS
  getAccount,
  getTransactions,
  getTransactionsByNumber,
  createWithdraw,
  createPayment,
  validateOperation
}