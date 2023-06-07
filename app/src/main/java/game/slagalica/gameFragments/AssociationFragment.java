package game.slagalica.gameFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import game.slagalica.R;

public class AssociationFragment extends Fragment {

    private View view;
    private List<String> column1;
    private List<String> column2;
    private List<String> column3;
    private List<String> column4;

    private List<String> winList;

    private List<TextView> columnA = new ArrayList<>();
    private List<TextView> columnB = new ArrayList<>();
    private List<TextView> columnC = new ArrayList<>();
    private List<TextView> columnD = new ArrayList<>();

    private List<EditText> winInputs = new ArrayList<>();

    private TextView a1,a2,a3,a4,b1,b2,b3,b4,c1,c2,c3,c4,d1,d2,d3,d4;
    private EditText a,b,c,d,win;
    private Button subBtn;

    private int points;

    private List<Boolean> columFlags;

    private SubmitCallback submitCallback;

    private boolean finished = false;



    public static AssociationFragment newInstance(){
        return new AssociationFragment();
    }

    public interface SubmitCallback {
        void onAnswerSubmitAssociation(int points, boolean finish);
    }
    public void setSubmitCallback(SubmitCallback callback) {
        submitCallback = callback;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_association, container, false);
        initTextViews();
        initEditTexts();
        fetchMapData();
        initTextViewListeners();
        initSubmitBtn();

        return view;
    }

    private void initTextViews(){
        a1 = view.findViewById(R.id.column_1_1);
        a2 = view.findViewById(R.id.column_1_2);
        a3 = view.findViewById(R.id.column_1_3);
        a4 = view.findViewById(R.id.column_1_4);
        columnA = Arrays.asList(a1,a2,a3,a4);

        b1 = view.findViewById(R.id.column_2_1);
        b2 = view.findViewById(R.id.column_2_2);
        b3 = view.findViewById(R.id.column_2_3);
        b4 = view.findViewById(R.id.column_2_4);
        columnB = Arrays.asList(b1,b2,b3,b4);

        c1 = view.findViewById(R.id.column_3_1);
        c2 = view.findViewById(R.id.column_3_2);
        c3 = view.findViewById(R.id.column_3_3);
        c4 = view.findViewById(R.id.column_3_4);
        columnC = Arrays.asList(c1,c2,c3,c4);

        d1 = view.findViewById(R.id.column_4_1);
        d2 = view.findViewById(R.id.column_4_2);
        d3 = view.findViewById(R.id.column_4_3);
        d4 = view.findViewById(R.id.column_4_4);
        columnD = Arrays.asList(d1,d2,d3,d4);
    }

    private void initEditTexts(){
        columFlags = new ArrayList<>();
        columFlags.add(false);
        columFlags.add(false);
        columFlags.add(false);
        columFlags.add(false);
        a = view.findViewById(R.id.column_1_answer);
        b = view.findViewById(R.id.column_2_answer);
        c = view.findViewById(R.id.column_3_answer);
        d = view.findViewById(R.id.column_4_answer);
        win = view.findViewById(R.id.win_answer);
        winInputs = Arrays.asList(a,b,c,d,win);
    }

    private void initSubmitBtn(){
        subBtn = view.findViewById(R.id.submit_btn);
        subBtn.setOnClickListener(v -> {
            int points = 0;
            for (int i=0;i<winInputs.size();i++){
                if (winInputs.get(i).getText().toString().equals(winList.get(i))){
                    revealEdit(winInputs.get(i),winList.get(i));
                    switch(i){
                        case 0:
                            if (!columFlags.get(i)){
                            fillArray(columnA,column1);
                            columFlags.set(i, true);
                            this.points +=2;                            }
                            break;
                        case 1:
                            if (!columFlags.get(i)){
                                fillArray(columnB,column2);
                                columFlags.set(i, true);
                                this.points +=2;                            }
                            break;
                        case 2:
                            if (!columFlags.get(i)){
                                fillArray(columnC,column3);
                                columFlags.set(i, true);
                                this.points +=2;                            }
                            break;
                        case 3:
                            if (!columFlags.get(i)){
                                fillArray(columnD,column4);
                                columFlags.set(i, true);
                                this.points +=2;
                            }
                            break;
                        case 4:
                            fillArray(columnA,column1);
                            fillArray(columnB,column2);
                            fillArray(columnC,column3);
                            fillArray(columnD,column4);
                            fillEdits(winInputs,winList);
                            this.points +=7;
                            this.finished = true;
                            break;
                    }
                    submitAnswer();
                    this.points = 0;
                } else {
                    reveal(winInputs.get(i),"");
                }
            }
        });
    }

    private void fetchMapData(){
        FirebaseFirestore fsdb = FirebaseFirestore.getInstance();
        fsdb.collection("association").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                QuerySnapshot querySnapshot=task.getResult();
                if (querySnapshot!=null){
                    for (QueryDocumentSnapshot document:querySnapshot){
                        column1 = (List<String>) document.get("column1");
                        column2 = (List<String>) document.get("column2");
                        column3 = (List<String>) document.get("column3");
                        column4 = (List<String>) document.get("column4");

                        winList = (List<String>) document.get("column5");
                    }
                }
            }
        });
    }
    private void initTextViewListeners(){
        for (int i=0; i<columnA.size();i++){
            TextView t = columnA.get(i);
            final int j = i;
            t.setOnClickListener(v -> {
                reveal(t,column1.get(j));
            });
        }
        for (int i=0; i<columnB.size();i++){
            TextView t = columnB.get(i);
            final int j = i;
            t.setOnClickListener(v -> {
                reveal(t,column2.get(j));
            });
        }
        for (int i=0; i<columnC.size();i++){
            TextView t = columnC.get(i);
            final int j = i;
            t.setOnClickListener(v -> {
                reveal(t,column3.get(j));
            });
        }
        for (int i=0; i<columnD.size();i++){
            TextView t = columnD.get(i);
            final int j = i;
            t.setOnClickListener(v -> {
                reveal(t,column4.get(j));
            });
        }
    }

    private void assignFieldPoint(TextView te){
        if (te.getText().toString().equals("")){
        points++;
        }
    }

    private void fillArray(List<TextView> t, List<String> s){
        for (int i=0;i<t.size();i++){
            assignFieldPoint(t.get(i));
            reveal(t.get(i),s.get(i));
        }
    }
    private void reveal(TextView te, String se){
        te.setText(se);
    }

    private void assignEditPoint(EditText et){
        if (et.getText().toString().equals("")){
            points += 2;
        }
    }

    private void revealEdit(EditText et, String se){
        assignEditPoint(et);
        et.setText(se);
        disableEdit(et);
    }

    private void fillEdits(List<EditText> t, List<String> s){
        for (int i=0;i<t.size();i++){
            revealEdit(t.get(i), s.get(i));
        }
    }
    private void disableEdit(EditText t){
        t.setEnabled(false);
    }
    private void enableEdit(EditText t){
        t.setEnabled(true);
    }
    private void enableAll(List<EditText> t){
        for(EditText te:t){
            enableEdit(te);
        }
    }
    private void disableAll(List<EditText> t){
        for(EditText te: t){
            disableEdit(te);
        }
    }

    public void resetAll(){
        List<String> empty = new ArrayList<>();
        empty.add(" ");
        empty.add(" ");
        empty.add(" ");
        empty.add(" ");
        fillArray(columnA,empty);
        fillArray(columnB,empty);
        fillArray(columnC,empty);
        fillArray(columnD,empty);
        empty.add(" ");
        fillEdits(winInputs,empty);
    }

    public void submitAnswer(){
        if (submitCallback != null){
            submitCallback.onAnswerSubmitAssociation(this.points,this.finished);
        }
    }

}