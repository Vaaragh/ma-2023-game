package game.slagalica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_login);

        Button loginBtn = (Button) findViewById(R.id.login_btn);
        loginBtn.setOnClickListener((view -> {
            Intent intent = new Intent(LoginActivity.this, UserHomeActivity.class);
            startActivity(intent);
        }));

    }
}