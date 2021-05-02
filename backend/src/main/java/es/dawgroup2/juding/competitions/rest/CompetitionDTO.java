package es.dawgroup2.juding.competitions.rest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class CompetitionDTO {
    private final String id;
    @NotBlank
    private final String shortName;
    @NotBlank
    private final String additionalInfo;
    private final String minWeight;
    private final String maxWeight;
    @Pattern(regexp = "^([0-2][0-9]|3[0-1])/(0[0-9]|1[0-2])/([0-9][0-9])?[0-9][0-9] [012]?[0-9]:[0-5][0-9]$")
    private final String startDate;
    @Pattern(regexp = "^([0-2][0-9]|3[0-1])/(0[0-9]|1[0-2])/([0-9][0-9])?[0-9][0-9] [012]?[0-9]:[0-5][0-9]$")
    private final String endDate;
    private final String referee;

    public CompetitionDTO(String id, String shortName, String additionalInfo, String minWeight, String maxWeight, String startDate, String endDate, String referee) {
        this.id = id;
        this.shortName = shortName;
        this.additionalInfo = additionalInfo;
        this.minWeight = minWeight;
        this.maxWeight = maxWeight;
        this.startDate = startDate;
        this.endDate = endDate;
        this.referee = referee;
    }

    public String getId() {
        return id;
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
