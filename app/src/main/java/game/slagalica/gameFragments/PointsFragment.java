package game.slagalica.gameFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import game.slagalica.R;
import game.slagalica.model.aggregate.PlayerInfo;


public class PointsFragment extends Fragment {
    private View view;
    private int duration;
    private TimerCallBack timerCallBack;
    private TextView timeLeft;

    private PlayerInfo leftPLayer = new PlayerInfo("guest", 0);

    private TextView leftPoints;

    public interface TimerCallBack{
        void onTimeTick(int secondsLeft);
        void onTimerFinished();

    }

    public static PointsFragment newInstance(int duration) {
        PointsFragment fragment = new PointsFragment();
        fragment.duration = duration;
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_points, container, false);

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
        leftPoints.setText(leftPLayer.getPoints());
    }


    public void setTimerCallBack(TimerCallBack callBack){
        timerCallBack = callBack;
    }

    public void startTimer(int duration) {
        long miDur = duration * 60 * 1000;
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