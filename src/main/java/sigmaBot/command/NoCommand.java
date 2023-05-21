package sigmaBot.command;

import org.telegram.telegrambots.meta.api.objects.Update;
import sigmaBot.Service.SendBotMessageService;

public class NoCommand implements Command{
    private final SendBotMessageService sendBotMessageService;

    private final String NO_MESSAGE = "Данная команда не поддерживается";

    public NoCommand(SendBotMessageService sendBotMessageService){
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
      sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), NO_MESSAGE);

    }
}
