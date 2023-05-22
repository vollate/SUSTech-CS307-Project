<script setup>
const obj = defineProps({
  content: {type: Array}
});
</script>

<template>
  <div class="col">
  <p>User Name: {{ obj.content[0].name }}</p>
  <p>User ID: {{ obj.content[0].user_id }}</p>
  <p>Registration Time: {{ obj.content[0].registration_time }}</p>
  <p>Phone: {{ obj.content[0].phone }}</p>
  </div>
</template>

<style scoped>

</style>