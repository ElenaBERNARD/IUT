<template>
    <v-dialog v-model="deleteDialog" max-width="500px">
        <v-card>
            <v-card-title>{{ title }}</v-card-title>
            <v-card-text>
                {{ message }}
            </v-card-text>
            <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="blue darken-1" text @click="closeDialog">Cancel</v-btn>
                <v-btn color="error" text @click="confirm">Delete</v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script>
export default {
    name: 'DeleteDialog',
    data() {
        return {
            deleteDialog: false,
            title: "",
            message: "",
            onConfirm: Function,
        }
    },
    methods: {
        closeDialog() {
            this.deleteDialog = false;
        },
        openDialog(onConfirm, title = "Confirm Deletion", message = "Are you sure you want to delete this ? This action cannot be undone.") {
            this.onConfirm = onConfirm;
            this.deleteDialog = true;
            this.title = title;
            this.message = message;
        },
        confirm() {
            this.onConfirm();
            this.closeDialog();
        },
    },
};
</script>