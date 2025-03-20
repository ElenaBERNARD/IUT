<template>
    <v-dialog v-model="dialog" max-width="500px">
        <v-card>
            <v-card-title>{{ title }}</v-card-title>
            <v-card-text>
                <v-select v-model="selectedItem" :items="availableItems" label="Select" item-text="name"
                    item-value="_id"  required></v-select>
            </v-card-text>
            <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="blue darken-1" text @click="closeDialog">Cancel</v-btn>
                <v-btn color="primary" text @click="confirm" :disabled="!selectedItem">
                    Confirm
                </v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
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