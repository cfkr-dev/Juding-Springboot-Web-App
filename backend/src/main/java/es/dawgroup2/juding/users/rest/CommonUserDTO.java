package es.dawgroup2.juding.users.rest;

public class CommonUserDTO {
    private final String name;
    private final String surname;
    private final String gender;
    private final String phone;
    private final String email;
    private final String birthDate;
    private final String dni;
    private final String licenseId;
    private final String nickname;
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
