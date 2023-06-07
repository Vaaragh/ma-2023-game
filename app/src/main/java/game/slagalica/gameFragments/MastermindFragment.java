package game.slagalica.gameFragments;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import game.slagalica.R;


public class MastermindFragment extends Fragment {

    private View view;
    private ImageView row11,row12,row13,row14;
    private ImageView row21,row22,row23,row24;
    private ImageView row31,row32,row33,row34;

    private ImageView ans31,ans32,ans33,ans34;
    private ImageView ans21,ans22,ans23,ans24;
    private ImageView ans11,ans12,ans13,ans14;

    private ImageView rowB1,rowB2,rowB3,rowB4,rowB5,rowB6;
    private List<ImageView> rowB = new ArrayList<>();

    List<Integer>genA=new ArrayList<>();
    List<Integer>subA= new ArrayList<>();

    private List<ImageView> row1 = new ArrayList<>();
    private List<ImageView> row2 = new ArrayList<>();
    private List<ImageView> row3 = new ArrayList<>();
    private List<ImageView> row4 = new ArrayList<>();
    private List<List<ImageView>> row = new ArrayList<>();


    private List<ImageView> ans1 = new ArrayList<>();
    private List<ImageView> ans2 = new ArrayList<>();
    private List<ImageView> ans3 = new ArrayList<>();
    private List<ImageView> ans4 = new ArrayList<>();
    private List<List<ImageView>> ans = new ArrayList<>();

    private int rowCounter = 0;
    private int columnCounter = 0;

    private int corPos = 0;
    private int corSym = 0;


    private SubmitCallback submitCallback;

    private boolean finished;


    private MastermindFragment(){}

    public static MastermindFragment newInstance(){
        return new MastermindFragment();
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

        generateFinal();
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

        row = Arrays.asList(row1,row2,row3);
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

        ans = Arrays.asList(ans1,ans2,ans3);
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
    private void generateFinal(){
        genA = Arrays.asList(1,2,3,4);
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
                    columnCounter = 0;
                    rowCounter++;
                    checkAns();
                    renderRes(rowCounter-1);
                    subA = new ArrayList<>();
                }
                if (rowCounter==3){
                    rowCounter = 0;
                }
            });
            index++;
        }
    }

    private void checkAns(){
        List<Integer> copy = new ArrayList<>(genA);

        for(int i=0;i<4;i++){
            if (subA.get(i)==copy.get(i)){
                corPos++;
                copy.set(i,9);
                subA.set(i,9);
            }
        }
        if (corPos==4){
            submitPoints();
        }
        for(int j=0;j<4;j++){
            if (copy.contains(subA.get(j))){
                corSym++;
                copy.set(copy.indexOf(subA.get(j)),9);
            }
        }


    }

    private void renderRes(int r){
        for (int i=0;i<corPos;i++){
            ans.get(r).get(i).setImageDrawable(getResources().getDrawable(R.drawable.ic_home_black_24dp));
        }
        for (int j=corPos;j<corSym-1;j++){
            ans.get(r).get(j).setImageDrawable(getResources().getDrawable(R.drawable.ic_dashboard_black_24dp));
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
        generateFinal();
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