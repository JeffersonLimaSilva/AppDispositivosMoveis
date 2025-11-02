package br.edu.utfpr.alunos.jeffersonlima.monisaudemental.persistence;

import android.util.LruCache;

import androidx.room.TypeConverter;


import java.time.LocalDate;

public class ConvertLocalDate {

    @TypeConverter
    public static  Long fromLocalDateTolong(LocalDate date){
        if(date == null){
            return null;
        }
        return date.toEpochDay();
    }

    @TypeConverter
    public static LocalDate fromLongToLocalDate(Long epochDay){
        if(epochDay == null){
            return null;
        }

        return LocalDate.ofEpochDay(epochDay);
    }
}
