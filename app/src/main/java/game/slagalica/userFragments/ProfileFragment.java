package game.slagalica.userFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import game.slagalica.MainActivity;
import game.slagalica.R;
import game.slagalica.UserHomeActivity;

public class ProfileFragment extends Fragment {



    public ProfileFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        Button logoutBtn = rootView.findViewById(R.id.logout_btn);
        logoutBtn.setOnClickListener((view -> {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }));
        return rootView;
    }




}