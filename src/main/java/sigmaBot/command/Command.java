package sigmaBot.command;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface Command {
    // Интерфейс для дальнейших команд

    // метод для выполнения данных команд
    void execute(Update update);
}
