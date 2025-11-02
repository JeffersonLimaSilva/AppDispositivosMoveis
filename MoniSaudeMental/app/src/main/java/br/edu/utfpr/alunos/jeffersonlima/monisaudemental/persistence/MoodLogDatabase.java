package br.edu.utfpr.alunos.jeffersonlima.monisaudemental.persistence;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import br.edu.utfpr.alunos.jeffersonlima.monisaudemental.modelo.MoodLog;

@Database(entities = {MoodLog.class}, version = 3)
@TypeConverters({ConvertIntensityEmotion.class, ConvertLocalDate.class})
public abstract class MoodLogDatabase extends RoomDatabase {

    public abstract MoodLogDao getMoodLogDao();

    private static MoodLogDatabase INSTANCE;

    public static MoodLogDatabase getInstance(final Context context){

        if(INSTANCE == null){

            synchronized (MoodLogDatabase.class){
                if(INSTANCE == null){

                    Builder builder = Room.databaseBuilder(context,
                                                    MoodLogDatabase.class,
                                                    "moodlog.db");
                    builder.allowMainThreadQueries();

                    builder.addMigrations(new Migrated_1_2());
                    builder.addMigrations(new Migrated_2_3());


                    INSTANCE = (MoodLogDatabase) builder.build();
                }
            }
        }
        return INSTANCE;
    }
}
