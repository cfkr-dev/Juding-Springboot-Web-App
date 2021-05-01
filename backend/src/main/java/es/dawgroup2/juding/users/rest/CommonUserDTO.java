package es.dawgroup2.juding.users.rest;

import javax.validation.constraints.Pattern;

public class CommonUserDTO {
    private final String name;
    private final String surname;
    @Pattern(regexp = "M|H")
    private final String gender;
    @Pattern(regexp = "(?=.*\\d).{9}")
    private final String phone;
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$")
    private final String email;
    @Pattern(regexp = "^([0-2][0-9]|3[0-1])/(0[1-9]|1[0-2])/([0-9][0-9])?[0-9][0-9]$")
    private final String birthDate;
    @Pattern(regexp = "^([0-9]{8}|[XYZ][0-9]{7})[A-Z]{1}$")
    private final String dni;
    @Pattern(regexp = "^((JU-[0-9]{10})|([0-9]{10}))$")
    private final String licenseId;
    private final String nickname;
    @Pattern(regexp = "B|BAm|Am|AmN|NV|V|VAz|Az|AzM|M|N10|N[1-9]?")
    private final String belt;

    public CommonUserDTO(String name, String surname, String gender, String phone, String email, String birthDate, String dni, String licenseId, String nickname, String belt) {
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.birthDate = birthDate;
        this.dni = dni;
        this.licenseId = licenseId;
        this.nickname = nickname;
        this.belt = belt;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getGender() {
        return gender;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getDni() {
        return dni;
    }

    public String getLicenseId() {
        return licenseId;
    }

    public String getNickname() {
        return nickname;
    }

    public String getBelt() {
        return belt;
    }
}
