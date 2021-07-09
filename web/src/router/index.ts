import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import Home from '../views/home/index.vue'
import Wiki from '@/views/wiki/index.vue';
import About from '@/views/about/index.vue';
import EbookAdmin from '@/views/admin/ebook/index.vue';
import CategoryAdmin from '@/views/admin/category/index.vue'
import DocAdmin from '@/views/admin/doc/index.vue'
const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/wiki',
    name: 'Wiki',
    component: Wiki
  },
  {
    path: '/about',
    name: 'About',
    component: About
  },
  {
    path: '/admin/ebook',
    name: 'EbookAdmin',
    component: EbookAdmin
  },
  {
    path: '/admin/category/',
    name: 'CategoryAdmin',
    component: CategoryAdmin
  },
  {
    path: '/admin/doc/',
    name: 'DocAdmin',
    component: DocAdmin
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
