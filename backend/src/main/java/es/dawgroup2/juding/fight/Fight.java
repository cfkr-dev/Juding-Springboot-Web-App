package es.dawgroup2.juding.fight;

import es.dawgroup2.juding.competitions.Competition;
import es.dawgroup2.juding.users.User;

import javax.persistence.*;

@Entity
public class Fight {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idFight;

    @ManyToOne
    private Competition idCompetition;

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

    @OneToOne
    private User winner;

    @OneToOne
    private User loser;

    public Fight(Competition idCompetition, int levelInTree, Fight upFight, Fight downFight, Fight parentFight, boolean isFinished, User winner, User loser) {
        this.idCompetition = idCompetition;
        this.levelInTree = levelInTree;
        this.upFight = upFight;
        this.downFight = downFight;
        this.parentFight = parentFight;
        this.isFinished = isFinished;
        this.winner = winner;
        this.loser = loser;
    }

    protected Fight() {
    }

    public int getIdFight() {
        return idFight;
    }

    public Competition getIdCompetition() {
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

    public User getWinner() {
        return winner;
    }

    public User getLoser() {
        return loser;
    }

    public Fight setIdFight(int idFight) {
        this.idFight = idFight;
        return this;
    }

    public Fight setIdCompetition(Competition idCompetition) {
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

    public Fight setWinner(User winner) {
        this.winner = winner;
        return this;
    }

    public Fight setLoser(User loser) {
        this.loser = loser;
        return this;
    }
}
