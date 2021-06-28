<template>
  <a-layout-header class="header">

    <a-menu
        theme="dark"
        mode="horizontal"
        v-model:selectedKeys="selectedKeys1"
        :style="{ lineHeight: '64px' }"
    >
      <a-tag color="#e6f7ff00" key="title" style="width: 200px;text-align: center;font-size: 24px;font-weight: 600;color: #ffffff;font-family:'黑体';margin-left: 50px;margin-right: 100px;">八鸽栈 - Bugio</a-tag>
      <a-menu-item key="home"><router-link to="/">首页</router-link></a-menu-item>
      <a-menu-item key="wiki"><router-link to="/wiki">知识库</router-link></a-menu-item>
      <a-menu-item key="admin-ebook"><router-link to="/admin/ebook">电子书管理</router-link></a-menu-item>
      <a-menu-item key="about"><router-link to="/about">关于作者</router-link></a-menu-item>
    </a-menu>
  </a-layout-header>

</template>


<script lang="ts">
import { defineComponent } from 'vue';

export default defineComponent({
  name: 'the-header'
});
</script>

<style>

</style>