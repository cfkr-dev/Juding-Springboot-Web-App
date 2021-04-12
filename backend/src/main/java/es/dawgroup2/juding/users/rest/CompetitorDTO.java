package es.dawgroup2.juding.users.rest;

public class CompetitorDTO extends RefereeDTO{
    private final String gym;
    private final String weight;

    public CompetitorDTO(String name, String surname, String gender, String phone, String email, String birthDate, String dni, String licenseId, String nickname, String password, String securityQuestion, String securityAnswer, String belt, String gym, String weight) {
        super(name, surname, gender, phone, email, birthDate, dni, licenseId, nickname, belt, password, securityQuestion, securityAnswer);
        this.gym = gym;
        this.weight = weight;
    }

    public String getGym() {
        return gym;
    }

    public Integer getWeight() {
        try{
            return Integer.parseInt(weight);
        } catch (Exception e){
            return null;
        }
    }
}
