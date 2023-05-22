package sigmaBot.callBack.trainer;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import sigmaBot.Service.SendBotMessageService;
import sigmaBot.callBack.CallBack;
import sigmaBot.callBack.CallBackName;

import java.util.ArrayList;
import java.util.List;

public class TrainerJoinCallBack implements CallBack {
    private final SendBotMessageService sendBotMessageService;

    public TrainerJoinCallBack(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        System.out.println("dfdfdffdfdfd");
        sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), "Нажмите, если хотите увидеть список пользователей, отправивших заявку на редактирование", getStartInlineKeyboardMarkup());
    }

    public InlineKeyboardMarkup getStartInlineKeyboardMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row1 = new ArrayList<>();


        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText("Просмотр списка для редактирования");
        inlineKeyboardButton1.setCallbackData(CallBackName.TRAINER_REDACT.toString());
        row1.add(inlineKeyboardButton1);

        rows.add(row1);

        inlineKeyboardMarkup.setKeyboard(rows);

        return inlineKeyboardMarkup;
    }

}
