package br.edu.utfpr.alunos.jeffersonlima.monisaudemental.persistence;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class Migrated_2_3 extends Migration {

    public Migrated_2_3(){
        super(2,3);
    }

    @Override
    public void migrate(@NonNull SupportSQLiteDatabase database) {

        database.execSQL("ALTER TABLE MoodLog ADD COLUMN dateRegister INTEGER");
    }
}
