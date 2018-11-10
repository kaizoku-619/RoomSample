package net.kaizoku.roomsample;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import net.kaizoku.roomsample.database.Movie;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ListView listView;
    private List<Movie> moviesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list_view);
        FloatingActionButton fab = findViewById(R.id.fab);

        final MovieViewModel movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);

        movieViewModel.getMovies().observe(MainActivity.this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                assert movies != null;
                moviesList = movies;
                updateListView(moviesList);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddMovieActivity.class);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                movieViewModel.deleteMovie(moviesList.get(i));
                Snackbar.make(view, "Movie Deleted", Snackbar.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    private void updateListView(List<Movie> movieList) {
        ArrayList<String> movieNames = new ArrayList<>();
        for (Movie movie : movieList) {
            movieNames.add(movie.getMovieName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                movieNames
        );
        listView.setAdapter(adapter);
    }
}
