import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import Home from '../views/home/index.vue'
import Wiki from '@/views/wiki/index.vue';
import About from '@/views/about/index.vue';
import EbookAdmin from '@/views/admin/ebook/index.vue';
import CategoryAdmin from '@/views/admin/category/index.vue'
import DocAdmin from '@/views/admin/doc/index.vue'
import Statistics from '@/views/admin/statistics/index.vue'
import Doc from '@/views/doc/index.vue'
import User from '@/views/admin/user/index.vue'
import {Tool} from "@/util/tool";
import store from "@/store";
import {message} from "ant-design-vue";
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
    component: EbookAdmin,
    meta:{
      loginRequire:true
    }
  },
  {
    path: '/admin/category',
    name: 'CategoryAdmin',
    component: CategoryAdmin,
    meta:{
      loginRequire:true
    }
  },
  {
    path: '/admin/doc',
    name: 'DocAdmin',
    component: DocAdmin,
    meta:{
      loginRequire:true
    }
  },
  {
    path: '/admin/statistics',
    name: 'Statistics',
    component: Statistics,
    meta:{
      loginRequire:true
    }
  },
  {
    path: '/admin/user',
    name: 'User',
    component: User,
    meta:{
      loginRequire:true
    }
  },
  {
    path: '/doc',
    name: 'Doc',
    component: Doc
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

// 路由登录拦截
router.beforeEach((to, from, next) => {
  // 要不要对meta.loginRequire属性做监控拦截
  if (to.matched.some(function (item) {
    console.log(item, "是否需要登录校验：", item.meta.loginRequire);
    return item.meta.loginRequire
  })) {
    const loginUser = store.state.user;
    if (Tool.isEmpty(loginUser)) {
     message.error("未登入不能访问");
      next('/');
    } else {
      next();
    }
  } else {
    next();
  }
});

export default router
