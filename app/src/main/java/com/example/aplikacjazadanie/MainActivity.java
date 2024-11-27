package com.example.aplikacjazadanie;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aplikacjazadanie.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText editTextTask;
    private Button buttonAddTask;
    private ListView listViewTasks;
    private ArrayList<String> tasksList;
    private ArrayAdapter<String> adapter;

    private int lastClickedPosition = -1;
    private long lastClickTime = 0;
    private final long DOUBLE_CLICK_THRESHOLD = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        editTextTask = findViewById(R.id.editTextTask);
        buttonAddTask = findViewById(R.id.buttonAddTask);
        listViewTasks = findViewById(R.id.listViewTasks);


        tasksList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tasksList);
        listViewTasks.setAdapter(adapter);


        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String task = editTextTask.getText().toString();
                if (!task.isEmpty()) {
                    tasksList.add(task);
                    adapter.notifyDataSetChanged();
                    editTextTask.setText("");
                }
            }
        });


        listViewTasks.setOnItemClickListener((parent, view, position, id) -> {
            long clickTime = System.currentTimeMillis();
            if (lastClickedPosition == position && (clickTime - lastClickTime) < DOUBLE_CLICK_THRESHOLD) {

                tasksList.remove(position);
                adapter.notifyDataSetChanged();
                lastClickedPosition = -1;
            } else {

                view.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                lastClickedPosition = position;
            }
            lastClickTime = clickTime;
        });
    }
}
