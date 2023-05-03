<script setup>
import Table from "./Table.vue";
import Foot from "./Foot.vue";
import {ref} from "vue";

const Parameters = defineProps({
    headerClass: {type: String},
    tableClass: {type: String},
    tableHeader: {type: Array},
    tableContent: {type: Array},
    curPage: {type: Number},
    totalPages: {type: Number}
})
const TableParameters = ref({
        headerClass: Parameters.headerClass,
        tableClass: Parameters.tableClass,
        tableHeader: Parameters.tableHeader,
        tableContent: Parameters.tableContent
    }
)
const FootParameters = ref({
    curPage: Parameters.curPage,
    totalPages: Parameters.totalPages
})
</script>

<template>
    <div class="container-xxl pt-5">
        <p class="display-1 post-title text-center">All Posts</p>
        <div class="m-3"></div>
        <Table v-bind="TableParameters"/>
    </div>
    <Foot v-bind="FootParameters"/>
</template>

<style scoped>
.post-title {
    color: #8a38ce
}
</style>