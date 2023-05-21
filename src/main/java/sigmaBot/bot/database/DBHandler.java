package sigmaBot.bot.database;

import org.sqlite.JDBC;
import sigmaBot.bot.generate.Client;
import sigmaBot.bot.generate.Exercise;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
                PreparedStatement statement = this.connection.prepareStatement("INSERT INTO clients " +
                        "(`idClient`, `height`, `weight`, `age`, `idProgram`, `experience`) values (?, ?, ?, ?, ?, ?)")) {
            statement.setObject(1, username);
            statement.setObject(2, height);
            statement.setObject(3, weight);
            statement.setObject(4, age);
            statement.setObject(5, null);
            statement.setObject(6, experience);
            statement.execute();
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
        st.setBoolean(4,isCurrent);
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

        while(rs2.next()){

            idOfExercises.add(rs2.getInt("exercise_id"));
        }

        int[] intArray = new int[idOfExercises.size()];

        for (int i = 0; i < idOfExercises.size(); i++) {
            intArray[i] = idOfExercises.get(i);
        }

        exercises = getChoiceExercise(intArray);

        String test = "";
        for(Exercise e: exercises){
            test = test.concat(e.toString());
        }

        return exercises;
    }




}