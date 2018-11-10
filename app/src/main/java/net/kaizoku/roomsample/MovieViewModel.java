package net.kaizoku.roomsample;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import net.kaizoku.roomsample.database.Movie;
import net.kaizoku.roomsample.database.MovieDatabase;

import java.util.List;

public class MovieViewModel extends AndroidViewModel {

    private MovieDatabase movieDatabase;
    private final LiveData<List<Movie>> movies;
    private static final String TAG = "MovieViewModel";

    public MovieViewModel(@NonNull Application application) {
        super(application);

        movieDatabase = MovieDatabase.getDatabase(this.getApplication());
        movies = movieDatabase.daoAccess().fetchAllMovies();
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }

    public void addMovie(Movie movie) {
        if (movie == null) {
            Log.i(TAG, "addMovie: movie is null");
        } else {
            Log.i(TAG, "addMovie: " + movie.toString());
            new AddMovieAsyncTask(movieDatabase).execute(movie);
        }
    }

    public void deleteMovie(Movie movie) {
        new DeleteMovieAsyncTask(movieDatabase).execute(movie);
    }

    private static class AddMovieAsyncTask extends AsyncTask<Movie, Void, Void> {

        private MovieDatabase movieDb;

        AddMovieAsyncTask(MovieDatabase movieDatabase) {
            movieDb = movieDatabase;
        }

        @Override
        protected Void doInBackground(final Movie... params) {
            movieDb.daoAccess().insertSingleMovie(params[0]);
            return null;
        }
    }

    private static class DeleteMovieAsyncTask extends AsyncTask<Movie, Void, Void> {

        private MovieDatabase movieDb;

        DeleteMovieAsyncTask(MovieDatabase movieDatabase) {
            movieDb = movieDatabase;
        }

        @Override
        protected Void doInBackground(final Movie... params) {
            movieDb.daoAccess().deleteMovie(params[0]);
            return null;
        }
    }
}
