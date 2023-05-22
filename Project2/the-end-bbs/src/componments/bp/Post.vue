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
      <button @click="$emit('dealRelation',['Like',post.post_id])" type="button" class="col-1 btn btn-info">like</button>
      <div class="col-1"/>
      <button @click="$emit('dealRelation',['Fav',post.post_id])" type="button" class="col-1 btn btn-info">favorite</button>
      <div class="col-1"/>
      <button @click="$emit('dealRelation',['Share',post.post_id])" type="button" class="col-1 btn btn-info">share</button>
    </div>
    <div class="row">
      <hr/>
      <p style="font-size: 25pt">Post content: {{ post.content }}</p>
      <div v-for="reply of post.replies">
        <p class="row reply-info "> Author: {{ reply.author_name }}</p>
        <!--        <p class="row reply-info">reply id: {{ reply.reply_id }}</p>-->
        <p class="col-lg-10 "> {{ reply.content }}</p>
        <hr/>
        replies
        <div class="row">
          <div class="card ">
            <div class="card-body">
              <div class="card-title" v-for="secReply of reply.secReplies">
                <p class="sec-reply-title">Author: {{ secReply.author_name }}</p>
                <p>{{ secReply.content }}</p>
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
  color: #085c73;
}

.sec-reply-title {
//font-size: 18pt; color: #4f55a5;
}

</style>