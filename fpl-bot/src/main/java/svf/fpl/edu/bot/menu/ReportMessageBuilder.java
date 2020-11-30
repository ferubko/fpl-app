package svf.fpl.edu.bot.menu;

import com.svf.fpl.edu.entity.LeagueMember;
import com.svf.fpl.edu.service.LeagueMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ReportMessageBuilder {
    @Autowired
    private LeagueMemberService leagueMemberService;

    public SendMessage buildLeagueMembersStatisticMessage(long chatId) {
        synchronized (this) {
            List<LeagueMember> savedLeagueMembers = leagueMemberService.getSavedLeagueMembers();
            StringBuilder messageContent = new StringBuilder("Statistic:").append("\n");

            if (!savedLeagueMembers.isEmpty()) {
                AtomicInteger i = new AtomicInteger(1);
                savedLeagueMembers.forEach(r -> messageContent.append("<b>")
                        .append("#").append(i.getAndIncrement()).append(" ")
                        .append(r.getPlayerName()).append("</b>")
                        .append(" ").append(r.getTotal()).append(" (+")
                        .append(r.getPoints()).append(")").append("\n"));
            } else {
                messageContent.append("No members");
            }
            SendMessage message = new SendMessage();
            message.setParseMode(ParseMode.HTML);
            message.setChatId(chatId);
            message.setText(messageContent.toString());
            return message;
        }
    }

    public SendMessage buildAccessDeniedMessage(long chatId) {
        synchronized (this) {
            StringBuilder messageContent = new StringBuilder("<b>").append("No permission to get statistics").append("</b>").append("\n");
            SendMessage message = new SendMessage();
            message.setParseMode(ParseMode.HTML);
            message.setChatId(chatId);
            message.setText(messageContent.toString());
            return message;
        }
    }

    public SendMessage buildAccessRequestMessage(long adminChatId, long chatId, String userName) {
        synchronized (this) {
            StringBuilder messageContent = new StringBuilder("<b>")
                    .append("Chat: ")
                    .append(chatId).append("\n")
                    .append(" User Name: ")
                    .append(userName).append("\n")
                    .append(" Requesting access")
                    .append("</b>").append("\n");
            SendMessage message = new SendMessage();
            message.setParseMode(ParseMode.HTML);
            message.setChatId(adminChatId);
            message.setText(messageContent.toString());
            return message;
        }
    }

    public SendMessage buildGrantedAccessMessage(long chatId, String userName) {
        synchronized (this) {
            StringBuilder messageContent = new StringBuilder("Dear ")
                    .append(userName)
                    .append(" access to ")
                    .append("<b>")
                    .append("fpl_bot")
                    .append("</b>")
                    .append(" was allowed")
                    .append("\n");
            SendMessage message = new SendMessage();
            message.setParseMode(ParseMode.HTML);
            message.setChatId(chatId);
            message.setText(messageContent.toString());
            return message;
        }
    }
}
