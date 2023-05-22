package sigmaBot.bot.database;

import org.sqlite.JDBC;
import sigmaBot.bot.fucntionality.TrainerFunctionality;
import sigmaBot.bot.generate.Client;
import sigmaBot.bot.generate.Exercise;
import sigmaBot.bot.generate.Program;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DBHandler {

    // Константа, в которой хранится адрес подключения
    private static final String CON_STR = "jdbc:sqlite:C:/sqllite/users.db";

    // Используем шаблон одиночка, чтобы не плодить множество
    // экземпляров класса DbHandler
    private static DBHandler instance = null;

    public static synchronized DBHandler getInstance() throws SQLException {
        if (instance == null)
            instance = new DBHandler();
        return instance;
    }

    // Объект, в котором будет храниться соединение с БД
    private Connection connection;

    private DBHandler() throws SQLException {
        // Регистрируем драйвер, с которым будем работать
        // в нашем случае Sqlite
        DriverManager.registerDriver(new JDBC());
        // Выполняем подключение к базе данных
        this.connection = DriverManager.getConnection(CON_STR);
    }

    public void addUser(String username, int height, int weight, int age, int experience) {
        try (
                PreparedStatement selectStatement = this.connection.prepareStatement("SELECT COUNT(*) FROM clients WHERE idClient = ?");
                PreparedStatement insertStatement = this.connection.prepareStatement("INSERT INTO clients (idClient, height, weight, age, idProgram, experience) values (?, ?, ?, ?, ?, ?)");
                PreparedStatement updateStatement = this.connection.prepareStatement("UPDATE clients SET height = ?, weight = ?, age = ?, experience = ? WHERE idClient = ?");
        ) {
            // Check if a row with the given username already exists
            selectStatement.setString(1, username);
            ResultSet resultSet = selectStatement.executeQuery();
            int count = resultSet.getInt(1);
            if (count == 0) {
                // Insert a new row
                insertStatement.setString(1, username);
                insertStatement.setInt(2, height);
                insertStatement.setInt(3, weight);
                insertStatement.setInt(4, age);
                insertStatement.setNull(5, java.sql.Types.INTEGER);
                insertStatement.setInt(6, experience);
                insertStatement.executeUpdate();
            } else {
                // Update the existing row
                updateStatement.setInt(1, height);
                updateStatement.setInt(2, weight);
                updateStatement.setInt(3, age);
                updateStatement.setInt(4, experience);
                updateStatement.setString(5, username);
                updateStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<ArrayList<Object>> getAllExercise() throws SQLException {
//
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM exercises");
        // Создаем внешний ArrayList для хранения строк таблицы
        ArrayList<ArrayList<Object>> tableData = new ArrayList<>();
        // Получаем все данные из ResultSet и добавляем их в ArrayList
        while (rs.next()) {
            // Создаем внутренний ArrayList для хранения данных каждой строки таблицы
            ArrayList<Object> rowData = new ArrayList<>();
            // Получаем значения каждого столбца и добавляем их в ArrayList
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                Object value = rs.getObject(i);
                rowData.add(value);
            }
            // Добавляем внутренний ArrayList во внешний ArrayList
            tableData.add(rowData);
        }
        return tableData;
    }

    public List<Exercise> getTrainerChoiseExercises(String username) throws SQLException {
        List<Exercise> listOfExercicesForClient = getListOfExercicesForClient(getClientById(username));
        List<ArrayList<Object>> tableData = getAllExercise();
        List<Exercise> listAllExer = new ArrayList<>();

        for (ArrayList<Object> arrayList : tableData) {
            Exercise exercise = new Exercise();
            exercise.setId_exercise((Integer) arrayList.get(0));
            exercise.setName((String) arrayList.get(1));
            boolean isAbility = (Integer) arrayList.get(2) == 1;
            exercise.setAbilitty(isAbility);
            exercise.setDescription((String) arrayList.get(3));
            exercise.setAimGroup((String) arrayList.get(4));
            exercise.setNecessaryExperience((Integer) arrayList.get(5));
            listAllExer.add(exercise);
        }
        return filterExercises(listAllExer, listOfExercicesForClient);
    }

    public List<Exercise> filterExercises(List<Exercise> firstList, List<Exercise> secondList) {
        List<Integer> secondListIds = secondList.stream()
                .map(Exercise::getId_exercise)
                .collect(Collectors.toList());

        List<Exercise> filteredList = firstList.stream()
                .filter(e -> !secondListIds.contains(e.getId_exercise()))
                .collect(Collectors.toList());

        return filteredList;
    }

    // возвращаем всю информацию об упражнениях которые выбрал пользователь по индексам
    public ArrayList<Exercise> getChoiceExercise(int[] mas) throws SQLException {
        ArrayList<Exercise> result = new ArrayList<>();
        for (int i = 0; i < mas.length; i++) {
            PreparedStatement st = connection.prepareStatement("SELECT * FROM exercises WHERE `id_exercise` = ? ");
            st.setInt(1, mas[i]);
            ResultSet rs = st.executeQuery();

            Exercise exercise = new Exercise();

            exercise.setId_exercise(Integer.parseInt(rs.getString("id_exercise")));
            exercise.setName(rs.getString("name"));
            exercise.setDescription(rs.getString("description"));
            exercise.setAimGroup(rs.getString("aimGroup"));
            exercise.setNecessaryExperience(Integer.parseInt(rs.getString("necessaryExperience")));
            exercise.setAbilitty(rs.getBoolean("isAbility"));
            result.add(exercise);
        }
        return result;
    }

    public Client getClientById(String username) throws SQLException {
        Client client = new Client();
        PreparedStatement st = connection.prepareStatement("SELECT * FROM clients WHERE `idClient` LIKE ? ");
        st.setString(1, username);
        ResultSet rs = st.executeQuery();

        client.setIdClient(rs.getString("idClient"));
        client.setHeight(rs.getInt("height"));
        client.setWeight(rs.getInt("weight"));
        client.setAge(rs.getInt("age"));
        client.setExperience(rs.getInt("experience"));
        client.setIdProgram(rs.getInt("idProgram"));

        return client;
    }

    // добавляем программу и сразу упражнения к ней
    public void addPrograms(boolean isMas, String idClient, int indexOfBody, ArrayList<Exercise> exercises, boolean isCurrent) throws SQLException {
        PreparedStatement st = connection.prepareStatement("INSERT INTO programs(`isMas`, `idClient`, `indexOfBody`, `isCurrent`) VALUES(?, ?, ?, ?)");
        st.setBoolean(1, isMas);
        st.setString(2, idClient);
        st.setInt(3, indexOfBody);
        st.setBoolean(4, isCurrent);
        st.execute();

        PreparedStatement st3 = connection.prepareStatement("select `idProgram` from programs ORDER BY `idProgram` DESC LIMIT 1");
        ResultSet rs = st3.executeQuery();
        int a = rs.getInt("idProgram");

        for (Exercise e : exercises) {
            PreparedStatement st2 = connection.prepareStatement("INSERT INTO programs_exercises(`program_id`, `exercise_id`) values (?, ?)");
            st2.setInt(1, a);
            st2.setInt(2, e.getId_exercise());
            st2.execute();
        }
    }

    public void updateCurrentProgram() {
        try {
            ArrayList<Exercise> currentExercises = getListOfExercicesForClient(getClientById(TrainerFunctionality.getWhoIs()));
            ArrayList<String> idOfNewExercises = TrainerFunctionality.getIdOfChoiceExercise();
            int[] a = new int[idOfNewExercises.size()];
            int i = 0;
            for (String s : idOfNewExercises) {
                a[i] = Integer.parseInt(s);
                i++;
            }
            ArrayList<Exercise> newExercises = getChoiceExercise(a);
            Program currentProgram = this.getCurrentProgramClient(TrainerFunctionality.getWhoIs());
            int idCurrentProgram = getIdCurrentProgramm(TrainerFunctionality.getWhoIs());
            for(Exercise e: newExercises) {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO programs_exercises(program_id, exercise_id) VALUES(?, ?)" );
                preparedStatement.setInt(1, idCurrentProgram);
                preparedStatement.setInt(2, e.getId_exercise());
                preparedStatement.execute();
            }
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE programs SET isRedact=true WHERE idProgram =?");
            preparedStatement.setInt(1, idCurrentProgram);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public int getIdCurrentProgramm(String username) throws SQLException {
        Client client = getClientById(username);
        ArrayList<Exercise> exercises = getListOfExercicesForClient(client);
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM programs WHERE (`idClient` LIKE ?) and (`isCurrent`=?) ");
        preparedStatement.setString(1, client.getIdClient());
        preparedStatement.setInt(2, 1);
        ResultSet rs = preparedStatement.executeQuery();

        int idProgram = rs.getInt("idProgram");
        boolean isMas = rs.getBoolean("isMas");
        boolean isRedact = rs.getBoolean("isRedact");

        Program program = new Program(client, isMas, isRedact, exercises);
        return idProgram;
    }



    public Program getCurrentProgramClient(String username) throws SQLException {
        Client client = getClientById(username);
        ArrayList<Exercise> exercises = getListOfExercicesForClient(client);
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM programs WHERE (`idClient` LIKE ?) and (`isCurrent`=?) ");
        preparedStatement.setString(1, client.getIdClient());
        preparedStatement.setInt(2, 1);
        ResultSet rs = preparedStatement.executeQuery();

        int idProgram = rs.getInt("idProgram");
        boolean isMas = rs.getBoolean("isMas");
        boolean isRedact = rs.getBoolean("isRedact");

        Program program = new Program(client, isMas, isRedact, exercises);
        return program;
    }

    public ArrayList<Exercise> getListOfExercicesForClient(Client client) throws SQLException {

        ArrayList<Exercise> exercises = new ArrayList<>();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM programs WHERE (`idClient` LIKE ?) and (`isCurrent`=?) ");
        preparedStatement.setString(1, client.getIdClient());
        preparedStatement.setInt(2, 1);
        ResultSet rs = preparedStatement.executeQuery();

        int idProgram = rs.getInt("idProgram");
        boolean isMas = rs.getBoolean("isMas");
        String idClient = rs.getString("idClient");
        int indexOfBody = rs.getInt("indexOfBody");
        boolean isCurrent = rs.getBoolean("isCurrent");

        ArrayList<Integer> idOfExercises = new ArrayList<>();

        PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT * FROM programs_exercises WHERE `program_id`=?");
        preparedStatement1.setInt(1, idProgram);

        ResultSet rs2 = preparedStatement1.executeQuery();

        while (rs2.next()) {

            idOfExercises.add(rs2.getInt("exercise_id"));
        }

        int[] intArray = new int[idOfExercises.size()];

        for (int i = 0; i < idOfExercises.size(); i++) {
            intArray[i] = idOfExercises.get(i);
        }

        exercises = getChoiceExercise(intArray);

        String test = "";
        for (Exercise e : exercises) {
            test = test.concat(e.toString());
        }

        return exercises;
    }

    public void setRedactCurrentProg(){
        String whois = TrainerFunctionality.getWhoIs();
        Program program;
        try {
         program = getCurrentProgramClient(whois);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("UPDATE programs set isRedact = 1 WHERE idProgram=?");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            preparedStatement.setInt(1, getIdCurrentProgramm(whois));
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isRedactProgram(int idProgram){
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT isRedact from programs where idProgram = ?");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            preparedStatement.setInt(1, idProgram);
            return  preparedStatement.executeQuery().getInt("isRedact") == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Program> getAllProgrammForClient(String username) throws SQLException {
        Client client;
        try {
            client = this.getClientById(username);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM programs WHERE (`idClient` LIKE ?)  ");
        preparedStatement.setString(1, client.getIdClient());
        ResultSet rs = preparedStatement.executeQuery();

        ArrayList<Program> programs = new ArrayList<>();

        while (rs.next()) {
            int idProgram = rs.getInt("idProgram");
            boolean isMas = rs.getBoolean("isMas");
            int indexOfBody = rs.getInt("indexOfBody");
            boolean isCurrent = rs.getBoolean("isCurrent");
            boolean isRedact = rs.getBoolean("isRedact");

            PreparedStatement preparedStatement2 = connection.prepareStatement("SELECT * FROM programs_exercises WHERE (`program_id`= ?)  ");
            preparedStatement2.setInt(1, idProgram);
            ResultSet rs2 = preparedStatement2.executeQuery();
            ArrayList<Integer> idOfExercises = new ArrayList<>();
            while (rs2.next()) {
                idOfExercises.add(rs2.getInt("exercise_id"));
            }
            int[] exer = new int[idOfExercises.size()];
            int g = 0;
            for (Integer i : idOfExercises) {
                exer[g] = i;
                g++;
            }
            ArrayList<Exercise> exerciseArrayList = this.getChoiceExercise(exer);

            Program program = new Program(client, isMas, isRedact, exerciseArrayList);
            programs.add(program);
        }
        return programs;
    }

    public ArrayList<Client> getAllClients() {
        ArrayList<Client> result = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM clients");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Client client = new Client();
                client.setIdClient(rs.getString("idClient"));
                client.setHeight(rs.getInt("height"));
                client.setWeight(rs.getInt("weight"));
                client.setIdProgram(rs.getInt("idProgram"));
                client.setAge(rs.getInt("age"));
                client.setExperience(rs.getInt("experience"));
                result.add(client);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }


}