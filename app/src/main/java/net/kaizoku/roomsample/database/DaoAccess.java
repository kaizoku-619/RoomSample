package net.kaizoku.roomsample.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface DaoAccess {

    @Insert
    void insertSingleMovie(Movie movie);

    @Insert
    void insertMultipleMovies(List<Movie> movieList);

    @Query("SELECT * FROM Movie WHERE movieId = :movieId")
    Movie fetchMovieById(int movieId);

    @Query("SELECT * FROM Movie")
    LiveData<List<Movie>> fetchAllMovies();

    @Update
    void updateMovie(Movie movie);

    @Delete
    void deleteMovie(Movie movie);
}
