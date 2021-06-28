<template>
  <a-layout style="display: flex;">

    <a-layout-sider>
      <a-menu
          mode="inline"
          v-model:selectedKeys="selectedKeys2"
          v-model:openKeys="openKeys"
          :style="{ height: '100%', borderRight: 0 }"
      >
        <a-sub-menu key="sub1">
          <template #title>
              <span>
                <user-outlined />
                subnav 1
              </span>
          </template>
          <a-menu-item key="1">option1</a-menu-item>
          <a-menu-item key="2">option2</a-menu-item>
          <a-menu-item key="3">option3</a-menu-item>
          <a-menu-item key="4">option4</a-menu-item>
        </a-sub-menu>
        <a-sub-menu key="sub2">
          <template #title>
              <span>
                <laptop-outlined />
                subnav 2
              </span>
          </template>
          <a-menu-item key="5">option5</a-menu-item>
          <a-menu-item key="6">option6</a-menu-item>
          <a-menu-item key="7">option7</a-menu-item>
          <a-menu-item key="8">option8</a-menu-item>
        </a-sub-menu>
        <a-sub-menu key="sub3">
          <template #title>
              <span>
                <notification-outlined />
                subnav 3
              </span>
          </template>
          <a-menu-item key="9">option9</a-menu-item>
          <a-menu-item key="10">option10</a-menu-item>
          <a-menu-item key="11">option11</a-menu-item>
          <a-menu-item key="12">option12</a-menu-item>
        </a-sub-menu>
      </a-menu>
    </a-layout-sider>

    <a-layout-content
        :style="{ background: '#fff',padding: '24px', margin: 0,width:'100%', minHeight: '280px' }">
      <a-table :columns="columns" :data-source="data" :loading="Loading">
        <template #name="{ text }">
          <a>{{ text }}</a>
        </template>
        <template #customTitle>
      <span>
        电子书名称
      </span>
        </template>
        <template #categoryIdList="{ text: categoryIdList }">
      <span>
        <a-tag
            v-for="tag in categoryIdList"
            :key="tag"
            :color="tag === 'loser' ? 'volcano' : tag.length > 5 ? 'geekblue' : 'green'"
        >
          {{ tag.toUpperCase() }}
        </a-tag>
      </span>
        </template>
        <template #action="{ record }">
      <span>
        <a>Invite 一 {{ record.name }}</a>
        <a-divider type="vertical" />
        <a>Delete</a>
        <a-divider type="vertical" />
        <a class="ant-dropdown-link">
          More actions
          <down-outlined />
        </a>
      </span>
        </template>
      </a-table>
    </a-layout-content>

  </a-layout>
</template>


<script lang="ts">
import {  DownOutlined } from '@ant-design/icons-vue';
import { defineComponent } from 'vue';
const columns = [
  {
    dataIndex: 'name',
    key: 'name',
    slots: { title: 'customTitle', customRender: 'name' },
  },
  {
    title: '描述',
    dataIndex: 'desc',
    key: 'desc',
  },
  {
    title: '分类',
    key: 'categoryIdList',
    dataIndex: 'categoryIdList',
    slots: { customRender: 'categoryIdList' },
  },
  {
    title: '封面',
    dataIndex: 'cover',
    key: 'cover',
  },
  {
    title: '文档数',
    dataIndex: 'docCount',
    key: 'docCount',
  },
  {
    title: '页面数',
    dataIndex: 'viewCount',
    key: 'viewCount',
  },
  {
    title: '点赞数',
    dataIndex: 'voteCount',
    key: 'voteCount',
  },
  {
    title: '创建时间',
    dataIndex: 'created',
    key: 'created',
  },
  {
    title: '更新时间',
    dataIndex: 'updated',
    key: 'updated',
  },
  {
    title: 'Action',
    key: 'action',
    slots: { customRender: 'action' },
  },
];

const data = [
  {
    key: '1',
    name: 'John Brown',
    age: 32,
    desc:"xxxxx",
    address: 'New York No. 1 Lake Park',
    categoryIdList: ['nice', 'developer'],
  },
  {
    key: '2',
    name: 'Jim Green',
    age: 42,
    desc:"xxxxx",
    address: 'London No. 1 Lake Park',
    categoryIdList: ['loser'],
  },
  {
    key: '3',
    name: 'Joe Black',
    age: 32,
    desc:"xxxxx",
    address: 'Sidney No. 1 Lake Park',
    categoryIdList: ['cool', 'teacher'],
  },
];

const Loading = false;

export default defineComponent({
  setup() {

    return {
      data,
      columns,
      Loading,
    };
  },
  components: {

    DownOutlined,
  },
});
</script>