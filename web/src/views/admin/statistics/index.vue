<template>
  <div>
    <a-row>
      <a-col :span="4">
        <the-admin-menu/>
      </a-col>
      <a-col :span="20">
        <div class="welcome">
          <the-statistics/>
        </div>
      </a-col>
    </a-row>
  </div>


</template>


<script lang="ts">
import {defineComponent, onMounted, ref} from 'vue';
import TheAdminMenu from '@/components/the-admin-menu.vue';
import TheStatistics from '@/components/the-statistics.vue';
const Loading = ref();


export default defineComponent({
  components: {
    TheAdminMenu,
    TheStatistics
  },
  setup() {

    Loading.value = true;

    //启动执行
    // onMounted(() => {
    //
    //
    // })





    return {
      Loading
    };
  },

});
</script>

<style scoped>
.tip {
  padding: 10px 5px;
  margin-bottom: 20px;
  border: 1px solid transparent;
  background: linear-gradient(white,white) padding-box,repeating-linear-gradient(-45deg, black 0, black 25%, white 0, white 50%) 0/.6em .6em;
  animation:ants 12s linear infinite;
}
.tip b{
  color: red;
}

.ant-col-20 {
  padding: 10px;
}
</style>
