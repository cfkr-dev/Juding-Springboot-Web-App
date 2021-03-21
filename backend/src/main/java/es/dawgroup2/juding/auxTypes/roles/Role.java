package es.dawgroup2.juding.auxTypes.roles;

/**
 * ROLE enumeration.
 * It is used for keeping encapsulated the different existing roles for users.
 */
public enum Role {
    C("Competitor"),
    R("Referee"),
    A("Administrator");

    private final String longName;

    Role(String longName) {
        this.longName = longName;
    }

    public String getLongName() {
        return longName;
    }
}
