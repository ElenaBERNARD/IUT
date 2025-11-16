<template>
    <div class="navbar">
        <ul class="navbar-list">
            <li v-for="(item, index) in items" :key="index" class="navbar-item">
                <button class="navbar-button" @click="$emit('menu-clicked', index)" :style="{ color: item.color }">
                    {{ item.text }}
                </button>
            </li>
        </ul>
    </div>
</template>

<script>
export default {
    name: "NavBar",
    props: {
        items: Array,
    },
};
</script>

<style scoped>
.navbar {
    background-color: #f8f9fa;
    padding: 10px 20px;
    border-bottom: 2px solid #ddd;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    transition: ease-in-out 0.1s;
}

.navbar:hover {
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.3);
    transition: ease-in-out 0.1s;
}

.navbar-list {
    display: flex;
    justify-content: space-around;
    list-style: none;
    margin: 0;
    padding: 0;
}

.navbar-item {
    margin: 0 10px;
}

.navbar-button {
    background-color: transparent;
    border: 2px solid transparent;
    border-radius: 5px;
    padding: 10px 15px;
    font-size: 16px;
    cursor: pointer;
    transition: background-color 0.3s, border-color 0.3s;
}

.navbar-button:hover {
    background-color: #e7e7e7;
    border-color: #ccc;
}
</style>