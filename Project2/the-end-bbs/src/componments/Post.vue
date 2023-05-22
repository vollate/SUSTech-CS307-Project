<script setup>
import {ref} from "vue";

defineEmits(['dealRelation', 'replyPost', 'replyReply']);
const Parameters = defineProps({
    post: {type: Object}
});
const post = ref(Parameters.post);
</script>

<template>
    <div class="container">
        <div class="mb-4"/>
        <h1>{{ post.title }}</h1>
        <div class="row">
            <p class="col post-info">Post ID: {{ post.post_id }}</p>
            <p class="col post-info"> Posting Time: {{ post.posting_time }}</p>
            <p class="col post-info">Country: {{ post.country }}</p>
            <p class="col post-info">Author: {{ post.author_name }}</p>
        </div>
        <div class="row">
            <button @click="$emit('dealRelation',['Like',post.post_id])" type="button" style="color: #1ba9bf"
                    class="col-1 btn">like
            </button>
            <div class="col-1"/>
            <button @click="$emit('dealRelation',['Fav',post.post_id])" type="button" style="color: #1ba9bf"
                    class="col-1 btn">favorite
            </button>
            <div class="col-1"/>
            <button @click="$emit('dealRelation',['Share',post.post_id])" type="button" style="color: #1ba9bf"
                    class="col-1 btn">share
            </button>
        </div>
        <div class="row">
            <hr/>
            <p style="font-size: 20pt">{{ post.content }}</p>
            <div v-for="reply of post.replies">
                <p class="row reply-info "> Author: {{ reply.author_name }}</p>
                <p class="col-lg-10 "> {{ reply.content }}</p>
                <hr/>
                <p>Secondary Replies</p>
                <div class="card">
                    <div class="card-body">
                        <div class="card-title" v-for="secReply of reply.secReplies">
                            <div class="row">
                                <p class="sec-reply-title col">Author: {{ secReply.author_name }}</p>
                                <button @click="$emit('dealRelation',['Follow',secReply.author_name])" class="col-1 btn"
                                        style="color: #1ba9bf">Follow
                                </button>
                                <button @click="$emit('dealRelation',['DeleteFollow',secReply.author_name])"
                                        class="col-1 btn"
                                        style="color: #bf1b83">Unfollow
                                </button>
                                <p style="margin-left: 4em">{{ secReply.content }}</p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="mb-4"/>

            </div>
        </div>
    </div>
</template>

<style scoped>
.post-info {
    color: #398b82;
}


.reply-info {
    font-size: 15pt;
    color: #085c73;
}

.sec-reply-title {
    font-size: 15pt;
    color: #4f55a5;
}

</style>