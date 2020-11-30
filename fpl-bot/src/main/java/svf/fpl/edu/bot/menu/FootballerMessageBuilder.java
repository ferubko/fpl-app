package svf.fpl.edu.bot.menu;

import com.svf.fpl.edu.dto.report.ElementType;
import com.svf.fpl.edu.dto.report.TopFootballerDto;
import com.svf.fpl.edu.service.FootballerStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

@Component
public class FootballerMessageBuilder {
    @Autowired
    private FootballerStatisticService footballerStatisticService;

    public SendMessage buildMessage(long chatId, ElementType elementType) {
        synchronized (this) {
            List<TopFootballerDto> top10BestGkps = footballerStatisticService.getTop10BestFootballers(elementType, 10);
            StringBuilder messageContent = new StringBuilder("Top ").append(elementType.getName()).append(":").append("\n");
            if (!top10BestGkps.isEmpty()) {
                top10BestGkps.forEach(r -> messageContent.append("<b>").append(r.getFootballerName()).append("</b>").append("\n")
                        .append("<u>").append("Average Points: ").append("</u>")
                        .append("<i>").append(r.getPointsPerGame()).append("</i>").append("\n")
                        .append("<u>").append("Total Points: ").append("</u>")
                        .append("<i>").append(r.getTotalPoints()).append("</i>").append("\n")
                        .append("<u>").append("Cost: ").append("</u>")
                        .append("<i>").append(r.getCost()).append("</i>")
                        .append("\n"));
            } else {
                messageContent.append("No ").append(elementType.getName()).append(" information");
            }
            SendMessage message = new SendMessage();
            message.setParseMode(ParseMode.HTML);
            message.setChatId(chatId);
            message.setText(messageContent.toString());
            return message;
        }
    }
}
