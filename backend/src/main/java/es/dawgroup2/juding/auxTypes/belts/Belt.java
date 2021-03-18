package es.dawgroup2.juding.auxTypes.belts;

public enum Belt {
    B("Blanco", false),
    BAm("Blanco-Amarillo", false),
    Am("Amarillo", false),
    AmN("Amarillo-Naranja", false),
    N("Naranja", false),
    NV("Naranja-Verde", false),
    V("Verde", false),
    VAz("Verde-Azul", false),
    Az("Azul", false),
    AzM("Azul-Marrón", false),
    M("Marrón", false),
    N1("Negro - Dan 1", true),
    N2("Negro - Dan 2", true),
    N3("Negro - Dan 3", true),
    N4("Negro - Dan 4", true),
    N5("Negro - Dan 5", true),
    N6("Negro - Dan 6", true),
    N7("Negro - Dan 7", true),
    N8("Negro - Dan 8", true),
    N9("Negro - Dan 9", true),
    N10("Negro - Dan 10", true);

    private final String longName;
    private final Boolean isBlack;

    Belt(String longName, Boolean isBlack) {
        this.longName = longName;
        this.isBlack = isBlack;
    }

    public String getLongName() {
        return longName;
    }

    public boolean isBlack() {
        return isBlack;
    }
}
