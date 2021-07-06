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
                <user-outlined/>
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
                <laptop-outlined/>
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
                <notification-outlined/>
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

      <p>
        <a-input-search
            v-model:value="value"
            placeholder="书名"
            style="width: 200px"
            @search="onSearch"
        />
        <a-button type="primary" style="margin-left: 10px;" @click="showAdd">添加</a-button>
      </p>
      <a-table :columns="columns" :data-source="ebooks" :pagination="pagination" :loading="Loading" >
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
        <template #cover="{text}" >
          <img  :src="text" :width="70" :height="70"/>
        </template>
        <template #action="{ record }">
      <span>
        <a-button type="primary" style="margin-right: 10px;" @click="showEdit(record)">编辑</a-button>

        <a-popconfirm
            title="是否永久删除，存在不可恢复风险"
            ok-text="是"
            cancel-text="否"
            @confirm="handleDelete(record.id)"
        >
            <a-button type="primary">删除</a-button>
          </a-popconfirm>
      </span>
        </template>
      </a-table>
    </a-layout-content>

  </a-layout>

  <a-modal v-model:visible="visible" :title="modalTitle" @ok="handleEditOk">
    <a-form :model="curEbook" :label-col="{span:4}" :wrapper-col="wrapperCol">
      <a-form-item label="封面">
        <a-input v-model:value="curEbook.cover" />
      </a-form-item>
      <a-form-item label="名称">
        <a-input v-model:value="curEbook.name" />
      </a-form-item>
      <a-form-item label="分类">
        <a-select
            v-model:value="curEbook.categoryIdList"
            mode="tags"
            style="min-width: 200px;max-width: 300px;"
            :token-separators="[',']"
            @change="handleChange"
        >
          <a-select-option v-for="i in 25" :key="i">
            {{ i }}
          </a-select-option>
        </a-select>
      </a-form-item>

      <a-form-item label="描述">
        <a-input v-model:value="curEbook.desc" type="textarea" />
      </a-form-item>

    </a-form>
  </a-modal>
</template>


<script lang="ts">
import {DownOutlined} from '@ant-design/icons-vue';
import {defineComponent, onMounted, ref,reactive, toRaw, UnwrapRef} from 'vue';
import {ebookList, ebookSave, ebookDel, ebookSearch} from "@/api/ebook";
import {message} from "ant-design-vue";
import moment from 'moment'
import { Moment } from 'moment';
import { PlusOutlined, LoadingOutlined } from '@ant-design/icons-vue';
import {Tool} from "@/util/tool";

const columns = [
  {
    dataIndex: 'name',
    key: 'name',
    slots: {title: 'customTitle', customRender: 'name'},
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
    slots: {customRender: 'categoryIdList'},
  },
  {
    title: '封面',
    dataIndex: 'cover',
    key: 'cover',
    slots: {customRender: 'cover'},
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
    customRender: function (val:any) {
      return val ? moment(val).format('YYYY-MM-DD HH:mm:ss') : ''
    }
  },
  {
    title: '更新时间',
    dataIndex: 'updated',
    key: 'updated',
    // 时间格式化, 需要引入moment组件
    customRender: function (val:any) {
      return val ? moment(val).format('YYYY-MM-DD HH:mm:ss') : ''
    }
  },
  {
    title: 'Action',
    key: 'action',
    slots: {customRender: 'action'},
  },
];
const Loading = ref();
const ebooks = ref();
const getList = () => {
  ebookList().then((res) => {
    const data = res.data;
    if (data.code === 0) {
      ebooks.value = data.data;
      console.log(ebooks);
    } else {
      message.error(data.msg);
    }
    Loading.value = false;
  })
}

export default defineComponent({
  components: {

  },
  setup() {

    Loading.value = true;

    //启动执行
    onMounted(() => {
      console.log("x")
      getList();
    })
    //查询
    const onSearch = (keyword:any) => {
      ebookSearch(keyword).then((res) => {
        const data = res.data;
        if (data.code === 0) {
          ebooks.value = data.data;
          console.log(ebooks);
        } else {
          message.error(data.msg);
        }
        Loading.value = false;
      })
    }
    //分页
    const pagination = {
      onChange: (page: number) => {
        console.log(page);
      },
      pageSize: 10,
    };
    //对话框
    const visible = ref<boolean>(false);
    const modalTitle = ref();
    //编辑 弹出窗口
    const curEbook = ref();
    const showEdit = (ebook:any) => {
      visible.value = true;
      curEbook.value = Tool.copy(ebook);
      modalTitle.value = "编辑";
    };

    const showAdd = () => {
      visible.value = true;
      curEbook.value = {};
      modalTitle.value = '添加';
    }

    const handleEditOk = (e: MouseEvent) => {
      visible.value = false;
      console.log("保存/更新",curEbook.value)
      ebookSave(curEbook.value).then((res) => {
        const data = res.data;
        if (data.code === 0) {
          ebooks.value = data.data;
          message.success(data.msg);
        } else {
          message.error(data.msg);
        }
        getList();
        Loading.value = false;
      })

    };

    const  handleDelete = (id:number) => {
      ebookDel(id).then((res) => {
        const data = res.data;
        if (data.code === 0) {
          ebooks.value = data.data;
          message.success(data.msg);
        } else {
          message.error(data.msg);
        }
        getList();
        Loading.value = false;
      })
    }


    // const handleChange = (value: string) => {
    //   console.log(`selected ${value}`);
    // };





    return {
      ebooks,
      onSearch,
      columns,
      Loading,
      pagination,
      visible,
      showEdit,
      handleEditOk,
      showAdd,
      handleDelete,
      modalTitle,
      curEbook,
      getList,
      value: ref<string[]>([]),
    };
  },

});
</script>