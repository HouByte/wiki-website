<template>
  <div>
    <a-row>
      <a-col :span="24">
        <a-card>
          <a-row>
            <a-col :span="8">
              <a-statistic title="总阅读量" :value="statistic.viewCount" style="margin-right: 50px">
                <template #suffix>
                  <EyeOutlined/>
                </template>
              </a-statistic>
            </a-col>
            <a-col :span="8">
              <a-statistic title="总点赞量" :value="statistic.voteCount" style="margin-right: 50px">
                <template #suffix>
                  <like-outlined />
                </template>
              </a-statistic>
            </a-col>
            <a-col :span="8">
              <a-statistic title="点赞率" :value="statistic.likeRate" suffix="%" style="margin-right: 50px;color: #cf1322">
              </a-statistic>
            </a-col>
          </a-row>
        </a-card>
      </a-col>
    </a-row>
    <br>
    <a-row :gutter="16">
      <a-col :span="12">
        <a-card>
          <a-row>
            <a-col :span="12">
              <a-statistic title="今日阅读" :value="statistic.todayViewCount" style="margin-right: 50px">
                <template #suffix>
                  <EyeOutlined/>
                </template>
              </a-statistic>
            </a-col>
            <a-col :span="12">
              <a-statistic title="今日点赞" :value="statistic.todayVoteCount" style="margin-right: 50px">
                <template #suffix>
                  <like-outlined />
                </template>
              </a-statistic>
            </a-col>
          </a-row>
        </a-card>
      </a-col>
      <a-col :span="12">
        <a-card>
          <a-row>
            <a-col :span="12">
              <a-statistic title="预计今日阅读" :value="statistic.todayViewIncrease" style="margin-right: 50px">
                <template #suffix>
                  <EyeOutlined/>
                </template>
              </a-statistic>
            </a-col>
            <a-col :span="12">
              <a-statistic title="预计今日阅读增长" :value="statistic.todayViewIncreaseRateAbs" style="margin-right: 50px" :value-style="statistic.todayViewIncreaseRate < 0 ?{ color: '#3f8600' }:{ color: '#cf1322' }">
                <template #prefix>
                  <arrow-up-outlined v-if="statistic.todayViewIncreaseRate >= 0"/>
                  <arrow-down-outlined v-if="statistic.todayViewIncreaseRate < 0" />
                </template>
                <template #suffix>
                  <like-outlined />
                </template>
              </a-statistic>
            </a-col>
          </a-row>
        </a-card>
      </a-col>
    </a-row>
    <br>
    <a-row>
      <a-col :span="24">
        <div id="main" style="width: 100%;height: 500px;"></div>
      </a-col>
    </a-row>
  </div>
</template>


<script lang="ts">
import {defineComponent, onMounted, ref,reactive} from 'vue';
import { LikeOutlined } from '@ant-design/icons-vue';
import {get30Statistics, getStatistics} from "@/api/statistics";
declare let echarts:any;
export default defineComponent({
  name: 'the-statistics',
  components:{
    LikeOutlined
  },
  setup(){
    const statistics = ref();
    const statistic =  ref();
    statistic.value={
      viewCount:1,
      voteCount:1,
      todayViewCount:1,
      todayVoteCount:1,
      todayViewIncrease:1,
      todayVoteIncrease:1,
      likeRate:0
    };

    const init30DayEcharts = (list:any) => {
      const myChart = echarts.init(document.getElementById('main'));

      const xAxis = [];
      const seriesView = [];
      const seriesVote = [];
      for (let i = 0; i < list.length; i++) {
        const record = list[i];
        xAxis.push(record.date);
        seriesView.push(record.viewIncrease);
        seriesVote.push(record.voteIncrease);
      }

      let option = {
        title: {
          text: '30天趋势图'
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: ['总阅读量', '总点赞量']
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        toolbox: {
          feature: {
            saveAsImage: {}
          }
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: xAxis
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: '总阅读量',
            type: 'line',
            stack: '总量',
            data: seriesView,
            smooth:true
          },
          {
            name: '总点赞量',
            type: 'line',
            stack: '总量',
            data: seriesVote,
            smooth:true
          }
        ]
      };
      myChart.setOption(option);

    }

    onMounted(()=>{
      console.log("xxxx")
      getStatistics().then((res) => {
        let resp = res.data;
        console.log(resp)
        if (resp.code === 0){
          console.log(resp)
          statistics.value = resp.data;
          statistic.value.viewCount = statistics.value[1].viewCount;
          statistic.value.voteCount = statistics.value[1].voteCount;
          statistic.value.likeRate = (statistic.value.voteCount / statistic.value.viewCount * 100).toFixed(2);
          statistic.value.todayViewCount = statistics.value[1].viewIncrease;
          statistic.value.todayVoteCount =statistics.value[1].voteIncrease;

          //按分钟计算当前时间点，占-一天的百分比
          const now = new Date();
          const nowRate = (now.getHours() * 60 + now.getMinutes()) / (60 * 24);

          statistic.value.todayViewIncrease = parseInt(String(statistics.value[1].viewIncrease / nowRate));
          //今日预计增长率
          statistic.value.todayViewIncreaseRate = ((statistic.value.todayViewIncrease - statistics.value[0].viewIncrease) / statistics.value[0].viewIncrease * 100).toFixed(2);
          statistic.value.todayViewIncreaseRateAbs = Math.abs(statistic.value.todayViewIncreaseRate).toFixed(2);
        }
      })
      get30Statistics().then((res) => {
        let resp = res.data;
        if (resp.code === 0){
          init30DayEcharts(resp.data);
        }
      })
    })





    return {
      statistic
    }
  }
});
</script>