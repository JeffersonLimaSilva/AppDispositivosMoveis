package br.edu.utfpr.alunos.jeffersonlima.monisaudemental;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MoodLogActivity extends AppCompatActivity {

    public static final String KEY_DESCRIPTION = "KEY_DESCRIPTION";
    public static final String KEY_SADNESS = "KEY_SADNESS";
    public static final String KEY_ANXIETY = "KEY_ANXIETY";
    public static final String KEY_HAPPINESS = "KEY_HAPPINESS";
    public static final String KEY_ANGER = "KEY_ANGER";
    public static final String KEY_INTENSITY = "KEY_INTENSITY";
    public static final String KEY_CATEGORY = "KEY_CATEGORY";
    public static final String KEY_MODO = "MODO";

    public static final int NEW_MODO = 0;
    public static final int EDIT_MODO = 1;
    private EditText editTextDescription;
    private CheckBox checkBoxSadness, checkBoxAnxiety, checkBoxHappiness, checkBoxAnger;
    private RadioGroup radioGroupIntensityEmotion;
    private RadioButton radioButtonLeve, radioButtonModerada, radioButtonIntensa;
    private Spinner spinnerCategoryDay;

    private int modo;

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
        radioButtonLeve            = findViewById(R.id.radioButtonLight);
        radioButtonModerada        = findViewById(R.id.radioButtonModerate);
        radioButtonIntensa         = findViewById(R.id.radioButtonIntense);

        Intent intentOpening = getIntent();

        Bundle bundle = intentOpening.getExtras();
        if(bundle != null){
            modo = bundle.getInt(KEY_MODO);
            if (modo == NEW_MODO){
                setTitle(getString(R.string.register_mood));
            }else{
                setTitle(getString(R.string.edit_mood));

                String description = bundle.getString(MoodLogActivity.KEY_DESCRIPTION);
                boolean sadness    = bundle.getBoolean(MoodLogActivity.KEY_SADNESS);
                boolean anxiety    = bundle.getBoolean(MoodLogActivity.KEY_ANXIETY);
                boolean happiness  = bundle.getBoolean(MoodLogActivity.KEY_HAPPINESS);
                boolean anger      = bundle.getBoolean(MoodLogActivity.KEY_ANGER);
                String intensity   = bundle.getString(MoodLogActivity.KEY_INTENSITY);
                int categoryDay    = bundle.getInt(MoodLogActivity.KEY_CATEGORY);

                IntensityEmotion intensityEmotion = IntensityEmotion.valueOf(intensity);

                editTextDescription.setText(description);
                checkBoxSadness.setChecked(sadness);
                checkBoxAnxiety.setChecked(anxiety);
                checkBoxHappiness.setChecked(happiness);
                checkBoxAnger.setChecked(anger);
                spinnerCategoryDay.setSelection(categoryDay);

                if(intensityEmotion == IntensityEmotion.Leve){
                    radioButtonLeve.setChecked(true);
                }else{
                    if(intensityEmotion == IntensityEmotion.Moderada){
                        radioButtonModerada.setChecked(true);
                    }else{
                        if(intensityEmotion == IntensityEmotion.Intensa){
                            radioButtonIntensa.setChecked(true);
                        }
                    }
                }
            }
        }
    }
    public void clearInput(){
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
    public void saveValues(){
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
        intentResponse.putExtra(KEY_DESCRIPTION, description);
        intentResponse.putExtra(KEY_SADNESS, sadness);
        intentResponse.putExtra(KEY_ANXIETY, anxiety);
        intentResponse.putExtra(KEY_HAPPINESS, happiness);
        intentResponse.putExtra(KEY_ANGER, anger);
        intentResponse.putExtra(KEY_INTENSITY, intensityEmotion.toString());
        intentResponse.putExtra(KEY_CATEGORY, categoryDay);
        setResult(MoodLogActivity.RESULT_OK, intentResponse);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mood_add_options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int idMenuItem = item.getItemId();
        if(idMenuItem == R.id.menu_item_save){
            saveValues();
            return true;
        }
        else{
            if(idMenuItem == R.id.menu_item_clear){
                clearInput();
                return true;
            }
            else{
                return super.onOptionsItemSelected(item);
            }
        }
    }
}