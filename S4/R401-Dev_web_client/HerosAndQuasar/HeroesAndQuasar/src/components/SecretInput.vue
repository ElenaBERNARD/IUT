<template>
  <q-input v-model="secretPhrase" :type="showSecret ? 'text' : 'password'" label="Input a secret phrase">
    <template v-slot:append>
      <q-btn flat icon @click="toggleShowSecret">
        <q-icon :name="showSecret ? 'mdi-eye-off' : 'mdi-eye'" />
      </q-btn>
    </template>
  </q-input>
</template>

<script setup>
import { ref, watch } from 'vue';
import { useOrganisationStore } from '../stores/org';


const orgStore = useOrganisationStore();
const secretPhrase = ref('');
const showSecret = ref(false);

const toggleShowSecret = () => {
  showSecret.value = !showSecret.value;
}

watch(secretPhrase, (newValue) => {
  orgStore.organisationSecret = newValue;
  console.log("Updated organisationSecret:", orgStore.organisationSecret);
})

watch(
  () => orgStore.organisationSecret,
  (newValue) => {
    if (newValue !== secretPhrase.value) {
      secretPhrase.value = newValue;
    }
  },
  { immediate: true }
)
</script>
