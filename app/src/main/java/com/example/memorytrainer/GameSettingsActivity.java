package com.example.memorytrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;

public class GameSettingsActivity extends AppCompatActivity {

    private int size = 3;
    private int content = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_settings);

        initRadioButtons();
        initTableSizes();
    }

    private void initTableSizes() {
        String[] sizes = { "3", "4", "5" };
        Spinner spinner = findViewById(R.id.spTableSize);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sizes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = (String)parent.getItemAtPosition(position);
                size = Integer.parseInt(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        };
        spinner.setOnItemSelectedListener(itemSelectedListener);
    }

    private void initRadioButtons() {
        RadioButton rButtonDigits = findViewById(R.id.rButtonDigits);
        rButtonDigits.setOnClickListener(rButtonClick);

        RadioButton rButtonRussianLetters = findViewById(R.id.rButtonRussianLetters);
        rButtonRussianLetters.setOnClickListener(rButtonClick);

        RadioButton rButtonEnglishLetters = findViewById(R.id.rButtonEnglishLetters);
        rButtonEnglishLetters.setOnClickListener(rButtonClick);
    }

    View.OnClickListener rButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RadioButton rButton = (RadioButton)v;
            switch (rButton.getId()) {
                case R.id.rButtonDigits: content = 1;
                    break;
                case R.id.rButtonRussianLetters: content = 2;
                    break;
                case R.id.rButtonEnglishLetters: content = 3;
                    break;
                default:
                    break;
            }
        }
    };

    public void btnPlayClick(View view) {
        Intent intent = new Intent(this, GameTableActivity.class);
        intent.putExtra("table_size", size);
        intent.putExtra("table_content", content);
        startActivity(intent);
    }
}