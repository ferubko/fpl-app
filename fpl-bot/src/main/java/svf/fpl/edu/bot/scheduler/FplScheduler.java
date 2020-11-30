package svf.fpl.edu.bot.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import svf.fpl.edu.bot.service.FplReportService;
import svf.fpl.edu.bot.service.FplUpdateService;


@Slf4j
@Service
public class FplScheduler {

    @Autowired
    private FplReportService fplReportService;
    @Autowired
    private FplUpdateService fplUpdateService;
    @Value("${group.chat.id}")
    private long groupChatId;

    //    @Scheduled(cron = "0 36 21 * * THU,FRI")
    public void synchronizeData() {
        log.info("Start update data ");
        fplUpdateService.updateTeamMember();
        log.info("Stop update data");
    }

    //    @Scheduled(fixedRate = 10000)
    @Scheduled(cron = "${injury.time}")
    public void footballerInjuryReport() {
        log.info("Start generate footballer injury report ");
        fplReportService.sendInjuryMessage(groupChatId);
        log.info("Stop generate footballer injury report ");
    }
}
