<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
  <v-container>

    <v-card>
      <v-card-title>
        Users list
      </v-card-title>
      <v-card-text>
        <v-row v-for="(user,index) in users" :key="index">
          <v-col cols="6">
            {{user._id}}
          </v-col>
          <v-col cols="2">
            {{user.login}}
          </v-col>
        </v-row>
      </v-card-text>
    </v-card>
  </v-container>
</template>

<script>

  import UserService from '../services/user.service'


  export default {
    name: 'UsersEdit',
    data: () => ({
      users: []
    }),

    async mounted() {
      /* NB: in UserEdit.vue, the user is retrieved tahnks to a store action.
      In this SFC, we assume that the list is not used in another component so we
      can call directly the service to retrieve the list and keep it just here
       */
      let ret = await UserService.getUsers()
      if (ret.error === 0) {
        this.users = ret.data
      }
    }
  }

</script>
