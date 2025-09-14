package br.edu.utfpr.alunos.jeffersonlima.monisaudemental;

public class MoodLog {

    private String description;
    private String emotion;
    private IntensityEmotion intensityEmotion;
    private int CategoryDay;

    public MoodLog(String description, String emotion, IntensityEmotion intensityEmotion, int categoryDay) {
        this.description = description;
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
