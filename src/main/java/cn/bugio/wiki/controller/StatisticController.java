package cn.bugio.wiki.controller;

import cn.bugio.wiki.common.CommonResult;
import cn.bugio.wiki.service.EbookSnapshotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/07/17
 */
@RestController
@RequestMapping("/statistics/")
public class StatisticController {

    private final EbookSnapshotService ebookSnapshotService;

    @Autowired
    public StatisticController(EbookSnapshotService ebookSnapshotService) {
        this.ebookSnapshotService = ebookSnapshotService;
    }

    @PostMapping("/get-statistics")
    public CommonResult getStatistics(){
        return ebookSnapshotService.getStatistics();
    }

    @PostMapping("/get-30-statistics")
    public CommonResult get30Statistics(){
        return ebookSnapshotService.get30Statistics();
    }
}
