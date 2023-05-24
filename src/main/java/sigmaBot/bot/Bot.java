package sigmaBot.bot;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import sigmaBot.Service.SendBotMessageServiceImpl;
import sigmaBot.bot.fucntionality.BotFunctionality;
import sigmaBot.bot.fucntionality.ClientFunctionality;
import sigmaBot.bot.fucntionality.Functionality;
import sigmaBot.bot.fucntionality.TrainerFunctionality;
import sigmaBot.command.CommandContainer;
import sigmaBot.token.congif;

import static sigmaBot.command.CommandName.NO;

public class Bot extends TelegramLongPollingBot {
    private Update update;
    Functionality functionality;

    public static String COMMAND_PREFIX = "/";

    @Override
    public String getBotUsername() {
        return "magafit_bot";
    }

    @Override
    public String getBotToken() {
        return congif.token ;
    }

    private final CommandContainer commandContainer;

    public Bot(){
        this.commandContainer = new CommandContainer(new SendBotMessageServiceImpl(this));
    }
// передаем
    @Override
    public void onUpdateReceived(Update update) {
        this.update = update;
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText().trim();
            if (message.startsWith(COMMAND_PREFIX)) {
                String commandIdentifier = message.split(" ")[0].toLowerCase();

                commandContainer.retrieveCommand(commandIdentifier).execute(update);
            } else {
                commandContainer.retrieveCommand(NO.getCommandName());
            }
        }
        if(update.hasCallbackQuery()){
           if(update.getCallbackQuery().getData().equals("TRAINER")){
               this.functionality = new Functionality(new TrainerFunctionality(this));
               //functionality.executeFunctionality(update.getCallbackQuery());
           } else if(update.getCallbackQuery().getData().equals("CLIENT")){
               this.functionality = new Functionality(new ClientFunctionality(this));
              // functionality.executeFunctionality(update.getCallbackQuery());
           }
        }
        functionality.executeFunctionality(update);

    }

    public void sendText(Long who, String what) {
        SendMessage sm = SendMessage.builder()
                .chatId(who.toString()) //Who are we sending a message to
                .text(what).build();    //Message content
        try {
            execute(sm);                        //Actually sending the message
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);      //Any error will be printed here
        }
    }

    public Update getUpdate() {
        return update;
    }

}
