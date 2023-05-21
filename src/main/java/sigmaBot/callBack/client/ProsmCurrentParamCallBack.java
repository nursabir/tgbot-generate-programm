package sigmaBot.callBack.client;

import org.telegram.telegrambots.meta.api.objects.Update;
import sigmaBot.Service.SendBotMessageService;
import sigmaBot.bot.generate.Client;
import sigmaBot.callBack.CallBack;

public class ProsmCurrentParamCallBack implements CallBack {
    private final SendBotMessageService sendBotMessageService;

    public ProsmCurrentParamCallBack(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        Client client = new Client(update.getCallbackQuery().getFrom().getUserName());
        sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), "" +
                "Ваши текущие параметры: рост=" + client.getHeight() +  " вес=" + client.getWeight() + "" +
                " возраст=" + client.getAge() + " опыт тренировок=" + client.getExperience());
    }
}
