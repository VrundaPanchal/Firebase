package com.example.moviesfirebase;

import com.example.moviesfirebase.Model.MovieModel;

public interface OnCLicked {
    public void on_clicked(MovieModel position);
    public void on_delete_clicked(MovieModel position);
}
