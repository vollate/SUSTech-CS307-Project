<template>
  <NavBar v-bind="NavBarParameters" @change-nav="changeNav" @search="search"/>
  <HomePage v-bind="HomePageParameters" v-if="HomeStatus"/>
  <!--  <PostPage v-bind="PostPageParameters" v-if="PostStatus"/>-->
  <CategoryPage v-bind="CategoryParameters" v-if="CategoryStatus"/>
  <UserPage v-if="UserStatus"/>
  <Post v-bind="PostParameters" v-if="PostStatus" @dealRelation="dealRelation"/>
  <NewPost v-if="NewPostStatus"/>
</template>

<script setup>
import {computed, provide, reactive, ref} from "vue";
import NavBar from "./componments/NavBar.vue";
import HomePage from "./componments/HomePage.vue";
import PostPage from "./componments/PostPage.vue";
import CategoryPage from "./componments/CategoryPage.vue";
import UserPage from "./componments/UserPage.vue";
import Post from "./componments/Post.vue";
import {GlobalVariable} from "./componments/GlobalVariable.vue";
import NewPost from "./componments/NewPost.vue";

async function changeDisplay() {
}

provide('fetchSinglePost', fetchSinglePost);

async function doInit() {
  switch (NavBarParameters.curSelected) {
    case 'Home':
      // return fetchHotTable();TODO
      return;
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
  curSelected: 'User',
  navBarInfo: [['Home'], ['Post'], ['Category'], ['User'], ['NewPost']]
})
const HomeStatus = computed(() => NavBarParameters.curSelected === 'Home');
const PostStatus = computed(() => NavBarParameters.curSelected === 'Post');
const CategoryStatus = computed(() => NavBarParameters.curSelected === 'Category');
const UserStatus = computed(() => NavBarParameters.curSelected === 'User');
const NewPostStatus = computed(() => NavBarParameters.curSelected === 'NewPost');

function changeNav(clickedName) {
  if (clickedName !== NavBarParameters.curSelected) {
    NavBarParameters.curSelected = clickedName;
    doInit();
  }
}

async function search(postID) {
  let config = GlobalVariable.fetchTemplate;
  config.body = JSON.stringify({
    type: "PostOp",
    content: ["GetPost", postID]
  })
  return fetch(GlobalVariable.postURL, config)
      .then(res => res.json())
      .then(data => {
        // console.log(data.content);
        PostParameters.post = data.content[0];
      }).catch(e => console.log(e));
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
  let config = GlobalVariable.fetchTemplate;
  let timeStamp = new Date();
  timeStamp.setDate(timeStamp.getDate() - 7);
  config.body = JSON.stringify({
    type: "SearchOp",
    content: ['SearchByHot', timeStamp.toISOString().slice(0, 19).replace('T', ' ')]
  })
  fetch(GlobalVariable.postURL, config)
      .then(res => res.json())
      .then(data => {
        console.log(data);
      })
}

async function fetchHotTable() {
  let config = GlobalVariable.fetchTemplate;
  config.body = JSON.stringify({
    type: "SearchOp",
    content: []
  })
  hotTable = fetch(GlobalVariable.postURL, config).then((content) => {
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

async function fetchPosts() {//TODO

}

// CategoryPage variable
const CategoryParameters = reactive({
  categories: {
    name: ['f', 'e', 'd', 'c', 'b', 'a'],
    amount: [1, 2, 3, 4, 5, 6]
  }
})

async function fetchCategories() {
  let config = GlobalVariable.fetchTemplate;
  config.body = JSON.stringify({
    type: 'PostOp',
    content: ['GetCategories']
  });
  return fetch(GlobalVariable.postURL, config)
      .then(ret => ret.json())
      .then(contents => {
        CategoryParameters.categories.name.length = 0;
        CategoryParameters.categories.amount.length = 0;
        for (let i = 0; i < contents.length;) {
          CategoryParameters.categories.name.push(contents[i++]);
          CategoryParameters.categories.amount.push(contents[i++]);//TODO
        }
      });
}

const PostParameters = reactive({post: {}});

async function fetchSinglePost(postId) {
  let config = GlobalVariable.fetchTemplate;
  config.body = JSON.stringify({
    type: 'PostOp',
    content: ['GetPost', postId]
  });
  return fetch(GlobalVariable.postURL, config)
      .then(ret => ret.json())
      .then(contents => {
        console.log(contents);
        PostParameters.post = contents;
      });
}

async function dealRelation(opInfo) {
  if (!GlobalVariable.hadLogin)
    return window.alert("Login First !");
  let config = GlobalVariable.fetchTemplate;
  config.body = JSON.stringify({
    type: 'RelationOp',
    content: [opInfo[0], opInfo[1], GlobalVariable.userName]
  })
  return fetch(GlobalVariable.postURL, config)
      .then(ret => ret.json())
      .then(data => {
        console.log(data);
        if (data.content[0])
          window.alert('Operation succeed');
        else
          window.alert('Operation failed: ' + contents[1]);
      });
}
</script>

<style scoped>

</style>