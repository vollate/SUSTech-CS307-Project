<script setup>
import {computed} from "vue";
import {globalVariable} from "./GlobalVariable.vue";
import Login from "./Login.vue";

defineEmits(['signIn', 'signUp']);
const Parameters = defineProps({
  hadLogin: {
    type: Boolean
  }
})

async function signIn(userInfo) {
  for (let i = 0; i < userInfo.length; ++i) {
    if (userInfo[i] === '')
      userInfo[i] = null;
  }
  let config = globalVariable.fetchTemplate;
  config.body = JSON.stringify(
      {
        type: "UserOp",
        content: ['Login', userInfo.userName, userInfo.password]
      });
  return fetch(globalVariable.postURL, config)
      .then(res => res.json())
      .then(data => {
        if (data.content[0])
          globalVariable.hadLogin = true;
        else
          window.alert('failed to login');
      })
}

async function signUp(userInfo) {
  for (let i = 0; i < userInfo.length; ++i) {
    if (userInfo[i] === '')
      userInfo[i] = null;
  }
  let config = globalVariable.fetchTemplate;
  config.body = JSON.stringify(
      {
        type: "UserOp",
        content: ['CreateUser', userInfo.userName, userInfo.password]
      });
  return fetch(globalVariable.postURL, config)
      .then(res => res.json())
      .then(data => {
        if (data.content[0])
          globalVariable.hadLogin = true;
        else
          window.alert('failed to register');
      })
}

const displayLogin = computed(() => !Parameters.hadLogin);
</script>

<template>
  <Login v-if="displayLogin" @signIn="signIn" @signUp="signUp"/>
  <div v-if="!displayLogin">
    <p>this is user profile</p>
  </div>
</template>

<style scoped>

</style>