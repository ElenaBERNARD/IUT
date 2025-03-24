<template>
  <v-app>

    <ErrorDialog
      title="ERROR"
      width="600"
    ></ErrorDialog>

    <v-navigation-drawer
        v-model="showLeftMenu"
        absolute
        temporary
    >
      <v-list>
        <v-list-item
            to="/"
        >
          <v-list-item-content>
            Home
          </v-list-item-content>
        </v-list-item>
        <v-list-item
            to="/edituser"
        >
          <v-list-item-content>
            User profile
          </v-list-item-content>
        </v-list-item>
        <v-list-item
            to="/allusers"
        >
          <v-list-item-content>
            Users list
          </v-list-item-content>
        </v-list-item>
      </v-list>


    </v-navigation-drawer>

    <v-app-bar app>
      <v-app-bar-nav-icon
          @click="showLeftMenu=true"
      ></v-app-bar-nav-icon>

      <v-toolbar-title>
        Authentication example
      </v-toolbar-title>

      <v-spacer />

      <v-tooltip v-if="authState.login === false" bottom>
        <template v-slot:activator="{ on, attrs }">
          <v-btn
              v-bind="attrs"
              v-on="on"
              fab
              class="ma-5"
              small
              to="/login"
          >
            <v-icon>mdi-login</v-icon>
          </v-btn>
        </template>
        <span>connexion</span>
      </v-tooltip>
      <v-btn
          fab
          class="ma-5"
          small
          @click="doLogout"
          v-else-if="authState.login === true"
      >
        <v-icon>mdi-logout</v-icon>
      </v-btn>

      <v-btn
          color="green"
          @click="doRegister"
      >
        Register
      </v-btn>

    </v-app-bar>

    <v-main>
      <v-container>
        <router-view name="central"></router-view>
      </v-container>
    </v-main>
  </v-app>

</template>

<script>

import ErrorDialog from "./components/ErrorDialog";
import {mapState, mapActions} from 'vuex'


export default {
  name: 'App',
  components: {ErrorDialog},
  data: () => ({
    showLeftMenu: false,
  }),
  computed: {
    ...mapState('auth',['authState']),
  },
  methods: {
    ...mapActions('auth',['logout']),
    ...mapActions('user',['register']),
    doLogout() {
      this.logout();
      if (this.$route.path !== '/') {
        this.$router.push('/');
      }
    },
    doRegister() {
      this.$router.push('/register')
    }
  },
};
</script>
