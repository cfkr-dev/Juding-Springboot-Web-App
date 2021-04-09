package es.dawgroup2.juding.users.rest;

public class AdminUserEditionDTO extends CommonUserDTO {
    private final String gym;
    private final Integer weight;
    private final String refereeRange;

    public AdminUserEditionDTO(String name, String surname, String gender, String phone, String email, String birthDate, String dni, String licenseId, String nickname, String belt, String gym, Integer weight, String refereeRange) {
        super(name, surname, gender, phone, email, birthDate, dni, licenseId, nickname, belt);
        this.gym = gym;
        this.weight = weight;
        this.refereeRange = refereeRange;
    }

    public String getGym() {
        return gym;
    }

    public Integer getWeight() {
        return weight;
    }

    public String getRefereeRange() {
        return refereeRange;
    }
}
