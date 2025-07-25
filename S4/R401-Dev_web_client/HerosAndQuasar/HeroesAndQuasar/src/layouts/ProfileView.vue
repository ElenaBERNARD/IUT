<template>
  <q-page>
    <q-card v-if="!currentUser" class="q-pa-md text-center">
      <q-card-section>
        <div class="text-h6">Vous n'êtes pas connecté</div>
        <p>
          Vous avez déjà un compte ?
          <q-btn flat label="Connectez-vous" to="/login" />
        </p>
        <p>
          Sinon, <q-btn flat label="inscrivez-vous" to="/register" /> !
        </p>
      </q-card-section>
    </q-card>

    <q-card v-else>
      <q-card-section>
        <div class="text-h6">{{ currentUser }}</div>
        <div>{{ currentPassword }}</div>
      </q-card-section>
    </q-card>

    <div class="row" v-if="currentUser && currentHero">
      <div class="col-12 text-center">
        <q-card class="q-pa-md">
          <q-card-section>
            <div class="text-h6">{{ currentHero.publicName }}</div>
            <div v-if="currentHero.realName">({{ currentHero.realName }})</div>
            <q-separator />
            <div v-if="currentHero.powers.length">
              <div><strong>Pouvoirs:</strong></div>
              <q-list>
                <q-item v-for="power in currentHero.powers" :key="power.name">
                  <q-item-section>
                    <div>{{ power.name }} ({{ powerTypes[power.type - 1] }}): Niveau {{ power.level
                    }}</div>
                  </q-item-section>
                </q-item>
              </q-list>
            </div>
            <div v-else>Aucun pouvoir</div>
          </q-card-section>
          <q-card-actions>
            <q-btn color="primary" @click="editHero">
              Edit
            </q-btn>
          </q-card-actions>
        </q-card>
      </div>
    </div>

    <EditHeroDialog ref="editHeroRef" />
  </q-page>
</template>

<script setup>
import { ref, computed } from "vue";
import { useAuthStore } from "../stores/auth";
import { useHeroStore } from "../stores/hero";
import EditHeroDialog from "../components/EditHeroDialog.vue";

const authStore = useAuthStore();
const heroStore = useHeroStore();

const powerTypes = ref([
  "Force",
  "Vitesse",
  "Endurance",
  "Magie",
  "Effrayant",
  "Furtivité",
  "Stupidité",
]);

const editHeroRef = ref(null);

const currentUser = computed(() => authStore.currentUser);
const currentPassword = computed(() => authStore.currentPassword);
const currentHero = computed(() => heroStore.currentHero);

const editHero = () => {
  if (editHeroRef.value) {
    editHeroRef.value.openDialog((hero) => {
      heroStore.updateHeroNoSecret({ hero });
    });
  }
};
</script>

<style scoped>
.q-page {
  max-width: 600px;
  margin: auto;
}
</style>
