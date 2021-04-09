package es.dawgroup2.juding.main.rest;

public class UserProfileDTO {
    private final String licenseId;
    private final String belt;
    private final String gym;
    private final Integer weight;
    private final String refereeRange;
    private final String nickname;
    private final String phone;
    private final String email;

    public UserProfileDTO(String licenseId, String belt, String gym, Integer weight, String nickname, String phone, String email) {
        this.licenseId = licenseId;
        this.belt = belt;
        this.gym = gym;
        this.weight = weight;
        this.refereeRange = null;
        this.nickname = nickname;
        this.phone = phone;
        this.email = email;
    }

    public UserProfileDTO(String licenseId, String belt, String refereeRange, String nickname, String phone, String email) {
        this.licenseId = licenseId;
        this.belt = belt;
        this.gym = null;
        this.weight = null;
        this.refereeRange = refereeRange;
        this.nickname = nickname;
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

    public String getNickname() {
        return nickname;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
}
