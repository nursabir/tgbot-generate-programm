package sigmaBot.callBack.client;

import org.telegram.telegrambots.meta.api.objects.Update;
import sigmaBot.Service.SendBotMessageService;
import sigmaBot.callBack.CallBack;

public class ClientRegistrationCallBack implements CallBack {
    private final SendBotMessageService sendBotMessageService;

    public ClientRegistrationCallBack(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {

    }
}
