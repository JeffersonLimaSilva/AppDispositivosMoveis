package br.edu.utfpr.alunos.jeffersonlima.monisaudemental.persistence;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.edu.utfpr.alunos.jeffersonlima.monisaudemental.modelo.MoodLog;

@Dao
public interface MoodLogDao {

    @Insert
    long insert(MoodLog moodLog);

    @Delete
    int delete(MoodLog moodLog);

    @Update
    int update(MoodLog moodLog);

    @Query("SELECT * FROM moodlog WHERE id=:id")
    MoodLog queryForId(long id);

    @Query("SELECT * FROM moodlog ORDER BY description ASC")
    List<MoodLog> queryAllAscending();

    @Query("SELECT * FROM moodlog ORDER BY description DESC")
    List<MoodLog> queryAllDownward();
}

