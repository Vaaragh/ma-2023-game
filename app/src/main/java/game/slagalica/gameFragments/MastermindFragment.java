package game.slagalica.gameFragments;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import game.slagalica.R;
import game.slagalica.SocketHandler;
import game.slagalica.model.aggregate.GameInfo;
import game.slagalica.model.single.User;
import io.socket.client.Socket;


public class MastermindFragment extends Fragment {

    private View view;

    private User user;

    private Socket socket;
    private GameInfo gameInfo;

    private int numOfTries = 0;
    private int numOfIcons = 0;
    private ImageView row11,row12,row13,row14;
    private ImageView row21,row22,row23,row24;
    private ImageView row31,row32,row33,row34;

    private ImageView row41,row42,row43,row44;
    private ImageView row51,row52,row53,row54;
    private ImageView row61,row62,row63,row64;

    private ImageView ans31,ans32,ans33,ans34;
    private ImageView ans21,ans22,ans23,ans24;
    private ImageView ans11,ans12,ans13,ans14;

    private ImageView ans41,ans42,ans43,ans44;
    private ImageView ans51,ans52,ans53,ans54;
    private ImageView ans61,ans62,ans63,ans64;

    private ImageView rowB1,rowB2,rowB3,rowB4,rowB5,rowB6;
    private List<ImageView> rowB = new ArrayList<>();

    List<Integer>genA=new ArrayList<>();
    List<Integer>subA= new ArrayList<>();

    private List<ImageView> row1 = new ArrayList<>();
    private List<ImageView> row2 = new ArrayList<>();
    private List<ImageView> row3 = new ArrayList<>();
    private List<ImageView> row4 = new ArrayList<>();
    private List<ImageView> row5 = new ArrayList<>();

    private List<ImageView> row6 = new ArrayList<>();

    private List<List<ImageView>> row = new ArrayList<>();


    private List<ImageView> ans1 = new ArrayList<>();
    private List<ImageView> ans2 = new ArrayList<>();
    private List<ImageView> ans3 = new ArrayList<>();
    private List<ImageView> ans4 = new ArrayList<>();
    private List<ImageView> ans5 = new ArrayList<>();

    private List<ImageView> ans6 = new ArrayList<>();

    private List<List<ImageView>> ans = new ArrayList<>();

    private int rowCounter = 0;
    private int columnCounter = 0;

    private int corPos = 0;
    private int corSym = 0;


    private SubmitCallback submitCallback;

    private boolean finished;


    private MastermindFragment(){}

    public static MastermindFragment newInstance(GameInfo gameInfo, User user){
        MastermindFragment mmf =new MastermindFragment();
        mmf.gameInfo = gameInfo;
        mmf.user = user;
        return mmf;
    }

    public interface SubmitCallback {
        void onAnswerSubmitMastermind(int points, boolean finished);
    }
    public void setSubmitCallback(SubmitCallback callback) {
        submitCallback = callback;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_mastermind, container, false);
        socket = SocketHandler.getInstance().getSocket();
        try {
            socket.emit("startMastermind", new JSONObject().put("matchId", gameInfo.getId()));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        initFields();
        initRes();
        initBut();
        initListeners();

        return view;
    }

    private void initFields(){
        row11 = view.findViewById(R.id.row11);
        row12 = view.findViewById(R.id.row12);
        row13 = view.findViewById(R.id.row13);
        row14 = view.findViewById(R.id.row14);
        row1 = Arrays.asList(row11,row12,row13,row14);

        row21 = view.findViewById(R.id.row21);
        row22 = view.findViewById(R.id.row22);
        row23 = view.findViewById(R.id.row23);
        row24 = view.findViewById(R.id.row24);
        row2 = Arrays.asList(row21,row22,row23,row24);

        row31 = view.findViewById(R.id.row31);
        row32 = view.findViewById(R.id.row32);
        row33 = view.findViewById(R.id.row33);
        row34 = view.findViewById(R.id.row34);
        row3 = Arrays.asList(row31,row32,row33,row34);

        row41 = view.findViewById(R.id.row41);
        row42 = view.findViewById(R.id.row42);
        row43 = view.findViewById(R.id.row43);
        row44 = view.findViewById(R.id.row44);
        row4 = Arrays.asList(row41,row42,row43,row44);


        row51 = view.findViewById(R.id.row51);
        row52 = view.findViewById(R.id.row52);
        row53 = view.findViewById(R.id.row53);
        row54 = view.findViewById(R.id.row54);
        row5 = Arrays.asList(row51,row52,row53,row54);

        row61 = view.findViewById(R.id.row61);
        row62 = view.findViewById(R.id.row62);
        row63 = view.findViewById(R.id.row63);
        row64 = view.findViewById(R.id.row64);
        row6 = Arrays.asList(row61,row62,row63,row64);

        row = Arrays.asList(row1,row2,row3, row4, row5, row6);
    }

    private void initRes(){
        ans11 = view.findViewById(R.id.ans11);
        ans12 = view.findViewById(R.id.ans12);
        ans13 = view.findViewById(R.id.ans13);
        ans14 = view.findViewById(R.id.ans14);
        ans1 = Arrays.asList(ans11,ans12,ans13,ans14);

        ans21 = view.findViewById(R.id.ans21);
        ans22 = view.findViewById(R.id.ans22);
        ans23 = view.findViewById(R.id.ans23);
        ans24 = view.findViewById(R.id.ans24);
        ans2 = Arrays.asList(ans21,ans22,ans23,ans24);

        ans31 = view.findViewById(R.id.ans31);
        ans32 = view.findViewById(R.id.ans32);
        ans33 = view.findViewById(R.id.ans33);
        ans34 = view.findViewById(R.id.ans34);
        ans3 = Arrays.asList(ans31,ans32,ans33,ans34);

        ans41 = view.findViewById(R.id.ans41);
        ans42 = view.findViewById(R.id.ans42);
        ans43 = view.findViewById(R.id.ans43);
        ans44 = view.findViewById(R.id.ans44);
        ans4 = Arrays.asList(ans41,ans42,ans43,ans44);

        ans51 = view.findViewById(R.id.ans51);
        ans52 = view.findViewById(R.id.ans52);
        ans53 = view.findViewById(R.id.ans53);
        ans54 = view.findViewById(R.id.ans54);
        ans5 = Arrays.asList(ans51,ans52,ans53,ans54);


        ans61 = view.findViewById(R.id.ans61);
        ans62 = view.findViewById(R.id.ans62);
        ans63 = view.findViewById(R.id.ans63);
        ans64 = view.findViewById(R.id.ans64);
        ans6 = Arrays.asList(ans61,ans62,ans63,ans64);

        ans = Arrays.asList(ans1,ans2,ans3,ans4,ans5,ans6);
    }
    private void initBut(){

        rowB1 = view.findViewById(R.id.rowB1);
        rowB2 = view.findViewById(R.id.rowB2);
        rowB3 = view.findViewById(R.id.rowB3);
        rowB4 = view.findViewById(R.id.rowB4);
        rowB5 = view.findViewById(R.id.rowB5);
        rowB6 = view.findViewById(R.id.rowB6);
        rowB = Arrays.asList(rowB1,rowB2,rowB3,rowB4,rowB5,rowB6);
    }

    private void initListeners(){
        int index = 0;
        for (ImageView im: rowB){
            int finalIndex = index;
            im.setOnClickListener(v -> {
                row.get(rowCounter).get(columnCounter).setImageDrawable(im.getDrawable());
                columnCounter++;
                subA.add(finalIndex);
                if (columnCounter == 4){
                    try {
                        socket.emit("answerMastermind", new JSONObject()
                                .put("answer", subA)
                                .put("counter", rowCounter+1)
                                .put("player", user.getId())
                                .put("match", gameInfo.getId()));
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    socket.on("pegUpdate", val -> {
                        JSONObject jsonObject = (JSONObject) val[0];
                        try {
                            JSONObject stats = jsonObject.getJSONObject("mastermindInfo");
                            corPos = stats.getInt("fullMatch");
                            corSym = stats.getInt("partialMatch");
                            columnCounter = 0;
                            rowCounter++;
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    });

                    renderRes(rowCounter-1);
                    subA = new ArrayList<>();
                }
            });
            index++;
        }
    }





    private void renderRes(int r){
        List comboAns = new ArrayList<>();
        for (int i=0;i<corPos;i++){
            comboAns.add("b");
        }
        for (int i=0;i<corSym;i++){
            comboAns.add("w");
        }
        for (int i=0;i<comboAns.size();i++){
            if (comboAns.get(i).equals("b")) {
                ans.get(r).get(i).setImageDrawable(getResources().getDrawable(R.drawable.ic_home_black_24dp));
            } else {
                ans.get(r).get(i).setImageDrawable(getResources().getDrawable(R.drawable.ic_dashboard_black_24dp));
            }
        }
        if (corPos==4){
            submitPoints();
        }
        corPos=0;
        corSym=0;
    }

    private void submitPoints(){
        if (rowCounter==0 || rowCounter==1){
            submitCallback.onAnswerSubmitMastermind(20, true);
            return;
        }
        if (rowCounter==2 || rowCounter==3){
            submitCallback.onAnswerSubmitMastermind(15, true);
            return;
        }
        if (rowCounter==4 || rowCounter==5){
            submitCallback.onAnswerSubmitMastermind(10, true);
            return;
        }
        submitCallback.onAnswerSubmitMastermind(0, true);
    }

    public void resetAll(){
        initFields();
        initRes();
        setBlanks();
        initBut();
        initListeners();
    }

    public void setBlanks(){
        for (List<ImageView> im : row){
            for (ImageView m:im){
                m.setImageDrawable(null);
            }
        }
        for (List<ImageView> em : ans){
            for(ImageView e: em){
                e.setImageDrawable(null);
            }
        }
    }



}