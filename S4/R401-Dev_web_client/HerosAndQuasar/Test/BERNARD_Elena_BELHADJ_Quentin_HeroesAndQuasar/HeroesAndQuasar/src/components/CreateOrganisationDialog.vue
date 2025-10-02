<template>
  <q-dialog v-model="dialog">
    <q-card style="width: 500px">
      <q-card-section>
        <div class="text-h6">Create New Organisation</div>
      </q-card-section>

      <q-card-section>
        <q-input v-model="name" label="Organisation Name" filled dense />
      </q-card-section>

      <q-card-section>
        <q-input v-model="secret" label="Organisation Secret" filled dense />
      </q-card-section>

      <q-card-actions align="right">
        <q-btn flat label="Cancel" color="primary" @click="closeDialog" />
        <q-btn flat label="Create" color="primary" @click="confirm" />
      </q-card-actions>
    </q-card>
  </q-dialog>
</template>

<script>
export default {
  name: 'CreateOrganisationDialog',
  data() {
    return {
      dialog: false,
      name: '',
      secret: '',
      onConfirm: Function,
    };
  },
  methods: {
    closeDialog() {
      this.dialog = false;
      this.name = '';
      this.secret = '';
    },
    openDialog(onConfirm) {
      this.onConfirm = onConfirm;
      this.dialog = true;
    },
    confirm() {
      this.onConfirm(this.name, this.secret);
      this.closeDialog();
    },
  }
};
</script>
