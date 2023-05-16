<template>
    <NavBar v-bind="NavBarParameters" @change-nav="changeNav"/>
    <HomePage v-bind="HomePageParameters" v-if="HomeStatus"/>
    <PostPage v-bind="PostPageParameters" v-if="PostStatus"/>
    <CategoryPage v-bind="CategoryParameters" v-if="CategoryStatus"/>
    <UserPage v-bind="UserParameters" v-if="UserStatus"/>
    <Post/>
</template>

<script setup>
import {computed, provide, reactive, ref} from "vue";
// import fetch from "node-fetch";
import NavBar from "./componments/NavBar.vue";
import HomePage from "./componments/HomePage.vue";
import PostPage from "./componments/PostPage.vue";
import CategoryPage from "./componments/CategoryPage.vue";
import UserPage from "./componments/UserPage.vue";
import Post from "./componments/Post.vue";

// global variable
let hadLogin = false;
const fetchTemplate = {
    method: 'POST',
    headers: {
        "Content-Type":
            "application/json"
    },
    body: ''
};

async function fetchSinglePost(header) {
    console.log(header)//TODO
}

async function changeDisplay() {//TODO
}

provide('fetchSinglePost', fetchSinglePost);

async function doInit() {
    switch (NavBarParameters.curSelected) {
        case 'Home':
            return fetchHotTable();
        case 'Post':
            return fetchPosts();
        case 'Category':
            return fetchCategories();
        case 'User':
            return fetchPosts();
    }
}

// NavBar variable
const NavBarParameters = reactive({
    curSelected: 'Post',
    navBarInfo: [['Home'], ['Post'], ['Category'], ['User']]
})
const HomeStatus = computed(() => NavBarParameters.curSelected === 'HomePage');
const PostStatus = computed(() => NavBarParameters.curSelected === 'Post');
const CategoryStatus = computed(() => NavBarParameters.curSelected === 'CategoryPage');
const UserStatus = computed(() => NavBarParameters.curSelected === 'UserPage');

function changeNav(clickedName) {
    if (clickedName !== NavBarParameters.curSelected) {
        NavBarParameters.curSelected = clickedName;
        doInit();
    }
}


// HomePage variable
const HomePageParameters = reactive({
    headerClass: 'table-primary',
    tableClass: 'table-default',
    tableHeader: ['Title', 'UserPage', 'Content', 'Clicks'],
    tableContent: [['T1', 'far', 'emmmmmm', '3'], ['T2', 'ptr', 'example', '1']]
    // tableContent: hotTable
})
let hotTable = reactive([['T1', 'far', 'emmmmmm', '3'], ['T2', 'ptr', 'example', '1']]);

async function fetchHotTable() {
    let config = fetchTemplate;
    config.body = JSON.stringify({
        type: "SearchOp",
        content: []
    })
    hotTable = fetch('/request', config).then((content) => {
        hotTable = content.json();
    })
}

// PostPage variable
const PostPageParameters = ref({
    headerClass: 'table-primary',
    tableClass: 'table-default',
    tableHeader: ['Title', 'Author', 'CategoryPage', 'Likes'],
    tableContent: [['f', 'f', 'f', 's'], ['e', 'e', 'e', 'q']]
})

async function fetchPosts() {

}

// CategoryPage variable
const CategoryParameters = reactive({
    categories: {
        name: ['f', 'e', 'd', 'c', 'b', 'a'],
        amount: [1, 2, 3, 4, 5, 6]
    }
})

async function fetchCategories() {
    let config = fetchTemplate;
    config.body = JSON.stringify({
        type: 'PostOp',
        content: ['GetCategories']
    });
    return fetch('/request', config)
        .then(ret => ret.json())
        .then(contents => {
            console.log(contents);
            CategoryParameters.categories.name.length = 0;
            CategoryParameters.categories.amount.length = 0;
            for (let i = 0; i < contents.length;) {
                CategoryParameters.categories.name.push(contents[i++]);
                CategoryParameters.categories.amount.push(contents[i++]);
            }
        });
}

// UserPage variable
const UserParameters = reactive({
    hadLogin: computed(() => hadLogin)
});

//Post variable
const PostParameters = reactive({});
</script>

<style scoped>

</style>