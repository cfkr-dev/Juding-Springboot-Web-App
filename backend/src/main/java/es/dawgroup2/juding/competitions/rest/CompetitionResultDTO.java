package es.dawgroup2.juding.competitions.rest;

public class CompetitionResultDTO {
    private final String idCompetition;
    private final String winner;
    private final String loser;

    public CompetitionResultDTO(String idCompetition, String winner, String loser) {
        this.idCompetition = idCompetition;
        this.winner = winner;
        this.loser = loser;
    }

    public String getIdCompetition() {
        return idCompetition;
    }

    public String getWinner() {
        return winner;
    }

    public String getLoser() {
        return loser;
    }
}
