package es.dawgroup2.juding.main.rest;

import javax.validation.constraints.Pattern;

public class UserProfileDTO {
    private final String licenseId;
    @Pattern(regexp = "B|BAm|Am|AmN|NV|V|VAz|Az|M|N[1-10]{0,2}")
    private final String belt;
    private final String gym;
    private final Integer weight;
    private final String refereeRange;
    private final String nickname;
    @Pattern(regexp = "(?=.*\\d).{9}")
    private final String phone;
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$")
    private final String email;

    public UserProfileDTO(String licenseId, String belt, String gym, Integer weight, String phone, String email) {
        this.licenseId = licenseId;
        this.belt = belt;
        this.gym = gym;
        this.weight = weight;
        this.refereeRange = null;
        this.phone = phone;
        this.email = email;
    }

    public UserProfileDTO(String licenseId, String belt, String refereeRange, String phone, String email) {
        this.licenseId = licenseId;
        this.belt = belt;
        this.gym = null;
        this.weight = null;
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
        return weight;
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
