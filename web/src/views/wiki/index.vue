<template>
  <a-layout style="display: flex;">

    <a-layout-sider>
      <a-menu
          mode="inline"
          v-model:selectedKeys="selectedKeys2"
          v-model:openKeys="openKeys"
          :style="{ height: '100%', borderRight: 0 }"
          @click="handleClick"
      >
        <a-menu-item key="welcome">
          <PieChartOutlined />
          <span>欢迎</span>
        </a-menu-item>
        <a-sub-menu v-for="item in level" :key="item.id">
          <template #title>
              <span>
                <laptop-outlined />
                {{item.name}}
              </span>
          </template>
          <a-menu-item v-for="child in item.children" :key="child.id">{{ child.name }}</a-menu-item>
        </a-sub-menu>
      </a-menu>
    </a-layout-sider>

    <a-layout-content
        :style="{ background: '#fff',padding: '24px', margin: 0,width:'100%', minHeight: '280px' }">

      <div class="welcome" v-show="isShowWelcome">
        <h1>欢迎使用Bug IO 知识库</h1>
      </div>
      <a-list v-show="!isShowWelcome" item-layout="vertical" size="large" :pagination="pagination" :grid="{gutter :20,column:4}"
 :data-source="ebooks">

        <template #renderItem="{ item }">
          <a-list-item key="item.id">
            <template #actions>
          <span v-for="{ type, text } in actions" :key="type">
            <component v-bind:is="type" style="margin-right: 8px" />
            {{ text }}
          </span>
            </template>

            <a-list-item-meta :description="item.desc">
              <template #title>
                <a href="#">{{ item.name }}</a>
              </template>
              <template #avatar><a-avatar :src="item.cover" /></template>
            </a-list-item-meta>

          </a-list-item>
        </template>
      </a-list>
    </a-layout-content>

  </a-layout>
</template>

<script lang="ts">
import { defineComponent,onMounted,ref} from 'vue';
import {ebookList, ebookSearch} from "@/api/ebook";
import { message } from 'ant-design-vue';
import { StarOutlined,PieChartOutlined, LikeOutlined, MessageOutlined } from '@ant-design/icons-vue';

import {categoryList} from "@/api/category";
import {Tool} from "@/util/tool";

const Loading = ref();
const listData: Record<string, string>[] = [];

for (let i = 0; i < 23; i++) {
  listData.push({
    href: 'https://www.antdv.com/',
    title: `ant design vue part ${i}`,
    avatar: 'https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png',
    description:
        'Ant Design, a design language for background applications, is refined by Ant UED Team.',
    content:
        'We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.',
  });
}

export default defineComponent({
  name: 'app',
  components: {
    StarOutlined,
    LikeOutlined,
    MessageOutlined,
    PieChartOutlined,
  },
  setup(){

    //导航
    const level = ref();
    const getCategoryList = () => {
      categoryList().then((res) => {
        const data = res.data;
        if (data.code === 0) {
          level.value = [];
          level.value = Tool.array2Tree(data.data,0);
          console.log("分类 ",level.value);
        } else {
          message.error(data.msg);
        }
      })
    }
    const getList = (keyword:any,categoryId:number) => {
      ebookSearch(keyword,categoryId).then((res) =>{
        const data = res.data;
        if (data.code === 0){
          ebooks.value = data.data;
          console.log(ebooks);
        } else {
          message.error(data.msg);
        }
        Loading.value = false;

      })
    }

    const isShowWelcome = ref(true);
    const ebooks =ref();
    onMounted(()=>{
      Loading.value = true;
      getCategoryList();
      getList(undefined,0);
    })

    const pagination = {
      onChange: (page: number) => {
        console.log(page);
      },
      pageSize: 24,
    };

    let categoryId = 0;
    //导航栏点击事件
    const handleClick = (value: any) => {
      isShowWelcome.value = value.key === "welcome";
      if (!isShowWelcome.value){
        categoryId = value.key;
        getList(undefined,categoryId);
      }
    }
    const actions: Record<string, string>[] = [
      { type: 'StarOutlined', text: '156' },
      { type: 'LikeOutlined', text: '156' },
      { type: 'MessageOutlined', text: '2' },
    ];

    return {
      isShowWelcome,
      ebooks,listData,
      pagination,
      actions,
      level,
      handleClick
    };

  }
});
</script>

<style scoped>
  .ant-avatar {
    width: 50px;
    height: 50px;
    line-height: 50px;
    border-radius: 8%;
    margin: 5px 0;
  }
</style>