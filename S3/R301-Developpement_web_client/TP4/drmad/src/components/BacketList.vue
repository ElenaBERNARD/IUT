<template>
    <div class="basket-wrapper">
        <h2>Basket</h2>
        <CheckedList @list-button-clicked="emptyBasket()" @item-button-clicked="removeItem($event)" :data=formatedBasket
            :fields=fields :itemCheck=itemCheck :itemButton=itemButton :listButton=listButton />
        <button class="buy-button" @click="buyBasket()" :disabled="basketIsEmpty()">Buy</button>
        <button class="addOrder-button" @click="addToOrders()" :disabled="basketIsEmpty()">Add to orders</button>
    </div>
</template>
<script>
import { mapState, mapActions } from 'vuex';
import CheckedList from './CheckedList.vue';
import ShopService from '../services/shop.service.js';
import router from '../router';


export default {
    name: "BasketList",
    components: {
        CheckedList
    },
    data: () => ({
        itemCheck: false,
        listButton: {
            show: true,
            text: "Vider panier",
            color: "white"
        },
        fields: [
            "name",
            "description",
            "price",
            ['promotion', ['discount', 'amount']],
            "amount"
        ],
    }),
    methods: {
        ...mapActions('shop', ['emptyBasket', 'removeItemFromBasketByItemId', 'setBasket']),
        removeItem(item_id) {
            console.log("item id " + item_id)
            this.removeItemFromBasketByItemId(item_id)
        },
        async buyBasket() {
            // Creation des donnees a envoyer au serveur
            let data = {
                user_id: this.shopUser._id,
                items: this.basket
            }
            // Attente de la reponse du serveur
            let response = await ShopService.addOrderByUserId(data)
            let uuid = response.data
            // Verification de l'UUID
            if (this.isUUID(uuid)) {
                {
                    this.emptyBasket()
                    router.push({ name: 'shopPay', params: { orderId: uuid } })
                }
            }
        },
        async addToOrders() {
            // Creation des donnees a envoyer au serveur
            let data = {
                user_id: this.shopUser._id,
                items: this.basket
            }
            // Attente de la reponse du serveur
            let response = await ShopService.addOrderByUserId(data)
            console.log(response.data)
            let uuid = response.data
            // Verification de l'UUID
            if (this.isUUID(uuid)) {
                {
                    this.emptyBasket()
                    router.push({ name: 'shopOrders'})
                }
            }
        },
        isUUID(uuid) {
            let s = "" + uuid;
            s = s.match('^[0-9a-f]{8}-[0-9a-f]{4}-[4][0-9a-f]{3}-[0-9a-f]{4}-[0-9a-f]{12}$');
            if (s === null) {
                return false;
            }
            return true;
        },
        basketIsEmpty(){
            return this.formatedBasket.length === 0
        }
    },
    computed: {
        ...mapState('shop', ['basket', 'shopUser']),
        itemButton() {
            let itemButton = []
            for (let i = 0; i < this.basket.length; i++) {
                itemButton.push({
                    show: true,
                    text: "Remove",
                    color: "red"
                })
            }
            return itemButton
        },
        formatedBasket() {
            let formatedBasket = []
            for (let i = 0; i < this.basket.length; i++) {
                formatedBasket.push({
                    name: this.basket[i].item.name,
                    description: this.basket[i].item.description,
                    price: this.basket[i].item.price,
                    promotion: this.basket[i].item.promotion,
                    amount: this.basket[i].amount
                })
            }
            return formatedBasket
        }
    },
    mounted() {
        this.setBasket()
    }
}

</script>

<style scoped>
.basket-wrapper {
  padding: 20px;
  margin: 20px auto;
  background-color: #f9f9f9;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  transition: ease-in-out 0.1s;
}

.basket-wrapper:hover {
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.3);
  transition: ease-in-out 0.1s;
}

.basket-wrapper h2 {
  font-size: 24px;
  margin-bottom: 20px;
  color: #333;
}

.addOrder-button {
  display: inline-block;
  margin: 10px 5px;
  padding: 10px 20px;
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 5px;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.buy-button {
  display: inline-block;
  margin: 10px 5px;
  padding: 10px 20px;
  background-color: #28a745;
  color: #fff;
  border: none;
  border-radius: 5px;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.addOrder-button:enabled:hover {
  background-color: #0056b3;
}

.buy-button:enabled:hover {
  background-color: #218838;
}

button:disabled {
    background-color: #ddd;
    cursor: not-allowed;
}


</style>