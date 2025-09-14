package br.edu.utfpr.alunos.jeffersonlima.monisaudemental;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextName, editTextAge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        String age = editTextAge.getText().toString();
        if(age == null || age.trim().isEmpty()){
            Toast.makeText(this,
                    R.string.invalid_value_age,
                    Toast.LENGTH_LONG).show();
            editTextName.requestFocus();
            return;
        }

    }
}