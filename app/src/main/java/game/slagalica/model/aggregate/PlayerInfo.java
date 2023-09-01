package game.slagalica.model.aggregate;

public class PlayerInfo {

    private String playerId;
    private int points;

    public PlayerInfo() {
    }

    public PlayerInfo(String playerId, int points) {
        this.playerId = playerId;
        this.points = points;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
