<template>
  <a-layout style="display: flex;">

    <the-admin-menu/>

    <a-layout-content
        :style="{ background: '#fff',padding: '24px', margin: 0,width:'100%', minHeight: '280px' }">

      <p>
        <a-input-search
            v-model:value="keyword"
            placeholder="用户名/昵称"
            style="width: 200px"
            @search="onSearch(keyword)"
        />
        <a-button type="primary" style="margin-left: 10px;" @click="showAdd">添加</a-button>
      </p>
      <a-table :columns="columns" :data-source="users" :pagination="pagination" :loading="Loading" >

        <template #avatar="{text}" >
          <a-avatar :src="text" />
        </template>
        <template #enable="{text}" >
          <a-tag :color="text ? 'success' : 'error'">
            <template #icon>
              <span  v-show="text">
                <check-circle-outlined />
              </span>
              <span  v-show="!text">
                <close-circle-outlined />
              </span>

            </template>
            <span  v-show="text">
              可用
            </span>
            <span  v-show="!text">
              不可用
            </span>
          </a-tag>
        </template>
        <template #action="{ record }">
      <span>
        <a-button type="primary" style="margin-right: 10px;" @click="showEdit(record)">编辑</a-button>
        <a-popconfirm
            :title="'是否确定'+ (record.enable ? '禁用':'启用')"
            ok-text="是"
            cancel-text="否"
            @confirm="showEdit(record)"
        >
            <a-button type="primary" style="margin-right: 10px;margin-bottom: 1px;margin-top: 1px;">{{record.enable ? '禁用':'启用'}}</a-button>
          </a-popconfirm>

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

  <a-modal v-model:visible="visible" :title="modalTitle" @ok="handleEditOk" ok-text="确认" cancel-text="取消">
    <a-form :model="curUser" :label-col="{span:4}" :wrapper-col="wrapperCol">

      <a-form-item label="头像">
        <a-input v-model:value="curUser.avatar" />
      </a-form-item>
      <a-form-item label="登入名">
        <a-input v-model:value="curUser.loginName" :disabled="curUser.id" placeholder="请输入登入名"/>
      </a-form-item>

      <a-form-item label="昵称">
        <a-input v-model:value="curUser.name" placeholder="请输入昵称"/>
      </a-form-item>
      <a-form-item label="邮箱">
        <a-input v-model:value="curUser.email" placeholder="请输入邮箱"/>
      </a-form-item>
      <a-form-item label="修改密码" v-show="curUser.id">
        <a-switch v-model:checked="isUpdatePass" >
          <template #checkedChildren><check-outlined /></template>
          <template #unCheckedChildren><close-outlined /></template>
        </a-switch>
      </a-form-item>
      <a-form-item label="密码" v-show="isUpdatePass">
        <a-input-password v-model:value="curUser.password" placeholder="请输入密码" />
      </a-form-item>

    </a-form>
  </a-modal>
</template>


<script lang="ts">
import {defineComponent, onMounted, ref} from 'vue';
import {userDel, userResetPassword, userSave, userSearch} from "@/api/user";
import {message} from "ant-design-vue";
import moment from 'moment'
import {Tool} from "@/util/tool";
import TheAdminMenu from '@/components/the-admin-menu.vue';
declare let hexMd5: any;
declare let KEY: any;

const columns = [
  {
    title: '头像',
    dataIndex: 'avatar',
    key: 'avatar',
    slots: {customRender: 'avatar'},
  },
  {
    title: '登入名',
    dataIndex: 'loginName',
    key: 'loginName'
  },
  {
    title: '昵称',
    dataIndex: 'name',
    key: 'name'
  },
  {
    title: '邮箱',
    dataIndex: 'email',
    key: 'email',
  },
  {
    title: '是否可用',
    dataIndex: 'enable',
    key: 'enable',
    slots: {customRender: 'enable'},
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
    const users = ref();
    //查询
    const onSearch = (keyword:any) => {
      userSearch(keyword).then((res) => {
        const data = res.data;
        if (data.code === 0) {
          users.value = data.data;
          console.log(users);
          Loading.value = false;
        } else {
          message.error(data.msg);
        }
        Loading.value = false;
      })
    }

    //启动执行
    onMounted(() => {
      onSearch(null);
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
    const isUpdatePass = ref(false);

    //编辑 弹出窗口
    const curUser = ref();
    const showEdit = (user:any) => {
      curUser.value = null;
      curUser.value = Tool.copy(user);
      isUpdatePass.value =  false;
      modalTitle.value = "编辑";
      visible.value = true;
    };

    const showAdd = () => {
      curUser.value = {};
      modalTitle.value = '添加';
      isUpdatePass.value = true;
      visible.value = true;
    }

    const handleEditOk = (e: MouseEvent) => {
      visible.value = false;
      let save = Tool.copy(curUser.value);
      save.password = hexMd5 ((curUser.value.password === undefined ? 'NoChangePassword1':curUser.value.password) + KEY);
      Loading.value = true;
      let response ;
      userSave(save).then((res) => {
        const data = res.data;
        if (data.code === 0) {
          message.success(data.msg);
          onSearch(null);
        } else {
          message.error(data.msg);
        }
        Loading.value = false;
      })

      if (!isUpdatePass.value){

        return true;
      }
      console.log("test")
      let resetPassword = {
        id:save.id,
        password:save.password
      }
      Loading.value = true;
      userResetPassword(resetPassword).then((res)=>{
        const data = res.data;
        if (data.code === 0) {
          message.success(data.msg);
          onSearch(null);
        } else {
          message.error(data.msg);
        }
        Loading.value = false;
      })



    };

    const  handleDelete = (id:number) => {
      userDel(id).then((res) => {
        const data = res.data;
        if (data.code === 0) {
          users.value = data.data;
          message.success(data.msg);
        } else {
          message.error(data.msg);
        }
        onSearch(null);
        Loading.value = false;
      })
    }

    





    return {
      users,
      onSearch,
      columns,
      Loading,
      pagination,
      visible,
      isUpdatePass,
      showEdit,
      handleEditOk,
      showAdd,
      handleDelete,
      modalTitle,
      curUser,
      keyword:ref(null),
    };
  },

});
</script>