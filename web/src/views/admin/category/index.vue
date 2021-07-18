<template>
  <a-layout style="display: flex;">

    <the-admin-menu/>

    <a-layout-content
        :style="{ background: '#fff',padding: '24px', margin: 0,width:'100%', minHeight: '280px' }">

      <p>
        <a-input-search
            v-model:value="value"
            placeholder="分类名称"
            style="width: 200px"
            @search="onSearch"
        />
        <a-button type="primary" style="margin-left: 10px;" @click="showAdd">添加</a-button>
      </p>
      <a-table :columns="columns" :data-source="level1" :pagination="pagination" :loading="Loading" >
        <template #name="{ text }">
          <a>{{ text }}</a>
        </template>
        <template #customTitle>
          <span>
            分类名称
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
    <a-form :model="curCategory" :label-col="{span:4}" :wrapper-col="wrapperCol">
      <a-form-item label="名称">
        <a-input v-model:value="curCategory.name" />
      </a-form-item>
      <a-form-item label="父分类">
        <a-select
            v-model:value="curCategory.parent"
            style="width: 180px"
            ref="select"
        >
          <a-select-option value="0">无</a-select-option>
          <a-select-option v-for="c in level1" :key="c.id" :value="c.id" :disable="curCategory.id === c.id">{{c.name}}</a-select-option>
        </a-select>
      </a-form-item>

      <a-form-item label="排序">
        <a-input v-model:value="curCategory.sort"  />
      </a-form-item>

    </a-form>
  </a-modal>
</template>


<script lang="ts">
import {defineComponent, onMounted, ref} from 'vue';
import {categoryDel, categoryList, categorySave, categorySearch} from "@/api/category";
import {message} from "ant-design-vue";
import {Tool} from "@/util/tool";
import TheAdminMenu from '@/components/the-admin-menu.vue';

const columns = [
  {
    dataIndex: 'name',
    key: 'name',
    slots: {title: 'customTitle', customRender: 'name'},
  },
  {
    title: '父分类',
    dataIndex: 'parent',
    key: 'parent',
  },
  {
    title: '排序',
    dataIndex: 'sort',
    key: 'sort',
  },
  {
    title: 'Action',
    key: 'action',
    slots: {customRender: 'action'},
  },
];
const Loading = ref();
const categorys = ref();
const level1 = ref();
const getList = () => {
  categoryList().then((res) => {
    const data = res.data;
    if (data.code === 0) {
      categorys.value = data.data;
      level1.value = [];
      level1.value = Tool.array2Tree(categorys.value,0) || [];
      console.log(level1.value);
    } else {
      message.error(data.msg);
    }
    Loading.value = false;
  })
}

export default defineComponent({
  components: {
    TheAdminMenu
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
      categorySearch(keyword).then((res) => {
        const data = res.data;
        if (data.code === 0) {
          categorys.value = data.data;
          console.log(categorys);
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
    const curCategory = ref();
    const showEdit = (category:any) => {
      visible.value = true;
      curCategory.value = Tool.copy(category);
      modalTitle.value = "编辑";
    };

    const showAdd = () => {
      visible.value = true;
      curCategory.value = {};
      modalTitle.value = '添加';
    }

    const handleEditOk = (e: MouseEvent) => {
      visible.value = false;
      console.log("保存/更新",curCategory.value)
      categorySave(curCategory.value).then((res) => {
        const data = res.data;
        if (data.code === 0) {
          categorys.value = data.data;
          message.success(data.msg);
        } else {
          message.error(data.msg);
        }
        getList();
        Loading.value = false;
      })

    };

    const  handleDelete = (id:number) => {
      categoryDel(id).then((res) => {
        const data = res.data;
        if (data.code === 0) {
          categorys.value = data.data;
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
      categorys,
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
      curCategory,
      getList,
      level1,
      value: ref<string[]>([]),
    };
  },

});
</script>