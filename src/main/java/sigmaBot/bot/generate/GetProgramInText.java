package sigmaBot.bot.generate;

import sigmaBot.bot.database.DBHandler;
import sigmaBot.bot.fucntionality.ClientFunctionality;

import java.sql.SQLException;
import java.util.ArrayList;

public class GetProgramInText {
   DBHandler db;
   Client client;

   boolean isMas;

   boolean isRedact;


   ArrayList<Exercise> exercises;

   public GetProgramInText(Client client){
      try {
         db = DBHandler.getInstance();
      } catch (SQLException e) {
         throw new RuntimeException(e);
      }
      this.client = client;
      this.isMas = ClientFunctionality.isMas();
   }

   public String getTextCurrentProgramm(){
      try {
          exercises = db.getListOfExercicesForClient(client);
      } catch (SQLException e) {
         throw new RuntimeException(e);
      }

      String SHAPKA = "Мы сгенерировали вам программу тренировок. \uD83D\uDCAA\uD83C\uDFFB \n" +
              "И отправили ее на редактирование тренеру " +
              "Общие реккомендации: \n Ваш индекс тела " + indexOfBody()   + ". По " +
              "нормам это " + aboutIndexOfBody() + " " + getNumOfRepeat() + "\n " + "Первый день: " + "\n";

      int i=1;


      StringBuilder firstDay =  new StringBuilder();
      ArrayList<Exercise> firstDayExercises = getListExercicesForFirstDay();
      for(Exercise e: firstDayExercises){
         firstDay.append(i).append(") ").append(e.name).append("\n").append("Краткое описание: ").append(e.description).append("\n");
         i++;
      }
      firstDay.append("\n" + "Второй день " + "\n");


       i =1;
      StringBuilder secondDay = new StringBuilder();
      ArrayList<Exercise> secondDayExercises =getListExercicesForSecondaDay();
      for(Exercise e: secondDayExercises){
         firstDay.append(i).append(") ").append(e.name).append("\n").append("Краткое описание: ").append(e.description).append("\n");
         i++;
      }
      secondDay.append("\n " + "Третий день " + "\n");

      i =1;
      StringBuilder thirdDay = new StringBuilder();
      ArrayList<Exercise> thirdDayExercises = getListExercicesForThirdDay();
      for(Exercise e: thirdDayExercises){
         thirdDay.append(i).append(") ").append(e.name).append("\n").append("Краткое описание: ").append(e.description).append("\n");
         i++;
      }

      StringBuilder result = new StringBuilder();
      result.append(SHAPKA);
      result.append(firstDay);
      result.append(secondDay);
      result.append(thirdDay);
      result.append("\n");
      return result.toString();
   }

   public String getNumOfRepeat(){
      if(this.isMas) return "Вы работаете на набор мышечной массы. Старайтесь делать каждое упражнение по 12 повторений";
      else return "Вы работаете на похудение. Старайтесь делать упражнения по 15-16 повторений";
   }

   public String aboutIndexOfBody(){
      if(indexOfBody()<16) return "выраженный дефицит массы тела";
      if(indexOfBody()<19 && indexOfBody()>16) return "дефицит массы тела";
      if(indexOfBody()<25 && indexOfBody()>18) return "нормальный индекс тела";
      else return "Избыточная масса тела";
   }

   public int numOfRepeat() {
      if (isMas) return 12;
      else return 16;
   }

   public double indexOfBody() {
      return client.getWeight()/(client.getHeight()* client.getHeight()*0.0001);
   }

   public ArrayList<Exercise> getListExercicesForFirstDay() {
      ArrayList<Exercise> result = new ArrayList<>();
      for (Exercise e : exercises) {
         if (e.aimGroup.equals("Грудь") || e.aimGroup.equals("Трицепс") && client.getExperience()>e.necessaryExperience) {
            result.add(e);
         }
      }
      return result;
   }

   public ArrayList<Exercise> getListExercicesForSecondaDay(){
      ArrayList<Exercise> result = new ArrayList<>();
      for(Exercise e: exercises) {
         if (e.aimGroup.equals("Бицепс") || e.aimGroup.equals("Ноги") && client.getExperience()>e.necessaryExperience) {
            result.add(e);
         }
      }
      return result;
   }

   public ArrayList<Exercise> getListExercicesForThirdDay(){
      ArrayList<Exercise> result = new ArrayList<>();
      for(Exercise e: exercises){
         if(e.aimGroup.equals("Плечи") || e.aimGroup.equals("Спина") && client.getExperience()>e.necessaryExperience){
            result.add(e);
         }
      }
      return result;
   }


}
