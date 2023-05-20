<script setup>
import {inject} from "vue";
import Table from "./Table.vue";

const Parameters = defineProps({
    headerClass: {type: String},
    tableClass: {type: String},
    tableHeader: {
        type: Array
    },
    tableContent: {
        type: Array
    }
})
const fetchSinglePost = inject('fetchSinglePost');

async function openClicked(index) {
    fetchSinglePost(Parameters.tableContent[index]);
}
</script>

<template>
    <div class="container text-center flex-row pt-3" id="main-list">
        <div class="mb-5 h-auto"></div>
        <p class="main-title">THE END</p>
        <p class="h1 sub-title">The end will never end</p>
        <div class="mb-5 h-auto"></div>
        <div class="row">
            <p class="h3 table-title">What's Hot</p>
        </div>
        <Table v-bind="Parameters" @table-clicked="openClicked"/>
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

.table-title {
    font-size: 18pt;
    color: #ff3200;
}

</style>