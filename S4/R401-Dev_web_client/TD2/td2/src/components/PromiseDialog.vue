<template>
  <v-dialog
      v-model="dialog"
      persistent
      :max-width="width"
      :retain-focus="false"
  >
    <v-card>
      <v-card-title class="headline">
        {{ title }}
      </v-card-title>
      <v-card-text>
        {{text}}
      </v-card-text>

      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn
            color="green darken-1"
            @click="accept"
        >
          Ok
        </v-btn>
        <v-btn
            color="warning"
            @click="cancel"
        >
          Cancel
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>

</template>

<script>

export default {
  name: 'ConfirmDialog',
  props: {
    title: String,
    text: String,
    width: String,
  },
  data: () => ({
    dialog: false,
    resolve:null,
    reject: null,
    checked: false,
  }),
  methods: {
    open() {
      this.dialog = true;
      return new Promise((resolve, reject) => {
        this.resolve = resolve;
        this.reject = reject;
      })
    },
    accept() {
      this.resolve(true);
      this.checked = false;
      this.dialog = false;
    },
    cancel() {
      this.resolve(false);
      this.checked = false;
      this.dialog = false;
    },
  },
};
</script>
