package es.dawgroup2.juding.competitions.rest;

public class CompetitionDTO {
    private final String idCompetition;
    private final String shortName;
    private final String additionalInfo;
    private final String minWeight;
    private final String maxWeight;
    private final String startDate;
    private final String endDate;
    private final String referee;

    public CompetitionDTO(String idCompetition, String shortName, String additionalInfo, String minWeight, String maxWeight, String startDate, String endDate, String referee) {
        this.idCompetition = idCompetition;
        this.shortName = shortName;
        this.additionalInfo = additionalInfo;
        this.minWeight = minWeight;
        this.maxWeight = maxWeight;
        this.startDate = startDate;
        this.endDate = endDate;
        this.referee = referee;
    }

    public String getIdCompetition() {
        return idCompetition;
    }

    public String getShortName() {
        return shortName;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public String getMinWeight() {
        return minWeight;
    }

    public String getMaxWeight() {
        return maxWeight;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getReferee() {
        return referee;
    }
}