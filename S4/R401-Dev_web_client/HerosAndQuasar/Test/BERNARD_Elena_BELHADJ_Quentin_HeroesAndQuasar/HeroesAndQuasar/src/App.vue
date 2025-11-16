<template>
  <q-layout view="lHh Lpr lFf">
    <q-header elevated class="bg-primary text-white">
      <q-toolbar>
        <q-btn flat dense @click="toggleDrawer" icon="menu" class="q-mr-sm" />

        <q-toolbar-title>
          <div class="row items-center">
            <q-img alt="Quasar Logo" src="https://cdn.quasar.dev/logo/svg/logo-64px.svg" width="40px" class="q-mr-sm" />
            <q-img alt="Quasar Name" src="https://cdn.quasar.dev/logo/svg/logo-type-quasar.svg" width="100px" />
          </div>
        </q-toolbar-title>

        <q-btn flat label="Latest Release" icon="open_in_new" href="https://github.com/ElenaBERNARD/HeroesAndVilains"
          target="_blank" />
      </q-toolbar>
    </q-header>

    <q-drawer v-model="drawer" side="left" bordered>
      <q-list padding>
        <q-item to="/organisations" clickable v-ripple>
          <q-item-section avatar>
            <q-icon name="mdi-domain" />
          </q-item-section>
          <q-item-section>
            <q-item-label>Organisation</q-item-label>
          </q-item-section>
        </q-item>

        <q-item to="/teams" clickable v-ripple>
          <q-item-section avatar>
            <q-icon name="mdi-account-group" />
          </q-item-section>
          <q-item-section>
            <q-item-label>Teams</q-item-label>
          </q-item-section>
        </q-item>

        <q-item to="/profile" clickable v-ripple>
          <q-item-section avatar>
            <q-icon name="mdi-account" />
          </q-item-section>
          <q-item-section>
            <q-item-label>Profile</q-item-label>
          </q-item-section>
        </q-item>
      </q-list>
    </q-drawer>

    <q-page-container>
      <router-view class="layout-page" />
    </q-page-container>

    <ErrorDialog />
  </q-layout>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useOrganisationStore } from './stores/org';
import { useHeroStore } from './stores/hero';
import ErrorDialog from './components/ErrorDialog.vue';

const drawer = ref(false);

const organisationStore = useOrganisationStore();
const heroStore = useHeroStore();

onMounted(() => {
  organisationStore.getOrganisations();
  heroStore.getHeroAliases();
});

const toggleDrawer = () => {
  drawer.value = !drawer.value;
};
</script>

<style>
/* Ensure proper layout spacing */
.q-page-container {
  padding-top: 64px;
  /* Matches header height */
}

.layout-page {
  min-height: calc(100vh - 64px);
}
</style>
