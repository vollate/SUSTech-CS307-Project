<template>
    <NavBar v-bind="NavBarParameters" @change-nav="changeNav"/>
    <MainPage />
</template>

<script setup>
import {onBeforeMount} from "vue";
import NavBar from "./apps/NavBar.vue";
import MainPage from "./apps/Home.vue";

let NavBarParameters = {
    selectedBar: 'Home',
    navBarInfo: [['#', 'Home'], ['#', 'Posts'], ['#', 'Category'], ['#', 'User']]

}
onBeforeMount(() => {
})

function changeNav(clickedName) {
    NavBarParameters.selectedBar=clickedName;

}
</script>

<style scoped>

</style>