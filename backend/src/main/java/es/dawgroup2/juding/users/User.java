package es.dawgroup2.juding.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import es.dawgroup2.juding.belts.Belt;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.core.io.Resource;

import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.Date;

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

    @Column(nullable = false)
    private char gender;

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

    @Column(nullable = false)
    private Integer role;

    private Integer refereeRange;

    protected User() {
    }

    public User(String licenseId, String name, String surname, String email, int phone, char gender, Date birthDate, String dni, String gym, int weight, Belt belt, String profileImage, String nickname, String password, String securityQuestion, String securityAnswer, Integer role) throws IOException {
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
        this.role = role;
    }

    public String getLicenseId() {
        return licenseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getGym() {
        return gym;
    }

    public void setGym(String gym) {
        this.gym = gym;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Belt getBelt() {
        return belt;
    }

    public void setBelt(Belt belt) {
        this.belt = belt;
    }

    public Blob getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String path) throws IOException {
        Resource image = new ClassPathResource(path);
        profileImage = BlobProxy.generateProxy(image.getInputStream(), image.contentLength());
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Integer getRefereeRange() {
        return refereeRange;
    }

    public User setRefereeRange(Integer refereeRange) {
        this.refereeRange = refereeRange;
        return this;
    }

    public boolean isMale() { return gender == 'M'; }

    public boolean isCompetitor(){ return role == 1; }

    public String getLiteralRefereeRange() {
        if (role == 2 && refereeRange != null){
            switch (refereeRange){
                case 1: return "Árbitro estándar";
                case 2: return "Árbitro de competición";
                case 3: return "Árbitro auxiliar";
            }
        }
        return null;
    }
}
