<script setup>
import {ref} from "vue";

const obj = defineProps({
  content: {type: Array}
});
</script>

<template>
  <div class="col">
    <div v-for="each of obj.content" class="col">
      <div class="row">
      <button @click="$emit('dealRelation',['Follow',each.author_name])" class="col-1 btn"
              style="color: #1ba9bf">Follow
      </button>
      <button @click="$emit('dealRelation',['DeleteFollow',each.author_name])" class="col-1 btn"
              style="color: #bf1b83">Unfollow
      </button>
      </div>
      <p>User Name: {{ each.name }}</p>
      <p>User ID: {{ each.user_id }}</p>
      <p>Phone: {{ each.phone }}</p>
      <p>Registration Time: {{ each.registration_time }}</p>
      <hr/>
    </div>
  </div>
</template>

<style scoped>

</style>