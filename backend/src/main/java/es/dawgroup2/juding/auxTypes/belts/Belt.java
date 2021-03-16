package es.dawgroup2.juding.auxTypes.belts;

public enum Belt {
    B("Blanco"),
    BAm("Blanco-Amarillo"),
    Am("Amarillo"),
    AmN("Amarillo-Naranja"),
    N("Naranja"),
    NV("Naranja-Verde"),
    V("Verde"),
    VAz("Verde-Azul"),
    Az("Azul"),
    AzM("Azul-Marrón"),
    M("Marrón"),
    N1("Negro - Dan 1"),
    N2("Negro - Dan 2"),
    N3("Negro - Dan 3"),
    N4("Negro - Dan 4"),
    N5("Negro - Dan 5"),
    N6("Negro - Dan 6"),
    N7("Negro - Dan 7"),
    N8("Negro - Dan 8"),
    N9("Negro - Dan 9"),
    N10("Negro - Dan 10");

    private final String longName;

    Belt(String longName) {
        this.longName = longName;
    }

    public String getLongName() {
        return longName;
    }
}
