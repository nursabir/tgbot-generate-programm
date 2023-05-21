package sigmaBot.bot;

public class StateForClient {

    boolean isVvodParam;
    boolean isVvodExperience;

    private static StateForClient instance;

    private StateForClient() {
        // приватный конструктор, чтобы предотвратить создание экземпляров снаружи класса
    }

    public static StateForClient getInstance() {
        if (instance == null) {
            instance = new StateForClient();
        }
        return instance;
    }

    public boolean isVvodParam() {
        return isVvodParam;
    }

    public void setVvodParam(boolean vvodParam) {
        isVvodParam = vvodParam;
    }

    public static void setInstance(StateForClient instance) {
        StateForClient.instance = instance;
    }

    public boolean isVvodExperience() {
        return isVvodExperience;
    }

    public void setVvodExperience(boolean vvodExperience) {
        isVvodExperience = vvodExperience;
    }
}

