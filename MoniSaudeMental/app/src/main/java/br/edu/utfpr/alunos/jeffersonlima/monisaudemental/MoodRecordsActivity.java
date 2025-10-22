package br.edu.utfpr.alunos.jeffersonlima.monisaudemental;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MoodRecordsActivity extends AppCompatActivity {

    private ListView listViewMoodRecords;
    private List<MoodLog> listMoods;
    private MoodsAdapter moodsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_records);
        setTitle("Controle Geral");
        listViewMoodRecords = findViewById(R.id.listViewMoodRecords);
        listViewMoodRecords.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view,
                                    int position,
                                    long l) {
                MoodLog moodLog = (MoodLog) listViewMoodRecords.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(),
                        getString(R.string.description_mood) + moodLog.getDescription(),
                        Toast.LENGTH_LONG).show();
            }
        });
        addMoodLog();
    }


    public void addMoodLog(){
        String[] moods_descriptions = getResources().getStringArray(R.array.mood_descriptions);
        String[] moods_emotions     = getResources().getStringArray(R.array.emotions);
        int[]    moods_intensity    = getResources().getIntArray(R.array.intensity_emotions);
        int[]    moods_category     = getResources().getIntArray(R.array.categories_days);

        MoodLog moodLog;
        IntensityEmotion intensity;
        IntensityEmotion[] intensities = IntensityEmotion.values();
        listMoods = new ArrayList<>();
        for(int cont = 0; cont < moods_descriptions.length; cont ++){
            intensity = intensities[moods_intensity[cont]];
            moodLog = new MoodLog(  moods_descriptions[cont],
                                    moods_emotions[cont],
                                    intensity,
                                    moods_category[cont]);

            listMoods.add(moodLog);
        }
        moodsAdapter = new MoodsAdapter(this, listMoods);
        listViewMoodRecords.setAdapter(moodsAdapter);
    }

    public void openAbout ( View view){
        Intent intentOpening = new Intent(this, AboutActivity.class);

        startActivity(intentOpening);
    }
    ActivityResultLauncher<Intent> launcherRegisterMood = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == MoodRecordsActivity.RESULT_OK){
                        Intent intent = result.getData();
                        Bundle bundle = intent.getExtras();
                        if(bundle != null){
                            String description = bundle.getString("KEY_DESCRIPTION");
                        }
                    }
                }
            });
    public void openMoodLog (View view){
        Intent intentOpening = new Intent(this, MoodLogActivity.class);
        startActivity(intentOpening);
    }
}