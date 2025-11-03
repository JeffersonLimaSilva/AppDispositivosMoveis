package br.edu.utfpr.alunos.jeffersonlima.monisaudemental.modelo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Objects;

@Entity
public class MoodLog implements Cloneable{

    public  static Comparator<MoodLog> ascendingOrder = new Comparator<MoodLog>() {
        @Override
        public int compare(MoodLog moodLog1, MoodLog moodLog2) {
            return moodLog1.getDescription().compareToIgnoreCase(moodLog2.getDescription());
        }
    };
    public  static Comparator<MoodLog> descendingOrder = new Comparator<MoodLog>() {
        @Override
        public int compare(MoodLog moodLog1, MoodLog moodLog2) {
            return -1 * moodLog1.getDescription().compareToIgnoreCase(moodLog2.getDescription());
        }
    };

    @PrimaryKey(autoGenerate = true)
    private long id;
    @NonNull
    @ColumnInfo(index = true)
    private String description;
    private boolean sadness;
    private boolean anxiety;
    private boolean happiness;
    private boolean anger;
    private String emotion;
    private IntensityEmotion intensityEmotion;
    private int categoryDay;
    private LocalDate dateEvent;

    public MoodLog(String description, boolean sadness, boolean anxiety, boolean happiness, boolean anger, String emotion, IntensityEmotion intensityEmotion, int categoryDay, LocalDate dateEvent) {
        this.description = description;
        this.sadness = sadness;
        this.anxiety = anxiety;
        this.happiness = happiness;
        this.anger = anger;
        this.emotion = emotion;
        this.intensityEmotion = intensityEmotion;
        this.categoryDay = categoryDay;
        this.dateEvent = dateEvent;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public boolean isHappiness() {
        return happiness;
    }

    public void setHappiness(boolean happiness) {
        this.happiness = happiness;
    }

    public boolean isAnxiety() {
        return anxiety;
    }

    public void setAnxiety(boolean anxiety) {
        this.anxiety = anxiety;
    }

    public boolean isSadness() {
        return sadness;
    }

    public void setSadness(boolean sadness) {
        this.sadness = sadness;
    }

    public boolean isAnger() {
        return anger;
    }

    public void setAnger(boolean anger) {
        this.anger = anger;
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public IntensityEmotion getIntensityEmotion() {
        return intensityEmotion;
    }

    public void setIntensityEmotion(IntensityEmotion intensityEmotion) {
        this.intensityEmotion = intensityEmotion;
    }

    public int getCategoryDay() {
        return categoryDay;
    }

    public void setCategoryDay(int categoryDay) {
        this.categoryDay = categoryDay;
    }

    public LocalDate getDateEvent() {
        return dateEvent;
    }

    public void setDateEvent(LocalDate dateEvent) {
        this.dateEvent = dateEvent;
    }

    @NonNull
    @Override
    public Object clone() throws CloneNotSupportedException {

        return super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MoodLog moodLog = (MoodLog) o;

        if(dateEvent == null && moodLog.dateEvent != null){
            return false;
        }
        if(dateEvent != null && dateEvent.equals(moodLog.dateEvent) == false) {
            return false;
        }
        return  sadness == moodLog.sadness &&
                anxiety == moodLog.anxiety &&
                happiness == moodLog.happiness &&
                anger == moodLog.anger &&
                categoryDay == moodLog.categoryDay &&
                description.equals(moodLog.description) &&
                emotion.equals(moodLog.emotion) &&
                intensityEmotion == moodLog.intensityEmotion;
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, sadness, anxiety, happiness, anger, emotion, intensityEmotion, categoryDay, dateEvent);
    }

    @Override
    public String toString() {
        return  description       + "\n" +
                emotion          + "\n" +
                intensityEmotion + "\n" +
                categoryDay+ "\n" +
                dateEvent;
    }


}
