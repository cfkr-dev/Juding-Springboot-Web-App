package es.dawgroup2.juding.competitions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.format.annotation.DateTimeFormat;

import javax.annotation.Resource;
import javax.persistence.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Competition {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idCompetition;

    @Column(nullable = false)
    private String shortName;

    @Column(nullable = false)
    private String additionalInfo;

    @Column(nullable = false)
    private int minWeight;

    @Column(nullable = false)
    private int maxWeight;

    @Column(nullable = false)
    private Timestamp startDate;

    @Column(nullable = false)
    private Timestamp endDate;

    //Clave foránea
    @Column(nullable = false)
    private String referee;

    @Column(nullable = false)
    private int refereeStatus;


    @Lob
    @JsonIgnore
    private Blob imageFile;


    public Competition(String shortName, String additionalInfo, int minWeight, int maxWeight, Timestamp startDate, Timestamp endDate, String referee, int refereeStatus) {
        this.shortName = shortName;
        this.additionalInfo = additionalInfo;
        this.minWeight = minWeight;
        this.maxWeight = maxWeight;
        this.startDate = startDate;
        this.endDate = endDate;
        this.referee = referee;
        this.refereeStatus = refereeStatus;

    }

    protected Competition() {

    }


    /**
     * getter
     *
     * @return id of the competition
     */
    public int getIdCompetition() {
        return idCompetition;
    }

    /**
     * setter
     *
     * @param idCompetition
     */
    public void setIdCompetition(int idCompetition) {
        this.idCompetition = idCompetition;
    }

    /**
     * @return short name of the competition
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * setter
     *
     * @param shortName
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * @return the additional info of a competition
     */
    public String getAdditionalInfo() {
        return additionalInfo;
    }

    /**
     * setter
     *
     * @param additionalInfo
     */
    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public int getMinWeight() {
        return minWeight;
    }

    public void setMinWeight(int minWeight) {
        this.minWeight = minWeight;
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(int maxWeight) {
        this.maxWeight = maxWeight;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public String getReferee() {
        return referee;
    }

    public void setReferee(String referee) {
        this.referee = referee;
    }

    public int getRefereeStatus() {
        return refereeStatus;
    }

    public void setRefereeStatus(int refereeStatus) {
        this.refereeStatus = refereeStatus;
    }


    public Blob getImageFile() {
        return imageFile;
    }

    public void setImageFile(Blob imageFile) throws IOException {
        this.imageFile = imageFile;
    }

    public String translatingDates(Timestamp startDate, Timestamp endDate) {
        LocalDateTime localDateTime = LocalDateTime.now();
        Timestamp actualDate = Timestamp.valueOf(localDateTime);
        int state = actualDate.compareTo(startDate);
        int state2 = actualDate.compareTo(endDate);
        if (state >= 0) {
            if (state2 < 0) {
                return "Comenzada";
            } else return "Finalizada";
        } else return "Por comenzar";
    }

    public String translatingRefereeStatus(int refereeStatus) {
        switch (refereeStatus) {
            case 0:
                return "Sin confirmar";
            case 1:
                return "Confirmada";
            case 2:
                return "Rechazada";
            default:
                return "Error";
        }
    }
    public int encodeRefereeStatus(String refereeStatus){
        switch (refereeStatus){
            case "Sin confirmar":
                return 0;
            case "Confirmada":
                return 1;
            case "Rechazada":
                return 2;
            default:
                return 3;
        }
    }

}
