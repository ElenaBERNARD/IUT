<template>
  <div id="app">
    <NavBar :links="links" class="navbar">
      <template v-slot:button-Boutique="{ label }">
        <span style="color: red; font-weight: bold;">{{ label }}</span>
      </template>
      <template v-slot:button-Banque>
        <img src="@/assets/bank.jpg" alt="Logo" style="height: 30px;" />
      </template>
    </NavBar>
    <router-view></router-view>
  </div>
</template>

<script>
import NavBar from "@/components/NavBar.vue";

export default {
  name: "App",

  components: {
    NavBar,
  },
  data: () => ({
    // Data for the navbar:
    links: [
      { label: "Boutique", to: "/shop" },
      { label: "Banque", to: "/bank" },
    ],
  }),
};
</script>

<style>
* {
  font-family: Arial, sans-serif;
}
body {
  margin: 0;
  padding: 0;
}
</style>
