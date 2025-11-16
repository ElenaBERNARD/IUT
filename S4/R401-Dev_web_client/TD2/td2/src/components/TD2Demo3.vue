<template>
  <v-container>
    <v-row>
      <v-btn to="freeroute">Follow free access route</v-btn>
      <v-btn to="privateroute">Follow private access route (must login)</v-btn>
    </v-row>
    <v-row>
      <router-view name="central"></router-view>
    </v-row>
  </v-container>
</template>

<script>

export default {
  name: 'TD2Demo3',
}
</script>

