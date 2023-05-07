<script setup>
const Parameters = defineProps({
    headerClass: {type: String},
    tableClass: {type: String},
    tableHeader: {
        type: Array
    },
    tableContent: {
        type: Array
    }
});
defineEmits(['table-clicked']);
</script>

<template>
    <table class="table table-hover">
        <thead>
        <tr :class="Parameters.headerClass">
            <th scope="col" v-for="each of Parameters.tableHeader" :key="each">{{ each }}</th>
        </tr>
        </thead>
        <tbody>
        <tr :class="Parameters.tableClass" v-for="(eachRow,index) of Parameters.tableContent"
            @click="$emit('table-clicked',index)">
            <template v-for="(eachCol,index) of eachRow">
                <th scope="row" v-if="index===0">{{ eachCol }}</th>
                <td v-if="index!==0">{{ eachCol }}</td>
            </template>
        </tr>
        </tbody>
    </table>
</template>

<style scoped>

</style>