package game.slagalica;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class GuestHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_guest_home);
    }
}