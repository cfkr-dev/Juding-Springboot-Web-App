package es.dawgroup2.juding.users.gender;

public enum Gender {
    H("Hombre"),
    M("Mujer");

    private final String description;

    Gender(String description) {
        this.description = description;
    }

    public String getDescription(){
        return description;
    }
}
