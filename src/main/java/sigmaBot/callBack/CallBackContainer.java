package sigmaBot.callBack;

import com.google.common.collect.ImmutableMap;
import sigmaBot.Service.SendBotMessageService;
import sigmaBot.callBack.client.*;
import sigmaBot.callBack.trainer.*;
import sigmaBot.command.*;

import static sigmaBot.command.CommandName.*;
import static sigmaBot.command.CommandName.STOP;
import static sigmaBot.callBack.CallBackName.*;

public class CallBackContainer {
    private final ImmutableMap<String, CallBack> commandMap;
    private final CallBack unknownCommand;

    public CallBackContainer(SendBotMessageService sendBotMessageService) {

        commandMap = ImmutableMap.<String, CallBack>builder()
                .put(CLIENT.getCallBackName(), new IAmClientCallBack(sendBotMessageService))
                .put(CLIENT_JOIN.getCallBackName(), new ClientJoinCallBack(sendBotMessageService)).put(CLIENT_REGISTRATION.getCallBackName(), new ClientRegistrationCallBack(sendBotMessageService))
                .put(CLIENT_MAIN_MENU.getCallBackName(), new ClientJoinCallBack(sendBotMessageService))
                .put(CLIENT_VVOD_PARAM.getCallBackName(), new EnterParametersCallBack(sendBotMessageService))
                .put(CLIENT_GENERATE.getCallBackName(), new ClientGenerateCallBack(sendBotMessageService))
                .put(CLIENT_NABOR.getCallBackName(), new ClientChoiseExercise(sendBotMessageService))
                .put(CLIENT_SEND_CHOICE.getCallBackName(), new ClientSendChoiceCallBack(sendBotMessageService))
                .put(CLIENT_PROSM_PROG.getCallBackName(), new ClientProsmProgCallBack(sendBotMessageService))
                .put(PROSM_VSE_PROG.getCallBackName(), new ClientProsmVseProgCallBack(sendBotMessageService))
                .put(PROSM_CURRENT_PARAM.getCallBackName(), new ProsmCurrentParamCallBack(sendBotMessageService))
                .put(CLIENT_XUDOI.getCallBackName(), new ClientChoiseExercise(sendBotMessageService))
                .put(TRAINER.getCallBackName(), new TrainerJoinCallBack(sendBotMessageService))
                .put(TRAINER_REDACT.getCallBackName(), new TrainerRedactCallBack(sendBotMessageService))
                .put(TRAINER_GET_CLIENT_CURRENT_PROG.getCallBackName(), new TraineGetClientCurrentProg(sendBotMessageService))
                .put(YES_REDACT.getCallBackName(), new TrainerAddExercises(sendBotMessageService))
                .put(TRAINER_SEND_ADD_EXERCISES.getCallBackName(), new TrainerSendAddExercisesCallBack(sendBotMessageService))
                .put(NO_REDACT.getCallBackName(), new NoRedactProg(sendBotMessageService))
                .build();

       unknownCommand = new UnknownCallBack(sendBotMessageService);
    }

    public CallBack retrieveCommand(String commandIdentifier) {
        return commandMap.getOrDefault(commandIdentifier, unknownCommand);
    }
}
