package game.slagalica.userFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import game.slagalica.GameActivity;
import game.slagalica.R;

public class HomeFragment extends Fragment {

   public HomeFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
       View rootView = inflater.inflate(R.layout.fragment_home, container, false);
       Button startBtn = rootView.findViewById(R.id.start_btn_user);
       startBtn.setOnClickListener((view -> {
           Intent intent = new Intent(getActivity(), GameActivity.class);
           startActivity(intent);
       }));


       return rootView;
    }
}