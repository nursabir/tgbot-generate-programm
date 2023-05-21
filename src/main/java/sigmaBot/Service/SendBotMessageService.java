package sigmaBot.Service;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public interface SendBotMessageService {
    void sendMessage(String chatId, String message);
    void sendMessage(String chatId, String message, InlineKeyboardMarkup inlineKeyboardMarkup);
    public void sendMessage(String chatId, String message, InlineKeyboardMarkup inlineKeyboardMarkup, boolean isReplace);
}
