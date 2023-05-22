package sigmaBot.bot.generate;

import sigmaBot.bot.database.DBHandler;

import java.sql.SQLException;
import java.util.ArrayList;

public class ProgramPrinter {
    DBHandler dbHandler;
    public ProgramPrinter(){
        try {
            dbHandler = DBHandler.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public String printAllProgramm(String username){
        StringBuilder result = new StringBuilder();
        ArrayList<Program> programs;
        try {
            programs = dbHandler.getAllProgrammForClient(username);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for(Program program: programs){
            result.append(program.getTextCurrentProgramm());
        }
        return result.toString();
    }
    public String printCurrentProgramm(String username) throws SQLException {
        Program program = dbHandler.getCurrentProgramClient(username);
        return program.getTextCurrentProgramm();
    }

}
