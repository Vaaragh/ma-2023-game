package game.slagalica.gameFragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import game.slagalica.R;

public class AssociationFragment extends Fragment {



    public AssociationFragment(){}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_association, container, false);


        ViewGroup vg = (ViewGroup) rootView;
        for (int i = 0; i < vg.getChildCount(); i++) {
            View child = vg.getChildAt(i);
            if (child instanceof TextView ){
                child.setOnClickListener((view -> {
                    child.setBackgroundColor(getResources().getColor(R.color.reveal));
                }));
            }

         }






        return rootView;
    }

}