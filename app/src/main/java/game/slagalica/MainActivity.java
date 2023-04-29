package game.slagalica;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import game.slagalica.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);

        Button guestBtn =(Button)findViewById(R.id.guest_btn);
        guestBtn.setOnClickListener((view -> {
            Intent intent =new Intent(MainActivity.this, GuestHomeActivity.class);
            startActivity(intent);
        }));

        Button loginBtn = (Button) findViewById(R.id.login_btn);
        loginBtn.setOnClickListener((view -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }));

        Button regBtn = (Button) findViewById(R.id.register_btn);
        regBtn.setOnClickListener((view -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        }));

    }

}