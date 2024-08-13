package com.example.moviesfirebase.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviesfirebase.EditMovie;
import com.example.moviesfirebase.Model.MovieModel;
import com.example.moviesfirebase.OnCLicked;
import com.example.moviesfirebase.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;


public class AllMoviesAdapter extends RecyclerView.Adapter<AllMoviesAdapter.ViewHolder>
{

    public Context context;
    public List<MovieModel> movie_list;
    OnCLicked onCLicked;




    public AllMoviesAdapter(Context context, List<MovieModel> movie_list, OnCLicked onCLicked)
    {
        this.context = context;
        this.movie_list = movie_list;
        this.onCLicked=onCLicked;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        ViewHolder card = new ViewHolder(v);
        return card;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position)
    {



        final MovieModel movie_details = movie_list.get(position);

//        int id=movie_details.id;
        String title=movie_details.title;
       String studio=movie_details.studio;
     String cric_rating=movie_details.criticsRating;
     String imagepath=movie_details.image;
     String doc_id=movie_details.id;
//        Log.e("mango","imagepath"+imagepath);

        holder.movieTitle.setSelected(true);
        holder.movieTitle.setText(title);
        holder.movieCRating.setText(cric_rating);
        holder.movieStudio.setText(studio);

        Glide.with(context).load(Uri.parse(imagepath)).into(holder.movieImage);


        holder.btnMEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(context, EditMovie.class);
                i.putExtra("document_id",doc_id);
                i.putExtra("title",title);
                i.putExtra("studio",studio);
                i.putExtra("cric_rate",cric_rating);
                context.startActivity(i);



            }
        });

        holder.btnMDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //on_movie_clicked.on_delete_clicked(id,movie_details);


                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("Movies").document(doc_id)
                        .delete()
                        .addOnSuccessListener(aVoid -> {
                            // Remove the movie from the list and notify the adapter
                            movie_list.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, movie_list.size());
                            Toast.makeText(context, "Data Deleted", Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e -> {
                            // Handle the failure
                        });
            }
        });

    }



    @Override
    public int getItemCount() {

        return movie_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView movieTitle,movieStudio,movieCRating;
       ImageView movieImage;
       Button btnMEdit,btnMDelete;

       LinearLayout fl_category;

        public ViewHolder(View itemView) {
            super(itemView);

            movieImage = itemView.findViewById(R.id.movieImage);
            btnMEdit = itemView.findViewById(R.id.btnMEdit);
            btnMDelete = itemView.findViewById(R.id.btnMDelete);
            movieTitle = itemView.findViewById(R.id.movieTitle);
            movieStudio = itemView.findViewById(R.id.movieStudio);
            movieCRating = itemView.findViewById(R.id.movieCRating);


        }
    }

}
