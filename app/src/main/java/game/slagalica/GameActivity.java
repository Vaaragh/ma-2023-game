package game.slagalica;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

import game.slagalica.databinding.ActivityGameBinding;
import game.slagalica.gameFragments.AssociationFragment;
import game.slagalica.gameFragments.ConectFragment;
import game.slagalica.gameFragments.MastermindFragment;
import game.slagalica.gameFragments.MyNumberFragment;
import game.slagalica.gameFragments.QuestionFragment;
import game.slagalica.gameFragments.StepsFragment;

public class GameActivity extends AppCompatActivity {

    private int counter=0;
    private ActivityGameBinding binding;
    private List<Fragment> fList= new ArrayList<Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        binding = ActivityGameBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        FragmentTransaction ft0 = getSupportFragmentManager().beginTransaction();
        ft0.replace(R.id.game_host_fragment, new QuestionFragment());
        ft0.commit();

        fList.add(new ConectFragment());
        fList.add(new AssociationFragment());
        fList.add(new MastermindFragment());
        fList.add(new StepsFragment());
        fList.add(new MyNumberFragment());


        Button game2 = findViewById(R.id.switch_button);
        game2.setOnClickListener(view -> {
            if (counter != 5) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.game_host_fragment, fList.get(counter));
                ft.commit();
                counter += 1;
            } else {
                counter = 0;
                Intent intent = new Intent(GameActivity.this, UserHomeActivity.class);
                startActivity(intent);
            }
        });


    }
}