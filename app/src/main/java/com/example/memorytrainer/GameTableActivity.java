package com.example.memorytrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

public class GameTableActivity extends AppCompatActivity {

    private TableLayout gameTable;
    private TextView tViewNextElement;
    private TextView tViewTimer;
    private int size;
    private int content;

    private int[] digits = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 30, 31, 32, 33 };
    private char[] russianLetters = new char[] { 'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я' };
    private char[] englishLetters = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

    private int[][] solutionDigits;
    private char[][] solutionLetters;

    private int[][] gameDigits;
    private char[][] gameLetters;

    private String nextElement;
    private int gameCounter;

    private int ticks;
    private boolean isRunTimer = true;

    DBHelper dbHelper;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_table);

        Bundle data = getIntent().getExtras();
        if (data != null) {
            size = data.getInt("table_size");
            content = data.getInt("table_content");
        }

        initGame();
        runTimer();

        dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();
    }

    private void initGame () {
        initGameTable();
        initSolutionData();
        initGameData();

        gameCounter = 0;

        if(content == 1)
            nextElement = Integer.toString(digits[gameCounter]);
        else if(content == 2)
            nextElement = Character.toString(russianLetters[gameCounter]).toUpperCase();
        else if(content == 3)
            nextElement = Character.toString(englishLetters[gameCounter]).toUpperCase();

        tViewNextElement = findViewById(R.id.tViewNextElement);
        tViewNextElement.setText("Найдите: " + nextElement);
    }

    private void initSolutionData() {
        if(content == 1) {
            solutionDigits = GameBuilder.getSolutionDigits(digits, size);
        }else if(content == 2) {
            solutionLetters = GameBuilder.getSolutionLetters(russianLetters, size);
        }else if(content == 3) {
            solutionLetters = GameBuilder.getSolutionLetters(englishLetters, size);
        }
    }

    private void initGameData() {
        if(content == 1) {
            gameDigits = GameBuilder.getGameDigits(solutionDigits);
            printGameDigits();
        }else if(content == 2) {
            gameLetters = GameBuilder.getGameLetters(solutionLetters);
            printGameLetters();
        }else if(content == 3) {
            gameLetters = GameBuilder.getGameLetters(solutionLetters);
            printGameLetters();
        }
    }

    private void printGameDigits(){
        int counter = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Button button = findViewById(counter);
                button.setText(Integer.toString(gameDigits[i][j]));

                counter++;
            }
        }
    }

    private void printGameLetters(){
        int counter = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Button button = findViewById(counter);
                button.setText(Character.toString(gameLetters[i][j]));

                counter++;
            }
        }
    }

    private void initGameTable() {
        gameTable = findViewById(R.id.tLGameTable);

        for (int i = 0; i < size; i++)
            gameTable.setColumnShrinkable(i,true);

        int counter = 0;
        for (int i = 0; i < size; i++) {
            TableRow row = createTableRow();
            for (int j = 0; j < size; j++) {
                Button button = createButton(counter);
                row.addView(button);
                counter++;
            }
            gameTable.addView(row);
        }
    }

    private TableRow createTableRow() {
        TableRow row = new TableRow(this);
        row.setGravity(Gravity.CENTER_HORIZONTAL);
        return row;
    }

    private Button createButton(int id){
        Button button = new Button(this);
        button.setId(id);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkButtonClick(button);
            }
        });

        return button;
    }

    private void checkButtonClick(Button button){
        String buttonText = button.getText().toString();
        if(buttonText.equalsIgnoreCase(nextElement)) {
            if(gameCounter + 1 < size * size) {
                gameCounter++;

                if(content == 1)
                    nextElement = Integer.toString(digits[gameCounter]);
                else if(content == 2)
                    nextElement = Character.toString(russianLetters[gameCounter]).toUpperCase();
                else if(content == 3)
                    nextElement = Character.toString(englishLetters[gameCounter]).toUpperCase();

                tViewNextElement.setText("Найдите: " + nextElement);
            }else{
                stopTimer();
                tViewNextElement.setText("Задача решена");
                insertResultToDB();
            }

            button.setText("");
        }
    }

    private void runTimer(){
        tViewTimer = findViewById(R.id.tViewTimer);
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(isRunTimer) {
                    int hours = ticks / 3600;
                    int minutes = (ticks % 3600) / 60;
                    int seconds = ticks % 60;

                    String time = String.format("%02d:%02d:%02d", hours, minutes, seconds);
                    tViewTimer.setText(time);
                    ticks++;
                    handler.postDelayed(this, 1000);
                }
            }
        });
    }

    private void stopTimer() {
        isRunTimer = false;
    }

    private void insertResultToDB() {
        ContentValues contentValues = new ContentValues();

        String time_result = tViewTimer.getText().toString();
        int type_game = content;
        int size_game = size;

        contentValues.put(DBHelper.RESULTS_KEY_TIME_RESULT, time_result);
        contentValues.put(DBHelper.RESULTS_KEY_TYPE_OF_GAME, type_game);
        contentValues.put(DBHelper.RESULTS_KEY_SIZE_OF_GAME, size_game);
        database.insert(DBHelper.RESULTS_TABLE_NAME, null, contentValues);
    }

    public void btnBackClick(View view) {
        super.finish();
        Intent intent = new Intent(this, GameSettingsActivity.class);
        startActivity(intent);
    }
}