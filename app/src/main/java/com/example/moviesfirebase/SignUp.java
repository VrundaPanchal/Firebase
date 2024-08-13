package com.example.moviesfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.moviesfirebase.Adapters.NewUserAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class SignUp extends AppCompatActivity {

    EditText email, password, confirmPasssword;
    String uemail, upassword, uconfirmPassword;
    TextView login;
    Button signup;
    public FirebaseAuth auth;
    public FirebaseFirestore fs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        auth = FirebaseAuth.getInstance();
        fs = FirebaseFirestore.getInstance();

        initView();

    }

    public void initView() {
        email = findViewById(R.id.edtEmail);
        password = findViewById(R.id.edtPassword);
        confirmPasssword = findViewById(R.id.edtConPassword);
        signup = findViewById(R.id.btnSignup);
        login = findViewById(R.id.txtlogin);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().trim().isEmpty()
                        || password.getText().toString().trim().isEmpty()
                        || confirmPasssword.getText().toString().trim().isEmpty()) {
                    if (email.getText().toString().trim().isEmpty()) {
                        email.setError("Required Field");
                    }
                    if (password.getText().toString().trim().isEmpty()) {
                        password.setError("Required Field");
                    }
                    if (confirmPasssword.getText().toString().trim().isEmpty()) {
                        confirmPasssword.setError("Required Field");
                    }
                } else {
                    registerUser();
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUp.this, MainActivity.class);
                startActivity(i);
            }
        });
    }


    private void updateUI(FirebaseUser user) {
        if (user != null) {
            // User is signed in
            // You can update the UI here, for example, by showing a welcome message or navigating to a new screen
            Toast.makeText(this, "Welcome " + user.getEmail(), Toast.LENGTH_SHORT).show();
        } else {
            // User is signed out
            // You can update the UI here to show a message that the user is not signed in
        }
    }

    private void registerUser() {
        uemail = email.getText().toString().trim();
        upassword = password.getText().toString().trim();
        uconfirmPassword = confirmPasssword.getText().toString().trim();


        if (!upassword.equals(uconfirmPassword))
        {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
        }
        else {
            auth.createUserWithEmailAndPassword(uemail, upassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = auth.getCurrentUser();
                                updateUI(user);

                            } else {
                                Log.e("B","task : "+task.getException().getMessage());
                                // If sign in fails, display a message to the user.
                                Toast.makeText(SignUp.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);

                            }
                        }
                    });
//            createUserWithEmailAndPassword(uemail,upassword);
            Log.e("Bunny","Sign Up.0");
//            auth.createUserWithEmailAndPassword(uemail,upassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull Task<AuthResult> task) {
//                    if (task.isSuccessful()) {
//                        Log.e("Bunny","Sign Up.1");
//                        String uid = Objects.requireNonNull(task.getResult().getUser()).getUid();
//                        fs.collection("user").document(uid)
//                                .set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//                                        if (task.isSuccessful()) {
//                                            startActivity(new Intent(SignUp.this, MainActivity.class));
//                                        }
//                                    }
//                                });
//                    } else {
//                        Log.e("Bunny","Sign Up.1"+task.getException().getMessage());
//                        Toast.makeText(SignUp.this, "Signup Failed", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
        }
    }
}