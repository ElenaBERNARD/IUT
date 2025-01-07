<template>
  <div class="buy-wrapper">
    <h1>Buy</h1>
    <div class="lists-wrapper">
      <ItemsList class="items-list" />

      <BacketList class="basket-list" />
    </div>
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

.buy-wrapper h1 {
  font-size: 24px;
  margin-bottom: 20px;
  color: #333;
}

.lists-wrapper {
  display: flex;
  justify-content: space-between;
  gap: 20px;
}

.basket-list {
  flex: 1;
}

.items-list {
  flex: 1;
}
</style>
