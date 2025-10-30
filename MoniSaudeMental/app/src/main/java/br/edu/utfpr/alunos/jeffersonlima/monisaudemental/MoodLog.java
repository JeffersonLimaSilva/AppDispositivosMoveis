package br.edu.utfpr.alunos.jeffersonlima.monisaudemental;

import java.util.Comparator;

public class MoodLog {

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
    private String description;
    private boolean sadness;
    private boolean anxiety;
    private boolean happiness;
    private boolean anger;
    private String emotion;
    private IntensityEmotion intensityEmotion;
    private int CategoryDay;

    public MoodLog(String description, boolean sadness, boolean anxiety, boolean happiness, boolean anger, String emotion, IntensityEmotion intensityEmotion, int categoryDay) {
        this.description = description;
        this.sadness = sadness;
        this.anxiety = anxiety;
        this.happiness = happiness;
        this.anger = anger;
        this.emotion = emotion;
        this.intensityEmotion = intensityEmotion;
        CategoryDay = categoryDay;
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
        return CategoryDay;
    }

    public void setCategoryDay(int categoryDay) {
        CategoryDay = categoryDay;
    }

    @Override
    public String toString() {
        return  description       + "\n" +
                emotion          + "\n" +
                intensityEmotion + "\n" +
                CategoryDay ;
    }


}
