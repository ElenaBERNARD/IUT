<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
  <v-container>

    <v-card>
      <v-card-title>
        My profile
      </v-card-title>
      <v-card-text>
        <v-row>
          <v-col cols="2">
            {{user.login}}
          </v-col>
          <v-col cols="8">
            {{user.password}}
          </v-col>
        </v-row>
      </v-card-text>
    </v-card>
  </v-container>
</template>

<script>
  import {mapState, mapActions} from 'vuex'


  export default {
    name: 'UsersEdit',
    data: () => ({
    }),
    computed: {
      ...mapState('user',['user']),
      ...mapState('auth',['authState']),
    },
    methods: {
      ...mapActions('user',['getUser']),
    },
    mounted() {
      this.getUser(this.authState.user.name);
    }
  }

</script>
