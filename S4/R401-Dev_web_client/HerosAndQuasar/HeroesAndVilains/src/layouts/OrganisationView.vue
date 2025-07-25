<template>
  <q-page class="q-pa-md">
    <!-- Connect to Organisation -->
    <div v-if="!currentOrganisation" class="row q-fill-height flex justify-center items-center">
      <q-btn color="primary" @click="goBack">Connect to an organisation</q-btn>
      <q-banner type="negative" class="q-mt-md" style="max-width: fit-content;">
        Please connect to an organisation
      </q-banner>
    </div>

    <!-- Display Organisation Info -->
    <div v-if="currentOrganisation" class="row">
      <div class="col">
        <q-card>
          <q-card-section>
            <div class="text-h6">{{ currentOrganisation.name }}</div>
          </q-card-section>
        </q-card>
      </div>
    </div>

    <!-- Add Team Button -->
    <div v-if="currentOrganisation" class="row justify-center q-mb-md">
      <div class="col-auto">
        <q-btn color="primary" @click="openAddTeamDialog">
          Add Team to Organisation
        </q-btn>
      </div>
    </div>

    <!-- Teams List -->
    <div v-if="currentOrganisation && currentOrganisation.teams && currentOrganisation.teams.length" class="row">
      <div v-for="team in currentOrganisation.teams" :key="team._id" class="col-12 col-md-6">
        <q-card class="q-pa-md">
          <q-card-section>
            <div class="text-h6">{{ team.name || "Team name" }}</div>
            <q-separator />
            <q-list>
              <q-item v-for="memberId in team.members" :key="memberId">
                <q-item-section>{{ getHeroName(memberId) }}</q-item-section>
              </q-item>
            </q-list>
          </q-card-section>
          <q-card-actions>
            <q-btn color="primary" @click="editTeam(team._id)">Edit Team</q-btn>
            <q-btn color="negative" @click="deleteTeam(team._id)">Remove Team</q-btn>
          </q-card-actions>
        </q-card>
      </div>
    </div>

    <!-- Dialogs -->
    <ConfirmDialog ref="deleteTeamRef" />
    <AddDialog ref="addTeamRef" />
  </q-page>
</template>

<script setup>
import { ref, computed, watch, onMounted } from 'vue';
import { useHeroStore } from '../stores/hero.js';
import { useTeamStore } from '../stores/team.js';
import { useOrganisationStore } from '../stores/org.js';
import ConfirmDialog from '../components/ConfirmDialog.vue';
import AddDialog from '../components/AddDialog.vue';
import { useRouter } from 'vue-router';

const router = useRouter();

const heroStore = useHeroStore();
const teamStore = useTeamStore();
const orgStore = useOrganisationStore();

const currentOrganisation = computed(() => orgStore.currentOrganisation);
const heroAliases = computed(() => heroStore.heroAliases);
const teams = computed(() => teamStore.teams);

const deleteTeamRef = ref(null);
const addTeamRef = ref(null);

const availableTeams = computed(() => {
  if (!currentOrganisation.value || !currentOrganisation.value.teams || !teams.value) {
    return [];
  }
  const organisationTeamIds = currentOrganisation.value.teams.map(team => team._id);
  return teams.value.filter(team => !organisationTeamIds.includes(team._id));
});

const getHeroName = (memberId) => {
  const hero = heroAliases.value.find(hero => hero._id === memberId);
  return hero ? hero.publicName : "Unknown Hero";
};

const editTeam = (teamId) => {
  if (!teamId) {
    return;
  }
  const team = currentOrganisation.value.teams.find(team => team._id === teamId);
  if (!team) {
    return;
  }
  teamStore.currentTeam = team;
  router.push(`/team`);
};

const deleteTeam = (teamId) => {
  if (!deleteTeamRef.value) return;

  deleteTeamRef.value.openDialog(
    () => { orgStore.removeTeam(teamId); },
    "Remove Team",
    "Are you sure you want to remove this team from the organisation?"
  );
};

const openAddTeamDialog = () => {
  if (!addTeamRef.value) return;

  addTeamRef.value.openDialog(
    (team) => {
      orgStore.addTeam(team);
    },
    availableTeams.value,
    'Add Team to Organisation'
  );
};

const goBack = () => {
  router.push('/organisations');
};

onMounted(() => {
  teamStore.getTeams();
});

watch(currentOrganisation, (newVal, oldVal) => {
  if (newVal && newVal._id !== (oldVal ? oldVal._id : null)) {
    orgStore.getOrganisation(newVal._id);
  }
});
</script>

<style scoped>
.q-page {
  max-width: 800px;
  margin: auto;
}
</style>
