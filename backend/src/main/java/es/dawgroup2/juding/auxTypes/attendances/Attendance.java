package es.dawgroup2.juding.auxTypes.attendances;

public enum Attendance {
    C("Confirmada"),
    N("Sin confirmar"),
    R("Rechazada");

    private final String longName;

    Attendance(String attendance) {
        this.longName = attendance;
    }

    public String getLongName() {
        return longName;
    }
}
