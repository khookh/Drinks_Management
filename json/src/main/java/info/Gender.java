package info;

/**
 * Enum class of gender.
 *
 * @author GriffinBabe
 */
public enum Gender {

    MAN(0, "Man"),
    WOMAN(1, "Woman");

    int id;
    String name;

    Gender(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
