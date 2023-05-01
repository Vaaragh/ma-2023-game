package game.slagalica.gameFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import game.slagalica.R;

public class MyNumberFragment extends Fragment {

    public MyNumberFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_my_number, container, false);

        //TODO


        return rootView;
    }

}