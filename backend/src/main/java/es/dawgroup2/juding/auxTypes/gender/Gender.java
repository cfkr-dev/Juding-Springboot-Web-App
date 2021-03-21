package es.dawgroup2.juding.auxTypes.gender;

/**
 * GENDER enumeration.
 * It is used for keeping encapsulated the different genders used in the application.
 * Traditionally, judo only includes the binary genders: Male and Female.
 */
public enum Gender {
    H("Hombre"),
    M("Mujer");

    private final String description;

    Gender(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
