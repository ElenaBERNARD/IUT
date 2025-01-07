<template>
<div>
    <ul>
        <li v-for="(item, index) in items" :key="index">
            <button @click="$emit('menu-clicked', index)" :style="'color:' + item.color + ';'">{{ item.text }}</button>
        </li>
    </ul>
</div>
</template>

  
  
<script>
export default {
    name: 'NavBar',
    props: {
        items: Array
    }
}
</script>
  
  <!-- Add "scoped" attribute to limit CSS to this component only -->
  
<style scoped>
ul {
    display: flex;
    justify-content: center;
    list-style: none;
}

ul li {
    padding-right: 5%;
    padding-left: 5%;
}

button {
    background-color: rgba(51, 51, 51, 0.05);
    border-radius: 8px;
    border-width: 0;
    cursor: pointer;
    display: inline-block;
    font-family: "Haas Grot Text R Web", "Helvetica Neue", Helvetica, Arial, sans-serif;
    font-size: 14px;
    font-weight: 500;
    line-height: 20px;
    list-style: none;
    margin: 0;
    padding: 10px 12px;
    text-align: center;
    transition: all 200ms;
    vertical-align: baseline;
    white-space: nowrap;
    user-select: none;
    -webkit-user-select: none;
    touch-action: manipulation;
}
</style>
