package es.dawgroup2.juding.main.rest;

import javax.validation.constraints.Pattern;

public class RecoverPasswordDTO {
    @Pattern(regexp = "JU-[0-9]{10}$|[0-9]{10}$")
    private final String licenseId;

    private final String securityAnswer;

    @Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}")
    private final String newPassword;

    public RecoverPasswordDTO(String licenseId, String securityAnswer, String newPassword) {
        this.licenseId = licenseId;
        this.securityAnswer = securityAnswer;
        this.newPassword = newPassword;
    }

    public String getLicenseId() {
        return licenseId;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
