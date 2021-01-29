package com.example.memorytrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class StatisticsActivity extends AppCompatActivity {
    private String[] data;
    private int[] ticks;
    private DBHelper dbHelper;
    private SQLiteDatabase database;

    private TextView tViewLastData;
    GraphView graph;

    private int content = 1;
    private int size = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        tViewLastData = findViewById(R.id.tViewLastData);
        graph = findViewById(R.id.graph);

        dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();

        initTableSizes();
        initTableContent();

        getData();
        showData();
    }

    private void initTableContent() {
        String[] sizes = { "Цифры", "Буквы русского алфавита", "Буквы английского алфавита" };
        Spinner spinner = findViewById(R.id.spTableContentForStatistics);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sizes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                content = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        };
        spinner.setOnItemSelectedListener(itemSelectedListener);
    }

    private void initTableSizes() {
        String[] sizes = { "3", "4", "5" };
        Spinner spinner = findViewById(R.id.spTableSizeForStatistics);
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

    public void btnGetStatisticsClick(View view) {
        getData();
        showData();
    }

    private void getData() {
        String selection = "type_game = ? AND size_game = ?";
        String[] selectionArgs = new String[] { Integer.toString(content), Integer.toString(size) };

        Cursor cursor = database.query(DBHelper.RESULTS_TABLE_NAME, null, selection, selectionArgs, null, null, null);
        data = new String[cursor.getCount()];

        int counter = 0;
        if (cursor.moveToFirst()) {
            int resultIndex = cursor.getColumnIndex(DBHelper.RESULTS_KEY_TIME_RESULT);

            do {
                data[counter] = cursor.getString(resultIndex);
                counter++;
            } while (cursor.moveToNext()); }

        cursor.close();
    }

    private void showData() {
        showAllData();
        showLastData();
    }

    private void showAllData() {
        timeToTicks();

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
        graph.removeAllSeries();

        for (int i = 0; i < ticks.length; i++) {
            series.appendData(new DataPoint(i + 1, ticks[i]), true, 100);
        }

        series.setDrawDataPoints(true);

        graph.addSeries(series);
        graph.setTitle("Результаты в секундах Y - время, X - Попытка");
    }

    private void showLastData() {
        if(data.length > 0)
            tViewLastData.setText("Последний результат: " + data[data.length - 1]);
        else
            tViewLastData.setText("Нет данных");
    }

    private void timeToTicks() {
        ticks = new int[data.length];

        for (int i = 0; i < data.length; i++) {
            String[] parts = data[i].split(":");

            int hours = Integer.parseInt(parts[0]) * 3600;
            int minutes = Integer.parseInt(parts[1]) * 60;
            int seconds = Integer.parseInt(parts[2]);

            ticks[i] = hours + minutes + seconds;
        }
    }
}