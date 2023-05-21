package sigmaBot.callBack;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface CallBack {
    void execute(Update update);
}
