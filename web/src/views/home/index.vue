<template>
  <a-row justify="center" style="margin-top: 20px">
    <a-col :span="16">
      <a-card title="轮播图" >
        <template #extra><a href="#">more</a></template>
        <p>card content</p>
        <p>card content</p>
        <p>card content</p>
      </a-card>
    </a-col>
  </a-row>
  <br>
  <a-row justify="center" :gutter="12">
    <a-col :span="12">
      <a-card title="精选导航" >
        <template #extra><a href="#">more</a></template>
        <p>card content</p>
        <p>card content</p>
        <p>card content</p>
      </a-card>
      <a-card title="文章" >
        <template #extra><a href="#">more</a></template>
        <p>card content</p>
        <p>card content</p>
        <p>card content</p>
      </a-card>
      <a-card title="文章" >
        <template #extra><a href="#">more</a></template>
        <p>card content</p>
        <p>card content</p>
        <p>card content</p>
      </a-card>
      <a-card title="文章" >
        <template #extra><a href="#">more</a></template>
        <p>card content</p>
        <p>card content</p>
        <p>card content</p>
      </a-card>
    </a-col>
    <a-col :span="4">
      <a-card title="作者信息" >
        <template #extra><a href="#">more</a></template>
        <p>card content</p>
        <p>card content</p>
        <p>card content</p>
        <p>card content</p>
        <p>card content</p>
        <p>card content</p>
      </a-card>

      <a-card title="标签" >
        <template #extra><a href="#">more</a></template>
        <p>card content</p>
        <p>card content</p>
        <p>card content</p>
        <p>card content</p>
        <p>card content</p>
        <p>card content</p>
      </a-card>

      <a-card title="项目推荐" >
        <template #extra><a href="#">more</a></template>
        <p>card content</p>
        <p>card content</p>
        <p>card content</p>
        <p>card content</p>
        <p>card content</p>
        <p>card content</p>
      </a-card>
    </a-col>
  </a-row>

  <br>
  <a-row justify="center" style="margin-top: 20px">
    <a-col :span="16">
      <a-card title="友链" >
        <template #extra><a href="#">more</a></template>
        <p>card content</p>
        <p>card content</p>
        <p>card content</p>
      </a-card>
    </a-col>
  </a-row>
</template>


<style scoped>
/* For demo */
.ant-carousel :deep(.slick-slide) {
  text-align: center;
  height: 460px;
  line-height: 460px;
  background: #364d79;
  overflow: hidden;
}

.ant-carousel :deep(.slick-slide h3) {
  color: #fff;
}
</style>