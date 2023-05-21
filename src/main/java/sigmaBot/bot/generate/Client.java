package sigmaBot.bot.generate;

import sigmaBot.bot.database.DBHandler;
import sigmaBot.bot.fucntionality.ClientFunctionality;
import sigmaBot.bot.fucntionality.TrainerFunctionality;

import java.sql.SQLException;
import java.util.ArrayList;

public class Client {

    String idClient;
    int height;
    int weight;
    int idProgram;
    int age;
    int experience;
    public Client(){
    }



    public String getIdClient() {
        return idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getIdProgram() {
        return idProgram;
    }

    public void setIdProgram(int idProgram) {
        this.idProgram = idProgram;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    @Override
    public String toString() {
        return "Client{" +
                "idClient='" + idClient + '\'' +
                ", height=" + height +
                ", weight=" + weight +
                ", idProgram=" + idProgram +
                ", age=" + age +
                ", experience=" + experience +
                '}';
    }
}
