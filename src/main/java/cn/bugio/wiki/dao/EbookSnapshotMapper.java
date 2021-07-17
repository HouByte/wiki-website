package cn.bugio.wiki.dao;

import cn.bugio.wiki.domain.entity.EbookSnapshot;
import tk.mybatis.mapper.common.Mapper;

public interface EbookSnapshotMapper extends Mapper<EbookSnapshot> {

    int genEbookSnapshot();
}