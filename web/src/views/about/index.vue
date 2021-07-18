<template>
  <div class="about">
    <a-card title="关于作者" style="width: 900px">
      <a-row :gutter="12">
        <a-col :span="8">
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
        </a-col>
        <a-col :span="16">
          <a-descriptions  bordered size="2">
            <a-descriptions-item label="QQ" span="3">192391297</a-descriptions-item>
            <a-descriptions-item label="邮箱" span="3">192391297@qq.com</a-descriptions-item>
            <a-descriptions-item label="GitHub" span="3">https://github.com/Vincent-Vic</a-descriptions-item>
            <a-descriptions-item label="Gitee" span="3">https://gitee.com/Vincent-Vic</a-descriptions-item>


          </a-descriptions>
        </a-col>
      </a-row>
    </a-card>
  </div>
</template>

<style scoped >
.about{
  /*text-align: center; !*让div内部文字居中*!*/
  background-color: #fff;
  border-radius: 20px;
  width: 900px;
  height: 600px;
  margin: auto;
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
}
</style>
