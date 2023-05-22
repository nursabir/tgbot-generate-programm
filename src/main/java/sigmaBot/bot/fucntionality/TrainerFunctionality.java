package sigmaBot.bot.fucntionality;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import sigmaBot.Service.SendBotMessageService;
import sigmaBot.Service.SendBotMessageServiceImpl;
import sigmaBot.bot.Bot;
import sigmaBot.callBack.CallBack;
import sigmaBot.callBack.CallBackContainer;
import sigmaBot.callBack.CallBackName;

import java.util.ArrayList;

public class TrainerFunctionality extends BotFunctionality{
    SendBotMessageService sendBotMessageService;

    static ArrayList<String> idOfChoiceExercise;

    static String whoIs;


    protected final CallBackContainer callBackContainerContainer;

    public TrainerFunctionality(Bot bot){
        idOfChoiceExercise = new ArrayList<>();
        this.bot = bot;
        this.sendBotMessageService = new SendBotMessageServiceImpl(bot);
        this.callBackContainerContainer = new CallBackContainer(new SendBotMessageServiceImpl(bot));
    }
    @Override
    public void execute(Update command) {
        if(command.hasCallbackQuery()) {
             if(command.getCallbackQuery().getData().contains("client_")){
                 whoIs = command.getCallbackQuery().getData().split("_")[1];
                 callBackContainerContainer.retrieveCommand(CallBackName.TRAINER_GET_CLIENT_CURRENT_PROG.getCallBackName()).execute(command);
             }

             if(command.getCallbackQuery().getData().contains("exercises_")){
                 idOfChoiceExercise.add(command.getCallbackQuery().getData().split("_")[1]);
             }

            System.out.println("кринжален");
            callBackContainerContainer.retrieveCommand(command.getCallbackQuery().getData()).execute(command);

        }


    }

    public static String getWhoIs() {
        return whoIs;
    }
    public static ArrayList<String> getIdOfChoiceExercise(){
        return idOfChoiceExercise;
    }
}
