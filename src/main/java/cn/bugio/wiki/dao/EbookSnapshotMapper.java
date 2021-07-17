package cn.bugio.wiki.dao;

import cn.bugio.wiki.domain.entity.EbookSnapshot;
import cn.bugio.wiki.domain.vo.Statistic30Resp;
import cn.bugio.wiki.domain.vo.StatisticResp;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface EbookSnapshotMapper extends Mapper<EbookSnapshot> {

    int genEbookSnapshot();

    List<StatisticResp> getStatistic();

    List<Statistic30Resp> get30Statistic();
}