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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText email, password;
    TextView  signUp;
    Button login;
    public FirebaseAuth auth;
    String uemail, upassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        auth = FirebaseAuth.getInstance();

        initView();
    }

    public void initView() {
        email = findViewById(R.id.edtEmail);
        password = findViewById(R.id.edtPassword);

        signUp = findViewById(R.id.txtSignup);
        login = findViewById(R.id.btnLogin);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SignUp.class);
                startActivity(i);
            }
        });

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(MainActivity.this, MoviesDashboard.class));
            finish();
        }
        else {
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        uemail = email.getText().toString().trim();
                        upassword = password.getText().toString().trim();

                        if (uemail.isEmpty() || upassword.isEmpty()) {
                            if (uemail.isEmpty()) {
                                email.setError("Required Field");
                            }
                            if (upassword.isEmpty()) {
                                password.setError("Required Field");
                            }
                        }
                        else {
                            String user_email = email.getText().toString().trim();
                            String user_pass = password.getText().toString().trim();

                            auth.signInWithEmailAndPassword(user_email,user_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        startActivity(new Intent(MainActivity.this, MoviesDashboard.class));
                                        Log.e("Bunny","Successful.");
                                    } else {
                                        Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                    catch (Exception e)
                    {
                        Log.e("in_Main","exception "+e.getMessage());
                    }
                }
            });
        }
    }
}