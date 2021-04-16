package es.dawgroup2.juding.main.rest;

public class RecoverPasswordDTO {
    private final String licenseId;
    private final String securityAnswer;
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

    public String getNewPassword(){ return newPassword; }
}
