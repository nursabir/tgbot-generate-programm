package sigmaBot.callBack.trainer;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import sigmaBot.Service.SendBotMessageService;
import sigmaBot.bot.database.DBHandler;
import sigmaBot.bot.fucntionality.TrainerFunctionality;
import sigmaBot.bot.generate.Exercise;
import sigmaBot.callBack.CallBack;
import sigmaBot.callBack.CallBackName;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrainerAddExercises implements CallBack {

    DBHandler dbHandler;
    private final SendBotMessageService sendBotMessageService;

    public TrainerAddExercises(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
        try {
            dbHandler = DBHandler.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void execute(Update update) {
        System.out.println("ашел");
        try {
            sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), "Выберите упражнения которые хотите включить в программу, помимо уже имеющихся", getKeyboard(TrainerFunctionality.getWhoIs()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public InlineKeyboardMarkup getKeyboard(String usernameChoiseUser) throws SQLException {
        List<Exercise> exercisesList = dbHandler.getTrainerChoiseExercises(usernameChoiseUser);

        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();

// Создаем список кнопок
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

// Для каждого объекта Exercises создаем кнопку и добавляем ее в список
        for (int i = 0; i < exercisesList.size(); i++) {
            Exercise exercises = exercisesList.get(i);
            // Создаем кнопку с названием exercises.name и callbackData exercises_N
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(exercises.getName());
            button.setCallbackData("exercises_" + exercises.getId_exercise());
            // Добавляем кнопку в список
            List<InlineKeyboardButton> row = new ArrayList<>();
            row.add(button);
            rows.add(row);
        }
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("Добавить упражнения \uD83E\uDD3A");
        button.setCallbackData(CallBackName.TRAINER_SEND_ADD_EXERCISES.getCallBackName());
        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(button);
        rows.add(row);

// Добавляем список кнопок в InlineKeyboardMarkup
        inlineKeyboard.setKeyboard(rows);
        return inlineKeyboard;
    }




}
