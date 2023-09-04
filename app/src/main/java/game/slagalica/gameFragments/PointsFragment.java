package game.slagalica.gameFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import game.slagalica.R;
import game.slagalica.model.aggregate.GameInfo;
import game.slagalica.model.aggregate.PlayerInfo;


public class PointsFragment extends Fragment {
    private View view;
    private long duration;
    private TimerCallBack timerCallBack;
    private TextView timeLeft;

    private PlayerInfo leftPLayer, rightPlayer;

    private TextView leftPoints, rightPoints;
    private TextView leftUser, rightUser;
    private GameInfo gameInfo;

    public interface TimerCallBack{
        void onTimeTick(int secondsLeft);
        void onTimerFinished();

    }

    public static PointsFragment newInstance(long duration, GameInfo gameInfo) {
        PointsFragment fragment = new PointsFragment();
        fragment.gameInfo = gameInfo;
        fragment.duration = duration;
        return fragment;
    }

    public void updatePlayerPoints(String id, int points){
        if (id.equals(leftPLayer.getPlayerId())){
            leftPLayer.setPoints(leftPLayer.getPoints() + points);
            Log.d("peoni left", String.valueOf(leftPLayer.getPoints()));
        } else {
            rightPlayer.setPoints(rightPlayer.getPoints() + points);
            Log.d("peoni right", String.valueOf(leftPLayer.getPoints()));
        }
        rightPoints.setText(String.valueOf(rightPlayer.getPoints()));
        leftPoints.setText(String.valueOf(leftPLayer.getPoints()));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_points, container, false);
        initPLayers();
        initPoints();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        timeLeft = view.findViewById(R.id.timer);
        startTimer(duration);
    }

    private void initPoints(){
        leftPoints = view.findViewById(R.id.player_1_points);
        leftPoints.setText(String.valueOf(leftPLayer.getPoints()));
        rightPoints = view.findViewById(R.id.player_2_points);
        rightPoints.setText(String.valueOf(rightPlayer.getPoints()));
        leftUser = view.findViewById(R.id.player_1);
        leftUser.setText(leftPLayer.getUsername());
        rightUser = view.findViewById(R.id.player_2);
        rightUser.setText(rightPlayer.getUsername());
    }

    private void initPLayers(){
        leftPLayer = gameInfo.getPlayer1();
        rightPlayer = gameInfo.getPlayer2();
    }


    public void setTimerCallBack(TimerCallBack callBack){
        timerCallBack = callBack;
    }

    public void startTimer(long duration) {
        long miDur = duration * 60 * 1000 / 12;
        new CountDownTimer(miDur,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                int left = (int) (millisUntilFinished/1000);
                if (timerCallBack != null){
                    timerCallBack.onTimeTick(left);
                }
                if (timeLeft != null){
                    timeLeft.setText(String.valueOf(left));
                }
            }
            @Override
            public void onFinish() {
                if (timerCallBack != null) {
                    timerCallBack.onTimerFinished();
                }
            }
        }.start();
    }

}