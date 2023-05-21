package sigmaBot.bot.generate;

public class Exercise {

    int id_exercise;
    String name;
    boolean isAbilitty;
    String description;
    String aimGroup;
    int necessaryExperience;

    public int getId_exercise() {
        return id_exercise;
    }

    public void setId_exercise(int id_exercise) {
        this.id_exercise = id_exercise;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAbilitty() {
        return isAbilitty;
    }

    public void setAbilitty(boolean abilitty) {
        isAbilitty = abilitty;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAimGroup() {
        return aimGroup;
    }

    public void setAimGroup(String aimGroup) {
        this.aimGroup = aimGroup;
    }

    public int getNecessaryExperience() {
        return necessaryExperience;
    }

    public void setNecessaryExperience(int necessaryExperience) {
        this.necessaryExperience = necessaryExperience;
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "id_exercise=" + id_exercise +
                ", name='" + name + '\'' +
                ", isAbilitty=" + isAbilitty +
                ", description='" + description + '\'' +
                ", aimGroup='" + aimGroup + '\'' +
                ", necessaryExperience=" + necessaryExperience +
                '}';
    }
}
