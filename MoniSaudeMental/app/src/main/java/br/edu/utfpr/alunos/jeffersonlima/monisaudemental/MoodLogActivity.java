package br.edu.utfpr.alunos.jeffersonlima.monisaudemental;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
        setTitle("Cadastro de Humor Geral");

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
        IntensityEmotion intensityEmotion;
        if(radioButtonId == R.id.radioButtonLight){
            intensityEmotion = IntensityEmotion.Leve;
        }else{
            if(radioButtonId == R.id.radioButtonModerate){
                intensityEmotion = IntensityEmotion.Moderada;
            }else{
                if(radioButtonId == R.id.radioButtonIntense){
                    intensityEmotion = IntensityEmotion.Intensa;
                }else{
                    Toast.makeText(this, R.string.select_intent, Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }

        int categoryDay = spinnerCategoryDay.getSelectedItemPosition();
        if(categoryDay == AdapterView.INVALID_POSITION){
            Toast.makeText(this,
                    "O spinner Categoria não possui valores!",
                    Toast.LENGTH_LONG).show();
        }

        Intent intentResponse = new Intent();
        intentResponse.putExtra("KEY_DESCRIPTION", description);
        intentResponse.putExtra("KEY_SADNESS", sadness);
        intentResponse.putExtra("KEY_ANXIETY", anxiety);
        intentResponse.putExtra("KEY_HAPPINESS", happiness);
        intentResponse.putExtra("KEY_ANGER", anger);
        intentResponse.putExtra("KEY_INTENSITY", intensityEmotion.toString());
        intentResponse.putExtra("KEY_CATEGORY", categoryDay);
        setResult(MoodLogActivity.RESULT_OK, intentResponse);
        finish();
    }
}