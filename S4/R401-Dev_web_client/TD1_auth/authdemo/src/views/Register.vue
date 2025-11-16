<template>
    <v-container>

        <PromiseDialog ref="logindialog" title="Register successful" text="proceed to automatic login ?" width="600">
        </PromiseDialog>

        <v-card max-width="400" class="mx-auto">

            <v-card-title class="headline">
                Enregistrement
            </v-card-title>
            <v-card-text>
                <v-form ref="form" v-model="valid">
                    <v-text-field v-model="name" :rules="[nameRules]" :error-messages="nameError"
                        @input="nameError = null" label="login"></v-text-field>

                    <v-text-field v-model="password1" :rules="[passwordRules]" :error-messages="passError1"
                        label="mot de passe" :append-icon="showPass1 ? 'mdi-eye' : 'mdi-eye-off'"
                        :type="showPass1 ? 'text' : 'password'" counter @click:append="showPass1 = !showPass1"
                        @input="passError1 = null"></v-text-field>
                    <v-text-field v-model="password2" :rules="[passwordRules]" :error-messages="passError2"
                        label="confirmation mot de passe" :append-icon="showPass2 ? 'mdi-eye' : 'mdi-eye-off'"
                        :type="showPass2 ? 'text' : 'password'" counter @click:append="showPass2 = !showPass2"
                        @input="passError2 = null"></v-text-field>

                    <v-text-field v-model="publicName" :rules="[publicNameRules]" label="publicName"></v-text-field>
                </v-form>

                <VueRecaptcha :sitekey="siteKey" :loadRecaptchaScript="true" ref="recaptcha" @verify="onCaptchaVerify"
                    @expired="onCaptchaExpired"></VueRecaptcha>

            </v-card-text>
            <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn :disabled="!valid" color="success" class="mr-4" @click="validate">
                    Ok
                </v-btn>

                <v-btn color="error" class="mr-4" @click="reset">
                    Reset
                </v-btn>

                <v-btn color="warning" @click="toHome">
                    Cancel
                </v-btn>
            </v-card-actions>
        </v-card>
    </v-container>
</template>

<script>

import { mapMutations, mapActions } from 'vuex'
import VueRecaptcha from 'vue-recaptcha';
import Config from '../commons/config'
import PromiseDialog from "../components/PromiseDialog";

export default {
    name: 'Register',
    components: { VueRecaptcha, PromiseDialog },
    data: () => ({
        name: "",
        nameError: null,

        password1: "",
        showPass1: false,
        passError1: null,
        password2: "",
        showPass2: false,
        passError2: null,
        publicName: "",
        valid: false,

        redirectTo: "",
        siteKey: Config.captchaSiteKey,
        captchaToken: null,
    }),
    computed: {

    },
    methods: {
        ...mapActions('user', ['register']),
        ...mapActions('auth', ['login']),
        ...mapMutations('errors', ['pushError']),
        nameRules(v) {
            if (!v) return 'Nom obligatoire';
            return true;
        },
        passwordRules(v) {
            if (!v) return 'Mot de passe obligatoire';
            return true;
        },
        publicNameRules(v) {
            if (!v) return 'Nom public d\'un héro obligatoire';
            return true;
        },
        async validate() {
            this.valid = this.$refs.form.validate();

            if (this.captchaToken === null) {
                this.pushError('Veuillez cliquer sur le captcha');
                return
            }
            if (this.password1 !== this.password2) {
                this.passError2 = "le mot de passe de confirmation ne correspond pas";
                return
            }

            if (this.valid) {

                let user = {
                    login: this.name,
                    password: this.password1,
                    captchaToken: this.captchaToken,
                    hero: this.publicName,
                };
                let response = await this.register(user)
                if (response.error === 0) {

                    let answer = await this.$refs.logindialog.open()
                    if (answer) {
                        let user = {
                            login: this.name,
                            password: this.password1
                        };
                        let res = await this.login(user);
                        if (res.error === 0) {
                            this.toHome()
                        } else {
                            this.$router.push('/login')
                        }
                    }
                    else {
                        this.$router.push('/login')
                    }
                }
                else {
                    this.pushError('Cannot create a new user')
                    this.reset()
                }
            }
        },
        reset() {
            this.valid = false;
            this.$refs.form.reset();
        },
        toHome() {
            this.$router.push('/')
        },
        onCaptchaVerify: function (response) {
            console.log('Verify: ' + response);
            this.captchaToken = response;
        },
        onCaptchaExpired: function () {
            console.log('Expired')
        },
        resetRecaptcha() {
            this.$refs.recaptcha.reset() // Direct call reset method
        },
    }
};
</script>
