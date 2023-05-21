package sigmaBot.bot.fucntionality;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

public class Functionality {
    private BotFunctionality bot;

    public Functionality(BotFunctionality bot){
        this.bot = bot;
    }

    public void executeFunctionality(Update command){
        bot.execute(command);
    }

}
