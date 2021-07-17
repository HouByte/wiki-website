package cn.bugio.wiki.dao;

import cn.bugio.wiki.domain.entity.Doc;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tk.mybatis.mapper.entity.Example;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/07/17
 */
@SpringBootTest
public class DocMapperTest {

    @Autowired
    private DocMapper docMapper;

    @Test
    public void mapperTest(){
        Example example = new Example(Doc.class);
        example.createCriteria().andEqualTo("ebookId",1);
        int i = docMapper.selectCountByExample(example);
        System.out.println(i);
    }
}