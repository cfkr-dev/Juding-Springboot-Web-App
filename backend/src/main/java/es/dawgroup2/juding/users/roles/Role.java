package es.dawgroup2.juding.users.roles;

public enum Role {
    C("Competitor"),
    R("Referee"),
    A("Administrator");

    private final String longName;

    Role(String longName){
        this.longName = longName;
    }

    public String getLongName() {
        return longName;
    }
}
