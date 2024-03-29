<template>
  <a-layout style="display: flex;">

    <the-admin-menu/>

    <a-layout-content
        :style="{ background: '#fff',padding: '24px', margin: 0,width:'100%', minHeight: '280px' }">

      <p>
        <a-input-search
            v-model:value="keyword"
            placeholder="专栏名称"
            style="width: 200px"
            @search="onSearch(keyword)"
        />
        <a-button type="primary" style="margin-left: 10px;" @click="showAdd">添加</a-button>
      </p>
      <a-table :columns="columns" :data-source="ebooks" :pagination="pagination" :loading="Loading" >
        <template #name="{ text }">
          <a>{{ text }}</a>
        </template>
        <template #customTitle>
          <span>
            专栏名称
          </span>
        </template>
        <template #categoryIdList="{ text: categoryIdList }">
      <span>
        <a-tag
            v-for="tag in categoryIdList"
            :key="tag"
            :v-if="tag !== null"
            :color="tag === 'loser' ? 'volcano' : tag.length > 5 ? 'geekblue' : 'green'"
        >
          {{ getCategoryName(tag) }}
        </a-tag>

      </span>
        </template>
        <template #cover="{text}" >
          <img  :src="text" :width="70" :height="70"/>
        </template>
        <template #action="{ record }">
      <span>
        <a-button type="primary" style="margin-right: 10px;" @click="showEdit(record)">编辑</a-button>
        <a-button type="primary" style="margin-right: 10px;" @click="showEdit(record)">
          <router-link :to = "'/admin/doc?ebookId='+ record.id+'&eName='+record.name" >文档管理</router-link>
        </a-button>

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
        <a-cascader v-model:value="curEbook.categoryIdList" :options="categorys" :field-names="{label:'name',value:'id',children:'children'}" placeholder="Please select" />
      </a-form-item>

      <a-form-item label="描述">
        <a-input v-model:value="curEbook.desc" type="textarea" />
      </a-form-item>

    </a-form>
  </a-modal>

  <a-modal v-model:visible="visible" :title="modalTitle" @ok="handleEditOk" ok-text="确认" cancel-text="取消">
    <a-form :model="curEbook" :label-col="{span:4}" :wrapper-col="wrapperCol">
      <a-form-item label="封面">
        <a-input v-model:value="curEbook.cover" />
      </a-form-item>
      <a-form-item label="名称">
        <a-input v-model:value="curEbook.name" placeholder="请输入名称"/>
      </a-form-item>
      <a-form-item label="分类">
        <a-cascader v-model:value="curEbook.categoryIdList" :options="categorys" :field-names="{label:'name',value:'id',children:'children'}" placeholder="Please select" />
      </a-form-item>

      <a-form-item label="描述">
        <a-input v-model:value="curEbook.desc" type="textarea" placeholder="请填写描述"/>
      </a-form-item>

    </a-form>
  </a-modal>
</template>


<script lang="ts">
import {defineComponent, onMounted, ref} from 'vue';
import {ebookDel, ebookList, ebookSave, ebookSearch} from "@/api/ebook";
import {message} from "ant-design-vue";
import moment from 'moment'
import {Tool} from "@/util/tool";
import TheAdminMenu from '@/components/the-admin-menu.vue';
import {categoryList} from "@/api/category";
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




export default defineComponent({
  components: {
    TheAdminMenu
  },
  setup() {
    const Loading = ref(true);
    const ebooks = ref();
    //查询
    const onSearch = (keyword:any) => {
      ebookSearch(keyword,0).then((res) => {
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
    const categorys = ref();
    let categoryIds:any;
    const getCategorysList = () => {
      categoryList().then((res) => {
        const data = res.data;
        if (data.code === 0) {
          categoryIds = data.data;
          categorys.value = [];
          categorys.value = Tool.array2Tree(categoryIds,0);
          console.log(categorys.value);
        } else {
          message.error(data.msg);
        }
        Loading.value = false;
      })
    }

    const getCategoryName = (cid: number) => {
      // console.log(cid)
      let result = "";
      categoryIds.forEach((item: any) => {
        if (item.id === cid) {
          // return item.name; // 注意，这里直接return不起作用
          result = item.name;
        }
      });
      return result;
    };

    //启动执行
    onMounted(() => {
      console.log("x")
      onSearch(null);
      getCategorysList();
    })

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
      console.log("当前选择书籍 ",curEbook)
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
        onSearch(null);
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
        onSearch(null);
        Loading.value = false;
      })
    }





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
      categorys,
      getCategoryName,
      keyword:ref(null),
    };
  },

});
</script>