<template>
  <a-layout-footer class="footer">
    八鸽栈 Bugio © 2021 Vincent Vic <a href="https://beian.miit.gov.cn/" target="_blank">闽ICP备2020019789-1号</a>
  </a-layout-footer>

</template>


<script lang="ts">
import { defineComponent } from 'vue';

export default defineComponent({
  name: 'the-footer'
});
</script>

<style scoped >
.footer{

  text-align: center;
  position: fixed;
  left: 0px;
  bottom: 0px;
  width: 100%;
  height: 30px;
  background-color: #e0dbdb94;
  z-index: 9999;

}
</style>