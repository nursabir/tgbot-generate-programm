package sigmaBot.command;

public enum CommandName {
    START("/start"), STOP("/stop"), NO("/no"), HELP("/help");

    private final String commandName;

    CommandName(String commandName){
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}
