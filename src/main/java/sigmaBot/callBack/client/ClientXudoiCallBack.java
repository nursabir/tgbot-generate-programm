package sigmaBot.callBack.client;

import org.telegram.telegrambots.meta.api.objects.Update;
import sigmaBot.Service.SendBotMessageService;
import sigmaBot.callBack.CallBack;

public class ClientXudoiCallBack implements CallBack {
    private final SendBotMessageService sendBotMessageService;

    public ClientXudoiCallBack(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {

    }
}
