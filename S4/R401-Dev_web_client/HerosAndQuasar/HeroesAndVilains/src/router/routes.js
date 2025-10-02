import HeroView from '../layouts/HeroView.vue';
import OranisationView from '../layouts/OrganisationsView.vue';
import OrganisationView from '../layouts/OrganisationView.vue';
import TeamsView from '../layouts/TeamsView.vue';
import TeamView from '../layouts/TeamView.vue';
import OrganisationsView from '../layouts/OrganisationsView.vue';
import LoginView from '../layouts/LoginView.vue';
import RegisterView from '../layouts/RegisterView.vue';
import ProfileView from '../layouts/ProfileView.vue';

const routes = [
  {
      path: '/',
      name: 'home',
      component: OrganisationsView
  },
  {
      path: '/hero',
      name: 'hero',
      component: HeroView
  },
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
  },
  {
      path: '/login',
      name: 'login',
      component: LoginView
  },
  {
      path: '/profile',
      name: 'profile',
      component: ProfileView
  },
  {
      path: '/register',
      name: 'register',
      component: RegisterView
  },
  // Always leave this as last one,
  // but you can also remove it
  {
    path: '/:catchAll(.*)*',
    component: () => import('pages/ErrorNotFound.vue')
  }
]

export default routes
