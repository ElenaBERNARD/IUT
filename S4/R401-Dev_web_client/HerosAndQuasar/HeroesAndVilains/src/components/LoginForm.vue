<template>
  <q-page>
    <q-card class="q-pa-md">
      <q-card-section>
        <div class="text-h6">Connexion</div>
      </q-card-section>
      <q-card-section>
        <q-input v-model="identifiant" label="Identifiant" required />
        <q-input v-model="password" label="Mot de passe" type="password" required />
      </q-card-section>
      <q-card-actions>
        <q-btn color="primary" @click="authenticate">Se connecter</q-btn>
      </q-card-actions>
      <q-banner v-if="error" color="negative" class="q-mt-sm">
        {{ error }}
      </q-banner>
    </q-card>
  </q-page>
</template>

<script setup>
import { ref } from 'vue';
import { useAuthStore } from '../stores/auth';
import { useRouter } from 'vue-router';

const authStore = useAuthStore();
const router = useRouter();

const identifiant = ref('');
const password = ref('');
const error = ref('');


const authenticate = async () => {
  const result = await authStore.login({ login: identifiant.value, password: password.value });
  if (result) {
    authStore.getUser();
    router.push('/profile');
  } else {
    error.value = 'Identifiant ou mot de passe incorrect';
  }
}
</script>
