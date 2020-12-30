package com.karanrajpal.simpletodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> tasks;

    Button btnAdd;
    EditText etTaskItem;
    RecyclerView rvTasksList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        etTaskItem = findViewById(R.id.editTextTaskItem);
        rvTasksList = findViewById(R.id.rvTaskList);

        etTaskItem.setText("Test");

        tasks = new ArrayList<>();
        tasks.add("Buy eggs");
        tasks.add("Call mom");
        tasks.add("Pay rent");

        TaskItemsAdapter adapter = new TaskItemsAdapter(tasks);
        rvTasksList.setAdapter(adapter);
        rvTasksList.setLayoutManager(new LinearLayoutManager(this));
    }
}