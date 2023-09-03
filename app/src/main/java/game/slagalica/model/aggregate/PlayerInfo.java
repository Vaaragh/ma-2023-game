package game.slagalica.model.aggregate;

public class PlayerInfo {

    private String playerId, username;
    private int points;

    public PlayerInfo() {
    }

    public PlayerInfo(String playerId, String username, int points) {
        this.playerId = playerId;
        this.username = username;
        this.points = points;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}