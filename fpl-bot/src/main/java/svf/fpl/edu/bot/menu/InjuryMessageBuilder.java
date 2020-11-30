package svf.fpl.edu.bot.menu;

import com.svf.fpl.edu.dto.report.FootballerInjuryReportDto;
import com.svf.fpl.edu.service.FootballerStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

@Component
public class InjuryMessageBuilder {
    @Autowired
    private FootballerStatisticService footballerStatisticService;

    public SendMessage buildInjuryMessage(long chatId) {
        synchronized (this) {
            List<FootballerInjuryReportDto> report = footballerStatisticService.generateFootballerInjuryReport();
            StringBuilder messageContent = new StringBuilder("Injuries").append("\n");
            if (!report.isEmpty()) {
                report.forEach(r -> {
                    messageContent.append("<b>").append(r.getMemberName()).append("</b>").append("\n");
                    r.getFootballerInjurys().forEach(f -> messageContent
                            .append("<u>")
                            .append(f.getFootballerName())
                            .append("</u>")
                            .append(" -> ")
                            .append("<i>")
                            .append(f.getInjuryName())
                            .append("</i>")
                            .append("\n"));
                });
            } else {
                messageContent.append("No injured players");
            }
            SendMessage message = new SendMessage();
            message.setParseMode(ParseMode.HTML);
            message.setChatId(chatId);
            message.setText(messageContent.toString());
            return message;
        }
    }
}
