<template>
  <q-dialog v-model="dialog">
    <q-card class="q-pa-md" style="max-width: 500px;">
      <q-card-section>
        <div class="text-h6">{{ title }}</div>
      </q-card-section>

      <q-card-section>
        {{ message }}
      </q-card-section>

      <q-card-actions align="right">
        <q-btn flat label="Cancel" color="primary" @click="closeDialog" />
        <q-btn flat label="Confirm" color="primary" @click="confirm" />
      </q-card-actions>
    </q-card>
  </q-dialog>
</template>

<script setup>
import { ref, defineExpose } from 'vue';

const dialog = ref(false);
const title = ref("");
const message = ref("");
let onConfirm = () => {};

const openDialog = (confirmCallback, newTitle = "Confirm", newMessage = "Are you sure you want to do this? This action may be definitive.") => {
  onConfirm = confirmCallback;
  title.value = newTitle;
  message.value = newMessage;
  dialog.value = true;
};

const closeDialog = () => {
  dialog.value = false;
};

const confirm = () => {
  if (onConfirm) onConfirm();
  closeDialog();
};

defineExpose({ openDialog });
</script>
