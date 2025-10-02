<template>
  <q-page>
    <div class="row justify-center mb-6">
      <div class="col-auto">
        <q-btn color="primary" @click="openCreateTeamDialog">
          Create Team
        </q-btn>
      </div>
    </div>

    <div class="row">
      <div v-for="team in teams" :key="team._id" class="col-12 col-md-4">
        <q-card class="q-pa-md">
          <q-card-section>
            <div class="text-h6">{{ team.name }}</div>
          </q-card-section>

          <q-banner v-if="errorTeamId === team._id" class="bg-negative text-white" dense>
            {{ errorMessage }}
          </q-banner>
        </q-card>
      </div>
    </div>
    <CreateTeamDialog ref="createTeamRef" />
  </q-page>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import CreateTeamDialog from '../components/CreateTeamDialog.vue';
import { useTeamStore } from '../stores/team';

const store = useTeamStore();
const teams = computed(() => store.teams);
const errorTeamId = ref(null);
const errorMessage = ref("");
const createTeamRef = ref(null);

const openCreateTeamDialog = () => {
  if (createTeamRef.value) {
    createTeamRef.value.openDialog((name) => {
      handleTeamCreation(name);
    });
  }
};

const handleTeamCreation = async (name) => {
  const data = { name };
  await store.createTeam(data);
  store.getTeams();
};

onMounted(() => {
  store.getTeams();
});
</script>
