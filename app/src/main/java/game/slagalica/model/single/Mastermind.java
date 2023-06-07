package game.slagalica.model.single;

import game.slagalica.gameFragments.MastermindFragment;

public class Mastermind extends Game {

    public Mastermind() {

    }

    public Mastermind(int rounds, int maxPoints, int minPoints, int duration, int activeRound) {
        super(rounds, maxPoints, minPoints, activeRound, duration);
    }
}
