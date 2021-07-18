<template>
  <a-row justify="center" style="margin-top: 20px">
    <a-col :span="16">
      <a-card title="轮播图(首页开发中....)" >
        <a-skeleton />
      </a-card>
    </a-col>
  </a-row>
  <br>
  <a-row justify="center" :gutter="12">
    <a-col :span="12">
      <a-card title="精选导航(增量上线中....)" >
        <a-skeleton />
      </a-card>
      <a-card title="文章" >
        <a-skeleton />
      </a-card>
      <a-card title="文章" >
        <a-skeleton />
      </a-card>
      <a-card title="文章" >
        <a-skeleton />
      </a-card>
    </a-col>
    <a-col :span="4">
      <a-card hoverable style="width: 100%">
        <template #cover>
          <img
              alt="example"
              src="http://s.bugio.cn/img/bugio.jpg"
          />
        </template>
        <a-card-meta title="Bugio 八鸽栈" description="Vincent Vic 技术笔记">
          <template #avatar>
            <a-avatar src="http://s.bugio.cn/img/bugio.jpg" />
          </template>
        </a-card-meta>
      </a-card>

      <a-card title="标签" >
        <a-skeleton />
      </a-card>

      <a-card title="项目推荐" >
        <a-skeleton />
      </a-card>
    </a-col>
  </a-row>

  <br>
  <a-row justify="center" style="margin-top: 20px">
    <a-col :span="16">
      <a-card title="友链" >
        <a-skeleton />
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

.ant-card{
  margin-top: 10px;

}
</style>