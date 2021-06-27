<template>
  <a-layout-header class="header">

    <a-menu
        theme="dark"
        mode="horizontal"
        v-model:selectedKeys="selectedKeys1"
        :style="{ lineHeight: '64px' }"
    >
      <a-menu-item key="0" disabled = true>八鸽栈 - Bugio</a-menu-item>
      <a-menu-item key="1"><router-link to="/">首页</router-link></a-menu-item>
      <a-menu-item key="2"><router-link to="/wiki">知识库</router-link></a-menu-item>
      <a-menu-item key="3"><router-link to="/about">关于作者</router-link></a-menu-item>
    </a-menu>
  </a-layout-header>

</template>


<script lang="ts">
import { defineComponent } from 'vue';

export default defineComponent({
  name: 'the-header'
});
</script>