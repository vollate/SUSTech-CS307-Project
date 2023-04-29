<template>
    <header>

    </header>
    <main>
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark" id="nav-bar">
            <div class="container-fluid">
                <a class="navbar-brand">THE END</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarColor01"
                        aria-controls="navbarColor01" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarColor01">
                    <ul class="navbar-nav me-auto">
                        <template v-for="eachBar of nvBars">
                            <li class="nav-item" @click="$emit('change-nav',eachBar.name)">
                                <a class="nav-link active" :href="eachBar.hRef"
                                   v-if="eachBar.selected">{{ eachBar.name }}
                                    <span class="visually-hidden">(current)</span>
                                </a>
                                <a class="nav-link" :href="eachBar.hRef" v-else>{{ eachBar.name }}</a>
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
import {onBeforeMount, ref, watch} from "vue";

const Parameters = defineProps({
    selectedBar: {
        type: String,
        required: true
    },
    navBarInfo: {
        type: Array,
        required: true
    },
});
const emit = defineEmits(['change-nav']);

let navBarContent = [];
let nvBars;

onBeforeMount(() => {
    genNavBar();
})

function genNavBar() {
    for (let eachNav of Parameters.navBarInfo) {
        navBarContent.push({selected: false, hRef: eachNav[0], name: eachNav[1]});
        if (eachNav[1] === Parameters.selectedBar)
            navBarContent[navBarContent.length - 1].selected = true;
    }
    nvBars = ref(navBarContent);
}

watch(Parameters, async (newVal, oldVal) => {
    console.log(oldVal);

});

</script>