<template>
    <div class="table-wrapper">
        <table>
            <thead>
                <tr>
                    <th v-if="itemCheck">Select</th>
                    <th v-for="header in headers" :key="header.name">
                        {{ header.label }}
                    </th>
                    <th v-if="itemButton">Actions</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="item in items" :key="item.id">
                    <td v-if="itemCheck">
                        <input type="checkbox" v-model="selectedItems" :value="item" />
                    </td>
                    <td v-for="header in headers" :key="header.name">
                        {{ item[header.name] }}
                    </td>
                    <td v-if="itemButton">
                        <button @click="$emit('itemClicked', item)">
                            <slot name="item-button" :item="item">
                                Action
                            </slot>
                        </button>
                    </td>
                </tr>
            </tbody>
        </table>
        <button v-if="tableButton" @click="emitTableClicked" class="table-button">
            <slot name="table-button">
                Eventuel bouton
            </slot>
        </button>
    </div>
</template>

<script>
export default {
    name: "DataTable",
    props: {
        items: {
            type: Array,
            required: true,
        },
        headers: {
            type: Array,
            required: true,
        },
        itemCheck: {
            type: Boolean,
            default: false,
        },
        itemButton: {
            type: Boolean,
            default: false,
        },
        tableButton: {
            type: Boolean,
            default: false,
        },
    },
    data() {
        return {
            selectedItems: [],
        };
    },
    methods: {
        emitTableClicked() {
            this.$emit('tableClicked', this.selectedItems);
        },
    },
};
</script>

<style scoped>
.table-wrapper {
    overflow-x: auto;
    display: flex;
    flex-direction: column;
    align-items: center;
    margin: 16px 0;
    border: 1px solid #ddd;
    border-radius: 8px;
    background-color: #fff;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    transition: ease-in-out 0.1s;
}

.table-wrapper:hover {
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.3);
    transition: ease-in-out 0.1s;
}

table {
    width: 100%;
    border-collapse: collapse;
    font-family: 'Arial', sans-serif;
    font-size: 14px;
}

thead th {
    background-color: #f4f4f4;
    text-align: left;
    padding: 12px;
    border: 1px solid #ddd;
    font-weight: bold;
    color: #333;
}

tbody td {
    padding: 12px;
    border: 1px solid #ddd;
    color: #555;
}

tbody tr {
    transition: background-color 0.2s;
}

tbody tr:nth-child(even) {
    background-color: #f9f9f9;
}

tbody tr:hover {
    background-color: #f1f1f1;
}

button {
    padding: 6px 12px;
    border: none;
    border-radius: 4px;
    background-color: #007bff;
    color: #fff;
    font-size: 14px;
    cursor: pointer;
    transition: background-color 0.2s;
}

button:hover {
    background-color: red;
}

button:focus {
    outline: 2px solid #80bdff;
    outline-offset: 2px;
}

.table-button {
    width: 100px;
}

input[type="checkbox"] {
    width: 16px;
    height: 16px;
    cursor: pointer;
    accent-color: #007bff;
}

.table-wrapper button {
    margin: 12px 0;
    padding: 8px 16px;
    font-size: 16px;
}

.table-wrapper button:hover {
    background-color: #0056b3;
}
</style>