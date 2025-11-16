<template>
    <div class="buy-wrapper">
    <h1>Buy</h1>

        <ItemsList/>
        <BacketList/>
    </div>
</template>

<script>
import BacketList from '../components/BacketList.vue';
import ItemsList from '../components/ItemsList.vue';

export default {
    name: 'App',

    components: {
        BacketList,
        ItemsList
    },
}
</script>
<style scoped>
.buy-wrapper {
  padding: 20px;
  background-color: #f8f9fa;
}

.buy-wrapper h2 {
  font-size: 24px;
  margin-bottom: 20px;
  color: #333;
}
</style> 