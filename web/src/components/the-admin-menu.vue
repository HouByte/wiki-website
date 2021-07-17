<template>
  <a-layout-sider>
    <a-menu
        mode="inline"
        v-model:selectedKeys="selectedKeys2"
        v-model:openKeys="openKeys"
        :style="{ height: '100%', borderRight: 0 }"
    >
      <a-menu-item key="welcome">
        <PieChartOutlined />
        <span><router-link  to="/admin/statistics/">统计数据</router-link></span>
      </a-menu-item>
      <a-sub-menu key="sub1">
        <template #title>
              <span>
                <user-outlined/>
                Wiki管理
              </span>
        </template>
        <a-menu-item key="1"><router-link to="/admin/ebook">知识库管理</router-link></a-menu-item>
        <a-menu-item key="2"><router-link to="/admin/category">分类管理</router-link></a-menu-item>
      </a-sub-menu>
      <a-sub-menu key="sub2">
        <template #title>
              <span>
                <laptop-outlined/>
                系统管理
              </span>
        </template>
        <a-menu-item key="5">用户管理</a-menu-item>
        <a-menu-item key="6">首页管理</a-menu-item>
      </a-sub-menu>

    </a-menu>
  </a-layout-sider>

</template>


<script lang="ts">
import { defineComponent } from 'vue';

export default defineComponent({
  name: 'the-admin-menu'
});
</script>