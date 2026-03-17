import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue';
import Purchase_Rings from '@/views/Purchase_Rings.vue';
import Manage_Orders from '@/views/Manage_Orders.vue';
import Cart from '@/views/Cart.vue';
import About_Us from '@/views/About_Us.vue';

const paths = [
  { path: '/', component: Home },
  { path: '/purchase-rings', component: Purchase_Rings },
  { path: '/manage-orders', component: Manage_Orders },
  { path: '/cart', component: Cart },
  { path: '/about-us', component: About_Us },
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: paths,
})

export default router
