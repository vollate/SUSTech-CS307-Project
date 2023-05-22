<script setup>

const obj = defineProps({
  content: {type: Array}
});
</script>

<template>
  <div class="col">
    <div v-for="each of obj.content">
      <p>Title: {{ each.title }}</p>
      <p>Content: {{ each.content }}</p>
      <p>Post ID: {{ each.post_id }}</p>
      <hr/>
    </div>
  </div>
</template>

<style scoped>

</style>