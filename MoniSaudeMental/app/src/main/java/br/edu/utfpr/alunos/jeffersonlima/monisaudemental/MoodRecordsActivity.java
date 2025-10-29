package br.edu.utfpr.alunos.jeffersonlima.monisaudemental;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;

import java.util.ArrayList;
import java.util.List;

public class MoodRecordsActivity extends AppCompatActivity {

    private ListView listViewMoodRecords;
    private List<MoodLog> listMoods;
    private MoodsAdapter moodsAdapter;
    private ActionMode actionMode;
    private View viewSelected;
    private Drawable backgroundDrawable;
    private ActionMode.Callback actionCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflate = mode.getMenuInflater();
            inflate.inflate(R.menu.mood_recordes_selected, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            int idMenuItem = item.getItemId();
            if(idMenuItem == R.id.menu_item_edit){
                editMoods();
                return true;
            }else{
                if(idMenuItem == R.id.menu_item_delete){
                    deleteMoods();
                    mode.finish();
                    return true;
                }
                else{
                    return false;
                }
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            if(viewSelected != null){
                viewSelected.setBackground(backgroundDrawable);
            }
            actionMode = null;
            viewSelected = null;
            backgroundDrawable = null;

            listViewMoodRecords.setEnabled(true);
        }
    };
    private int positionSelected = -1;
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

        listViewMoodRecords.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {

                if(actionMode != null){
                    return false;
                }
                positionSelected = position;
                viewSelected = view;
                backgroundDrawable = view.getBackground();
                view.setBackgroundColor(Color.LTGRAY);

                listViewMoodRecords.setEnabled(false);
                actionMode = startSupportActionMode(actionCallback);

                return true;
           }
        });

        addMoodLog();

//        registerForContextMenu(listViewMoodRecords);
    }


    public void addMoodLog(){
//        String[] moods_descriptions = getResources().getStringArray(R.array.mood_descriptions);
//        String[] moods_emotions     = getResources().getStringArray(R.array.emotions);
//        int[]    moods_intensity    = getResources().getIntArray(R.array.intensity_emotions);
//        int[]    moods_category     = getResources().getIntArray(R.array.categories_days);

        MoodLog moodLog;
        IntensityEmotion intensity;
        IntensityEmotion[] intensities = IntensityEmotion.values();
        listMoods = new ArrayList<>();
//        for(int cont = 0; cont < moods_descriptions.length; cont ++){
//            intensity = intensities[moods_intensity[cont]];
//            moodLog = new MoodLog(  moods_descriptions[cont],
//                                    moods_emotions[cont],
//                                    intensity,
//                                    moods_category[cont]);
//
//            listMoods.add(moodLog);
//        }
        moodsAdapter = new MoodsAdapter(this, listMoods);
        listViewMoodRecords.setAdapter(moodsAdapter);
    }

    public void openAbout (){
        Intent intentOpening = new Intent(this, AboutActivity.class);
        intentOpening.putExtra(MoodLogActivity.KEY_MODO, MoodLogActivity.NEW_MODO);
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
                            String description = bundle.getString(MoodLogActivity.KEY_DESCRIPTION);
                            boolean sadness    = bundle.getBoolean(MoodLogActivity.KEY_SADNESS);
                            boolean anxiety    = bundle.getBoolean(MoodLogActivity.KEY_ANXIETY);
                            boolean happiness  = bundle.getBoolean(MoodLogActivity.KEY_HAPPINESS);
                            boolean anger      = bundle.getBoolean(MoodLogActivity.KEY_ANGER);
                            String intensity   = bundle.getString(MoodLogActivity.KEY_INTENSITY);
                            int categoryDay    = bundle.getInt(MoodLogActivity.KEY_CATEGORY);

                            String emotion = "";
                            emotion = sadness ? emotion + getString(R.string.sadness ): emotion;
                            emotion = anxiety ? emotion + getString(R.string.anxiety) : emotion;
                            emotion = happiness ? emotion + getString(R.string.happiness): emotion;
                            emotion = anger ? emotion + getString(R.string.anger): emotion;

                            MoodLog moodLog = new MoodLog(description,sadness, anxiety, happiness,anger, emotion, IntensityEmotion.valueOf(intensity), categoryDay);
                            listMoods.add(moodLog);
                            moodsAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
    public void openMoodLog(){
        Intent intentOpening = new Intent(this, MoodLogActivity.class);
        launcherRegisterMood.launch(intentOpening);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mood_recordes_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int idMenuItem = item.getItemId();

        if(idMenuItem == R.id.menu_item_add){
            openMoodLog();
            return true;
        }else {
            if (idMenuItem == R.id.menu_item_about) {
                openAbout();
                return true;
            } else {
                return super.onOptionsItemSelected(item);
            }
        }
    }
    ActivityResultLauncher<Intent> launcherEditMood = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == MoodRecordsActivity.RESULT_OK){
                        Intent intent = result.getData();
                        Bundle bundle = intent.getExtras();
                        if(bundle != null){
                            String description = bundle.getString(MoodLogActivity.KEY_DESCRIPTION);
                            boolean sadness    = bundle.getBoolean(MoodLogActivity.KEY_SADNESS);
                            boolean anxiety    = bundle.getBoolean(MoodLogActivity.KEY_ANXIETY);
                            boolean happiness  = bundle.getBoolean(MoodLogActivity.KEY_HAPPINESS);
                            boolean anger      = bundle.getBoolean(MoodLogActivity.KEY_ANGER);
                            String intensity   = bundle.getString(MoodLogActivity.KEY_INTENSITY);
                            int categoryDay    = bundle.getInt(MoodLogActivity.KEY_CATEGORY);

                            String emotion = "";
                            emotion = sadness ? emotion + getString(R.string.sadness ): emotion;
                            emotion = anxiety ? emotion + getString(R.string.anxiety) : emotion;
                            emotion = happiness ? emotion + getString(R.string.happiness): emotion;
                            emotion = anger ? emotion + getString(R.string.anger): emotion;

                            MoodLog moodLog = listMoods.get(positionSelected);

                            moodLog.setDescription(description);
                            moodLog.setSadness(sadness);
                            moodLog.setAnxiety(anxiety);
                            moodLog.setHappiness(happiness);
                            moodLog.setAnger(anger);
                            moodLog.setCategoryDay(categoryDay);

                            IntensityEmotion intensityEmotion = IntensityEmotion.valueOf(intensity);
                            moodLog.setIntensityEmotion(intensityEmotion);

                            moodsAdapter.notifyDataSetChanged();
                        }
                    }
                    positionSelected = -1;

                    if(actionMode != null){
                        actionMode.finish();
                    }
                }
            });
    private void editMoods(){

        MoodLog moodLog = listMoods.get(positionSelected);
        Intent intentOpening = new Intent(this, MoodLogActivity.class);

        intentOpening.putExtra(MoodLogActivity.KEY_MODO, MoodLogActivity.EDIT_MODO);

        intentOpening.putExtra(MoodLogActivity.KEY_DESCRIPTION, moodLog.getDescription());
        intentOpening.putExtra(MoodLogActivity.KEY_SADNESS, moodLog.isSadness());
        intentOpening.putExtra(MoodLogActivity.KEY_ANXIETY, moodLog.isAnxiety());
        intentOpening.putExtra(MoodLogActivity.KEY_HAPPINESS, moodLog.isHappiness());
        intentOpening.putExtra(MoodLogActivity.KEY_ANGER, moodLog.isAnger());
        intentOpening.putExtra(MoodLogActivity.KEY_INTENSITY, moodLog.getIntensityEmotion().toString());
        intentOpening.putExtra(MoodLogActivity.KEY_CATEGORY, moodLog.getCategoryDay());

        launcherEditMood.launch(intentOpening);
    }
    private void deleteMoods(){
        listMoods.remove(positionSelected);
        moodsAdapter.notifyDataSetChanged();
    }

//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
//
//        getMenuInflater().inflate(R.menu.mood_recordes_selected, menu);
//    }
//
//    @Override
//    public boolean onContextItemSelected(@NonNull MenuItem item) {
//
//        AdapterView.AdapterContextMenuInfo info;
//        info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
//
//
//        int idMenuItem = item.getItemId();
//        if(idMenuItem == R.id.menu_item_edit){
//            editMoods(info.position);
//            return true;
//        }else{
//            if(idMenuItem == R.id.menu_item_delete){
//                deleteMoods(info.position);
//                return true;
//            }
//            else{
//                return super.onContextItemSelected(item);
//            }
//        }
//    }
}