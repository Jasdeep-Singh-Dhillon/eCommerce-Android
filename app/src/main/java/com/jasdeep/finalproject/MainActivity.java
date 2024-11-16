package com.jasdeep.finalproject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText emailEdtxt;
    private EditText passwordEdtxt;
    private Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        auth = FirebaseAuth.getInstance();
        emailEdtxt = findViewById(R.id.emailEdtxt);
        passwordEdtxt = findViewById(R.id.passwordEdtxt);
        loginButton = findViewById(R.id.loginBtn);

        loginButton.setOnLongClickListener(v -> {
            String email = emailEdtxt.getEditableText().toString();
            String password = passwordEdtxt.getEditableText().toString();
            if(isValidEmail(email) && isValidPassword(password))
            createUser(email, password);
            return true;
        });

        loginButton.setOnClickListener(view -> {
            String email = emailEdtxt.getEditableText().toString();
            String password = passwordEdtxt.getEditableText().toString();
            loginUser(email, password);
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser user = auth.getCurrentUser();
        if(user != null) {
            Toast.makeText(
                getApplicationContext(),
                "User logged in",
                Toast.LENGTH_SHORT
            ).show();
        }
    }

    private void createUser(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("USER_MAIN_ACTIVITY", "createUserWithEmail:success");
                            FirebaseUser user = auth.getCurrentUser();
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Created User",
                                    Toast.LENGTH_SHORT)
                                    .show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("USER_MAIN_ACTIVITY", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();}
                    }
                });
    }

    private void loginUser(String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("USER_MAIN_ACTIVITY", "signInWithEmail:success");
                            FirebaseUser user = auth.getCurrentUser();
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Login Successful",
                                    Toast.LENGTH_SHORT)
                                    .show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("USER_MAIN_ACTIVITY", "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //TODO: Validate Email and Password
    private boolean isValidEmail (String email) {
        return true;
    }

    private boolean isValidPassword(String password) {
        return true;
    }
}