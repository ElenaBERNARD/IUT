<template>
  <q-page class="q-pa-md">
    <div v-if="!currentTeam" class="full-height flex flex-center row">
      <div class="text-center col">
        <q-btn color="primary" @click="goBack">
          Connect to an organisation
        </q-btn>
        <q-banner class="q-mt-sm bg-negative text-white">
          Please connect to an organisation to access teams
        </q-banner>
      </div>
    </div>

    <div v-if="currentTeam" class="row justify-center q-mb-md">
      <div class="col-12 col-md-6">
        <q-card class="q-pa-md">
          <q-card-section>
            <div class="text-h5 text-center">{{ currentTeam.name }}</div>
          </q-card-section>
        </q-card>
      </div>
    </div>

    <div v-if="currentTeam" class="row justify-center q-mb-md">
      <q-btn color="primary" @click="openAddDialog">Add Hero</q-btn>
    </div>

    <div v-if="currentTeam && members.length" class="row q-col-gutter-md justify-center">
      <div v-for="member in members" :key="member._id" class="col-12 col-sm-6 col-md-4">
        <q-card class="q-pa-md">
          <q-card-section>
            <div class="text-h6">{{ member.publicName }}</div>
            <div v-if="member.realName" class="text-subtitle1">({{ member.realName }})</div>
            <q-separator class="q-my-md" />
            <div v-if="member.powers.length">
              <strong>Powers:</strong>
              <q-list dense>
                <q-item v-for="power in member.powers" :key="power.name">
                  <q-item-section>
                    {{ power.name }} ({{ powerTypes[power.type - 1] }}): Level {{ power.level }}
                  </q-item-section>
                </q-item>
              </q-list>
            </div>
            <div v-else class="text-grey">No powers</div>
          </q-card-section>
          <q-card-actions align="right">
            <q-btn color="primary" @click="editHero(member._id)">Edit</q-btn>
            <q-btn color="negative" @click="openDeleteDialog(member)">Remove</q-btn>
          </q-card-actions>
        </q-card>
      </div>
    </div>

    <ConfirmDialog ref="deleteHeroRef" />
    <AddHero ref="addHeroRef" />
  </q-page>
</template>

<script setup>
import { ref, computed, watch, onMounted } from 'vue';
import { useTeamStore } from '../stores/team';
import { useHeroStore } from '../stores/hero';
import { useRouter } from 'vue-router';
import ConfirmDialog from '../components/ConfirmDialog.vue';
import AddHero from '../components/AddHero.vue';

const teamStore = useTeamStore();
const heroStore = useHeroStore();
const router = useRouter();

const addHeroRef = ref(null);
const deleteHeroRef = ref(null);


var members = ref([]);
const powerTypes = ref(["Force", "Vitesse", "Endurance", "Magie", "Effrayant", "Furtivité", "Stupidité"]);

const currentTeam = computed(() => teamStore.currentTeam);

const fetchTeamMembers = async () => {
  if (!currentTeam.value || !currentTeam.value.members) return;

  members.value = await Promise.all(
    currentTeam.value.members.map(async (memberId) => {
      return await heroStore.getHero({ id: memberId });
    })
  ).then(members => members.filter(Boolean));
}

const editHero = async (heroId) => {
  if (!heroId) return
  heroStore.currentHero = await heroStore.getHero({ id: heroId });
  router.push(`/hero`);
}

const openAddDialog = () => {
  if (addHeroRef.value) {
    addHeroRef.value.openDialog(() => {
      fetchTeamMembers();
    }, currentTeam.value.members);
  }
}

const openDeleteDialog = (hero) => {
  if (deleteHeroRef.value) {
    deleteHeroRef.value.openDialog(() => {
      teamStore.teamRemoveHeroes({ idHeroes: [hero._id], idTeam: teamStore.currentTeam._id });
    }, "Remove Hero", `Are you sure you want to remove ${hero.publicName} from the team?`);
  }
}

const goBack = () => {
  router.push('/organisations');
}

watch(currentTeam.value), (newTeam) => {
  if (newTeam) {
    fetchTeamMembers();
  }
}

onMounted(() => {
  if (currentTeam.value) {
    fetchTeamMembers();
  }
})
</script>
