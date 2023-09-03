package game.slagalica;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

import game.slagalica.model.single.User;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailInp, usernameInp, password1Inp, password2Inp;
    private Button regBtn;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);

        db = FirebaseFirestore.getInstance();

        initEditText();
        initButton();

    }

    private void initEditText(){
        this.emailInp = findViewById(R.id.emailText);
        this.usernameInp = findViewById(R.id.usernameText);
        this.password1Inp = findViewById(R.id.passwordText_1);
        this.password2Inp = findViewById(R.id.passwordText_2);
        this.regBtn = findViewById(R.id.register_btn);
    }

    private void initButton(){

        regBtn.setOnClickListener(v -> {
            String email = this.emailInp.getText().toString();
            String username = this.usernameInp.getText().toString();
            String password1 = this.password1Inp.getText().toString();
            String password2 = this.password2Inp.getText().toString();

            if (password1.equals(password2)){
                User user = new User();
                user.setId(UUID.randomUUID().toString());
                user.setEmail(email);
                user.setUsername(username);
                user.setPassword(password1);
                user.setStars(0);
                user.setTokens(10);

                CollectionReference userDB = db.collection("users");

                userDB.add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(RegisterActivity.this, "gg", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterActivity.this, "not gg", Toast.LENGTH_SHORT).show();
                    }
                });

            } else {
                Toast.makeText(RegisterActivity.this, "Password mismatch", Toast.LENGTH_SHORT).show();
            }

        });
    }

}