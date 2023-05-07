import {createApp} from 'vue';
import {createRouter} from "vue-router";
import Main from './Main.vue'
import Home from './components/Home.vue'
import './css/bootstrap.css';
import './css/custom.css'

// const home = createApp(Home);
// const main = createApp(Main);
const routes = [{path: '/', component: Home}];
const router = VueRouter.createRouter({history: VueRouter.createWebHistory(), routes});
const app=createApp({});
app.use(router);
app.mount('#app');
// main.mount('#app');