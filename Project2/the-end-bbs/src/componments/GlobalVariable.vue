<script>
import {reactive} from "vue";

export const GlobalVariable = reactive({
  hadLogin: false,
  userName: '',
  fetchTemplate: {
    method: 'POST',
    headers: {
      "Content-Type":
          "application/json"
    },
    body: ''
  },
  postURL: '/request'
})
</script>

<template>

</template>

<style scoped>

</style>