package svf.fpl.edu.bot.menu;

import com.svf.fpl.edu.dto.report.ElementType;
import com.svf.fpl.edu.entity.BotUser;
import com.svf.fpl.edu.service.BotUserService;
import lombok.SneakyThrows;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

import static svf.fpl.edu.bot.common.Constants.*;

@Component
@Slf4j
public class MenuBot extends TelegramLongPollingBot {
    @Value("${telegram.boot.name}")
    private String BOT_NAME;
    @Value("${telegram.boot.token}")
    private String API_KEY;
    @Value("${admin.chat.id}")
    private long adminChatId;

    public void setBOT_NAME(String BOT_NAME) {
        this.BOT_NAME = BOT_NAME;
    }

    public void setAPI_KEY(String API_KEY) {
        this.API_KEY = API_KEY;
    }

    @Autowired
    private MenuManager menuManager;
    @Autowired
    private InjuryMessageBuilder injuryMessageBuilder;
    @Autowired
    private FootballerMessageBuilder footballerMessageBuilder;
    @Autowired
    private ReportMessageBuilder reportMessageBuilder;
    @Autowired
    private BotUserService botUserService;

    @PostConstruct
    public void init() {
        menuManager.setColumnsCount(2);
        menuManager.init();
    }

    @SneakyThrows
    @Synchronized
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            long chatId = update.getMessage().getChatId();
            log.info("Chat id: {}", chatId);
            String firstName = update.getMessage().getFrom().getFirstName();
            log.info("Username: {}", firstName);
            if (update.getMessage().hasText()) {
                String messageFromUser = update.getMessage().getText();
                if (messageFromUser.equals("/start") || messageFromUser.equals("/menu")) {
                    createNavigationButtons(chatId);
                } else {
                    BotUser user = botUserService.selectBotUserFromChat(chatId);
                    if (user == null) {
                        user = botUserService.saveUserBot(chatId, firstName);
                        SendMessage message = reportMessageBuilder.buildAccessDeniedMessage(chatId);
                        execute(message);
                        message = reportMessageBuilder.buildAccessRequestMessage(adminChatId, chatId, firstName);
                        execute(message);
                        return;
                    } else if (!user.isAllowedToReceiveMessage()) {
                        SendMessage message = reportMessageBuilder.buildAccessDeniedMessage(chatId);
                        execute(message);
                        message = reportMessageBuilder.buildAccessRequestMessage(adminChatId, chatId, firstName);
                        execute(message);
                        return;
                    }
                    switch (messageFromUser) {
                        case injuryCommand:
                            log.info("Chat {} selected injury report", chatId);
                            SendMessage message = injuryMessageBuilder.buildInjuryMessage(chatId);
                            execute(message);
                            break;
                        case tableCommand:
                            log.info("Chat {} selected table report", chatId);
//                            message = footballerMessageBuilder.buildMessage(chatId, ElementType.GKP);
//                            execute(message);
                            sendStandings(chatId);
                            break;
                        case topGKP:
                            log.info("Chat {} selected GKP report", chatId);
                            message = footballerMessageBuilder.buildMessage(chatId, ElementType.GKP);
                            execute(message);
                            break;
                        case topDEF:
                            log.info("Chat {} selected DEF report", chatId);
                            message = footballerMessageBuilder.buildMessage(chatId, ElementType.DEF);
                            execute(message);
                            break;
                        case topMID:
                            log.info("Chat {} selected MID report", chatId);
                            message = footballerMessageBuilder.buildMessage(chatId, ElementType.MID);
                            execute(message);
                            break;
                        case topFWD:
                            log.info("Chat {} selected FWD report", chatId);
                            message = footballerMessageBuilder.buildMessage(chatId, ElementType.FWD);
                            execute(message);
                            break;
                    }
                }
            }
        } else if (update.hasCallbackQuery()) {
            log.info("Action was cancelled");
            CallbackQuery callbackQuery = update.getCallbackQuery();
            Message receivedMess = callbackQuery.getMessage();
            long chatId = receivedMess.getChatId();
            long messageId = receivedMess.getMessageId();
            String callData = callbackQuery.getData();
        }
    }

    private synchronized void createNavigationButtons(long chat_id) {
        SendMessage message = new SendMessage()
                .setText("Select item:")// Create a message object object
                .setChatId(chat_id);
        ReplyKeyboardMarkup markupInline = new ReplyKeyboardMarkup()
                .setSelective(true)
                .setResizeKeyboard(true)
                .setOneTimeKeyboard(true);
        List<KeyboardRow> statisticInline = new ArrayList<>();
        KeyboardRow statisticRow = generateStatisticButtons();
        statisticInline.add(statisticRow);
//        KeyboardRow generatorRow = getGenerateTeamButtons();
//        statisticInline.add(generatorRow);
        markupInline.setKeyboard(statisticInline);
        message.setReplyMarkup(markupInline);
        try {
            execute(message); // Sending our message object to user
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private KeyboardRow generateStatisticButtons() {
        KeyboardRow statisticRow = new KeyboardRow();
        KeyboardButton injuryButton = new KeyboardButton(injuryCommand);
        injuryButton.setRequestContact(false);
        statisticRow.add(injuryButton);
        KeyboardButton statisticButton = new KeyboardButton(tableCommand);
        statisticButton.setRequestContact(false);
        statisticRow.add(statisticButton);
//        KeyboardButton topGKPButton = new KeyboardButton(topGKP);
//        topGKPButton.setRequestContact(false);
//        statisticRow.add(topGKPButton);
//        KeyboardButton topDEFButton = new KeyboardButton(topDEF);
//        topDEFButton.setRequestContact(false);
//        statisticRow.add(topDEFButton);
//        KeyboardButton topMIDButton = new KeyboardButton(topMID);
//        topMIDButton.setRequestContact(false);
//        statisticRow.add(topMIDButton);
//        KeyboardButton topFWDButton = new KeyboardButton(topFWD);
//        topFWDButton.setRequestContact(false);
//        statisticRow.add(topFWDButton);
        return statisticRow;
    }

    public void sendTeamStatistic(long chat_id) {
        SendMessage message = new SendMessage() // Create a message object object
                .setChatId(chat_id)
                .setText("Team Statistic");
        ReplyKeyboardMarkup markupInline = new ReplyKeyboardMarkup()
                .setSelective(true)
                .setResizeKeyboard(true)
                .setOneTimeKeyboard(true);
//        List<KeyboardRow> rowInline = new ArrayList<>();
//        KeyboardRow row = new KeyboardRow();
//        KeyboardButton list = new KeyboardButton("Обрати зі списку");
//        list.setRequestContact(false);
//        KeyboardButton setCity = new KeyboardButton("Введіть назву міста");
//        setCity.setRequestContact(false);
//        row.add(list);
//        row.add(setCity);
//        rowInline.add(row);
//        markupInline.setKeyboard(rowInline);
        message.setReplyMarkup(markupInline);
        try {
            execute(message); // Sending our message object to user
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public String sendStandings(Long chatId) {
        try {

            SendMessage message = reportMessageBuilder.buildLeagueMembersStatisticMessage(chatId);
            Message execute = execute(message);// Sending our message object to user
            return execute.getCaption();
        } catch (TelegramApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void executeSendingMessage(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void executeSendingEditMessage(EditMessageText message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return API_KEY;
    }
}
