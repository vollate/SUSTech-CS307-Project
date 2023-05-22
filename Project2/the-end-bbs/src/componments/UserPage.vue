<script setup>
import {computed} from "vue";
import {GlobalVariable} from "./GlobalVariable.vue";
import Login from "./Login.vue";

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
        if (data.content[0])
          GlobalVariable.hadLogin = true;
        else
          window.alert('failed to login');
      })
}

async function signUp(userInfo) {
  for (let i = 0; i < userInfo.length; ++i) {
    if (userInfo[i] === '')
      userInfo[i] = null;
  }
  let config = GlobalVariable.fetchTemplate;
  config.body = JSON.stringify(
      {
        type: "UserOp",
        content: ['CreateUser', userInfo.userName, userInfo.password]
      });
  return fetch(GlobalVariable.postURL, config)
      .then(res => res.json())
      .then(data => {
        if (data.content[0])
          GlobalVariable.hadLogin = true;
        else
          window.alert('failed to register');
      })
}

</script>

<template>
  <Login v-if="!GlobalVariable.hadLogin" @signIn="signIn" @signUp="signUp"/>
  <div v-if="GlobalVariable.hadLogin">
    <div class="mb-3"/>
    <div class="container">
      <div class="col-2">
        Your Profile:
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>