package sigmaBot.bot.generate;

import sigmaBot.bot.database.DBHandler;
import sigmaBot.bot.fucntionality.ClientFunctionality;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenerateProgram {


    boolean isMas;
    DBHandler dbHandler;
    Client client;
    ArrayList<Exercise> exercises;


    public GenerateProgram(String username) {
        try {
            dbHandler = DBHandler.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            this.client = dbHandler.getClientById(username);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        initializeExercises();
    }

    public void initializeExercises() {
        System.out.println("dfdfdfdfdfdfdfddddddddddddddddd");
        ArrayList<String> idOfChooseExercices = new ArrayList<>();
        idOfChooseExercices = ClientFunctionality.getIdOfChoiceExercise();
        int[] masOfId = new int[idOfChooseExercices.size()];
        int i = 0;
        for (String s : idOfChooseExercices) {
            masOfId[i] = (Integer.parseInt(String.valueOf(s.charAt(0))));
            i++;
        }
        try {
            this.exercises = dbHandler.getChoiceExercise(masOfId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void generate() throws SQLException {
        if (ClientFunctionality.isMas()) this.isMas = true;
        dbHandler.addPrograms(isMas, client.idClient, this.indexOfBody(), exercises, true);


    }


    public int indexOfBody() {
        return client.getHeight() / client.getWeight();
    }



    public Client getClient() {
        return client;
    }
}
