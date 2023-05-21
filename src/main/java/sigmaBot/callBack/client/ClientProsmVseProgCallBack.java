package sigmaBot.callBack.client;

import org.telegram.telegrambots.meta.api.objects.Update;
import sigmaBot.Service.SendBotMessageService;
import sigmaBot.bot.database.DBHandler;
import sigmaBot.bot.generate.Program;
import sigmaBot.bot.generate.ProgramPrinter;
import sigmaBot.callBack.CallBack;

import java.util.ArrayList;

public class ClientProsmVseProgCallBack implements CallBack {

    private final SendBotMessageService sendBotMessageService;

    public ClientProsmVseProgCallBack(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        ProgramPrinter programPrinter = new ProgramPrinter();
       String msg =  programPrinter.printAllProgramm(update.getCallbackQuery().getFrom().getUserName());
       sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), msg);
    }

}
