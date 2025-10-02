<template>
    <v-dialog v-model="dialog" max-width="500px">
        <v-card>
            <v-card-title>
                <span class="headline">Create New Team</span>
            </v-card-title>
            <v-card-text>
                <v-text-field v-model="name" label="Team Name" required></v-text-field>
            </v-card-text>
            <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="blue darken-1" text @click="closeDialog">Cancel</v-btn>
                <v-btn color="blue darken-1" text @click="confirm">Create</v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
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
