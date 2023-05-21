package sigmaBot.Service;

import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChat;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import sigmaBot.bot.Bot;

public class SendBotMessageServiceImpl implements SendBotMessageService {
    private final Bot bot;

    public SendBotMessageServiceImpl(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void sendMessage(String chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.enableHtml(true);
        sendMessage.setText(message);

        try {
            bot.execute(sendMessage);
        } catch (TelegramApiException e) {
            //todo add logging to the project.
            e.printStackTrace();
        }
    }
    // для отправки с разметкой
    @Override
    public void sendMessage(String chatId, String message, InlineKeyboardMarkup inlineKeyboardMarkup) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.enableHtml(true);
        sendMessage.setText(message);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);



        try {
            bot.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void sendMessage(String chatId, String message, InlineKeyboardMarkup inlineKeyboardMarkup, boolean isReplace){


    }
//    public void replaceInlineKeyboardMarkup(String chatId, String text, InlineKeyboardMarkup inlineKeyboardMarkup) {
//        EditMessageText editMessageText = new EditMessageText()
//                .setChatId(chatId)
//                .setMessageId(getLastMessageId(chatId))
//                .setText(text)
//                .setReplyMarkup(inlineKeyboardMarkup);
//        try {
//            bot.execute(editMessageText);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//    }
//
//    // Метод для получения id последнего сообщения в чате
//    public int getLastMessageId(String chatId) {
//        GetChat chat = new GetChat(chatId);
//        Chat chatObject;
//        try {
//            chatObject = bot.execute(chat);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//            return 0;
//        }
//        return chatObject.getLastMes
//    }

}


