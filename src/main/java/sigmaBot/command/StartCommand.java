package sigmaBot.command;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import sigmaBot.Service.SendBotMessageService;
import sigmaBot.callBack.CallBack;
import sigmaBot.callBack.CallBackName;

import java.util.ArrayList;
import java.util.List;

public class StartCommand implements Command{
    private final SendBotMessageService sendBotMessageService;

    private final String START_MESSAGE = "Добро пожаловать в наш бот для генерации программы тренировок, " +
            "для начала выберите, кто вы?";

    public StartCommand(SendBotMessageService sendBotMessageService){
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
       sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), START_MESSAGE, getMainMenuKeyboard());
    }

    // метод для генерации главного кнопочного меню, скорее всего надо закинуть в другой класс
    private InlineKeyboardMarkup getMainMenuKeyboard() {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        InlineKeyboardButton adminButton = new InlineKeyboardButton();
        adminButton.setText("Администратор");
        adminButton.setCallbackData(CallBackName.ADMIN.getCallBackName());
        InlineKeyboardButton clientButton = new InlineKeyboardButton();
        clientButton.setText("Клиент");
        clientButton.setCallbackData(CallBackName.CLIENT.getCallBackName());
        InlineKeyboardButton trainerButton = new InlineKeyboardButton();
        trainerButton.setText("Тренер");
        trainerButton.setCallbackData(CallBackName.TRAINER.getCallBackName());
        keyboardButtonsRow1.add(adminButton);
        keyboardButtonsRow1.add(clientButton);
        keyboardButtonsRow1.add(trainerButton);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        keyboardMarkup.setKeyboard(rowList);

        return keyboardMarkup;
    }
}
