import ShopService from '../services/shop.service';

export default ({
  namespaced: true,
  state: () => ({
    viruses: [],
    shopUser: null,
    basket: [],
  }),
  mutations: {
    updateViruses(state, viruses) {
      state.viruses = viruses;
    },
    updateShopUser(state, user) {
      state.shopUser = user;
    },
    updateBasket(state, basket) {
      state.basket = basket;
    }
  },
  actions: {
    // Connexion d'un utilisateur
    async shopLogin({ commit }, data) {
      let response = await ShopService.shopLogin(data)
      if (response.error === 0) {
        commit('updateShopUser', response.data)
      }
      else {
        console.log(response.data)
      }
      return response
    },

    // Récupère tous les virus
    async getAllViruses({ commit }) {
      let response = await ShopService.getAllViruses()
      if (response.error === 0) {
        commit('updateViruses', response.data)
      }
      else {
        console.log(response.data)
      }
    },

    // Retourne le virus par son nom)
    getViruseByName(name) {
      console.log('récupération du virus : ' + name);
      let viruses = this.state.viruses
      for (let i = 0; i < viruses.length; i++) {
        if (viruses[i].name === name) {
          return viruses[i]
        }
      }
      return null
    },

    // Set le bastket du shopUser courant, appelé au chargement de la page
    async setBasket({ state, commit }) {
      if (!state.shopUser) {
        console.log('shopUser is not defined');
        return;
      }
      let response = await ShopService.getBasketById(state.shopUser._id);
      if (response?.error === 0) {
        commit('updateBasket', response.data);
      } else {
        console.log(response?.data || 'Error fetching basket');
      }
    },

    // Ajoute un item au panier ou incremente la quantite si l'item est deja present
    async addItemToBasket({ state, commit }, data) {
      // Verifier que l'amount soit valide
      let amount = data.amount
      if (amount < 1) {
        console.log('Invalid amount');
        return;
      }
      // Variables locales
      let basket = [...state.basket]
      let virus = data.virus
      let maxAmount = virus.stock

      // Verifier que le stock ne soit pas epuise
      if(maxAmount === 0) {
        console.log('Stock epuise')
        alert('Stock epuise')
        return;
      }

      // Parcourrir le panier pour voir si l'item est deja present
      for (let i = 0; i < basket.length; i++) {
        if (basket[i].item.name === virus.name) {
          basket[i].amount += amount
          // Empeche d'ajouter plus d'items que le stock ne le permet
          if (basket[i].amount > maxAmount) {
            basket[i].amount = maxAmount
            alert('Stock maximal atteint')
          }

          // Mise a jour du panier
          let response = await ShopService.updateBasketById({ id: state.shopUser._id, basket })
          if (response?.error === 0) {
            commit('updateBasket', response.data)
          } else {
            console.log(response?.data || 'Error updating basket')
          }
          return;
        }
      }
      // Empeche d'ajouter plus d'items que le stock ne le permet
      if (amount > maxAmount) {
        amount = maxAmount;
        alert('Stock maximal atteint')
      }
      // Creation de item selon les specifications demandees
      let item = { "name": virus.name, "description": virus.description, "price": virus.price, "promotion": virus.promotion, "object": virus.object }
      // Ajout a basket
      basket.push({ "item": item, "amount": amount })

      let response = await ShopService.updateBasketById({ id: state.shopUser._id, basket })
      if (response?.error === 0) {
        commit('updateBasket', response.data)
      } else {
        console.log(response?.data || 'Error updating basket');
      }
    },

    // Supprime un item du panier de l'utilisateur courant
    async removeItemFromBasketByItemId({ state, commit }, item_id) {
      let basket = [...state.basket]
      // Verifier que l'item_id soit valide
      if (item_id === null || item_id < 0 || item_id >= basket.length) {
        console.log('Invalid item_id');
        return;
      }

      // Suppression de l'item du panier
      basket.splice(item_id, 1);
      let response = await ShopService.updateBasketById({ id: state.shopUser._id, basket });
      if (response?.error === 0) {
        console.log('mise à jour du panier');
        commit('updateBasket', response.data);
      } else {
        console.log(response?.data || 'Error updating basket');
      }
    },

    // Vide le panier de l'utilisateur courant
    async emptyBasket({ state, commit }) {
      let basket = [];
      console.log('mise à jour du panier : ' + basket);
      let response = await ShopService.updateBasketById({ id: state.shopUser._id, basket });
      if (response?.error === 0) {
        commit('updateBasket', response.data);
      } else {
        console.log(response?.data || 'Error updating basket');
      }
    }
  }
})