package sigmaBot.callBack.trainer;

import org.sqlite.core.DB;
import org.telegram.telegrambots.meta.api.objects.Update;
import sigmaBot.Service.SendBotMessageService;
import sigmaBot.bot.database.DBHandler;
import sigmaBot.callBack.CallBack;

import java.sql.SQLException;

public class NoRedactProg implements CallBack {
    DBHandler dbHandler;
    private final SendBotMessageService sendBotMessageService;

    public NoRedactProg(SendBotMessageService sendBotMessageService) {
        try {
            this.dbHandler = DBHandler.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        dbHandler.setRedactCurrentProg();
       sendBotMessageService.sendMessage( update.getCallbackQuery().getMessage().getChatId().toString(), "Мы оставили программу без изменений");
    }
}
