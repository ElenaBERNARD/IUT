<template>
    <div class="shop-pay">
        <h2>Payer une commande</h2>
        <div>
            <label for="order-id">ID de la commande :</label>
            <input type="text" id="order-id" v-model="currentOrderId"
                :placeholder="currentOrderId ? '' : 'Saisissez l\'ID de la commande'" />
        </div>
        <button @click="payOrder">Payer</button>
        <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
    </div>
</template>

<script>
import { mapState } from 'vuex';
import ShopService from '../services/shop.service';

export default {
    name: 'ShopPay',
    props: {
        orderId: String
    },
    data() {
    return {
      currentOrderId: this.orderId || '',
      errorMessage: ''
    };
  },
    computed: {
        ...mapState('shop', ['shopUser'])
    },
    methods: {
        async payOrder() {
            // Valider que l'ID de commande est non vide
            if (!this.currentOrderId) {
                this.errorMessage = "Veuillez fournir un ID de commande.";
                return;
            }

            let data= {
                order_id: this.currentOrderId,
                user_id: this.shopUser._id
            };

            let response = await ShopService.validateOrderByID(data);

            if(response.data.status === "finalized") {
                this.$router.push({ name: 'shopOrders' });
            } else {
                this.errorMessage = response.data;
            }
            // Rediriger vers les commandes de l'utilisateur
        }
    }
};
</script>

<style scoped>
.shop-pay {
    max-width: 400px;
    margin: auto;
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 5px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.shop-pay h2 {
    text-align: center;
}

.shop-pay .error {
    color: red;
    margin-top: 10px;
    text-align: center;
}
</style>