<template>
  <a-carousel autoplay style="height: 360px">
    <img src="http://s.bugio.cn/img/1.jpg" />
    <img src="http://s.bugio.cn/img/2.jpg" />
    <img src="http://s.bugio.cn/img/3.jpg" />
    <img src="http://s.bugio.cn/img/4.jpg" />
  </a-carousel>


</template>


<style scoped>
/* For demo */
.ant-carousel :deep(.slick-slide) {
  text-align: center;
  height: 360px;
  line-height: 360px;
  background: #364d79;
  overflow: hidden;
}

.ant-carousel :deep(.slick-slide h3) {
  color: #fff;
}
</style>