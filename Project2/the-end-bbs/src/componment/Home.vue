<script setup>
import Post from "./Post.vue";
</script>

<template>
    <div class="container text-center" id="main-list">
        <div class="mb-5 h-auto"></div>
        <p class="main-title">THE END</p>
        <p class="sub-title">The end will never end</p>
        <div class="mb-5 h-auto"></div>
        <Post/>
    </div>
</template>

<style scoped>
.main-title {
    font-size: 70pt;
    color: rgba(0, 44, 169, 0.89);
}

.sub-title {
    font-size: 20pt;
    color: #00865e;
}
</style>