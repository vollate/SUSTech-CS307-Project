<script setup>
defineEmits(['change-nav', 'search']);
const Parameters = defineProps({
    curSelected: {
        type: String,
    },
    navBarInfo: {
        type: Array,
    }
});

function checkStatus(curName) {
    return curName === Parameters.curSelected;
}

let searchInput;
</script>

<template>
    <main>
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <div class="container-fluid">
                <a class="navbar-brand" @click="$emit('change-nav','Home')">THE END</a>
                <!--                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarColor01"-->
                <!--                        aria-controls="navbarColor01" aria-expanded="false" aria-label="Toggle navigation">-->
                <!--                    <span class="navbar-toggler-icon"></span>-->
                <!--                </button>-->
                <div class="collapse navbar-collapse" id="navbarColor01">
                    <ul class="navbar-nav me-auto">
                        <template v-for="each of Parameters.navBarInfo" :key="each[0]">
                            <li class="nav-item" @click="$emit('change-nav',each[0])">
                                <a class="nav-link active"
                                   v-if="checkStatus(each[0])">{{ each[0] }}
                                    <span class="visually-hidden">(current)</span>
                                </a>
                                <a class="nav-link" v-else>{{ each[0] }}</a>
                            </li>
                        </template>
                    </ul>
                    <form class="d-flex">
                        <!--                        <input v-model="searchInput" class="form-control me-sm-2" type="search" placeholder="Search">-->
                        <input v-model="searchInput" class="form-control me-sm-2">
                        <!--            <button @click="$emit('search',searchInput)" class="btn btn-primary my-2 my-sm-0" type="submit">Search-->
                    </form>
                    <button @click="$emit('search',searchInput)" class="btn btn-primary my-2 my-sm-0">Search
                    </button>
                </div>
            </div>
        </nav>
    </main>
</template>
