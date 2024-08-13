package com.example.moviesfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesfirebase.Adapters.AllMoviesAdapter;
import com.example.moviesfirebase.Model.MovieModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MoviesDashboard extends AppCompatActivity {


    RecyclerView rvMovieList;
    FloatingActionButton fabAdd;
    public AllMoviesAdapter all_moviewadapter;
    public FirebaseFirestore db;
    public List<MovieModel> movie_list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_movies_dashboard);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        rvMovieList = findViewById(R.id.moviesList);
        fabAdd=findViewById(R.id.fabadd);

        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(MoviesDashboard.this, RecyclerView.VERTICAL, false);
        rvMovieList.setLayoutManager(gridLayoutManager);

        db= FirebaseFirestore.getInstance();

        load_data();


       // List<MovieModel> movies = dataFetcher.fetchAllMovies();
//        all_moviewadapter = new AllMoviesAdapter(MoviesDashboard.this, movies,onCLicked);
//        rvMovieList.setAdapter(all_moviewadapter);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MoviesDashboard.this, EditMovie.class);
                startActivity(i);
            }
        });

        OnCLicked onCLicked=new OnCLicked() {
            @Override
            public void on_clicked(MovieModel moviewmodel)
            {

            }

            @Override
            public void on_delete_clicked(MovieModel position) {

            }
        };
    }

    public void load_data() {
        db.collection("Movies")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful())
                        {

                            for (DocumentSnapshot document : task.getResult())
                            {

                               String documentId = document.getId();
                                try {
                                    MovieModel movie = document.toObject(MovieModel.class);

                                    movie.setId(documentId);
                                    movie_list.add(movie);
                                }
                                catch (Exception e)
                                {
                                    Log.e("in_main","mango_exception "+e.getMessage());
                                }



                            }

                            Log.e("in_main","mango");
                            all_moviewadapter=new AllMoviesAdapter(MoviesDashboard.this,movie_list,onCLicked);
                            rvMovieList.setAdapter(all_moviewadapter);

                        }
                        else
                        {

                        }

                    }
                });

    }

    OnCLicked onCLicked=new OnCLicked() {
        @Override
        public void on_clicked(MovieModel position) {

        }

        @Override
        public void on_delete_clicked(MovieModel position) {

        }
    };
}