package game.slagalica.gameFragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import game.slagalica.GameActivity;
import game.slagalica.R;
import game.slagalica.SocketHandler;
import game.slagalica.model.aggregate.GameInfo;
import game.slagalica.model.single.SingleQuestion;
import io.socket.client.Socket;

public class QuestionFragment extends Fragment {

    private View view;
    private TextView question;
    private List<Button> submits = new ArrayList<>();
    private List<SingleQuestion> questions = new ArrayList<>();
    private GameInfo gameInfo;
    private int currentQuestion;
    private SubmitCallback callbackQuestion;
    private Socket socket;

    public interface SubmitCallback {
        void onAnswerSubmitQuestion(int points, boolean finish);
        void updateQuestionPoints();
    }

    public SubmitCallback getSubmitCallback(){
        return callbackQuestion;
    }
    public void setSubmitCallback(SubmitCallback callbackQuestion){
        this.callbackQuestion = callbackQuestion;
    }

    public static QuestionFragment newInstance(GameInfo gameInfo){
        QuestionFragment qf = new QuestionFragment();
        qf.gameInfo = gameInfo;
        return qf;
    }
    public QuestionFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
            view = inflater.inflate(R.layout.fragment_question, container, false);
            initViews();
            socket = SocketHandler.getInstance().getSocket();
        try {
            socket.emit("startQuestion", new JSONObject().put("matchId", gameInfo.getId()));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


            initQuestions();
            initBtnListeners();


            return view;
    }

    private void initViews(){
        question = view.findViewById(R.id.question_text);
        submits = new ArrayList<>();
        submits.add(view.findViewById(R.id.answer_btn_1));
        submits.add(view.findViewById(R.id.answer_btn_2));
        submits.add(view.findViewById(R.id.answer_btn_3));
        submits.add(view.findViewById(R.id.answer_btn_4));
    }

    private void initQuestions(){
        generateQuestionList();

    }

    private SingleQuestion generateQuestion(JSONObject obj) throws JSONException {
        String q = obj.getString("question");
        String ans = obj.getString("correct");
        JSONArray aArray = obj.getJSONArray("answers");
        List<String> arr = new ArrayList<>();
        for (int i=0;i<aArray.length(); i++){
            arr.add(aArray.getString(i));
        }
        SingleQuestion ques = new SingleQuestion(q,ans,arr);
        return ques;
    }

    private void generateQuestionList(){
        socket.on("questionList", val -> {
            Log.d("Emiter", "Received");
            JSONObject jObj = (JSONObject) val[0];
            questions = new ArrayList<>();
            try{

            JSONArray jArray = jObj.getJSONArray("data");
            for (int i=0;i<jArray.length();i++){
                Log.d("Question generated", "yep");
                questions.add(generateQuestion(jArray.getJSONObject(i)));
            }
            initQuestionRound();
            } catch (JSONException e){
                throw new RuntimeException(e);
            }

        });

    }

    private void initQuestionRound(){
        question.setText(questions.get(currentQuestion).getQuestion());
        for (int i=0;i<questions.size();i++){
            submits.get(i).setText(questions.get(currentQuestion).getOptions().get(i));
        }
    }

    private void initBtnListeners(){
        for (int i=0;i<submits.size();i++){
            int finalI = i;
            submits.get(i).setOnClickListener(click -> {
                try {
                    buttonSubmit(submits.get(finalI), questions.get(currentQuestion).getAnswer());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    private void buttonSubmit(Button b, String ans) throws JSONException {
        socket.emit("answerQuestion", answerEmit(b, ans));

        if (b.getText().toString().equals(ans)){
            b.setBackgroundColor(Color.rgb(0,128,0));
        } else {
            b.setBackgroundColor(Color.rgb(255,0,0));
        }
    }

    private JSONObject answerEmit(Button b, String ans) throws JSONException {
        JSONObject ret = new JSONObject();
        ret.put("matchId", gameInfo.getId());
        ret.put("player", GameActivity.user.getId());
        ret.put("time", LocalTime.now());
        ret.put("correct", b.getText().toString().equals(ans));
        return ret;
    }

    private void questionUpdate(){
        socket.on("questionUpdate", val -> {
            JSONObject jObj = (JSONObject) val[0];
            try{
                int p1Points = jObj.getInt("player1Points");
                int p2Points = jObj.getInt("player2Points");

                this.gameInfo.getPlayer1().setPoints(p1Points);
                this.gameInfo.getPlayer2().setPoints(p2Points);
                callbackQuestion.updateQuestionPoints();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });
    }






}