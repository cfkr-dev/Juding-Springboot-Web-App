package es.dawgroup2.juding.users;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;

@Entity
public class User {
    @Id
    private String licenseId;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false)
    private String surname;

    @Column(nullable=false)
    private String email;
    
    private int phone;

    @Column(nullable=false)
    private char gender;

    @Column(nullable=false)
    private Date birthDate;

    @Column(nullable=false)
    private int dni;

    @Column(nullable=false)
    private int gym;

    protected User() { }

    public String getLicenseId() {
        return licenseId;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public int getPhone() {
        return phone;
    }

    public char getGender() {
        return gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public int getDni() {
        return dni;
    }

    public int getGym() {
        return gym;
    }
}
