<template>
  <q-layout>
    <q-page class="q-pa-md bg-white flex column items-center">
      <SecretInput />

      <div class="q-mt-md q-gutter-md row justify-center">
        <q-card
          v-for="org in organisationNames"
          :key="org._id"
          class="col-12 col-sm-6 col-md-4 col-lg-3 q-pa-sm cursor-pointer"
          @click="fetchOrganisation(org._id)"
        >
          <q-card-section class="text-h6 text-center">
            {{ org.name }}
          </q-card-section>
          <q-banner v-if="errorOrgId === org._id" class="bg-negative text-white" dense>
            {{ errorMessage }}
          </q-banner>
        </q-card>
      </div>

      <div class="q-mt-sm">
        <q-btn color="primary" @click="openCreateOrganisationDialog">
          Create Organisation
        </q-btn>
      </div>

      <CreateOrganisationDialog ref="createOrganisationRef" />
    </q-page>
  </q-layout>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import { useRouter } from 'vue-router';
import { useOrganisationStore } from '../stores/org';
import SecretInput from '../components/SecretInput.vue';
import CreateOrganisationDialog from '../components/CreateOrganisationDialog.vue';

const router = useRouter();
const orgStore = useOrganisationStore();
const createOrganisationRef = ref(null);

const errorOrgId = ref(null);
const errorMessage = ref("");

const organisationNames = computed(() => orgStore.organisationNames);

const fetchOrganisation = async (orgId) => {
  errorOrgId.value = null;
  errorMessage.value = "";

  try {
    const response = await orgStore.getOrganisation(orgId);
    if (response) {
      router.push('/organisation');
    } else {
      throw new Error("Failed to fetch organisation");
    }
  } catch (error) {
    errorOrgId.value = orgId;
    errorMessage.value = "Impossible d'accéder à l'organisation";
    console.error("Error fetching organisation:", error);
  }
};

const openCreateOrganisationDialog = () => {
  createOrganisationRef.value?.openDialog((name, secret) => {
    handleOrganisationCreation(name, secret);
  });
};

const handleOrganisationCreation = async (name, secret) => {
  try {
    await orgStore.createOrganisation({ name, secret });
    await orgStore.getOrganisations(); // Refresh list
  } catch (error) {
    console.error("Error creating organisation:", error);
  }
};

onMounted(async () => {
  try {
    await orgStore.getOrganisations();
  } catch (error) {
    console.error("Error loading organisations:", error);
  }
});
</script>

<style scoped>
.q-card {
  transition: transform 0.2s;
}

.q-card:hover {
  transform: translateY(-2px);
}
</style>
