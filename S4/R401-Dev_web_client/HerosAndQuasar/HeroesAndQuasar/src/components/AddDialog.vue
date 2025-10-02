<template>
  <q-dialog v-model="dialog">
    <q-card class="q-pa-md" style="max-width: 500px;">
      <q-card-section>
        <div class="text-h6">{{ title }}</div>
      </q-card-section>

      <q-card-section>
        <q-select v-model="selectedItem" :options="availableItems" label="Select" option-label="name" option-value="_id"
          filled dense emit-value map-options required />
      </q-card-section>

      <q-card-actions align="right">
        <q-btn flat label="Cancel" color="primary" @click="closeDialog" />
        <q-btn flat label="Confirm" color="primary" @click="confirm" :disable="!selectedItem" />
      </q-card-actions>
    </q-card>
  </q-dialog>
</template>

<script>
export default {
  name: 'AddDialog',
  data() {
    return {
      dialog: false,
      selectedItem: null,
      availableItems: [],
      title: "",
      onConfirm: Function,
    }
  },
  methods: {
    closeDialog() {
      this.dialog = false;
    },
    openDialog(onConfirm, availableItems = [], title = "Add") {
      this.onConfirm = onConfirm;
      this.availableItems = availableItems;
      this.title = title;
      this.selectedItem = null;
      this.dialog = true;
    },
    confirm() {
      this.onConfirm(this.selectedItem);
      this.closeDialog();
    },
  },
};
</script>
