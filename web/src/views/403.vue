<template>
  <a-result status="403" title="403" sub-title="抱歉你没有这个页面权限">
    <template #extra>
      <a-button type="primary"><router-link to="/">返回首页</router-link></a-button>
    </template>
  </a-result>
</template>