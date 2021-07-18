<template>
  <a-layout-header class="header">

    <a-menu
        theme="dark"
        mode="horizontal"
        v-model:selectedKeys="selectedKeys1"
        :style="{ lineHeight: '64px' }"
    >
      <a-tag color="#e6f7ff00" key="title" style="width: 200px;text-align: center;font-size: 24px;font-weight: 600;color: #ffffff;font-family:'黑体';margin-left: 50px;margin-right: 100px;">八鸽栈 - Bugio</a-tag>
      <a-menu-item key="home"><router-link to="/">首页</router-link></a-menu-item>
      <a-menu-item key="wiki"><router-link to="/wiki">专栏</router-link></a-menu-item>
      <a-menu-item key="admin-ebook" :style="user.id ?{}:{display:'none'}"><router-link to="/admin/statistics/">后台管理</router-link></a-menu-item>
      <a-menu-item key="about"><router-link to="/about">关于作者</router-link></a-menu-item>
      <div class="login-menu" v-show="user.id">
        <a-avatar :src="user.avatar" style="margin-right: 5px;"/>
        {{user.name}}
        <a-popconfirm
            placement="bottom"
            title="是否确认退出登入"
            ok-text="是"
            cancel-text="否"
            @confirm="logout"
        >
        <a-button ghost style="margin-left: 5px;margin-right: 30px;">登出</a-button>
        </a-popconfirm>
      </div>
      <div class="login-menu" v-show="!user.id">
        <a-button ghost v-show="!user.id" @click="loginModalVisible = true">登入</a-button>
      </div>
    </a-menu>

    <a-modal  width="380px" v-model:visible="loginModalVisible" title="欢迎登入八鸽栈">
      <template #footer>
        <a-button class="login-btn" type="primary" :loading="loading" @click="handlerLogin" >登入</a-button>
      </template>
      <a-form :model="login" :label-col="{span:6}" :wrapper-col="wrapperCol">

        <a-form-item label="登入名">
          <a-input v-model:value="login.loginName"  style="width: 200px;" placeholder="请输入登入名" />
        </a-form-item>
        <a-form-item label="密码" >
          <a-input-password v-model:value="login.password" style="width: 200px;" placeholder="请输入密码" />
        </a-form-item>

      </a-form>
    </a-modal>
  </a-layout-header>


</template>


<script lang="ts">
import {defineComponent,computed, ref} from 'vue';
import {userLogin, userLogout} from "@/api/user";
import {message} from "ant-design-vue";
import store from "@/store";
declare let hexMd5: any;
declare let KEY: any;
export default defineComponent({
  name: 'the-header',
  setup(){
    const loading = ref(false);
    const loginModalVisible = ref(false);
    const login = ref();
    const resetLogin = () => {
      login.value = {
        loginName : '',
        password:''
      }
    }
    resetLogin();
    const user = computed(()=>store.state.user);
    const handlerLogin = () =>{

      login.value.password = hexMd5 (login.value.password + KEY);
      loading.value = true;
      userLogin(login.value).then((res) => {
        const data = res.data;
        if (data.code === 0) {
          store.commit("setUser",data.data);
          message.success(data.msg);
          loginModalVisible.value = false;
          console.log(data);
        } else {
          message.error(data.msg);
        }
        loading.value = false;
        resetLogin();
      })
    }

    const logout = () =>{
      userLogout(user.value.token).then((res)=>{
        const data = res.data;
        if (data.code === 0) {
          store.commit("setUser", {});
          message.success(data.msg);
        } else {
          message.error(data.msg);
        }
      })
    }

    return {
      loading,
      loginModalVisible,
      login,
      handlerLogin,
      user,
      logout
    }

  }
});
</script>

<style scoped>
.login-menu{
  margin-right: 40px;
  float: right;
  color: white;
}
.login-btn{
  display:block;
  margin:0 auto;
  width: 200px;
}

</style>