<template>
  <v-container>

    <v-card
        max-width="400"
        class="mx-auto"
    >

      <v-card-title class="headline">
        Authentification
      </v-card-title>
      <v-card-text>
        <v-form
            ref="form"
            v-model="valid"
        >
          <v-text-field
              v-model="loginName"
              :rules="loginRules"
              :error-messages="nameError"
              @input="nameError=null"
              label="login"
          ></v-text-field>

          <v-text-field
              v-model="password"
              :rules="passwordRules"
              :error-messages="passError"
              label="mot de passe"
              :append-icon="showPass ? 'mdi-eye' : 'mdi-eye-off'"
              :type="showPass ? 'text' : 'password'"
              counter
              @click:append="showPass = !showPass"
              @input="passError=null"
          ></v-text-field>

        </v-form>

      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn
            :disabled="!valid"
            color="success"
            class="mr-4"
            @click="validate"
        >
          Ok
        </v-btn>

        <v-btn
            color="error"
            class="mr-4"
            @click="reset"
        >
          Reset
        </v-btn>

        <v-btn
            color="warning"
            @click="toHome"
        >
          Cancel
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-container>
</template>

<script>

import {mapActions, mapMutations} from 'vuex'

export default {
  name: 'DirectLogin',
  data: () => ({
    loginName: "",
    loginRules: [
      v => !!v || 'Nom obligatoire'
    ],
    password: "",
    passwordRules: [
      v => !!v || 'mot de passe obligatoire',
    ],
    valid: false,
    showPass: false,
    nameError: null,
    passError: null,

  }),
  computed: {
  },
  methods: {
    ...mapMutations('errors',['pushError']),
    ...mapActions('auth',['login']),
    async validate () {
      this.valid = this.$refs.form.validate();
      if (this.valid) {

        let user= {
          login: this.loginName,
          password: this.password
        };
        let response = await this.login(user);

        if (response.error === 0) {
          this.toHome()
        }
        else {
          this.pushError('Invalid login')
          this.reset()
        }
      }
    },
    reset () {
      this.valid = false;
      this.$refs.form.reset();
    },
    toHome() {
      this.$router.push('/')
    }
  },
};
</script>
