<template>
    <NavBar v-bind="NavBarParameters" @change-nav="changeNav"/>
    <Home v-if="enableHome"/>
    <Post v-if="enablePost"/>
    <Category v-if="enableCategory"/>
    <User v-if="enableUser"/>
</template>

<script setup>
import {computed, reactive} from "vue";
import NavBar from "./componment/NavBar.vue";
import Home from "./componment/Home.vue";
import Post from "./componment/Post.vue";
import Category from "./componment/Category.vue";
import User from "./componment/User.vue";

const NavBarParameters = reactive({
    curSelected: 'Home',
    navBarInfo: [['Home'], ['Post'], ['Category'], ['User']]
})
const enableHome = computed(() => NavBarParameters.curSelected === 'Home');
const enablePost = computed(() => NavBarParameters.curSelected === 'Post');
const enableCategory = computed(() => NavBarParameters.curSelected === 'Category');
const enableUser = computed(() => NavBarParameters.curSelected === 'User');

function changeNav(clickedName) {
    NavBarParameters.curSelected = clickedName;
}


</script>

<style scoped>

</style>