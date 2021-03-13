package es.dawgroup2.juding.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import es.dawgroup2.juding.belts.Belt;
import es.dawgroup2.juding.users.gender.Gender;
import es.dawgroup2.juding.users.refereeRange.RefereeRange;
import es.dawgroup2.juding.users.roles.Role;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.core.io.Resource;

import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.Date;
import java.util.List;

@Entity
@Table(indexes = {@Index(columnList = "dni, nickname", unique = true)})
public class User implements Serializable {
    @Id
    private String licenseId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false, unique = true)
    private String email;

    private int phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 1)
    private Gender gender;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date birthDate;

    @Column(nullable = false)
    private String dni;

    @Column(nullable = false)
    private String gym;

    @Column(nullable = false)
    private int weight;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 3)
    private Belt belt;

    @Lob
    @JsonIgnore
    private Blob profileImage;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String securityQuestion;

    @Column(nullable = false)
    private String securityAnswer;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(nullable = false, length = 1)
    private List<Role> roles;

    @Enumerated(EnumType.STRING)
    @Column(length = 1)
    private RefereeRange refereeRange;

    protected User() {
    }

    public User(String licenseId, String name, String surname, String email, int phone, Gender gender, Date birthDate, String dni, String gym, int weight, Belt belt, String profileImage, String nickname, String password, String securityQuestion, String securityAnswer, List<Role> roles) throws IOException {
        this.licenseId = licenseId;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.birthDate = birthDate;
        this.dni = dni;
        this.gym = gym;
        this.weight = weight;
        this.belt = belt;
        this.setProfileImage(profileImage);
        this.nickname = nickname;
        this.password = password;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.roles = roles;
    }

    public String getLicenseId() {
        return licenseId;
    }

    public User setLicenseId(String licenseId) {
        this.licenseId = licenseId;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public User setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public int getPhone() {
        return phone;
    }

    public User setPhone(int phone) {
        this.phone = phone;
        return this;
    }

    public Gender getGender() {
        return gender;
    }

    public User setGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public User setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public String getDni() {
        return dni;
    }

    public User setDni(String dni) {
        this.dni = dni;
        return this;
    }

    public String getGym() {
        return gym;
    }

    public User setGym(String gym) {
        this.gym = gym;
        return this;
    }

    public int getWeight() {
        return weight;
    }

    public User setWeight(int weight) {
        this.weight = weight;
        return this;
    }

    public Belt getBelt() {
        return belt;
    }

    public User setBelt(Belt belt) {
        this.belt = belt;
        return this;
    }

    public Blob getProfileImage() {
        return profileImage;
    }

    public String getNickname() {
        return nickname;
    }

    public User setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public User setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
        return this;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public User setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
        return this;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public User setRoles(List<Role> roles) {
        this.roles = roles;
        return this;
    }

    public RefereeRange getRefereeRange() {
        return refereeRange;
    }

    public User setRefereeRange(RefereeRange refereeRange) {
        this.refereeRange = refereeRange;
        return this;
    }

    // CUSTOM METHODS

    public User setProfileImage(String path) throws IOException {
        Resource image = new ClassPathResource(path);
        profileImage = BlobProxy.generateProxy(image.getInputStream(), image.contentLength());
        return this;
    }

    public User setProfileImage(Blob profileImage) {
        this.profileImage = profileImage;
        return this;
    }

    public boolean isMale() { return gender.name().equals("H"); }

    public boolean isRole(Role role){
        return (roles.contains(role));
    }
}
