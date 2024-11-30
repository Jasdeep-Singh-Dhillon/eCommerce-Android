package com.jasdeep.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SignUp extends AppCompatActivity {
    private FirebaseAuth auth;

    private EditText emailTxt;
    private EditText passwordTxt;
    private EditText passwordRepeatTxt;

    private Button signUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser() != null) {
            auth.signOut();
        }

        emailTxt = findViewById(R.id.emailTxt);
        passwordTxt = findViewById(R.id.passwordTxt);
        passwordRepeatTxt = findViewById(R.id.passwordRepeatTxt);
        signUpBtn = findViewById(R.id.signupBtn);

        signUpBtn.setOnClickListener(view -> {
            String emailInput = emailTxt.getEditableText().toString();
            String passwordInput = passwordTxt.getEditableText().toString();
            String passwordRepeatInput = passwordRepeatTxt.getEditableText().toString();

            if(passwordInput.equals(passwordRepeatInput) && isValidEmail(emailInput) && isValidPassword(passwordInput)) {
                createUser(emailInput, passwordInput);
            }
        });
    }

    private void createUser(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("SIGNUP_ACTIVITY", "createUserWithEmail:success");
                            FirebaseUser user = auth.getCurrentUser();
                            Toast.makeText(
                                            getApplicationContext(),
                                            "Sign Up successful",
                                            Toast.LENGTH_SHORT)
                                    .show();
                            FirebaseDatabase.getInstance().getReference().child("cart").child(user.getUid()).setValue("");
                            if(user != null) {
                                Intent intent = new Intent(getApplicationContext(), Login.class);
                                startActivity(intent);
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("SIGNUP_ACTIVITY", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();}
                    }
                });
    }

    private boolean isValidEmail (String email) {
        return true;
    }

    private boolean isValidPassword(String password) {
        return true;
    }
}