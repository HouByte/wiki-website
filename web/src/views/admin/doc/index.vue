<template>
  <a-layout style="display: flex;">

<!--    <the-admin-menu/>-->

    <a-layout-content
        :style="{ background: '#fff',padding: '24px', margin: 0,width:'100%', minHeight: '280px' }">

      <a-row type="flex" justify="space-between">
        <a-col :span="6">
          <p>
            <a-input-search
                v-model:value="value"
                placeholder="文档名称"
                style="width: 200px"
                @search="onSearch"
            />
            <a-button type="primary" style="margin-left: 10px;" @click="showAdd">添加</a-button>
          </p>
          <a-table v-if="level1.length > 0" :columns="columns" :data-source="level1" :pagination="false" :loading="Loading" size="small" :defaultExpandAllRows="true">
            <template #name="{ text,record }">
<!--              <a>{{ text }}</a>-->
              <a-button type="link" style="margin-left: 10px;" @click="showEdit(record)">{{ text }}</a-button>
            </template>
            <template #customTitle>
          <span>
            文档名称
          </span>
            </template>
            <template #action="{ record }">
              <span>
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
        </a-col>

        <a-col :span="18">
          <div v-show="firstLoad">
            <h1>管理文档</h1>
          </div>
          <div v-show="!firstLoad">
            <p>
              <a-tag color="blue">{{modalTitle}}</a-tag>
            </p>
            <a-form :model="curDoc" :label-col="{span:4}" :wrapper-col="wrapperCol" layout = "vertical">
              <a-form-item >
                <a-input v-model:value="curDoc.name" placeholder="请输入文档名称" />
              </a-form-item>
              <a-form-item >
                <a-tree-select
                    v-model:value="curDoc.parent"
                    style="width: 100%"
                    :dropdown-style="{ maxHeight: '400px', overflow: 'auto' }"
                    :tree-data="treeSelectData"
                    placeholder="请选择父文档"
                    tree-default-expand-all
                    :replaceFields="{title: 'name',key:'id',value:'id'}"
                >
                </a-tree-select>
              </a-form-item>

              <a-form-item >
                <a-input v-model:value="curDoc.sort"  placeholder="请输入指定排序"/>
              </a-form-item>
              <a-form-item >
                <div id="contentEditor"></div>
              </a-form-item>

            </a-form>
            <a-button type="primary" style="margin-left: 10px;margin-right: 10px;" @click="handlePreviewContent">
              <EyeOutlined/>预览
            </a-button>
            <a-button type="primary" style="margin-left: 10px;margin-right: 10px;" @click="handleSave">保存</a-button>
            <a-popconfirm
                title="是否永久删除，存在不可恢复风险"
                ok-text="是"
                cancel-text="否"
                @confirm="handleDelete(record.id)"
            >
              <a-button type="primary" style="margin-left: 10px;margin-right: 10px;" >删除</a-button>
            </a-popconfirm>
          </div>

        </a-col>
      </a-row>
    </a-layout-content>

  </a-layout>


  <a-drawer width="900" placement="right" :closable="false" :visible="drawerVisible" @close="onDrawerClose">
    <div class="wangeditor" :innerHTML="previewHtml"></div>
  </a-drawer>

</template>


<script lang="ts">
import {createVNode, defineComponent, onMounted, ref} from 'vue';
import {docDel, docSave, docSearch,docQueryContent} from "@/api/doc";
import {message, Modal} from "ant-design-vue";
import {Tool} from "@/util/tool";
import {useRoute} from "vue-router";
import { ExclamationCircleOutlined } from '@ant-design/icons-vue';
import E from "wangeditor";
const columns = [
  {
    dataIndex: 'name',
    key: 'name',
    slots: {title: 'customTitle', customRender: 'name'},
  }
];



export default defineComponent({
  components: {
    // TheAdminMenu
  },
  setup() {

    //获取路由参数
    const route = useRoute();
    let ebookId = route.query.ebookId;
    // console.log("路由：", route);
    // console.log("route.path：", route.path);
    // console.log("route.query：", route.query);
    // console.log("route.param：", route.params);
    // console.log("route.fullPath：", route.fullPath);
    // console.log("route.name：", route.name);
    // console.log("route.meta：", route.meta);
    const Loading = ref(true);
    const firstLoad = ref(true);
    const docs = ref();

    const treeSelectData = ref();
    treeSelectData.value = [];
    //富文本编辑器
    const editor = new E('#contentEditor');
    editor.config.zIndex = 0;

    const level1 = ref();
    level1.value = [];
    //查询
    const onSearch = (ebookId:any,keyword:any) => {
      console.log("ebook id ",ebookId)
      if (ebookId === null){
        return false;
      }
      docSearch(ebookId,keyword).then((res) => {
        const data = res.data;
        if (data.code === 0) {
          docs.value = data.data;
          console.log(docs);
          level1.value = [];
          level1.value = Tool.array2Tree(docs.value,0);
          // 不能选择当前节点及其所有子孙节点，作为父节点，会使树断开
          treeSelectData.value = Tool.copy(level1.value);
          // 为选择树添加一个"无"
          treeSelectData.value.unshift({id: 0, name: '无'});
        } else {
          message.error(data.msg);
        }
        Loading.value = false;
      })
    }
    //启动执行
    onMounted(() => {
      editor.create();
      onSearch(ebookId,null);
    })

    //分页
    const pagination = {
      onChange: (page: number) => {
        console.log(page);
      },
      pageSize: 10,
    };


    /**
     * 将某节点及其子孙节点全部置为disabled
     */
    const setDisable = (treeSelectData: any, id: any) => {
      // console.log(treeSelectData, id);
      // 遍历数组，即遍历某一层节点
      for (let i = 0; i < treeSelectData.length; i++) {
        const node = treeSelectData[i];
        if (node.id === id) {
          // 如果当前节点就是目标节点
          console.log("disabled", node);
          // 将目标节点设置为disabled
          node.disabled = true;

          // 遍历所有子节点，将所有子节点全部都加上disabled
          const children = node.children;
          if (Tool.isNotEmpty(children)) {
            for (let j = 0; j < children.length; j++) {
              setDisable(children, children[j].id)
            }
          }
        } else {
          // 如果当前节点不是目标节点，则到其子节点再找找看。
          const children = node.children;
          if (Tool.isNotEmpty(children)) {
            setDisable(children, id);
          }
        }
      }
    };

    let deleteIds: Array<number> = [];
    let deleteNames: Array<string> = [];
    /**
     * 查找整根树枝
     */
    const getDeleteIds = (data: any, id: any) => {
      // 遍历数组，即遍历某一层节点
      for (let i = 0; i < data.length; i++) {
        const node = data[i];
        if (node.id === id) {
          // 如果当前节点就是目标节点
          // 将目标ID放入结果集ids
          // node.disabled = true;
          deleteIds.push(id);
          deleteNames.push(node.name);
          console.log("delete id node  ==> ",id);
          // 遍历所有子节点
          const children = node.children;
          if (Tool.isNotEmpty(children)) {
            for (let j = 0; j < children.length; j++) {
              getDeleteIds(children, children[j].id)
            }
          }
        } else {
          // 如果当前节点不是目标节点，则到其子节点再找找看。
          const children = node.children;
          if (Tool.isNotEmpty(children)) {
            getDeleteIds(children, id);
          }
        }
      }
    };




    //表单
    const modalTitle = ref("添加");
    const curDoc = ref();
    curDoc.value = {
      ebookId:undefined,
      parent:undefined,
      name:undefined,
      sort:undefined
    };
    const showEdit = (doc:any) => {
      editor.txt.clear();
      curDoc.value = Tool.copy(doc);
      modalTitle.value = "编辑";
      docQueryContent(doc.id).then((res) => {
        const data = res.data;
        if (data.code === 0) {
          editor.txt.html(data.data);
        }
      });

      firstLoad.value = false;
    };

    const showAdd = () => {
      editor.txt.clear();
      curDoc.value = {
        ebookId:ebookId,
        parent:0
      };
      modalTitle.value = '添加';
      firstLoad.value = false;
    }

    const handleSave = (e: MouseEvent) => {
      curDoc.value.content = editor.txt.html();
      console.log(curDoc);
      docSave(curDoc.value).then((res) => {
        const data = res.data;
        if (data.code === 0) {
          // docs.value = data.data;
          message.success(data.msg);
        } else {
          message.error(data.msg);
        }
        onSearch(ebookId,null);
        Loading.value = false;
      });

    };

    const  handleDelete = (id:number) => {
      deleteIds = [];
      // 清空数组，否则多次删除时，数组会一直增加
      deleteIds.length = 0;
      deleteNames = [];
      deleteNames.length = 0;
      getDeleteIds(level1.value,id)

      //二次确认
      Modal.confirm({
        title: '重要提醒',
        icon: createVNode(ExclamationCircleOutlined),
        content: '将删除：【' + deleteNames.join("，") + "】删除后不可恢复，确认删除？",
        onOk() {
          docDel(deleteIds).then((res) => {
            const data = res.data;
            if (data.code === 0) {
              // docs.value = data.data;
              message.success(data.msg);
            } else {
              message.error(data.msg);
            }
            onSearch(ebookId,null);
            Loading.value = false;
          })
        }
      });

    }

    const previewHtml = ref();
    const drawerVisible = ref(false);
    const handlePreviewContent = () => {
      drawerVisible.value = true
      previewHtml.value = editor.txt.html();
    }

    const onDrawerClose = () =>{
      drawerVisible.value = false
    }

    return {
      firstLoad,
      docs,
      onSearch,
      columns,
      Loading,
      pagination,
      showEdit,
      handleSave,
      showAdd,
      handleDelete,
      modalTitle,
      curDoc,
      level1,
      treeSelectData,
      value: ref<string[]>([]),
      drawerVisible,
      previewHtml,
      handlePreviewContent,
      onDrawerClose,

    };
  },

});
</script>