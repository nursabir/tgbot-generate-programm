package sigmaBot.callBack.trainer;

import org.telegram.telegrambots.meta.api.objects.Update;
import sigmaBot.Service.SendBotMessageService;
import sigmaBot.callBack.CallBack;

public class ClientRedactProg implements CallBack {
    private final SendBotMessageService sendBotMessageService;

    public ClientRedactProg(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {

    }
}
