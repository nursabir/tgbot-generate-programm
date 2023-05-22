package sigmaBot.callBack.trainer;

import org.telegram.telegrambots.meta.api.objects.Update;
import sigmaBot.Service.SendBotMessageService;
import sigmaBot.bot.database.DBHandler;
import sigmaBot.callBack.CallBack;

import java.sql.SQLException;

public class TrainerSendAddExercisesCallBack implements CallBack {
    private final SendBotMessageService sendBotMessageService;
    DBHandler dbHandler;


    public TrainerSendAddExercisesCallBack(SendBotMessageService sendBotMessageService) {
        try {
            this.dbHandler = DBHandler.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        this.sendBotMessageService = sendBotMessageService;
    }
     // здесь по id выбранных упражнений их нужно добавить в текущую программу клиента
    @Override
    public void execute(Update update) {
        dbHandler.updateCurrentProgram();
       sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), "Мы сохранили ваши изменения");
    }
}
