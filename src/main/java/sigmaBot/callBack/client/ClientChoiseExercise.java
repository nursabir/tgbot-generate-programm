package sigmaBot.callBack.client;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import sigmaBot.Service.SendBotMessageService;
import sigmaBot.bot.database.DBHandler;
import sigmaBot.callBack.CallBack;
import sigmaBot.callBack.CallBackName;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientChoiseExercise implements CallBack {

    DBHandler db;
    private final SendBotMessageService sendBotMessageService;

    public ClientChoiseExercise(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
        try {
            this.db = DBHandler.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), "Выберите из списка упражнения, которые Вы бы хотели видеть в вашей программе тренировок:", getKeyboard());
    }

    public InlineKeyboardMarkup getKeyboard() {
        try {
            // Получаем данные из ArrayList<ArrayList<Object>>
            List<ArrayList<Object>> tableData = db.getAllExercise();
            // Создаем список кнопок
            List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
            for (ArrayList<Object> row : tableData) {
                int i = 0;
                Object id = null;
                ArrayList<InlineKeyboardButton> rowButtons = new ArrayList<>();
                for (Object value : row) {
                    if(i==0){
                        id = value;
                    }
                    if (i == 1) {
                        InlineKeyboardButton button = new InlineKeyboardButton();
                        button.setText(value.toString());
                        button.setCallbackData(id.toString() + "_exercise");
                        rowButtons.add(button);
                    }
                    i++;
                }
                keyboard.add(rowButtons);
            }
            ArrayList<InlineKeyboardButton> lastRow = new ArrayList<>();
            InlineKeyboardButton but = new InlineKeyboardButton();
            but.setText("\u2705");
            but.setCallbackData(CallBackName.CLIENT_SEND_CHOICE.toString());
            lastRow.add(but);
            keyboard.add(lastRow);


            InlineKeyboardMarkup markup = new InlineKeyboardMarkup(keyboard);
            return markup;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}





