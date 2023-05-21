package sigmaBot.callBack;

public enum CallBackName {

    ADMIN("ADMIN"), CLIENT("CLIENT"), CLIENT_JOIN("CLIENT_JOIN"),
    CLIENT_MAIN_MENU("CLIENT_MAIN_MENU"), CLIENT_VVOD_PARAM("CLIENT_VVOD_PARAM"),
    CLIENT_GENERATE("CLIENT_GENERATE"), CLIENT_PROSM_PROG("CLIENT_PROSM_PROG"),
    PROSM_VSE_PROG("PROSM_VSE_PROG"), CLIENT_NABOR("CLIENT_NABOR"), CLIENT_XUDOI("CLIENT_XUDOI"),
    CLIENT_CHOISE("CLIENT_CHOISE"), CLIENT_SEND_CHOICE("CLIENT_SEND_CHOICE"),
    TRAINER("TRAINER"),
    CLIENT_REGISTRATION("CLIENT_REGISTRATION");
    private String callBackName;
     CallBackName(String commandName){
        this.callBackName = commandName;
    }

    public String getCallBackName() {
        return callBackName;
    }
}
