<script setup>
const Parameters = defineProps({
    categories: {
        type: Object
    }
})
defineEmits(['category-clicked']);
</script>

<template>
    <div class="container">
        <div class="card" v-for="(categoryName,index) of Parameters.categories.name" @click="$emit('category-clicked',categoryName)">
            <div class="card-header">
                <p class="card-title">{{ categoryName }}</p>
            </div>
        </div>
    </div>
</template>

<style scoped>

</style>