<script setup>
import {ref} from "vue";
import {GlobalVariable} from "./GlobalVariable.vue";

defineEmits(['dealRelation', 'replyPost', 'replyReply']);
const Parameters = defineProps({
    post: {type: Object}
});
const post = ref(Parameters.post);
let newReplyContent = '';
let newSecReplyContent = '';

async function newReply() {
    if (!GlobalVariable.hadLogin) {
        window.alert('Login first !');
        return;
    } else if (newReplyContent.length === 0) {
        window.alert('Input cannot be null');
        return;
    }
    let config = GlobalVariable.fetchTemplate;
    config.body = JSON.stringify({
        type: "PostOp",
        content: ["AddReply", Parameters.post.post_id, newReplyContent, GlobalVariable.userName]
    })
    return fetch(GlobalVariable.postURL, config)
        .then(res => res.json())
        .then(data => {
            // if (data.content[0])
            //     window.alert('Succeed');
            // else
            //     window.alert('Operation Failed');
        })
}

async function newSecReply(replyId) {
    if (!GlobalVariable.hadLogin) {
        window.alert('Login first !');
        return;
    } else if (newSecReplyContent.length === 0) {
        window.alert('Input cannot be null');
        return;
    }
    let config = GlobalVariable.fetchTemplate;
    config.body = JSON.stringify({
        type: "PostOp",
        content: ["AddSecondReply", replyId, newSecReplyContent, GlobalVariable.userName]
    })
    return fetch(GlobalVariable.postURL, config)
        .then(res => res.json())
        .then(data => {
            // if (data.content[0])
            //     window.alert('Succeed');
            // else
            //     window.alert('Operation Failed');
        })
}
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
            <input v-model="newReplyContent" type="text" class="form-control col" id="floatingPassword"
                   placeholder="New Reply">
            <button @click="newReply" class="btn col-1 btn-info">Submit</button>
        </div>
        <div class="row">
            <hr/>
            <p style="font-size: 20pt">{{ post.content }}</p>
            <div v-for="reply of post.replies">
                <p class="row reply-info "> Author: {{ reply.author_name }}</p>
                <p class="col-lg-10 "> {{ reply.content }}</p>
                <hr/>
                <div class="row">
                    <input v-model="newSecReplyContent" type="text" class="form-control col"
                           id="floatingPassword"
                           placeholder="New Reply">
                    <button @click="newSecReply(reply.reply_id)" class="btn col-1 btn">Submit
                    </button>
                </div>
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