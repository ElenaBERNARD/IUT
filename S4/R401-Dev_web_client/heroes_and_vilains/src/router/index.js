import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/HomeView.vue'
import OranisationView from '@/views/OrganisationsView.vue'
import OrganisationView from '@/views/OrganisationView.vue'
import TeamsView from '@/views/TeamsView.vue'
import TeamView from '@/views/TeamView.vue'


Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  },
  // {
  //   path: '/heroes',
  //   name: 'heroes',
  //   component: () => import(/* webpackChunkName: "heroes" */ '../views/HeroesView.vue')
  // },
  {
    path: '/teams',
    name: 'teams',
    component: TeamsView
  },
  {
    path: '/team',
    name: 'team',
    component: TeamView
  },
  {
    path: '/organisations',
    name: 'organisations',
    component: OranisationView
  },
  {
    path: '/organisation',
    name: 'organisation',
    component: OrganisationView
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
