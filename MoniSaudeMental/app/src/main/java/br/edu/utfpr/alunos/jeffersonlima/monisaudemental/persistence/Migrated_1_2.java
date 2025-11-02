package br.edu.utfpr.alunos.jeffersonlima.monisaudemental.persistence;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class Migrated_1_2 extends Migration {

    public Migrated_1_2(){
        super(1, 2);
    }

    @Override
    public void migrate(@NonNull SupportSQLiteDatabase database) {

        database.execSQL("CREATE TABLE IF NOT EXISTS `MoodLog_Provisional` (" +
                "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "`description` TEXT NOT NULL, " +
                "`sadness` INTEGER NOT NULL, " +
                "`anxiety` INTEGER NOT NULL, " +
                "`happiness` INTEGER NOT NULL, " +
                "`anger` INTEGER NOT NULL, " +
                "`emotion` TEXT, " +
                "`intensityEmotion` INTEGER, " +
                "`categoryDay` INTEGER NOT NULL)");

        database.execSQL("INSERT INTO MoodLog_Provisional(id, description, sadness, anxiety, happiness, anger, emotion, intensityEmotion, categoryDay) " +
                "SELECT id, description, sadness, anxiety, happiness, anger, emotion, " +
                "CASE " +
                "WHEN intensityEmotion = 'Leve' THEN 0 " +
                "WHEN intensityEmotion = 'Moderada' THEN 1 " +
                "WHEN intensityEmotion = 'Intensa' THEN 2 " +
                "ELSE -1 END, " +
                "categoryDay " +
                "FROM MoodLog");

        database.execSQL("DROP TABLE MoodLog");
        database.execSQL("ALTER TABLE MoodLog_Provisional RENAME TO MoodLog");

        database.execSQL("CREATE INDEX IF NOT EXISTS `index_MoodLog_description` ON `MoodLog` (`description`)");
    }
}
