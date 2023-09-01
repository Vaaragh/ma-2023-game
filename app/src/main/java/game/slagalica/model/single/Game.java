package game.slagalica.model.single;

public class Game {
    int rounds;
    int maxPoints;
    int minPoints;
    int activeRound;
    int duration;

    public Game() {
    }

    public Game(int rounds, int maxPoints, int minPoints, int activeRound, int duration) {
        this.rounds = rounds;
        this.maxPoints = maxPoints;
        this.minPoints = minPoints;
        this.activeRound = activeRound;
        this.duration = duration;
    }

    public int getRounds() {
        return rounds;
    }

    public int getMaxPoints() {
        return maxPoints;
    }

    public int getMinPoints() {
        return minPoints;
    }

    public int getActiveRound() {
        return activeRound;
    }

    public int getDuration() {
        return duration;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

    public void setMaxPoints(int maxPoints) {
        this.maxPoints = maxPoints;
    }

    public void setMinPoints(int minPoints) {
        this.minPoints = minPoints;
    }

    public void setActiveRound(int activeRound) {
        this.activeRound = activeRound;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
