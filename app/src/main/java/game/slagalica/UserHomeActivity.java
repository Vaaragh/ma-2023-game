package game.slagalica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import game.slagalica.databinding.ActivityMainBinding;
import game.slagalica.databinding.ActivityUserHomeBinding;


public class UserHomeActivity extends AppCompatActivity {


    private ActivityUserHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        binding = ActivityUserHomeBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());


        BottomNavigationView navView = findViewById(R.id.nav_view);

        AppBarConfiguration appBarConfig = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_ranks, R.id.navigation_profile, R.id.navigation_friends).build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_user_home);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfig);
        NavigationUI.setupWithNavController(binding.navView, navController);





    }
}