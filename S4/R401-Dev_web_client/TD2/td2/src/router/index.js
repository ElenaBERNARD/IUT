import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from "@/views/HomeView";
import TD1Demo2A from "@/views/TD2Demo2A";
import TD1Demo2B from "@/views/TD2Demo2B";
import FreeView from "@/views/FreeView";
import PrivateView from "@/views/PrivateView";
import LoginForm from "@/components/LoginForm";
import  store from "@/store"

Vue.use(VueRouter)

const routes = [
    // define routes from demo 2
  {
    path: '/',
    name: 'home',
    components: {
      central: HomeView
    }
  },
  {
    path: '/checkfirstname',
    components: {
      central: TD1Demo2A
    }
  },
  {
    path: '/checklastname',
    components: {
      central: TD1Demo2B
    }
  },
  // define routes for demo 3
  {
    path:'/login',
    components: {
      central: LoginForm
    },
    meta: { levelAuth: 0 },
  },
  {
    path:'/freeroute',
    components: {
      central: FreeView
    },
    meta: { levelAuth: 0 },
  },
  {
    path:'/privateroute',
    components: {
      central: PrivateView
    },
    meta: { levelAuth: 1 },
  },
    // fallback route when no other matches => error
  {
    path:'*',
    name:'error404',
  },
]


const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

function checkAccess(to) {
  // BEWARE: routes without a meta levelAuth field are considered to be public
  if ((to.meta.levelAuth) && (to.meta.levelAuth === 1) && (store.state.auth.auth === false)) {
    return false
  }
  return true
}

// check for all routes needing privileges that user is authenticated
router.beforeEach((to, from, next) => {
  if (to.name === 'error404') {
    store.commit('errors/pushError','Invalid route')
    next('/')
  }
  else if (checkAccess(to)) {
    next()
  }
  else {
    store.commit('errors/pushError','You need to login')
    next('/login');
  }
});


export default router
