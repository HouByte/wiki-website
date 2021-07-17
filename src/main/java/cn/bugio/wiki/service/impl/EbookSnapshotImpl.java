package cn.bugio.wiki.service.impl;

import cn.bugio.wiki.dao.EbookSnapshotMapper;
import cn.bugio.wiki.service.EbookSnapshotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public int genEbookSnapshot() {
        return ebookSnapshotMapper.genEbookSnapshot();
    }
}
