<script setup>
import {computed} from "vue";
import VueCookie from 'vue-cookie'
import Login from "./Login.vue";

const Parameters = defineProps({
    hadLogin: {
        type: Boolean
    }
})
const displayLogin = computed(() => !Parameters.hadLogin);
</script>

<template>
    <Login v-if="displayLogin"/>
    <div v-if="!displayLogin">
        <p>this is user profile</p>
    </div>
</template>

<style scoped>

</style>