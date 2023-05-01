package game.slagalica;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import game.slagalica.databinding.ActivityGameBinding;

public class GameActivity extends AppCompatActivity {


    private ActivityGameBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        binding = ActivityGameBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.game_nav_view);

        AppBarConfiguration appBarConfig = new AppBarConfiguration.Builder(
                R.id.navigation_question, R.id.navigation_conect, R.id.navigation_association).build();

        NavController navCont = Navigation.findNavController(this, R.id.game_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navCont, appBarConfig);
        NavigationUI.setupWithNavController(binding.gameNavView, navCont);


    }
}