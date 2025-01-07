import Vue from 'vue';
import VueRouter from 'vue-router';
import bankView from '../views/BankView.vue';
import shopView from '../views/ShopView.vue';

Vue.use(VueRouter);

const routes = [
  {
    path: '/shop',
    component: shopView,
    children: [
      { path: '', redirect: 'home' }, // Redirect to /shop/home
      { path: 'items', name: 'shopItems', component: () => import('../views/VirusesView.vue') },
      { path: 'login', name: 'shopLogin', component: () => import('../views/ShopLogin.vue') },
      { path: 'register', name: 'shopRegister', component: () => import('../views/ShopRegister.vue') },
      { path: 'buy', name: 'shopBuy', component: () => import('../views/ShopBuy.vue') },
      { path: 'pay/:orderId?', name: 'shopPay', component: () => import('../views/ShopPay.vue'), props: true },
      { path: 'orders', name: 'shopOrders', component: () => import('../views/ShopOrders.vue') },
      { path: 'home', name: 'shopHome', component: () => import('../views/ShopHome.vue') },
      { path: 'logout', name: 'shopLogout', component: () => import('../views/ShopLogout.vue'),
      }
    ],
  },
  {
    path: '/bank',
    component: bankView,
    children: [
      { path: '', redirect: 'home' }, // Redirect to /bank/home
      { path: 'account', name: 'bankAccount', component: () => import('../views/BankAccount.vue') },
      { path: 'home', name: 'bankHome', component: () => import('../views/BankHome.vue') },
      { path: 'amount', name: 'bankAmount', component: () => import('../views/BankAmount.vue') },
      { path: 'operation', name: 'bankOperation', component: () => import('../views/BankOperation.vue') },
      { path: 'history', name: 'bankHistory', component: () => import('../views/BankHistory.vue') },
      { path: 'logout', name: 'bankLogout', component: () => import('../views/BankLogout.vue') }
    ],
  },
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
});

export default router;