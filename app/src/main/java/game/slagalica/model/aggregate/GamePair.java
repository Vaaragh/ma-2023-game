package game.slagalica.model.aggregate;

import androidx.fragment.app.Fragment;

import game.slagalica.model.single.Game;

public class GamePair {

    private Game game;
    private Fragment fragment;

    public GamePair() {
    }

    public GamePair(Game game, Fragment fragment) {
        this.game = game;
        this.fragment = fragment;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
