package es.dawgroup2.juding.fight;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Fight {
    @Id
    private int idFight;

    //foreign key
    @Column(nullable = false)
    private int idCompetition;

    @Column(nullable = false)
    private int levelInTree;

    @OneToOne
    private Fight upFight;

    @OneToOne
    private Fight downFight;

    @OneToOne
    private Fight parentFight;

    @Column(nullable = false)
    private boolean isFinished;

    //foreign key
    @Column(nullable = false)
    private String winner;

    //foreign key
    @Column(nullable = false)
    private String loser;

    public Fight(int idFight, int idCompetition, int levelInTree, Fight upFight, Fight downFight, Fight parentFight, boolean isFinished, String winner, String loser) {
        this.idFight = idFight;
        this.idCompetition = idCompetition;
        this.levelInTree = levelInTree;
        this.upFight = upFight;
        this.downFight = downFight;
        this.parentFight = parentFight;
        this.isFinished = isFinished;
        this.winner = winner;
        this.loser = loser;
    }

    public int getIdFight() {
        return idFight;
    }

    public int getIdCompetition() {
        return idCompetition;
    }

    public int getLevelInTree() {
        return levelInTree;
    }

    public Fight getUpFight() {
        return upFight;
    }

    public Fight getDownFight() {
        return downFight;
    }

    public Fight getParentFight() {
        return parentFight;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public String getWinner() {
        return winner;
    }

    public String getLoser() {
        return loser;
    }

    public Fight setIdFight(int idFight) {
        this.idFight = idFight;
        return this;
    }

    public Fight setIdCompetition(int idCompetition) {
        this.idCompetition = idCompetition;
        return this;
    }

    public Fight setLevelInTree(int levelInTree) {
        this.levelInTree = levelInTree;
        return this;
    }

    public Fight setUpFight(Fight upFight) {
        this.upFight = upFight;
        return this;
    }

    public Fight setDownFight(Fight downFight) {
        this.downFight = downFight;
        return this;
    }

    public Fight setParentFight(Fight parentFight) {
        this.parentFight = parentFight;
        return this;
    }

    public Fight setFinished(boolean finished) {
        isFinished = finished;
        return this;
    }

    public Fight setWinner(String winner) {
        this.winner = winner;
        return this;
    }

    public Fight setLoser(String loser) {
        this.loser = loser;
        return this;
    }
}
