package es.dawgroup2.juding.users.rest;

public class AdminUserEditionDTO extends CommonUserDTO {
    private final String securityQuestion;
    private final String securityAnswer;
    private final String gym;
    private final Integer weight;
    private final String refereeRange;

    public AdminUserEditionDTO(String name, String surname, String gender, String phone, String email, String birthDate, String dni, String licenseId, String nickname, String belt, String securityQuestion, String securityAnswer, String gym, Integer weight, String refereeRange) {
        super(name, surname, gender, phone, email, birthDate, dni, licenseId, nickname, belt);
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.gym = gym;
        this.weight = weight;
        this.refereeRange = refereeRange;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
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
