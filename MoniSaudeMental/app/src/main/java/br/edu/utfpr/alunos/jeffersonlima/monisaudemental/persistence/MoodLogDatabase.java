package br.edu.utfpr.alunos.jeffersonlima.monisaudemental.persistence;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import br.edu.utfpr.alunos.jeffersonlima.monisaudemental.modelo.MoodLog;

@Database(entities = {MoodLog.class}, version = 1, exportSchema = true)
public abstract class MoodLogDatabase extends RoomDatabase {

    public abstract MoodLogDao getMoodLogDao();

    private static MoodLogDatabase INSTANCE;

    public static MoodLogDatabase getInstance(final Context context){

        if(INSTANCE == null){

            synchronized (MoodLogDatabase.class){
                if(INSTANCE == null){

                    INSTANCE = Room.databaseBuilder(context,
                                                    MoodLogDatabase.class,
                                                    "moodlog.db").allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }
}
