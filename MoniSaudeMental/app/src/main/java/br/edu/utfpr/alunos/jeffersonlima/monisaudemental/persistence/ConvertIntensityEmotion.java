package br.edu.utfpr.alunos.jeffersonlima.monisaudemental.persistence;

import androidx.room.TypeConverter;

import br.edu.utfpr.alunos.jeffersonlima.monisaudemental.modelo.IntensityEmotion;

public class ConvertIntensityEmotion {

    public static IntensityEmotion[] intensityEmotions = IntensityEmotion.values();
    @TypeConverter
    public static int fromEnumToInt(IntensityEmotion intensityEmotion){
        if(intensityEmotion == null){
            return -1;
        }

        return intensityEmotion.ordinal();
    }

    @TypeConverter
    public static IntensityEmotion fromIntToEnum(int ordinal){
        if(ordinal < 0){
            return null;
        }

        return intensityEmotions[ordinal];
    }

}
