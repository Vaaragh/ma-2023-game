package game.slagalica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class GuestHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_guest_home);

        Button startBtn = (Button) findViewById(R.id.start_btn);
        startBtn.setOnClickListener((view -> {
            Intent intent = new Intent(GuestHomeActivity.this, GameActivity.class);
            startActivity(intent);
        }));

    }
}