package cn.bugio.wiki.service.impl;

import cn.bugio.wiki.common.CommonResult;
import cn.bugio.wiki.dao.EbookSnapshotMapper;
import cn.bugio.wiki.domain.vo.Statistic30Resp;
import cn.bugio.wiki.domain.vo.StatisticResp;
import cn.bugio.wiki.service.EbookSnapshotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/07/17
 */
@Service
@Slf4j
public class EbookSnapshotImpl implements EbookSnapshotService {

    private final EbookSnapshotMapper ebookSnapshotMapper;

    @Autowired
    public EbookSnapshotImpl(EbookSnapshotMapper ebookSnapshotMapper) {
        this.ebookSnapshotMapper = ebookSnapshotMapper;
    }

    /**
     * <h2>创建更新快照</h2>
     * @return
     */
    @Override
    public int genEbookSnapshot() {
        return ebookSnapshotMapper.genEbookSnapshot();
    }

    /**
     * <h2>获取总阅读、总点赞、今日阅读、今日点赞、今日阅读增长、今日点赞增长</h2>
     *
     * @return
     */
    @Override
    public CommonResult<List<StatisticResp>> getStatistics() {
        return CommonResult.success(ebookSnapshotMapper.getStatistic());
    }

    /**
     * <h2>获取30天 今日阅读、今日点赞、今日阅读增长、今日点赞增长</h2>
     *
     * @return
     */
    @Override
    public CommonResult<List<Statistic30Resp>> get30Statistics() {
        return CommonResult.success(ebookSnapshotMapper.get30Statistic());
    }
}
