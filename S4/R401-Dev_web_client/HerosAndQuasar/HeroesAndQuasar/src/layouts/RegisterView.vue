<template>
    <q-page>
        <q-form @submit="submitForm" class="q-pa-md">
            <q-input v-model="userInfo.username" label="Username" filled required />
            <q-input v-model="userInfo.email" label="Email" type="email" filled required />
            <q-input v-model="userInfo.password" label="Password" type="password" filled required />
            <q-input v-model="userInfo.confirmPassword" label="Confirm Password" type="password" filled required />

            <q-btn label="Register" color="primary" type="submit" class="full-width" />
        </q-form>

        <q-banner v-if="errorMessage" class="bg-negative text-white" dense>
            {{ errorMessage }}
        </q-banner>
    </q-page>
</template>

<script setup>
import { ref } from "vue";
import { useAuthStore } from "../stores/auth";
import { useRouter } from "vue-router";

const userInfo = ref({
    username: "",
    email: "",
    password: "",
    confirmPassword: "",
});

const errorMessage = ref("");

const authStore = useAuthStore();
const router = useRouter();

const submitForm = async () => {
    if (userInfo.value.password !== userInfo.value.confirmPassword) {
        errorMessage.value = "Passwords do not match!";
        return;
    }

    const result = await authStore.register(userInfo.value);

    if (result) {
        router.push("/login");
    } else {
        errorMessage.value = authStore.errorMessage || "Registration failed!";
    }
};
</script>

<style scoped>
.q-page {
    max-width: 400px;
    margin: auto;
}
</style>
