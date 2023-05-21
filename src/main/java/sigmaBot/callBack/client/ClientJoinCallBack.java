package sigmaBot.callBack.client;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import sigmaBot.Service.SendBotMessageService;
import sigmaBot.callBack.CallBack;
import sigmaBot.callBack.CallBackName;

import java.util.ArrayList;
import java.util.List;

public class ClientJoinCallBack implements CallBack {
    private final SendBotMessageService sendBotMessageService;

    public ClientJoinCallBack(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        System.out.println("execute dfdfdfddfdf");
        sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), "Выберите действие", getStartInlineKeyboardMarkup());

        //sendBotMessageService.sendMessage();
    }
    public InlineKeyboardMarkup getStartInlineKeyboardMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row1 = new ArrayList<>();


        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText("Ввести параметры тела");
        inlineKeyboardButton1.setCallbackData(CallBackName.CLIENT_VVOD_PARAM.getCallBackName().toString());
        row1.add(inlineKeyboardButton1);


        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton2.setText("Сгенерировать программу");
        inlineKeyboardButton2.setCallbackData(CallBackName.CLIENT_GENERATE.toString());
        row1.add(inlineKeyboardButton2);

        List<InlineKeyboardButton> row2 = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();
        inlineKeyboardButton3.setText("Просмотреть текущую программу");
        inlineKeyboardButton3.setCallbackData(CallBackName.CLIENT_PROSM_PROG.toString());
        row2.add(inlineKeyboardButton3);

        InlineKeyboardButton inlineKeyboardButton4 = new InlineKeyboardButton();
        inlineKeyboardButton4.setText("Просмотреть все программы");
        inlineKeyboardButton4.setCallbackData(CallBackName.PROSM_VSE_PROG.toString());
        row2.add(inlineKeyboardButton4);

        List<InlineKeyboardButton> row3 = new ArrayList<>();

        InlineKeyboardButton inlineKeyboardButton5 = new InlineKeyboardButton();
        inlineKeyboardButton5.setText("Просморт текущих параметров");
        inlineKeyboardButton5.setCallbackData(CallBackName.PROSM_CURRENT_PARAM.toString());
        row3.add(inlineKeyboardButton5);


        rows.add(row1);
        rows.add(row2);
        rows.add(row3);

        inlineKeyboardMarkup.setKeyboard(rows);

        return inlineKeyboardMarkup;
    }


}
