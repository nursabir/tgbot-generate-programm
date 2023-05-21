package sigmaBot.callBack.client;

import org.telegram.telegrambots.meta.api.objects.Update;
import sigmaBot.Service.SendBotMessageService;
import sigmaBot.bot.generate.GenerateProgram;
import sigmaBot.bot.generate.GetProgramInText;
import sigmaBot.callBack.CallBack;

public class ClientProsmProgCallBack implements CallBack {
    private final SendBotMessageService sendBotMessageService;

    public ClientProsmProgCallBack(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        GenerateProgram generateProgram = new GenerateProgram(update.getCallbackQuery().getFrom().getUserName().toString());
        GetProgramInText getProgramInText = new GetProgramInText(generateProgram.getClient());
        sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), getProgramInText.getTextCurrentProgramm());


    }
}
