package game.slagalica;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

import java.util.Map;

import game.slagalica.gameFragments.AssociationFragment;

import game.slagalica.gameFragments.MastermindFragment;
import game.slagalica.gameFragments.PointsFragment;

import game.slagalica.gameFragments.QuestionFragment;
import game.slagalica.model.aggregate.GameInfo;
import game.slagalica.model.aggregate.GamePair;
import game.slagalica.model.single.Association;
import game.slagalica.model.single.Game;
import game.slagalica.model.single.Mastermind;
import game.slagalica.model.single.Question;
import game.slagalica.model.single.User;
import game.slagalica.utils.FragmentSwitch;
import io.socket.client.Socket;

public class GameActivity extends AppCompatActivity implements PointsFragment.TimerCallBack, AssociationFragment.SubmitCallback, MastermindFragment.SubmitCallback, QuestionFragment.SubmitCallback {

    private Map<Integer, GamePair> pairMap = new HashMap<>();
    private PointsFragment pf;
    private int currentGame = 0;
    private GameInfo gameInfo;
    private boolean gameFinished;
    private FirebaseFirestore db;
    private int currentActiveGame = 0;

    public static User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_game);
        db = FirebaseFirestore.getInstance();
        gameInfo = (GameInfo) getIntent().getSerializableExtra("gameInfo");
        user = (User) getIntent().getSerializableExtra("currentUser");


        initGamesList();
        initFragments();

    }

    private void initGamesList(){
        Question question = new Question(1, 20, 0, 5,1 );
        QuestionFragment questionFragment = QuestionFragment.newInstance(gameInfo);
        GamePair questionPair = new GamePair(question, questionFragment);

        Association association = new Association(1,30, 0, 12,1);
        AssociationFragment fragmentAssociation = AssociationFragment.newInstance(gameInfo);
        GamePair associationPair = new GamePair(association, fragmentAssociation);

        Mastermind mastermind = new Mastermind(2,30,0,12,1);
        MastermindFragment mmF = MastermindFragment.newInstance(gameInfo, user);
        GamePair mastermindPair = new GamePair(mastermind, mmF);

        pairMap.put(2, questionPair);
        pairMap.put(1, associationPair);
        pairMap.put(0, mastermindPair);
    }

    public void initFragments(){
        initGameFragment();
        initPointsFragment();
    }

    private void initGameFragment(){
        if (currentActiveGame==2){
            ((QuestionFragment) pairMap.get(currentActiveGame).getFragment()).setSubmitCallback(this);
            FragmentSwitch.to(pairMap.get(currentActiveGame).getFragment(), this, false,R.id.game_host_fragment);
        }

        if (currentActiveGame==1){
            ((AssociationFragment) pairMap.get(currentActiveGame).getFragment()).setSubmitCallback(this);
            FragmentSwitch.to(pairMap.get(currentActiveGame).getFragment(), this, false, R.id.game_host_fragment);
        }
        if (currentActiveGame==0){
            ((MastermindFragment) pairMap.get(currentActiveGame).getFragment()).setSubmitCallback(this);
            FragmentSwitch.to(pairMap.get(currentActiveGame).getFragment(), this,false, R.id.game_host_fragment);
        }
//        checkFinish();


    }

    private void initPointsFragment(){

        pf = PointsFragment.newInstance(pairMap.get(currentActiveGame).getGame().getDuration(), gameInfo);
        pf.setTimerCallBack(this);
        FragmentSwitch.to(pf, this, false, R.id.game_points_fragment);
    }

    @Override
    public void onTimeTick(int secondsLeft) {
    }


    @Override
    public void onTimerFinished() {
//       checkFinish();
    }

//    private void checkFinish(){
//
//        if (currentActiveGame == 2){
//            MastermindFragment mm = (MastermindFragment) pairMap.get(currentActiveGame).getFragment();
//            if (mm != null){
//                Game cg = pairMap.get(currentActiveGame).getGame();
//                if (cg.getActiveRound() < cg.getRounds()){
//                    cg.setActiveRound(cg.getActiveRound()+1);
//                    mm.resetAll();
//                    initFragments();
//                }
//                else {
//                    currentActiveGame++;
//                }
//            }
//        } else if (currentActiveGame == 1){
//            AssociationFragment asoc = (AssociationFragment) getSupportFragmentManager().findFragmentById(R.id.game_host_fragment);
//            if (asoc !=null){
//                Game cg = pairMap.get(currentActiveGame).getGame();
//                if (cg.getActiveRound() < cg.getRounds()){
//                    cg.setActiveRound(cg.getActiveRound()+1);
//                    asoc.resetAll();
//                    initFragments();
//                }
//                else {
//                    currentActiveGame++;
//                }
//            }
//        }else if (currentActiveGame == 0){
//            QuestionFragment asoc = (QuestionFragment) getSupportFragmentManager().findFragmentById(R.id.game_host_fragment);
//            if (asoc !=null){
//                Game cg = pairMap.get(currentActiveGame).getGame();
//                if (cg.getActiveRound() < cg.getRounds()){
//                    cg.setActiveRound(cg.getActiveRound()+1);
//                    initFragments();
//                }
//                else {
//                    currentActiveGame++;
//                }
//            }
//        }
//    }

    private void switchGame(){
        currentActiveGame++;
        initGameFragment();
    }



    @Override
    public void onAnswerSubmitMastermind(int points, boolean finish) {
        PointsFragment pf = (PointsFragment) getSupportFragmentManager().findFragmentById(R.id.game_points_fragment);
        pf.updatePlayerPoints(user.getId(), points);
        if (finish){
            Intent intent = new Intent(this, GuestHomeActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onAnswerSubmitAssociation(int points, boolean finish) {
        PointsFragment pf = (PointsFragment) getSupportFragmentManager().findFragmentById(R.id.game_points_fragment);
        pf.updatePlayerPoints(user.getId(), points);
        if (finish){
            switchGame();
        }
    }

    @Override
    public void onAnswerSubmitQuestion(boolean finish) {
        if (finish){
            switchGame();
        }

    }

    @Override
    public void updateQuestionPoints() {
        runOnUiThread(() -> {
            pf.updatePlayerPoints("", 0);
        });
    }
}