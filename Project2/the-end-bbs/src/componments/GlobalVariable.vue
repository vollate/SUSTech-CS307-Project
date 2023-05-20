<script >
import {reactive} from "vue";

export const globalVariable = reactive({
  hadLogin: false,
  fetchTemplate: {
    method: 'POST',
    headers: {
      "Content-Type":
          "application/json"
    },
    body: ''
  },
  postURL:'/request'
})
</script>

<template>

</template>

<style scoped>

</style>