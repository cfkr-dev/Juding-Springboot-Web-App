package es.dawgroup2.juding.users.rest;

public class RefereeDTO extends CommonUserDTO{
    private final String password;
    private final String securityQuestion;
    private final String securityAnswer;

    public RefereeDTO(String name, String surname, String gender, String phone, String email, String birthDate, String dni, String licenseId, String nickname, String belt, String password, String securityQuestion, String securityAnswer) {
        super(name, surname, gender, phone, email, birthDate, dni, licenseId, nickname, belt);
        this.password = password;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
    }

    public String getPassword() {
        return password;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }
}
