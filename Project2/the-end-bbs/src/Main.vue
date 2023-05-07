<template>
    <NavBar v-bind="NavBarParameters" @change-nav="changeNav"/>
    <Home v-bind="HomeParameters" v-if="HomeStatus"/>
    <Post v-bind="PostParameters" v-if="PostStatus"/>
    <Category v-bind="CategoryParameters" v-if="CategoryStatus"/>
    <User v-bind="UserParameters" v-if="UserStatus"/>
</template>

<script setup>
import {computed, provide, reactive, ref} from "vue";
import NavBar from "./components/NavBar.vue";
import Home from "./components/Home.vue";
import Post from "./components/Post.vue";
import Category from "./components/Category.vue";
import User from "./components/User.vue";

// global variable
const serverURL = 'https://localhost';
const serverPort = '5285';
let hadLogin = false;

async function fetchSinglePost(header) {
    console.log(header)//TODO
}

async function changeDisplay() {//TODO
}

provide('fetchSinglePost', fetchSinglePost);

async function doInit() {
    switch (NavBarParameters.curSelected) {
        case 'Home':
            await fetchHotTable();
            break;
        case 'Post':
            await fetchPosts();
            break;
        case 'Category':
            await fetchCategories();
            break;
        case 'User':
            await fetchPosts();
            break;
    }
}

// NavBar variable
const NavBarParameters = reactive({
    curSelected: 'Category',
    navBarInfo: ['Home', 'Post', 'Category', 'User']
})
const HomeStatus = computed(() => NavBarParameters.curSelected === 'Home');
const PostStatus = computed(() => NavBarParameters.curSelected === 'Post');
const CategoryStatus = computed(() => NavBarParameters.curSelected === 'Category');
const UserStatus = computed(() => NavBarParameters.curSelected === 'User');

function changeNav(clickedName) {
    if (clickedName !== NavBarParameters.curSelected) {
        NavBarParameters.curSelected = clickedName;
        doInit();
    }
}


// Home variable
const HomeParameters = reactive({
    headerClass: 'table-primary',
    tableClass: 'table-default',
    tableHeader: ['Title', 'User', 'Content', 'Clicks'],
    tableContent: [['T1', 'far', 'emmmmmm', '3'], ['T2', 'ptr', 'example', '1']]
})
let hotTable = [];

async function fetchHotTable() {
    hotTable = fetch('/hot-posts', {
        method: 'Post'
    }).then((content) => {
        hotTable = content.json();
    })
}

// Post variable
const PostParameters = ref({
    headerClass: 'table-primary',
    tableClass: 'table-default',
    tableHeader: ['Title', 'Author', 'Category', 'Likes'],
    tableContent: [['f', 'f', 'f', 's'], ['e', 'e', 'e', 'q']]
})

async function fetchPosts() {

}

// Category variable
const CategoryParameters = reactive({
    categories: {
        name: ['f', 'e', 'd', 'c', 'b', 'a'],
        amount: [1, 2, 3, 4, 5, 6]
    }
})

async function fetchCategories() {
}

// User variable
const UserParameters = reactive({
    hadLogin: computed(() => hadLogin)
})

</script>

<style scoped>

</style>