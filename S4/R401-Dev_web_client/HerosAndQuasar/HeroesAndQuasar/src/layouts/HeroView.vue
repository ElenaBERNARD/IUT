<template>
  <q-page class="q-pa-md">
    <div v-if="!currentHero" class="row q-fill flex-center">
      <q-btn color="primary" @click="goBack">
        Connect to an organisation
      </q-btn>

      <div v-if="!currentHero" class="q-mt-md"
        style="max-width: fit-content; padding: 10px; background-color: #f8d7da; border: 1px solid #f5c6cb; color: #721c24; border-radius: 5px;">
        <strong>Please connect to an organisation to access heroes</strong>
      </div>
    </div>

    <div class="row" v-if="currentHero">
      <div class="col">
        <q-card class="q-pa-md">
          <q-card-section>
            <div class="text-h6">{{ currentHero.publicName }}</div>
            <div v-if="currentHero.realName" class="text-subtitle2">
              ({{ currentHero.realName }})
            </div>
          </q-card-section>

          <!-- Alternative à q-divider -->
          <div style="border-top: 1px solid #e0e0e0; margin: 16px 0;"></div>

          <q-card-section>
            <p v-if="currentHero.powers.length"><strong>Pouvoirs :</strong></p>
            <q-list v-if="currentHero.powers.length">
              <q-item v-for="power in currentHero.powers" :key="power.name">
                <q-item-section>
                  <q-item-label>
                    {{ power.name }} ({{ powerTypes[power.type - 1] }}): Level {{ power.level }}
                  </q-item-label>
                </q-item-section>
              </q-item>
            </q-list>
            <p v-else>Aucun pouvoir</p>
          </q-card-section>

          <q-card-actions>
            <q-btn color="primary" @click="editHero(currentHero._id)">
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
import { ref, computed } from 'vue';
import { useHeroStore } from '../stores/hero';
import { useRouter } from 'vue-router';
import EditHeroDialog from '../components/EditHeroDialog.vue';

const heroStore = useHeroStore();
const router = useRouter();

const powerTypes = ref(["Force", "Vitesse", "Endurance", "Magie", "Effrayant", "Furtivité", "Stupidité"]);
const editHeroRef = ref(null);
var currentHero = computed(() => heroStore.currentHero);

const editHero = () => {
  if (editHeroRef.value) {
    editHeroRef.value.openDialog((hero) => {
      heroStore.updateHero({ hero });
    });
  }
};

const goBack = () => {
  router.push('/organisations');
};
</script>
