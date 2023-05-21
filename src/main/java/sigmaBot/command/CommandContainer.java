package sigmaBot.command;

import com.google.common.collect.ImmutableMap;
import sigmaBot.Service.SendBotMessageService;

import static sigmaBot.command.CommandName.*;

public class CommandContainer {
    // контейнер комманд неизменяемая мапа

    private final ImmutableMap<String, Command> commandMap;
    private final Command unknownCommand;

    public CommandContainer(SendBotMessageService sendBotMessageService) {

        commandMap = ImmutableMap.<String, Command>builder()
                .put(START.getCommandName(), new StartCommand(sendBotMessageService))
                .put(NO.getCommandName(), new NoCommand(sendBotMessageService)).put(HELP.getCommandName(), new HelpCommand(sendBotMessageService))
                .put(STOP.getCommandName(), new StopCommand(sendBotMessageService))
                .build();

        unknownCommand = new UnknownCommand(sendBotMessageService);
    }

    public Command retrieveCommand(String commandIdentifier) {
        return commandMap.getOrDefault(commandIdentifier, unknownCommand);
    }

}