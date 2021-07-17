package cn.bugio.wiki.dao;

import cn.bugio.wiki.domain.entity.Doc;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface DocMapper extends Mapper<Doc> {

    int increaseViewCount(@Param("id") Long id);

    int increaseVoteCount(@Param("id") Long id);
}