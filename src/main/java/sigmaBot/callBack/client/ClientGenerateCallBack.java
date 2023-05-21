package sigmaBot.callBack.client;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import sigmaBot.Service.SendBotMessageService;
import sigmaBot.callBack.CallBack;
import sigmaBot.callBack.CallBackName;

import java.util.ArrayList;
import java.util.List;

public class ClientGenerateCallBack implements CallBack {
    private final SendBotMessageService sendBotMessageService;

    public ClientGenerateCallBack(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }
    @Override
    public void execute(Update update) {
      sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), "Что вам необходимо?", getStartInlineKeyboardMarkup());
    }
    public InlineKeyboardMarkup getStartInlineKeyboardMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row1 = new ArrayList<>();


        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText("Набор мышечной массы");
        inlineKeyboardButton1.setCallbackData(CallBackName.CLIENT_NABOR.getCallBackName().toString());
        row1.add(inlineKeyboardButton1);


        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton2.setText("Похудение");
        inlineKeyboardButton2.setCallbackData(CallBackName.CLIENT_XUDOI.toString());
        row1.add(inlineKeyboardButton2);

        rows.add(row1);

        inlineKeyboardMarkup.setKeyboard(rows);

        return inlineKeyboardMarkup;
    }
}
