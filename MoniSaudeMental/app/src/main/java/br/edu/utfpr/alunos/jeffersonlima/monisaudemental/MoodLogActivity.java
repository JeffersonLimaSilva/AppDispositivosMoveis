package br.edu.utfpr.alunos.jeffersonlima.monisaudemental;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MoodLogActivity extends AppCompatActivity {

    private EditText editTextDescription;
    private CheckBox checkBoxSadness, checkBoxAnxiety, checkBoxHappiness, checkBoxAnger;
    private RadioGroup radioGroupIntensityEmotion;
    private Spinner spinnerCategoryDay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_log);

        editTextDescription        = findViewById(R.id.editTextDescription);
        checkBoxSadness            = findViewById(R.id.checkBoxSadness);
        checkBoxAnxiety            = findViewById(R.id.checkBoxAnxiety);
        checkBoxHappiness          = findViewById(R.id.checkBoxHappiness);
        checkBoxAnger              = findViewById(R.id.checkBoxAnger);
        radioGroupIntensityEmotion = findViewById(R.id.radioGroupIntensityEmotion);
        spinnerCategoryDay         = findViewById(R.id.spinnerCategoryDay);
    }
    public void clearInput(View view){
        editTextDescription.setText(null);
        checkBoxSadness.setChecked(false);
        checkBoxAnxiety.setChecked(false);
        checkBoxHappiness.setChecked(false);
        checkBoxAnger.setChecked(false);
        radioGroupIntensityEmotion.clearCheck();
        spinnerCategoryDay.setSelection(0);

        editTextDescription.requestFocus();
        Toast.makeText(this,
                R.string.clear_all,
                Toast.LENGTH_LONG).show();
    }
    public void saveValues(View view){
        String description = editTextDescription.getText().toString();
        if(description == null || description.trim().isEmpty()){
            Toast.makeText(this,
                    R.string.description_null,
                    Toast.LENGTH_LONG).show();
            editTextDescription.requestFocus();
            return;
        }
        boolean sadness = checkBoxSadness.isChecked();
        boolean anxiety = checkBoxAnxiety.isChecked();
        boolean happiness = checkBoxHappiness.isChecked();
        boolean anger = checkBoxAnger.isChecked();
        if(!sadness && !anxiety && !happiness && !anger){
            Toast.makeText(this, R.string.select_emotion, Toast.LENGTH_SHORT).show();
            return;
        }

        int radioButtonId = radioGroupIntensityEmotion.getCheckedRadioButtonId();
        String intensityEmotion;
        if(radioButtonId == R.id.radioButtonLight){
            intensityEmotion = getString(R.string.light);
        }else{
            if(radioButtonId == R.id.radioButtonModerate){
                intensityEmotion = getString(R.string.moderate);
            }else{
                if(radioButtonId == R.id.radioButtonIntense){
                    intensityEmotion = getString(R.string.intense);
                }else{
                    Toast.makeText(this, R.string.select_intent, Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }

        String categoryDay = (String) spinnerCategoryDay.getSelectedItem();

        Toast.makeText(this,
                getString(R.string.description_show) + description + "\n" +
                        getString(R.string.sadness_show) + (sadness ? "x": "") + "\n" +
                        getString(R.string.anxiety_show) + (anxiety ? "x": "") + "\n" +
                        getString(R.string.happiness_show) + (happiness ? "x": "") + "\n" +
                        getString(R.string.anger_show) + (anger ? "x": "") + "\n" +
                        getString(R.string.intensity_emotion_show) + intensityEmotion + "\n" +
                        getString(R.string.category_day_show) + categoryDay,

                Toast.LENGTH_LONG).show();
    }
}