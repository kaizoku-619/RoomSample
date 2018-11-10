package net.kaizoku.roomsample;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import net.kaizoku.roomsample.database.Movie;

public class AddMovieActivity extends AppCompatActivity {

    private static final String TAG = "AddMovieActivity";
    private EditText idEt, nameEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);

        idEt = findViewById(R.id.movie_id);
        nameEt = findViewById(R.id.movie_name);
        Button addMovieBtn = findViewById(R.id.add_movie);

        final MovieViewModel movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);

        addMovieBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (idEt.getText().toString().isEmpty() || nameEt.getText().toString().isEmpty()) {
                    Snackbar.make(view, "Missing data", Snackbar.LENGTH_SHORT);
                } else {
                    Movie mMovie = new Movie(idEt.getText().toString(), nameEt.getText().toString());
                    movieViewModel.addMovie(mMovie);
                    Snackbar.make(view, "movie added", Snackbar.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
