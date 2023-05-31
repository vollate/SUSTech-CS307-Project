<script setup>
import {GlobalVariable} from "./GlobalVariable.vue";
import Login from "./Login.vue";
import Com_UserInfo from "./UserPageComponments/Com_UserInfo.vue";
import Com_MyPosts from "./UserPageComponments/Com_Posts.vue";
import {computed, reactive} from "vue";
import Com_Users from "./UserPageComponments/Com_Users.vue";

const userInfo = reactive({});

async function signIn(userInfo) {
  for (let i = 0; i < userInfo.length; ++i) {
    if (userInfo[i] === '')
      userInfo[i] = null;
  }
  let config = GlobalVariable.fetchTemplate;
  config.body = JSON.stringify(
      {
        type: "UserOp",
        content: ['Login', userInfo.userName, userInfo.password]
      });
  return fetch(GlobalVariable.postURL, config)
      .then(res => res.json())
      .then(data => {
        if (data.content[0]) {
          GlobalVariable.userName = userInfo.userName;
          GlobalVariable.hadLogin = true;
          config.body = JSON.stringify(
              {
                type: "ShowOp",
                content: ['ShowUserInfo', userInfo.userName]
              });
          fetch(GlobalVariable.postURL, config)
              .then(res => res.json())
              .then(data => {
                userInfo = data[0];
              })
        } else
          window.alert('failed to login');
      })
}

async function signUp(userInfo) {
  if (userInfo.userName === '')
    window.alert("user name can't be empty");
  if (userInfo.password === '')
    window.alert("password can't be empty");
  let config = GlobalVariable.fetchTemplate;
  config.body = JSON.stringify(
      {
        type: "UserOp",
        content: ['CreateUser', userInfo.userName, userInfo.password, userInfo.userId, userInfo.phone]
      });
  return fetch(GlobalVariable.postURL, config)
      .then(res => res.json())
      .then(data => {
        if (data.content[0]) {
          GlobalVariable.hadLogin = true;
          GlobalVariable.userName = userInfo.userName;
        } else
          window.alert('Operation failed: ' + data.content[1]);
      })
}

const userPageVar = reactive({selected: 'User Info'});
const AllOptions = [['User Info', 'ShowUserInfo'],
  ['My Posts', 'ShowUserPost'],
  ['My Replies', 'ShowUserReplyPost'],
  ['My Follow', 'ShowFollowees'],
  ['My Fans', 'ShowFollowers'],
  ['Shared Posts', 'ShowSharePosts'],
  ['Liked Posts', 'ShowLikePosts'],
  ['Favorite Posts', 'ShowFavPosts']];
const dataToDisplay = reactive({
  content: []
});
const UserInfoStatus = computed(() => userPageVar.selected === 'User Info');
const PostsStatus = computed(() => {
  return userPageVar.selected === 'My Posts' ||
      userPageVar.selected === 'My Replies' ||
      userPageVar.selected === 'Shared Posts' ||
      userPageVar.selected === 'Liked Posts' ||
      userPageVar.selected === 'Favorite Posts'
});
const UsersStatus = computed(() => {
  return userPageVar.selected === 'My Follow' ||
      userPageVar.selected === 'My Fans'
});


async function changeDisplay(info) {
  if (info[0] !== userPageVar) {
    userPageVar.selected = info[0];
    let config = GlobalVariable.fetchTemplate;
    config.body = JSON.stringify({
      type: 'ShowOp',
      content: [info[1], GlobalVariable.userName]
    });
    return fetch(GlobalVariable.postURL, config)
        .then(res => res.json())
        .then(data => {
          console.log(data);
          dataToDisplay.content = data.content;
        })
  }
}
</script>

<template>
  <Login v-if="!GlobalVariable.hadLogin" @signIn="signIn" @signUp="signUp"/>
  <div v-if="GlobalVariable.hadLogin">
    <div class="mb-3"/>
    <div class="container-fluid">
      <div class="row">
        <div class="d-flex flex-column flex-shrink-0 p-3 bg-light" style="width: 280px;">
          <a class="d-flex align-items-center mb-3 mb-md-0 me-md-auto link-dark text-decoration-none">
            <svg class="bi me-2" width="40" height="32">
              <use xlink:href="#bootstrap"></use>
            </svg>
            <span class="fs-4">Welcome</span>
          </a>
          <hr>
          <ul class="nav nav-pills flex-column mb-auto">
            <li>
              <a v-for="each of AllOptions" @click="changeDisplay(each)" class="nav-link link-dark">
                <svg class="bi me-2" width="16" height="16">
                  <use xlink:href="#speedometer2"></use>
                </svg>
                {{ each[0] }}
              </a>
            </li>
          </ul>
          <hr>
        </div>
        <Com_UserInfo v-if="UserInfoStatus" v-bind="dataToDisplay"/>
        <Com_MyPosts v-if="PostsStatus" v-bind="dataToDisplay"/>
        <Com_Users v-if="UsersStatus" v-bind="dataToDisplay"/>
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>