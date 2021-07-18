<template>
  <a-layout>
    <a-layout-content :style="{background:'#fff',padding:'24px',margin:0,minHeight:'280px'}">
      <h3 v-show="level1.length === 0">找不到相关文档!</h3>

      <a-row v-show="level1.length > 0">
        <a-col :span="6">
          <a-tree
            v-if="level1.length > 0"
            :tree-data="level1"
            @select="onSelect"
            :replaceFields="{title:'name',key:'id',value:'id'}"
            :defaultExpandAll="true"
            :defaultSelectedKeys="defaultSelectedKeys"
          >

          </a-tree>
        </a-col>
        <a-col :span="18">
          <p>{{ doc.name }}</p>
          <div style="display: flex;">
            <a-statistic :value="doc.viewCount"  >
              <template #suffix>
                <EyeOutlined/>
              </template>
            </a-statistic>
            <a-statistic :value="doc.voteCount" >
              <template #suffix>
                <like-outlined />
              </template>
            </a-statistic>
          </div>
          <a-divider style="border-color: #c5c5c5" dashed />

          <div class="wangeditor" :innerHTML="html"></div>


          <a-divider style="border-color: #c5c5c5;margin-top: 100px;" dashed />
          <div style="text-align: center">
            <a-button type="primary" :size="size" @click="addVote(doc.id)" :disabled="voteDisable">
              <template #icon>
                <like-outlined />
              </template>
              点赞 : {{doc.voteCount}}
            </a-button>
          </div>

        </a-col>
      </a-row>

    </a-layout-content>
  </a-layout>
</template>

<script lang="ts">
import {defineComponent, onMounted, ref} from 'vue';
import {docAddVote, docQueryContent, docSearch} from "@/api/doc";
import {message} from "ant-design-vue";
import {Tool} from "@/util/tool";
import {useRoute} from "vue-router";
import { LikeOutlined } from '@ant-design/icons-vue';


export default defineComponent({
  components: {
    LikeOutlined
  },
  setup() {

    //获取路由参数
    const route = useRoute();
    let ebookId = route.query.ebookId;

    const Loading = ref(true);
    const level1 = ref();
    level1.value = [];
    const defaultSelectedKeys = ref();
    defaultSelectedKeys.value = [];

    const handleContent = (id:number)=>{
      docQueryContent(id).then((res)=>{
        const rep = res.data;
        if (rep.code === 0){
          html.value = rep.data;
        } else {
          html.value=null;
          message.error(rep.msg);
        }
      })
    }

    const doc = ref();
    doc.value = {};
    const getList = (id: any) => {

      level1.value = [];
      docSearch(id,null).then((res) => {
        const data = res.data;
        if (data.code === 0) {
          level1.value = [];
          level1.value = Tool.array2Tree(data.data,0);

          if (Tool.isNotEmpty(level1)){
            handleContent(level1.value[0].id);
            doc.value = level1.value[0];
          }
        } else {
          message.error(data.msg);
        }
        Loading.value = false;
      })
    }
    //启动执行
    onMounted(() => {
      // editor.create();

      if (ebookId !== null){
        getList(ebookId);
      }

    })

    const html = ref();

    const onSelect = (selectedKeys:any,info:any) =>{

      if (Tool.isNotEmpty(selectedKeys)){
        handleContent(selectedKeys[0]);
        doc.value =info.selectedNodes[0].props;
        voteDisable.value = false;
      }
    }

    const voteDisable = ref(false);
    const addVote = (id:number) => {
      console.log(html.value)
      if (html.value === null){
        message.error("没有内容就不用点赞啦！");
        voteDisable.value = true;
        return false;
      }
      doc.value.voteCount = doc.value.voteCount +1;
      docAddVote(id).then((res) => {
        const rep =res.data;
        if (rep.code !== 0){
          message.info(rep.msg);
        }
      })
      voteDisable.value = true;
    }

    return {
      Loading,
      level1,
      html,
      onSelect,
      defaultSelectedKeys,
      doc,
      addVote,
      voteDisable
    };
  },

});
</script>


<style>
/* table 样式 */
.wangeditor table {
  border-top: 1px solid #ccc;
  border-left: 1px solid #ccc;
}
.wangeditor table td,
.wangeditor table th {
  border-bottom: 1px solid #ccc;
  border-right: 1px solid #ccc;
  padding: 3px 5px;
}
.wangeditor table th {
  border-bottom: 2px solid #ccc;
  text-align: center;
}

/* blockquote 样式 */
.wangeditor blockquote {
  display: block;
  border-left: 8px solid #d0e5f2;
  padding: 5px 10px;
  margin: 10px 0;
  line-height: 1.4;
  font-size: 100%;
  background-color: #f1f1f1;
}

/* 和antdv p 冲突 覆盖样式 */
.wangeditor blockquote p{
  font-family: "YouYuan";
  margin: 20px 10px !important;
  font-size: 16px !important;
  font-weight: 600;
}

/* code 样式 */
.wangeditor code {
  display: inline-block;
  *display: inline;
  *zoom: 1;
  background-color: #f1f1f1;
  border-radius: 3px;
  padding: 3px 5px;
  margin: 0 3px;
}
.wangeditor pre code {
  display: block;
}

/* ul ol 样式 */
.wangeditor ul, ol {
  margin: 10px 0 10px 20px;
}
/* 富文本图片自适应 */
.wangeditor img {
  max-width: 100%;
  height: auto;
}
.ant-statistic-content-value-int {
  font-size: 12px !important;
}

.ant-statistic-content{
  margin-right: 10px;
}
</style>