package cn.bugio.wiki.job;

import cn.bugio.wiki.service.EbookService;
import cn.bugio.wiki.service.EbookSnapshotService;
import cn.bugio.wiki.util.SnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/07/17
 */
@Component
@Slf4j
public class EbookStatisticsJob {

    private final EbookService ebookService;

    private final EbookSnapshotService ebookSnapshotService;

    private final SnowFlake snowFlake;
    @Autowired
    public EbookStatisticsJob(EbookService ebookService, EbookSnapshotService ebookSnapshotService, SnowFlake snowFlake) {
        this.ebookService = ebookService;
        this.ebookSnapshotService = ebookSnapshotService;
        this.snowFlake = snowFlake;
    }


    /**
     * 1,6,10,15,19,23更新
     */
    @Scheduled(cron = "0 0 1,6,10,15,19,23 * * ?")
    public void statisticsJob(){
        // 增加日志流水号
        MDC.put("LOG_ID", String.valueOf(snowFlake.nextId()));
        log.info("定时统计电子书数据开始");
        long start = System.currentTimeMillis();
        int count = ebookService.updateEbookStatistics();
        log.info("定时统计电子书数据结束,影响{}条数据，耗时:{} 毫秒",count, System.currentTimeMillis()-start);
    }

    /**
     * 1,6,10,15,19,23的30分钟更新
     */
    @Scheduled(cron = "0 30 1,6,10,15,19,23 * * ?")
    public void snapshotJob(){
        // 增加日志流水号
        MDC.put("LOG_ID", String.valueOf(snowFlake.nextId()));
        log.info("定时更新快照开始");
        long start = System.currentTimeMillis();
        int count = ebookSnapshotService.genEbookSnapshot();
        log.info("定时更新快照结束,影响{}条数据，耗时:{} 毫秒",count, System.currentTimeMillis()-start);
    }
}
