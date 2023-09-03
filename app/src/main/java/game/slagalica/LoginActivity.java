package game.slagalica;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import game.slagalica.model.single.User;

public class LoginActivity extends AppCompatActivity {

    private EditText ident, password;
    private Button loginBtn;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        db = FirebaseFirestore.getInstance();

        setContentView(R.layout.activity_login);
        initView();

    }

    private void initView(){
        this.ident = findViewById(R.id.identityText);
        this.password = findViewById(R.id.passwordText);
        this.loginBtn = findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(v -> initBtn());
    }

    private void initBtn(){
        String username = ident.getText().toString();
        String pass = password.getText().toString();

        Query query = db.collection("users")
                .whereEqualTo("username", username)
                .whereEqualTo("password", pass)
                .limit(1);
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                if (querySnapshot != null && !querySnapshot.isEmpty()) {
                    DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);
                    String id = documentSnapshot.getString("id");
                    String username1 = documentSnapshot.getString("username");
                    String password = documentSnapshot.getString("password");
                    String email = documentSnapshot.getString("email");
                    Integer tokens = Integer.valueOf(Math.toIntExact(documentSnapshot.getLong("tokens")));
                    Integer stars = Integer.valueOf(Math.toIntExact(documentSnapshot.getLong("stars")));
                    User user = new User(id, username1, email, password, tokens, stars);
                    UserHomeActivity.currentUser = user;
                    startActivity(new Intent(LoginActivity.this, UserHomeActivity.class));
                    return;
                } else {
                    Toast.makeText(LoginActivity.this, "Nema te", Toast.LENGTH_SHORT).show();
                }
            } else {
                Exception exception = task.getException();
            }
        });
    }
}