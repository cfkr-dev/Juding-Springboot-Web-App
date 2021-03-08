package es.dawgroup2.juding.competitions;

import javax.persistence.*;
import java.sql.Timestamp;

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

    protected Competition() {

    }

    public Competition(int idComptition, String shortName, String additionalInfo, int minWeight, int maxWeight, Timestamp startDate, Timestamp endDate, String referee, int refereeStatus) {
        this.idCompetition = idCompetition;
        this.shortName = shortName;
        this.additionalInfo = additionalInfo;
        this.minWeight = minWeight;
        this.maxWeight = maxWeight;
        this.startDate = startDate;
        this.endDate = endDate;
        this.referee = referee;
        this.refereeStatus = refereeStatus;
    }

    public int getIdCompetition() {
        return idCompetition;
    }

    public void setIdCompetition(int idCompetition) {
        this.idCompetition = idCompetition;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

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
}
