package br.edu.utfpr.alunos.jeffersonlima.monisaudemental;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PersonActivity extends AppCompatActivity {

    private EditText editTextName, editTextAge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        editTextName = findViewById(R.id.editTextName);
        editTextAge  = findViewById(R.id.editTextAge);
    }

    public void clearInput(View view){
        editTextName.setText(null);
        editTextAge.setText(null);

        editTextName.requestFocus();
        Toast.makeText(this,
                R.string.clear_all,
                Toast.LENGTH_LONG).show();
    }
    public void saveValues(View view){
        String name = editTextName.getText().toString();
        if(name == null || name.trim().isEmpty()){
            Toast.makeText(this,
                    R.string.invalid_value_name,
                    Toast.LENGTH_LONG).show();
            editTextName.requestFocus();
            return;
        }
        String ageString = editTextAge.getText().toString();
        int age = 0;
        try {
            age = Integer.parseInt(ageString);
        } catch (NumberFormatException e) {
            Toast.makeText(this,
                    R.string.warning_age,
                    Toast.LENGTH_SHORT).show();
            editTextAge.requestFocus();
            return;
        }
        if(ageString == null || ageString.trim().isEmpty() || age <= 0){
            Toast.makeText(this,
                    R.string.invalid_value_age,
                    Toast.LENGTH_LONG).show();
            editTextName.requestFocus();
            return;
        }
        Toast.makeText(this,
                getString(R.string.name_show) + name + "\n" +
                    getString(R.string.idade_show) + age,
                Toast.LENGTH_LONG).show();
    }
}