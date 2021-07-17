package cn.bugio.wiki.service;

import cn.bugio.wiki.common.CommonResult;
import cn.bugio.wiki.domain.vo.Statistic30Resp;
import cn.bugio.wiki.domain.vo.StatisticResp;

import java.util.List;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/07/17
 */
public interface EbookSnapshotService {

    /**
     * <h2>创建更新快照</h2>
     * @return
     */
    int genEbookSnapshot();

    /**
     * <h2>获取总阅读、总点赞、今日阅读、今日点赞、今日阅读增长、今日点赞增长</h2>
     * @return
     */
    CommonResult<List<StatisticResp>> getStatistics();

    /**
     * <h2>获取30天 今日阅读、今日点赞、今日阅读增长、今日点赞增长</h2>
     * @return
     */
    CommonResult<List<Statistic30Resp>> get30Statistics();
}
