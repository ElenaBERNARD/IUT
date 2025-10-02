import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import store from '../store'
import Register from "../views/Register";

Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    name: 'home',
    components: {
      central: Home
    },
    alias: '/home',
    meta: { accessLevel: 0},
  },
  {
    path: '/login',
    name: 'login',
    components: {
      central: Login
    },
    meta: { accessLevel: 0},
  },
  {
    path: '/register',
    name: 'register',
    components: {
      central: Register,
    },
    meta: { accessLevel: 0},
  },
  {
    path: '/edituser',
    name: 'edituser',
    components: {
      central: () => import(/* webpackChunkName: "about" */ '../views/UserEdit.vue')
    },
    meta: { accessLevel: 1},
  },
  {
    path: '/allusers',
    name: 'allusers',
    components: {
      central: () => import(/* webpackChunkName: "about" */ '../views/UsersList.vue')
    },
    meta: { accessLevel: 1},
  },
  {
    path: '*',
    name: 'error404',
  }
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
});

function checkAccess(to) {
  if ((to.meta.accessLevel) && (to.meta.accessLevel  === 1) && (store.state.auth.authState.login === false)) {
    return false
  }
  return true
}
// intercept all requests to follow a route, so taht we are able to prevent following routes without being logged.
router.beforeEach((to, from, next) => {

  if (to.name === 'error404') {
    store.commit('errors/pushError', 'Invalid route')
    next('/')
  }
  else if (checkAccess(to)) {
    next()
  }
  else {
    store.commit('errors/pushError', 'You need to login')
    next('/login')
  }
});

export default router
