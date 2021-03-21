package es.dawgroup2.juding.auxTypes.refereeRange;

/**
 * REFEREE RANGE enumeration.
 * It is used for keeping encapsulated the different ranges available for referees.
 */
public enum RefereeRange {
    S("Solicitante", false),
    E("Árbitro estándar", true),
    C("Árbitro de competición", true),
    A("Árbitro auxiliar", true);

    private final String description;
    private final boolean isActiveReferee;

    RefereeRange(String description, boolean isActiveReferee) {
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
