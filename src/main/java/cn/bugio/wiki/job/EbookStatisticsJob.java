package cn.bugio.wiki.job;

import cn.bugio.wiki.service.EbookService;
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

    private final SnowFlake snowFlake;
    @Autowired
    public EbookStatisticsJob(EbookService ebookService, SnowFlake snowFlake) {
        this.ebookService = ebookService;
        this.snowFlake = snowFlake;
    }


    /**
     * 凌晨四点更新，减少服务器繁忙时开销
     */
    @Scheduled(cron = "0 0 4 * * ? ")
    public void statisticsJob(){
        // 增加日志流水号
        MDC.put("LOG_ID", String.valueOf(snowFlake.nextId()));
        log.info("定时统计电子书数据开始");
        long start = System.currentTimeMillis();
        ebookService.updateEbookStatistics();
        log.info("定时统计电子书数据结束，耗时:{} 毫秒", System.currentTimeMillis()-start);
    }
}
