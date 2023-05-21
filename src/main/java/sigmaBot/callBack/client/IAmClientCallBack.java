package sigmaBot.callBack.client;

import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChat;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import sigmaBot.Service.SendBotMessageService;
import sigmaBot.callBack.CallBack;
import sigmaBot.callBack.CallBackName;

import java.util.ArrayList;
import java.util.List;

public class IAmClientCallBack implements CallBack {
    private final SendBotMessageService sendBotMessageService;

    public IAmClientCallBack(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        System.out.println(update.getCallbackQuery().getMessage().getChatId());
        sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), "Выберите действие", getStartInlineKeyboardMarkup());
    }
    // Метод для формирования InlineKeyboardMarkup с сообщением "Выберите действие" и кнопками "Войти" и "Зарегистрироваться"
    public InlineKeyboardMarkup getStartInlineKeyboardMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row1 = new ArrayList<>();


        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText("Войти");
        inlineKeyboardButton1.setCallbackData(CallBackName.CLIENT_JOIN.toString());
        row1.add(inlineKeyboardButton1);


        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton2.setText("Зарегистрироваться");
        inlineKeyboardButton2.setCallbackData(CallBackName.CLIENT_REGISTRATION.toString());
        row1.add(inlineKeyboardButton2);


        rows.add(row1);

        inlineKeyboardMarkup.setKeyboard(rows);

        return inlineKeyboardMarkup;
    }



}
