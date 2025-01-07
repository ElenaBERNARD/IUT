<template>
    <div>
        <h1>Orders</h1>
        <div v-for="(order, order_id) in orders" :key="order_id">
            <table>
                <tr>
                    <th>Item</th>
                    <th>Description</th>
                    <th>Price</th>
                    <th>Amount</th>
                    <th>Total</th>
                </tr>
                <tr v-for="(item, item_id) in order.items" :key="item_id">
                    <td v-for="(field, field_id) in fields" :key="field_id">
                        {{ item.item[field] }}
                    </td>
                    <td>
                        {{ item.amount }}
                    </td>
                    <td>
                        {{ item.item.price * item.amount }}
                    </td>
                </tr>
            </table>
            <strong>Order total : </strong> {{ order.total }} <small> (with promotion)</small> <br>
            <strong>Order status : </strong> {{ order.status }}<br>
            <div>
                <button class="pay-button" @click="payOrder(order.uuid)" :disabled="isWaitingPayment(order)" >Pay</button>
                <button class="cancel-button" @click="cancelOder(order.uuid)" :disabled="isWaitingPayment(order)" >Cancel</button>
            </div>
        </div>
    </div>
</template>

<script>
import { mapState } from 'vuex';
import ShopService from '../services/shop.service';
import router from '../router';

export default {
    name: 'ShopOrders',
    data() {
        return {
            orders: [],
            fields: ['name', 'description', 'price'],
        };
    },
    computed: {
        ...mapState('shop', ['shopUser'])
    },
    methods: {
        async getOrders() {
            let response = await ShopService.getOrdersByUserId({ user_id: this.shopUser._id });
            this.orders = response.data;
        },
        async payOrder(order_id) {
            router.push({ name: 'shopPay', params: { orderId: order_id } });
        },
        cancelOder(order_id) {
            ShopService.cancelOrderById({ user_id: this.shopUser._id, order_id: order_id });
            // this.getOrders();
        },
        isWaitingPayment(order){
            return order.status !== 'waiting_payment';
        }
    },
    mounted() {
        this.getOrders();
    },
    watch: {
        shopUser: {
            handler: function () {
                this.getOrders();
            },
            immediate: true
        }
    },

};

</script>

<style scoped>
/* General Layout */
div {
    padding: 20px;
    max-width: 900px;
    margin: 0 auto;
}

/* Title */
h1 {
    text-align: center;
    color: #4CAF50;
    margin-bottom: 20px;
}

/* Table Styling */
table {
    width: 100%;
    border-collapse: collapse;
    margin-bottom: 20px;
}

th, td {
    padding: 10px;
    text-align: left;
    border: 1px solid #ddd;
}

th {
    background-color: #f4f4f4;
    color: #333;
}

tr:nth-child(even) {
    background-color: #f9f9f9;
}

tr:hover {
    background-color: #f1f1f1;
}

/* Strong Text */
strong {
    font-weight: bold;
    color: #333;
}

/* Order Total and Status */
div > strong {
    font-size: 16px;
    margin-top: 10px;
}

/* Buttons */
button:disabled {
    background-color: #ddd;
    cursor: not-allowed;
}

.pay-button {
    padding: 10px 15px;
    margin: 10px 5px;
    background-color: #4CAF50;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 16px;
}

.pay-button {
    padding: 10px 15px;
    margin: 10px 5px;
    background-color: #4CAF50;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 16px;
}

.pay-button:enabled:hover {
    background-color: #45a049;
}

.cancel-button {
    padding: 10px 15px;
    margin: 10px 5px;
    background-color: #f44336;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 16px;
}

.cancel-button:enabled:hover {
    background-color: #da190b;
}

/* Conditional Pay/Cancel Section */
div[v-if="order.status === 'waiting_payment'"] {
    margin-top: 15px;
    display: flex;
    justify-content: space-between;
    gap: 10px;
}

/* Responsive Design */
@media (max-width: 768px) {
    table, th, td {
        font-size: 14px;
    }

    button {
        width: 100%;
        font-size: 14px;
    }

    div {
        padding: 10px;
    }
}
</style>
