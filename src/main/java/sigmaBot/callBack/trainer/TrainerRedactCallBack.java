package sigmaBot.callBack.trainer;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import sigmaBot.Service.SendBotMessageService;
import sigmaBot.bot.database.DBHandler;
import sigmaBot.bot.generate.Client;
import sigmaBot.callBack.CallBack;
import sigmaBot.callBack.CallBackName;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrainerRedactCallBack implements CallBack {

    DBHandler dbHandler;
    private final SendBotMessageService sendBotMessageService;

    public TrainerRedactCallBack(SendBotMessageService sendBotMessageService) {
        try {
            dbHandler = DBHandler.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), "Выберите клиента, текущую программу которого вы хотите поменять", getKeyboard());

    }

    public InlineKeyboardMarkup getKeyboard() {
        ArrayList<Client> clients = dbHandler.getAllClients();
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        for (Client client : clients) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(client.getIdClient());
            button.setCallbackData("client_" + client.getIdClient());
            row.add(button);
            rows.add(row);
        }
        inlineKeyboardMarkup.setKeyboard(rows);
        return inlineKeyboardMarkup;
    }


}
