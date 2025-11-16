<template>
  <q-dialog v-model="dialog" max-width="800px">
    <q-card>
      <q-card-section>
        <div class="text-h6">Add a Hero</div>
      </q-card-section>

      <q-tabs v-model="tab" align="justify">
        <q-tab name="choose" label="Choose Existing" />
        <q-tab name="create" label="Create New" />
      </q-tabs>

      <q-card-section>
        <q-tab-panels v-model="tab" animated>
          <!-- Choose Existing Tab -->
          <q-tab-panel name="choose">
            <q-list>
              <q-item v-for="hero in filteredHeroAliases" :key="hero._id" clickable>
                <q-item-section>
                  <q-item-label>{{ hero.publicName }}</q-item-label>
                </q-item-section>
                <q-item-section side>
                  <q-checkbox :model-value="selectedHeroes.includes(hero._id)"
                    @update:model-value="toggleHeroSelection(hero._id)" />
                </q-item-section>
              </q-item>
            </q-list>
            <q-btn color="primary" :disable="selectedHeroes.length === 0" @click="addHeroesToTeam" class="q-mt-md">
              Add
            </q-btn>
          </q-tab-panel>

          <!-- Create New Hero Tab -->
          <q-tab-panel name="create">
            <q-input label="Display Name" v-model="newHero.publicName" class="q-mb-md" />
            <q-input label="Real Name" v-model="newHero.realName" class="q-mb-md" />

            <q-form>
              <div v-for="(power, index) in newHero.powers" :key="index" class="q-mb-md">
                <div class="row q-gutter-md">
                  <q-input class="col" label="Power Name" v-model="power.name" />

                  <q-select class="col" label="Type" v-model="power.type"
                    :options="powerTypes.map((type, i) => ({ label: type, value: i + 1 }))" option-label="label"
                    emit-value map-options />

                  <q-input class="col" label="Power Level" :min="0" :max="100" v-model="power.level"
                    @update:model-value="power.level = Math.min(100, Math.max(0, power.level))" />

                  <div class="flex items-center q-ml-sm">
                    <q-btn color="negative" @click="removePower(index)" dense flat
                      style="width: 32px; height: 32px; min-width: 32px;">
                      <q-icon name="close" />
                    </q-btn>
                  </div>
                </div>
              </div>
            </q-form>

            <q-btn color="green" @click="addPower" class="q-mt-md">+ Add Power</q-btn>
            <q-btn color="primary" class="q-mt-md" :disable="!newHero.publicName || !newHero.powers.length"
              @click="createAndAddHero">
              Create Hero
            </q-btn>
          </q-tab-panel>
        </q-tab-panels>
      </q-card-section>

      <q-card-actions align="right">
        <q-btn flat label="Cancel" @click="closeDialog" />
      </q-card-actions>
    </q-card>
  </q-dialog>
</template>

<script setup>
import { ref, computed, reactive, defineExpose } from 'vue';
import { useTeamStore } from '../stores/team';
import { useHeroStore } from '../stores/hero';

const teamStore = useTeamStore();
const heroStore = useHeroStore();

const dialog = ref(false);
const tab = ref('choose');
const selectedHeroes = ref([]);
const powerTypes = ref(["Force", "Vitesse", "Endurance", "Magie", "Effrayant", "Furtivité", "Stupidité"]);
const members = ref([]);
let onConfirm = () => { };

const newHero = reactive({
  publicName: '',
  realName: '',
  powers: []
});

const currentTeam = computed(() => teamStore.currentTeam);
const heroAliases = computed(() => heroStore.heroAliases);

const filteredHeroAliases = computed(() => {
  return heroAliases.value.filter(hero => !members.value.includes(hero._id));
});

const openDialog = (onConfirmFunc, teamMembers) => {
  members.value = teamMembers;
  onConfirm = onConfirmFunc;
  dialog.value = true;
  selectedHeroes.value = [];
  tab.value = 'choose';
  Object.assign(newHero, { publicName: '', realName: '', powers: [] });
};

const closeDialog = () => {
  dialog.value = false;
  Object.assign(newHero, { publicName: '', realName: '', powers: [] });
};

const confirm = () => {
  onConfirm();
  closeDialog();
};

const addHeroesToTeam = () => {
  if (!currentTeam.value || selectedHeroes.value.length === 0) return;

  teamStore.teamAddHeroes({
    idHeroes: selectedHeroes.value,
    idTeam: currentTeam.value._id
  });

  selectedHeroes.value = [];
  confirm();
};

const toggleHeroSelection = (heroId) => {
  if (selectedHeroes.value.includes(heroId)) {
    selectedHeroes.value = selectedHeroes.value.filter(id => id !== heroId);
  } else {
    selectedHeroes.value.push(heroId);
  }
};

const addPower = () => {
  newHero.powers.push({ name: 'newPower', type: 1, level: 0 });
};

const removePower = (index) => {
  newHero.powers.splice(index, 1);
};

const createAndAddHero = async () => {
  const createdHero = await heroStore.createHero(newHero);
  if (createdHero) {
    teamStore.teamAddHeroes({
      idHeroes: [createdHero._id],
      idTeam: currentTeam.value._id
    });
    confirm();
  }
};

defineExpose({ openDialog });
</script>
