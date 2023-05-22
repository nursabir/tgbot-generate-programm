package sigmaBot.bot.fucntionality;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import sigmaBot.Service.SendBotMessageService;
import sigmaBot.Service.SendBotMessageServiceImpl;
import sigmaBot.bot.Bot;
import sigmaBot.bot.StateForClient;
import sigmaBot.bot.database.DBHandler;
import sigmaBot.bot.generate.Exercise;
import sigmaBot.callBack.CallBack;
import sigmaBot.callBack.CallBackContainer;
import sigmaBot.callBack.client.ClientJoinCallBack;
import sigmaBot.command.Command;

import javax.security.auth.callback.CallbackHandler;

import java.sql.SQLException;
import java.util.ArrayList;

import static sigmaBot.callBack.CallBackName.*;

public class ClientFunctionality extends BotFunctionality {
    DBHandler db;
    SendBotMessageService sendBotMessageService;

    protected final CallBackContainer callBackContainerContainer;


    static boolean isMas = false;

    static boolean isRedact = false;
    static ArrayList<String> idOfChoiceExercise;

    public ClientFunctionality(Bot bot)  {
        try {
            this.db =  DBHandler.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        idOfChoiceExercise = new ArrayList<>();
        this.callBackContainerContainer = new CallBackContainer(new SendBotMessageServiceImpl(bot));
        this.bot = bot;
        sendBotMessageService = new SendBotMessageServiceImpl(bot);
    }
//    public ClientFunctionality(){
//        super();
//    }

    @Override
    public void execute(Update command) {
        if(command.hasCallbackQuery()){
            if(command.getCallbackQuery().getData().contains("_exercise")){
                System.out.println("ееееесть");
                System.out.println(command.getCallbackQuery().getMessage().getText());
                idOfChoiceExercise.add(command.getCallbackQuery().getData());
            }
            System.out.println(command.getCallbackQuery().getData());
            if(command.getCallbackQuery().getData().equals(CLIENT_NABOR.toString())) isMas = true;
            callBackContainerContainer.retrieveCommand(command.getCallbackQuery().getData().toString()).execute(command);
        } else {
            StateForClient stateForClient = StateForClient.getInstance();
            if(stateForClient.isVvodParam()){

                String[] parameters = command.getMessage().getText().toString().split(" ");

                db.addUser(command.getMessage().getChat().getUserName(), Integer.valueOf(parameters[0]), Integer.valueOf(parameters[1]), Integer.valueOf(parameters[2]), Integer.valueOf(parameters[3]));

                sendBotMessageService.sendMessage(command.getMessage().getChatId().toString(), "Мы сохранили ваши параметры");

                sendBotMessageService.sendMessage(command.getMessage().getChatId().toString(), "Выберите действие",
                    new ClientJoinCallBack(sendBotMessageService).getStartInlineKeyboardMarkup()
                );
                stateForClient.setVvodParam(false);
            }
        }

    }

    public static ArrayList<String> getIdOfChoiceExercise() {
        return idOfChoiceExercise;
    }

    public static boolean isMas() {
        return isMas;
    }
    public void setMas(boolean mas){
        this.isMas = mas;
    }

}
