package game.slagalica.model.aggregate;

import java.io.Serializable;

import game.slagalica.model.single.Game;

public class GameInfo implements Serializable {
    private String id, turn;
    private PlayerInfo player1, player2;
    private Game currentGame;

    public GameInfo() {
    }

    public GameInfo(String id, String turn, PlayerInfo player1, PlayerInfo player2, Game currentGame) {
        this.id = id;
        this.turn = turn;
        this.player1 = player1;
        this.player2 = player2;
        this.currentGame = currentGame;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTurn() {
        return turn;
    }

    public void setTurn(String turn) {
        this.turn = turn;
    }

    public PlayerInfo getPlayer1() {
        return player1;
    }

    public void setPlayer1(PlayerInfo player1) {
        this.player1 = player1;
    }

    public PlayerInfo getPlayer2() {
        return player2;
    }

    public void setPlayer2(PlayerInfo player2) {
        this.player2 = player2;
    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
    }
}
