<template>
    <main>
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark" id="nav-bar">
            <div class="container-fluid">
                <a class="navbar-brand" @click="$emit('change-nav','Home')">THE END</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarColor01"
                        aria-controls="navbarColor01" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
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
                        <!--                        <li class="nav-item dropdown">-->
                        <!--                            <a class="nav-link dropdown-toggle" data-bs-toggle="dropdown" href="#" role="button"-->
                        <!--                               aria-haspopup="true" aria-expanded="false">Dropdown</a>-->
                        <!--                            <div class="dropdown-menu">-->
                        <!--                                <a class="dropdown-item" href="#">Action</a>-->
                        <!--                                <a class="dropdown-item" href="#">Another action</a>-->
                        <!--                                <a class="dropdown-item" href="#">Something else here</a>-->
                        <!--                                <div class="dropdown-divider"></div>-->
                        <!--                                <a class="dropdown-item" href="#">Separated link</a>-->
                        <!--                            </div>-->
                        <!--                        </li>-->
                    </ul>
                    <form class="d-flex">
                        <input class="form-control me-sm-2" type="search" placeholder="Search">
                        <button class="btn btn-secondary my-2 my-sm-0" type="submit">Search</button>
                    </form>
                </div>
            </div>
        </nav>
    </main>
</template>

<script setup>

import {computed, reactive, watch} from "vue";

defineEmits(['change-nav']);
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

// let barStatus = reactive([]);
// watch(() => Parameters.curSelected, (val, oldVal) => {
//         barStatus.length = Parameters.navBarInfo.length;
//         for (let i = 0; i < Parameters.navBarInfo.length; ++i) {
//             barStatus[i] = (val === Parameters.navBarInfo[i]);
//         }
//     }
// )
</script>