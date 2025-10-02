<template>
  <q-dialog v-model="isError" persistent>
    <q-card>
      <q-card-section>
        <div class="text-h6">Error</div>
      </q-card-section>
      <q-card-section>
        <q-banner class="q-mb-sm" color="negative" dense>
          {{ errorMessage }}
        </q-banner>
      </q-card-section>
      <q-card-actions>
        <q-btn flat label="Close" @click="closeErrorDialog" />
      </q-card-actions>
    </q-card>
  </q-dialog>
</template>

<script>
import { useErrorStore } from '../stores/error';

export default {
  name: 'ErrorDialog',
  computed: {
    isError() {
      const errorStore = useErrorStore();
      return errorStore.isError;
    },
    errorMessage() {
      const errorStore = useErrorStore();
      return errorStore.errorMessage;
    },
  },
  methods: {
    closeErrorDialog() {
      const errorStore = useErrorStore();
      errorStore.clearError();
    },
  },
};
</script>
