package es.dawgroup2.juding.main.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Pattern;

public class UserProfileDTO {
    private final String licenseId;

    @Pattern(regexp = "B|BAm|Am|AmN|NV|V|VAz|Az|AzM|M|N10|N[1-9]?")
    private final String belt;

    private final String gym;

    private final String weight;

    private final String refereeRange;

    @Pattern(regexp = "(?=.*\\d).{9}")
    private final String phone;

    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$")
    private final String email;

    public UserProfileDTO(String licenseId, String belt, String gym, String weight, String refereeRange, String phone, String email) {
        this.licenseId = licenseId;
        this.belt = belt;
        this.gym = gym;
        this.weight = weight;
        this.refereeRange = refereeRange;
        this.phone = phone;
        this.email = email;
    }

    public String getLicenseId() {
        return licenseId;
    }

    public String getBelt() {
        return belt;
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

    public String getRefereeRange() {
        return refereeRange;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
}
