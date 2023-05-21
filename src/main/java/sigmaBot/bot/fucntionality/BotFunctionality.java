package sigmaBot.bot.fucntionality;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import sigmaBot.bot.Bot;
import sigmaBot.callBack.CallBack;
import sigmaBot.command.Command;

public abstract class BotFunctionality {
    Bot bot;
    abstract void execute(Update command);
}
