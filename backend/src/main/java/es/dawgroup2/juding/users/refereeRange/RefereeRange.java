package es.dawgroup2.juding.users.refereeRange;

public enum RefereeRange {
    SOLIC("Solicitante", false),
    ESTAN("Árbitro estándar", true),
    COMPT("Árbitro de competición", true),
    AUXIL("Árbitro auxiliar", true);

    private final String description;
    private final boolean isActiveReferee;

    RefereeRange(String description, boolean isActiveReferee){
        this.description = description;
        this.isActiveReferee = isActiveReferee;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActiveReferee() {
        return isActiveReferee;
    }
}
