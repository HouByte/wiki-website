<template>
  <a-layout>
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
    <router-view/>
    <a-layout-footer style="position:absolute;bottom: 10px;margin: auto;left: 0;right: 0;text-align: center">
                    八鸽栈 Bugio © 2021 Vincent Vic <a href="https://beian.miit.gov.cn/" target="_blank">闽ICP备2020019789-1号</a>
                  </a-layout-footer>
  </a-layout>
</template>

<style>

</style>

