package game.slagalica.model.single;

public class Question extends Game{

    public Question() {}

    public Question(int rounds, int maxPoints, int minPoints, int duration, int activeRound) {
        super(rounds, maxPoints, minPoints,activeRound , duration);
    }
}
