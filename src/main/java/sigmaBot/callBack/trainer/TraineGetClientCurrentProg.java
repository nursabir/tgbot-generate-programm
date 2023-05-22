package sigmaBot.callBack.trainer;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import sigmaBot.Service.SendBotMessageService;
import sigmaBot.bot.database.DBHandler;
import sigmaBot.bot.generate.*;
import sigmaBot.callBack.CallBack;
import sigmaBot.callBack.CallBackName;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TraineGetClientCurrentProg implements CallBack {
    DBHandler db;
    private final SendBotMessageService sendBotMessageService;

    public TraineGetClientCurrentProg(SendBotMessageService sendBotMessageService) {
        try {
            this.db = DBHandler.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        ProgramPrinter programPrinter = new ProgramPrinter();
        try {
            sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), programPrinter.printCurrentProgramm(update.getCallbackQuery().getData().split("_")[1]), getStartInlineKeyboardMarkup());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public InlineKeyboardMarkup getStartInlineKeyboardMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row1 = new ArrayList<>();


        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText("Редактировать");
        inlineKeyboardButton1.setCallbackData(CallBackName.YES_REDACT.toString());
        row1.add(inlineKeyboardButton1);


        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton2.setText("Оставить без изменений");
        inlineKeyboardButton2.setCallbackData(CallBackName.NO_REDACT.toString());
        row1.add(inlineKeyboardButton2);


        rows.add(row1);

        inlineKeyboardMarkup.setKeyboard(rows);

        return inlineKeyboardMarkup;
    }

}
