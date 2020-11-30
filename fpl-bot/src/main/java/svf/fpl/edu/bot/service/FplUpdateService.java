package svf.fpl.edu.bot.service;

import com.svf.fpl.edu.exeptions.FantasyPremierLeagueException;
import com.svf.fpl.edu.service.FplLoaderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static svf.fpl.edu.bot.common.Constants.SYNC_REPORT_MSG;

@Slf4j
@Service
public class FplUpdateService {
    @Autowired
    private FplLoaderService fplLoaderService;
    @Autowired
    private FplReportService fplReportService;
    @Value("${admin.chat.id}")
    private long adminChatId;

    public void updateTeamMember() {
        try {
            fplLoaderService.synchronizeFplData();
            fplReportService.sendResultMessage(adminChatId, SYNC_REPORT_MSG);
        } catch (FantasyPremierLeagueException ex) {
            fplReportService.sendResultMessage(adminChatId, ex.getMessage());
        }
    }
}
