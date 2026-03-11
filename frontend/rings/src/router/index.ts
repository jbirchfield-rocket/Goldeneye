import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue';


const paths = [
  { path: '/', component: Home },
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: paths,
})

export default router
