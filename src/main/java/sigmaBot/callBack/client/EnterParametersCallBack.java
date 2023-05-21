package sigmaBot.callBack.client;

import org.telegram.telegrambots.meta.api.objects.Update;
import sigmaBot.Service.SendBotMessageService;
import sigmaBot.bot.StateForClient;
import sigmaBot.bot.generate.Client;
import sigmaBot.callBack.CallBack;

public class EnterParametersCallBack implements CallBack {
    private final SendBotMessageService sendBotMessageService;

    public EnterParametersCallBack(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        //Client client = new Client(update.getMessage().getFrom().getUserName());

        sendBotMessageService.sendMessage(update.getCallbackQuery().getMessage().getChatId().toString(), "Введите параметры" +
                "в следующем формате: Рост Вес Возраст Опыт_тренировок_в_месяцах");

        StateForClient stateForClient = StateForClient.getInstance();
        stateForClient.setVvodParam(true);
    }


}
