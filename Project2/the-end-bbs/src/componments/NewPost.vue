<script setup>
import {GlobalVariable} from "./GlobalVariable.vue";
import {reactive} from "vue";

const NewPostContent = reactive({
    title: '',
    content: '',
    city: '',
    country: ''
})

async function newPost() {
    if (!GlobalVariable.hadLogin) {
        window.alert("Login first");
        return;
    }
    let config = GlobalVariable.fetchTemplate;
    config.body = JSON.stringify({
        type: "PostOp",
        content: ["AddPost", NewPostContent.title, GlobalVariable.userName, NewPostContent.city, NewPostContent.country, NewPostContent.content]
    })
    return fetch(GlobalVariable.postURL, config)
        .then(res => res.json())
        .then(data => {
        })
}
</script>

<template>
    <div class="container">
        <div class="mb-3"/>
        <h1>New Post</h1>
        <div class="mb-3"/>
        <input v-model="NewPostContent.title" type="text" class="form-control col" placeholder="Title">
        <div class="mb-3"/>
        <input v-model="NewPostContent.content" type="text" class="form-control col" placeholder="Content">
        <div class="mb-3"/>
        <input v-model="NewPostContent.city" type="text" class="form-control col" placeholder="City(Optional)">
        <div class="mb-3"/>
        <input v-model="NewPostContent.country" type="text" class="form-control col" placeholder="Country(Optional)">
        <div class="mb-3"/>
        <button @click="newPost" class="btn col-1 btn-info">Submit</button>
    </div>
</template>

<style scoped>

</style>