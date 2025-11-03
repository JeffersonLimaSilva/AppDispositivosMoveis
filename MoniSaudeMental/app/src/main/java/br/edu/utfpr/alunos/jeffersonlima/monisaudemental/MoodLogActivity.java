package br.edu.utfpr.alunos.jeffersonlima.monisaudemental;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDate;

import br.edu.utfpr.alunos.jeffersonlima.monisaudemental.modelo.IntensityEmotion;
import br.edu.utfpr.alunos.jeffersonlima.monisaudemental.modelo.MoodLog;
import br.edu.utfpr.alunos.jeffersonlima.monisaudemental.persistence.MoodLogDatabase;
import br.edu.utfpr.alunos.jeffersonlima.monisaudemental.utils.UtilsAlert;
import br.edu.utfpr.alunos.jeffersonlima.monisaudemental.utils.UtilsLocalDate;

public class MoodLogActivity extends AppCompatActivity {

    public static final String KEY_ID = "ID";
    public static final String KEY_MODO = "MODO";
    public static final String KEY_SUGGEST = "SUGGEST";
    public static final String KEY_LAST_CATEGORY = "LAST_CATEGORY";


    public static final int NEW_MODO = 0;
    public static final int EDIT_MODO = 1;
    private EditText editTextDescription, editTextDateEvent;
    private CheckBox checkBoxSadness, checkBoxAnxiety, checkBoxHappiness, checkBoxAnger;
    private RadioGroup radioGroupIntensityEmotion;
    private RadioButton radioButtonLeve, radioButtonModerada, radioButtonIntensa;
    private Spinner spinnerCategoryDay;

    private int modo;
    private MoodLog moodOriginl;
    private boolean suggest = false;
    private int lastCategory = 0;

    private LocalDate dateEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_log);

        editTextDescription        = findViewById(R.id.editTextDescription);
        editTextDateEvent          = findViewById(R.id.editTextDateEvent);
        checkBoxSadness            = findViewById(R.id.checkBoxSadness);
        checkBoxAnxiety            = findViewById(R.id.checkBoxAnxiety);
        checkBoxHappiness          = findViewById(R.id.checkBoxHappiness);
        checkBoxAnger              = findViewById(R.id.checkBoxAnger);
        radioGroupIntensityEmotion = findViewById(R.id.radioGroupIntensityEmotion);
        spinnerCategoryDay         = findViewById(R.id.spinnerCategoryDay);
        radioButtonLeve            = findViewById(R.id.radioButtonLight);
        radioButtonModerada        = findViewById(R.id.radioButtonModerate);
        radioButtonIntensa         = findViewById(R.id.radioButtonIntense);

        editTextDateEvent.setFocusable(false);
        editTextDateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        readPreferences();
        
        Intent intentOpening = getIntent();

        Bundle bundle = intentOpening.getExtras();
        if(bundle != null){
            modo = bundle.getInt(KEY_MODO);
            if (modo == NEW_MODO){
                setTitle(getString(R.string.register_mood));

                if(suggest){
                    spinnerCategoryDay.setSelection(lastCategory);
                }

                dateEvent = LocalDate.now();
            }else{
                setTitle(getString(R.string.edit_mood));

                long id = bundle.getLong(KEY_ID);

                MoodLogDatabase database = MoodLogDatabase.getInstance(this);
                moodOriginl = database.getMoodLogDao().queryForId(id);




                editTextDescription.setText(moodOriginl.getDescription());
                dateEvent = moodOriginl.getDateEvent();

                editTextDateEvent.setText(UtilsLocalDate.formatLocalDate(dateEvent));



                checkBoxSadness.setChecked(moodOriginl.isSadness());
                checkBoxAnxiety.setChecked(moodOriginl.isAnxiety());
                checkBoxHappiness.setChecked(moodOriginl.isHappiness());
                checkBoxAnger.setChecked(moodOriginl.isAnger());
                spinnerCategoryDay.setSelection(moodOriginl.getCategoryDay());

                IntensityEmotion intensityEmotion = moodOriginl.getIntensityEmotion();

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
                editTextDescription.requestFocus();
                editTextDescription.setSelection(editTextDescription.getText().length());
            }
        }
    }

    private void showDatePickerDialog(){

        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                dateEvent = LocalDate.of(year, month + 1, dayOfMonth);

                editTextDateEvent.setText(UtilsLocalDate.formatLocalDate(dateEvent));
            }
        };

        if(dateEvent == null){
            dateEvent = LocalDate.now();
        }
        DatePickerDialog picker = new DatePickerDialog(this, listener, dateEvent.getYear(), dateEvent.getMonthValue() - 1, dateEvent.getDayOfMonth());

        long dataMaxMilliseconds = UtilsLocalDate.toMilliseconds(LocalDate.now());
        picker.getDatePicker().setMaxDate(dataMaxMilliseconds);
        picker.show();
    }
    public void clearInput(){

        final String description = editTextDescription.getText().toString();

        final LocalDate dateEventPrevious = dateEvent;
        final boolean sadness    = checkBoxSadness.isChecked();
        final boolean anxiety    = checkBoxAnxiety.isChecked();
        final boolean happiness  = checkBoxHappiness.isChecked();
        final boolean anger      = checkBoxAnger.isChecked();
        final int intensity      = radioGroupIntensityEmotion.getCheckedRadioButtonId();
        final int category       = spinnerCategoryDay.getSelectedItemPosition();

        final ScrollView scrollView = findViewById(R.id.main);
        final View viewFocus =  scrollView.findFocus();

        editTextDescription.setText(null);

        editTextDateEvent.setText(null);
        dateEvent = LocalDate.now();

        checkBoxSadness.setChecked(false);
        checkBoxAnxiety.setChecked(false);
        checkBoxHappiness.setChecked(false);
        checkBoxAnger.setChecked(false);
        radioGroupIntensityEmotion.clearCheck();
        spinnerCategoryDay.setSelection(0);
        editTextDescription.requestFocus();

        Snackbar snackbar = Snackbar.make(scrollView, R.string.clear_all, Snackbar.LENGTH_LONG);

        snackbar.setAction(R.string.undo, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editTextDescription.setText(description);
                dateEvent = dateEventPrevious;
                editTextDateEvent.setText(UtilsLocalDate.formatLocalDate(dateEvent));
                checkBoxSadness.setChecked(sadness);
                checkBoxAnxiety.setChecked(anxiety);
                checkBoxHappiness.setChecked(happiness);
                checkBoxAnger.setChecked(anger);

                if(intensity == R.id.radioButtonLight){
                    radioButtonLeve.setChecked(true);
                }else{
                    if(intensity == R.id.radioButtonModerate){
                        radioButtonModerada.setChecked(true);
                    }else{
                        if(intensity == R.id.radioButtonIntense){
                            radioButtonIntensa.setChecked(true);
                        }
                    }
                }
                spinnerCategoryDay.setSelection(category);

                if(viewFocus != null){
                    viewFocus.requestFocus();
                }
            }
        });

        snackbar.show();
    }
    public void saveValues(){
        String description = editTextDescription.getText().toString();
        if(description == null || description.trim().isEmpty()){
            UtilsAlert.showAlert(this, R.string.description_null);
            editTextDescription.requestFocus();
            return;
        }
        String dateEventString = editTextDateEvent.getText().toString();
        if(dateEventString == null || dateEventString.trim().isEmpty()){
            UtilsAlert.showAlert(this, R.string.event_date_cannot_be_empty);
            return;
        }
        int months = UtilsLocalDate.differenceMonthsForToday(dateEvent);

        if (months < 0 || months > 12) {
            UtilsAlert.showAlert(this, R.string.event_cannot_more_1_year);
            return;
        }

        boolean sadness = checkBoxSadness.isChecked();
        boolean anxiety = checkBoxAnxiety.isChecked();
        boolean happiness = checkBoxHappiness.isChecked();
        boolean anger = checkBoxAnger.isChecked();
        if(!sadness && !anxiety && !happiness && !anger){
            UtilsAlert.showAlert(this, R.string.select_emotion);
            return;
        }

        String emotion = "";
        emotion = sadness ? emotion + getString(R.string.sadness ): emotion;
        emotion = anxiety ? emotion + getString(R.string.anxiety) : emotion;
        emotion = happiness ? emotion + getString(R.string.happiness): emotion;
        emotion = anger ? emotion + getString(R.string.anger): emotion;

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
                    UtilsAlert.showAlert(this, R.string.select_intent);
                    return;
                }
            }
        }

        int categoryDay = spinnerCategoryDay.getSelectedItemPosition();
        if(categoryDay == AdapterView.INVALID_POSITION){
            UtilsAlert.showAlert(this, R.string.not_spinner_value);
        }


        MoodLog moodLog = new MoodLog(description, sadness, anxiety, happiness, anger, emotion, intensityEmotion, categoryDay, dateEvent);

        if(moodLog.equals(moodOriginl)){
            setResult(MoodLogActivity.RESULT_CANCELED);
            finish();
            return;
        }

        Intent intentResponse = new Intent();
        MoodLogDatabase database = MoodLogDatabase.getInstance(this);
        if(modo == NEW_MODO){

            long newId = database.getMoodLogDao().insert(moodLog);
            if(newId <= 0){
                UtilsAlert.showAlert(this, R.string.error_to_insert);
                return;
            }
            moodLog.setId(newId);

        }else{

            moodLog.setId(moodOriginl.getId());
            int amountUpdate = database.getMoodLogDao().update(moodLog);

            if(amountUpdate != 1){
                UtilsAlert.showAlert(this, R.string.error_to_update);
                return;
            }
        }

        saveLastCategory(categoryDay);

        intentResponse.putExtra(KEY_ID, moodLog.getId());
        setResult(MoodLogActivity.RESULT_OK, intentResponse);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mood_add_options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.menu_item_suggestion);
        item.setChecked(suggest);
        return super.onPrepareOptionsMenu(menu);
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
                if(idMenuItem == R.id.menu_item_suggestion){
                    boolean valor = !item.isChecked();
                    saveSuggest(valor);
                    item.setChecked(valor);

                    if(suggest){
                        spinnerCategoryDay.setSelection(lastCategory);
                    }
                    return true;
                }else {
                    return super.onOptionsItemSelected(item);
                }
            }
        }
    }

    private void readPreferences(){

        SharedPreferences shared = getSharedPreferences(MoodRecordsActivity.FILE_PREFERENCES, Context.MODE_PRIVATE);
        suggest = shared.getBoolean(KEY_SUGGEST, suggest);
        lastCategory = shared.getInt(KEY_LAST_CATEGORY, lastCategory);
    }

    private void saveSuggest( boolean newValor){
        SharedPreferences shared = getSharedPreferences(MoodRecordsActivity.FILE_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = shared.edit();
        edit.putBoolean(KEY_SUGGEST, newValor);

        edit.commit();
        suggest = newValor;
    }

    private void saveLastCategory(int newValor){
        SharedPreferences shared = getSharedPreferences(MoodRecordsActivity.FILE_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = shared.edit();
        edit.putInt(KEY_LAST_CATEGORY, newValor);

        edit.commit();

        lastCategory = newValor;

    }


}