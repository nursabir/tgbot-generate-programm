package sigmaBot.bot.fucntionality;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import sigmaBot.bot.Bot;
import sigmaBot.callBack.CallBack;
import sigmaBot.callBack.CallBackName;

public class TrainerFunctionality extends BotFunctionality{

    public TrainerFunctionality(Bot bot){
        this.bot = bot;
    }
    @Override
    public void execute(Update command) {
//        System.out.println("Тренер");
//        TrainerCommandHandlerPassword trainerCommandHandler = new TrainerCommandHandlerPassword();
//        // функционирование для тренера
    }
}
