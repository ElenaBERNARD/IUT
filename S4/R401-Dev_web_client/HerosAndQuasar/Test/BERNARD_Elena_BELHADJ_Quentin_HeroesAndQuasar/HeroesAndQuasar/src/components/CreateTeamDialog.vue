<template>
  <q-dialog v-model="dialog">
    <q-card class="q-pa-md" style="max-width: 500px;">
      <q-card-section>
        <div class="text-h6">Create New Team</div>
      </q-card-section>

      <q-card-section>
        <q-input v-model="name" label="Team Name" filled dense required />
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
    name: 'CreateTeamDialog',
    data() {
        return {
            dialog: false,
            name: '',
            onConfirm: Function,
        }
    },
    methods: {
        closeDialog() {
            this.dialog = false;
            this.name = '';
        },
        openDialog(onConfirm) {
            this.onConfirm = onConfirm;
            this.dialog = true;
        },
        confirm() {
            this.onConfirm(this.name);
            this.closeDialog();
        },
    },
};
</script>
