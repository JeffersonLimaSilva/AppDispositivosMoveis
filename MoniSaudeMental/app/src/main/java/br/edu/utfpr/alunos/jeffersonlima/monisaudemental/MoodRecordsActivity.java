package br.edu.utfpr.alunos.jeffersonlima.monisaudemental;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.snackbar.Snackbar;

import java.util.Collections;
import java.util.List;

import br.edu.utfpr.alunos.jeffersonlima.monisaudemental.modelo.MoodLog;
import br.edu.utfpr.alunos.jeffersonlima.monisaudemental.persistence.MoodLogDatabase;
import br.edu.utfpr.alunos.jeffersonlima.monisaudemental.utils.UtilsAlert;

public class MoodRecordsActivity extends AppCompatActivity {

    private ListView listViewMoodRecords;
    private List<MoodLog> listMoods;
    private MoodsAdapter moodsAdapter;
    private ActionMode actionMode;
    private View viewSelected;
    private Drawable backgroundDrawable;
    public static final String FILE_PREFERENCES = "br.edu.utfpr.alunos.jeffersonlima.monisaudemental.PREFERENCES";
    public static final String KEY_ASCENDING_ORDER = "ASCENDING_ORDER";
    public static final boolean PATTERN_INITIAL_ORDER = true;
    private boolean ascendingOrder = PATTERN_INITIAL_ORDER;
    private MenuItem menuItemOrder;

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
        setTitle(getString(R.string.controle_geral));
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
                view.setBackgroundColor(getColor(R.color.colorSelected));

                listViewMoodRecords.setEnabled(false);
                actionMode = startSupportActionMode(actionCallback);

                return true;
           }
        });
        readPreferences();

        addMoodLog();

//        registerForContextMenu(listViewMoodRecords);
    }


    public void addMoodLog(){
        MoodLogDatabase database = MoodLogDatabase.getInstance(this);

        if(ascendingOrder){
            listMoods = database.getMoodLogDao().queryAllAscending();
        }else{
            listMoods = database.getMoodLogDao().queryAllDownward();
        }

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
                            long id = bundle.getLong(MoodLogActivity.KEY_ID);

                            MoodLogDatabase database = MoodLogDatabase.getInstance(MoodRecordsActivity.this);
                            MoodLog moodLog = database.getMoodLogDao().queryForId(id);

                            listMoods.add(moodLog);

                            listOrder();
                        }
                    }
                }
            });
    public void openMoodLog(){
        Intent intentOpening = new Intent(this, MoodLogActivity.class);
        intentOpening.putExtra(MoodLogActivity.KEY_MODO, MoodLogActivity.NEW_MODO);
        launcherRegisterMood.launch(intentOpening);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mood_recordes_options, menu);
        menuItemOrder = menu.findItem(R.id.menu_item_order);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        toUpdateIconOrder();
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
                if(idMenuItem == R.id.menu_item_order){
                    saveAscendingOrder(!ascendingOrder);
                    toUpdateIconOrder();
                    listOrder();
                    return true;
                }else{
                    if(idMenuItem == R.id.menu_item_restore){
                        confirmRestorePatterns();
                        return true;
                    }else{
                        return super.onOptionsItemSelected(item);
                    }
                }
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

                            final MoodLog moodOriginal = listMoods.get(positionSelected);

                            long id = bundle.getLong(MoodLogActivity.KEY_ID);

                            final MoodLogDatabase database = MoodLogDatabase.getInstance(MoodRecordsActivity.this);
                            final MoodLog moodLogUpdated = database.getMoodLogDao().queryForId(id);

                            listMoods.set(positionSelected, moodLogUpdated);

                            listOrder();

                            final ConstraintLayout constraintLayout = findViewById(R.id.main);
                            Snackbar snackbar = Snackbar.make(constraintLayout, R.string.change_made, Snackbar.LENGTH_LONG);
                            snackbar.setAction(R.string.undo, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    int amountUpdate = database.getMoodLogDao().update(moodOriginal);

                                    if(amountUpdate != 1){
                                        UtilsAlert.showAlert(MoodRecordsActivity.this, R.string.error_to_update);
                                        return;
                                    }

                                    listMoods.remove(moodLogUpdated);
                                    listMoods.add(moodOriginal);

                                    listOrder();
                                }
                            });
                            snackbar.show();
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

        intentOpening.putExtra(MoodLogActivity.KEY_ID, moodLog.getId());

        launcherEditMood.launch(intentOpening);
    }
    private void deleteMoods(){

        final MoodLog moodLog = listMoods.get(positionSelected);
//        String message = getString(R.string.want_delete, moodLog.getDescription());
        String message = getString(R.string.want_delete, moodLog.getDescription());

        DialogInterface.OnClickListener listenerYes = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                MoodLogDatabase database = MoodLogDatabase.getInstance(MoodRecordsActivity.this);
                int amountDelete = database.getMoodLogDao().delete(moodLog);

                if(amountDelete != 1){
                    UtilsAlert.showAlert(MoodRecordsActivity.this, R.string.error_to_delete);
                    return;
                }

                listMoods.remove(positionSelected);
                moodsAdapter.notifyDataSetChanged();
                actionMode.finish();
            }
        };

        UtilsAlert.actionConfirm(this, message, listenerYes, null);
    }
    private void listOrder(){
        if(ascendingOrder){
            Collections.sort(listMoods, MoodLog.ascendingOrder);
        }else{
            Collections.sort(listMoods, MoodLog.descendingOrder);
        }
        moodsAdapter.notifyDataSetChanged();
    }

    private void toUpdateIconOrder(){
        if(ascendingOrder){
            menuItemOrder.setIcon(R.drawable.ic_action_ascending);
        }else{
            menuItemOrder.setIcon(R.drawable.ic_action_descending);
        }
    }
    private void readPreferences(){

        SharedPreferences shared = getSharedPreferences(MoodRecordsActivity.FILE_PREFERENCES, Context.MODE_PRIVATE);
        ascendingOrder = shared.getBoolean(KEY_ASCENDING_ORDER, ascendingOrder);
    }
    private void saveAscendingOrder(boolean newValor){
        SharedPreferences shared = getSharedPreferences(FILE_PREFERENCES, Context.MODE_PRIVATE);

        SharedPreferences.Editor edit = shared.edit();
        edit.putBoolean(KEY_ASCENDING_ORDER, newValor);

        edit.commit();

        ascendingOrder = newValor;
    }
    private void  confirmRestorePatterns(){
        DialogInterface.OnClickListener listenerYes = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                restorePatterns();
                toUpdateIconOrder();

                listOrder();

                Toast.makeText(MoodRecordsActivity.this, R.string.factory_patterns, Toast.LENGTH_LONG).show();

            }
        };
        UtilsAlert.actionConfirm(this, getString(R.string.want_restore_defaults), listenerYes, null);
    }

    private void restorePatterns(){
        SharedPreferences shared = getSharedPreferences(FILE_PREFERENCES, Context.MODE_PRIVATE);

        SharedPreferences.Editor edit = shared.edit();

        edit.clear();
        edit.commit();

        ascendingOrder = PATTERN_INITIAL_ORDER;
    }
}