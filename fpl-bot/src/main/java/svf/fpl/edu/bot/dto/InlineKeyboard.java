package svf.fpl.edu.bot.dto;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class InlineKeyboard {
    private Long chatId;
    private String text;

    private List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
    private List<InlineKeyboardButton> row = null;

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<List<InlineKeyboardButton>> getKeyboard() {
        return keyboard;
    }

    public void setKeyboard(List<List<InlineKeyboardButton>> keyboard) {
        this.keyboard = keyboard;
    }

    public List<InlineKeyboardButton> getRow() {
        return row;
    }

    public void setRow(List<InlineKeyboardButton> row) {
        this.row = row;
    }
}
