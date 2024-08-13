package com.example.moviesfirebase;

import android.content.Intent;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditMovie extends AppCompatActivity {

    EditText edtTitle, edtStudio, edtCRating;
    String title, studio, criticsRating, thumbnail;
    Button add;
    String doc_id;
    public FirebaseFirestore db;
    public DocumentReference df;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movie);

        edtStudio = findViewById(R.id.studio);
        edtTitle = findViewById(R.id.title);
        edtCRating = findViewById(R.id.et_rate);
        add = findViewById(R.id.btnDetails);

        doc_id=getIntent().getStringExtra("document_id");

        title=getIntent().getStringExtra("title");
        studio=getIntent().getStringExtra("studio");
        criticsRating=getIntent().getStringExtra("cric_rate");
        thumbnail = "";

        edtTitle.setText(title);
        edtStudio.setText(studio);
        edtCRating.setText(criticsRating);



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    edit();
                } catch (Exception e) {
                    Log.e("dalbhat", "exception_insertjion " + e.getMessage());
                }
            }
        });

    }

    public void add_data() {

        String title = edtTitle.getText().toString().trim();
        String studio = edtStudio.getText().toString().trim();
        String cric_rate = edtCRating.getText().toString().trim();

        //MovieDetails movie = new MovieDetails("",title,studio,cric_rate,"","");
        //MovieDetails movie = new MovieDetails();
        // Create a new movie document using a Map
        Map<String, Object> movie = new HashMap<>();
        movie.put("title", title);
        movie.put("studio", studio);
        movie.put("criticsRating", cric_rate);
        movie.put("image", "");
        movie.put("shortDescription", "");


        db.collection("Movies")
                .add(movie)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Intent i = new Intent(EditMovie.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("daalbhat", "data_failed");
                    }
                });
    }

    public void edit()
    {
        df = db.collection("Movies").document(doc_id);

        df.update("title", edtTitle.getText().toString().trim(),
                        "criticsRating", edtCRating.getText().toString().trim(),
                        "studio", edtStudio.getText().toString().trim()).
                addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Intent i = new Intent(EditMovie.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("daalbhat", "data_updation_failed");
                    }
                });

    }
}