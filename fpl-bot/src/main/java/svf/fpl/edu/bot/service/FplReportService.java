package svf.fpl.edu.bot.service;

import com.svf.fpl.edu.service.LeagueMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import svf.fpl.edu.bot.menu.InjuryMessageBuilder;
import svf.fpl.edu.bot.menu.MenuBot;

import static svf.fpl.edu.bot.common.Constants.INJURY_REPORT_MSG;

@Slf4j
@Service
public class FplReportService {
    @Autowired
    private InjuryMessageBuilder injuryMessageBuilder;
    @Autowired
    private LeagueMemberService leagueMemberService;
    @Autowired
    private MenuBot menuBot;
    @Value("${admin.chat.id}")
    private long adminChatId;

    @Async
    public void sendInjuryMessage(long chatId) {
        try {
            SendMessage message = injuryMessageBuilder.buildInjuryMessage(chatId);
            menuBot.execute(message);
            sendResultMessage(adminChatId, INJURY_REPORT_MSG);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            sendResultMessage(adminChatId, e.getMessage());
        }
    }

    @Async
    public void sendResultMessage(long chatId, String messageContent) {
        try {
            SendMessage message = new SendMessage();
            message.setParseMode(ParseMode.HTML);
            message.setChatId(chatId);
            message.setText(messageContent);
            menuBot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
