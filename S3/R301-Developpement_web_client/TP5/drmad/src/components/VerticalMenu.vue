<template>
  <div class="vertical-menu">
    <ul class="menu-list">
      <li v-for="(item, index) in items" :key="index" class="menu-item">
        <template v-if="item.type === 'title'">
          <slot name="menu-title" :label="item.label">
            <div class="menu-title">{{ item.label }}</div>
          </slot>
        </template>
        <template v-else-if="item.type === 'link'">
          <span @click="goTo(item.to)" class="menu-link">
            <slot name="menu-link" :label="item.label">
              <button class="menu-button">{{ item.label }}</button>
            </slot>
          </span>
        </template>
      </li>
    </ul>
  </div>
</template>

<script>
import router from '@/router';

export default {
  name: "VerticalMenu",
  props: {
    items: {
      type: Array,
      required: true,
    },
  },
  methods: {
    goTo(to) {
      router.push({ path: to });
    },
  },
};
</script>

<style scoped>
.vertical-menu {
  padding: 24px;
  background-color: #f9f9f9;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  max-width: 1200px;
  margin: 5px auto;
  transition: ease-in-out 0.1s;
}

.vertical-menu:hover {
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.3);
  transition: ease-in-out 0.1s;
}

.menu-list {
  list-style: none;
  padding: 0;
  margin: 0;
}

.menu-item {
  margin: 10px 0;
}

.menu-title {
  font-size: 18px;
  font-weight: bold;
  color: #333;
  margin-bottom: 5px;
}

.menu-link {
  cursor: pointer;
  display: inline-block;
}

.menu-button {
  background-color: #f0f2f0;
  border: 2px solid transparent;
  border-radius: 5px;
  padding: 10px 15px;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s, border-color 0.3s;
}

.menu-button:enabled:hover {
  background-color: #e7e7e7;
  border-color: #ccc;
}

.menu-button:disabled {
  cursor: not-allowed;
}
</style>