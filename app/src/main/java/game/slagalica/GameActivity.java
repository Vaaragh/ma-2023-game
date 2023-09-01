package game.slagalica;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


import androidx.appcompat.app.AppCompatActivity;


import java.util.HashMap;

import java.util.Map;

import game.slagalica.gameFragments.AssociationFragment;

import game.slagalica.gameFragments.MastermindFragment;
import game.slagalica.gameFragments.PointsFragment;

import game.slagalica.model.aggregate.GamePair;
import game.slagalica.model.single.Association;
import game.slagalica.model.single.Game;
import game.slagalica.model.single.Mastermind;
import game.slagalica.utils.FragmentSwitch;

public class GameActivity extends AppCompatActivity implements PointsFragment.TimerCallBack, AssociationFragment.SubmitCallback, MastermindFragment.SubmitCallback {

    private Map<Integer, GamePair> pairMap = new HashMap<>();
    private int currentActiveGame = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_game);

        initGamesList();
        initFragments();

    }

    private void initGamesList(){
        Association association = new Association(1,30, 0, 1,1);
        AssociationFragment fragmentAssociation = new AssociationFragment();
        GamePair associationPair = new GamePair(association, fragmentAssociation);

        Mastermind mastermind = new Mastermind(1,30,0,1,1);
        MastermindFragment mmF = MastermindFragment.newInstance();
        GamePair mastermindPair = new GamePair(mastermind, mmF);

        pairMap.put(0, associationPair);
        pairMap.put(1, mastermindPair);
    }

    public void initFragments(){
        initGameFragment();
        initPointsFragment();
    }

    private void initGameFragment(){
        if (currentActiveGame==0){
            ((AssociationFragment) pairMap.get(currentActiveGame).getFragment()).setSubmitCallback(this);
            FragmentSwitch.to(pairMap.get(currentActiveGame).getFragment(), this, false, R.id.game_host_fragment);
        }
        if (currentActiveGame==1){
            ((MastermindFragment) pairMap.get(currentActiveGame).getFragment()).setSubmitCallback(this);
            FragmentSwitch.to(pairMap.get(currentActiveGame).getFragment(), this,false, R.id.game_host_fragment);
        }
        checkFinish();


    }

    private void initPointsFragment(){
        PointsFragment pf = PointsFragment.newInstance(pairMap.get(currentActiveGame).getGame().getDuration());
        pf.setTimerCallBack(this);
        FragmentSwitch.to(pf, this, false, R.id.game_points_fragment);
    }

    @Override
    public void onTimeTick(int secondsLeft) {
    }


    @Override
    public void onTimerFinished() {
       checkFinish();
    }

    private void checkFinish(){

        if (currentActiveGame == 1){
            MastermindFragment mm = (MastermindFragment) pairMap.get(currentActiveGame).getFragment();
            if (mm != null){
                Game cg = pairMap.get(currentActiveGame).getGame();
                if (cg.getActiveRound() < cg.getRounds()){
                    cg.setActiveRound(cg.getActiveRound()+1);
                    mm.resetAll();
                    initFragments();
                }
                else {
                    currentActiveGame++;
                }
            }
        } else if (currentActiveGame == 0){
            AssociationFragment asoc = (AssociationFragment) getSupportFragmentManager().findFragmentById(R.id.game_host_fragment);
            if (asoc !=null){
                Game cg = pairMap.get(currentActiveGame).getGame();
                if (cg.getActiveRound() < cg.getRounds()){
                    cg.setActiveRound(cg.getActiveRound()+1);
                    asoc.resetAll();
                    initFragments();
                }
                else {
                    currentActiveGame++;
                }
            }
        }
    }

    private void switchGame(){
        currentActiveGame++;
        initGameFragment();
    }


    @Override
    public void onAnswerSubmitAssociation(int points, boolean finish) {
        PointsFragment pf = (PointsFragment) getSupportFragmentManager().findFragmentById(R.id.game_points_fragment);
        pf.updatePlayerPoints("guest", points);
        if (finish){
            switchGame();
        }


    }

    @Override
    public void onAnswerSubmitMastermind(int points, boolean finish) {
        PointsFragment pf = (PointsFragment) getSupportFragmentManager().findFragmentById(R.id.game_points_fragment);
        pf.updatePlayerPoints("guest", points);
        if (finish){
            Intent intent = new Intent(this, GuestHomeActivity.class);
            startActivity(intent);
        }
    }
}