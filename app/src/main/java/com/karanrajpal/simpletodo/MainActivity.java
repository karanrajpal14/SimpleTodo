package com.karanrajpal.simpletodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> tasks;

    Button btnAdd;
    EditText etTaskItem;
    RecyclerView rvTasksList;
    TaskItemsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        etTaskItem = findViewById(R.id.editTextTaskItem);
        rvTasksList = findViewById(R.id.rvTaskList);

        tasks = new ArrayList<>();
        tasks.add("Buy eggs");
        tasks.add("Call mom");
        tasks.add("Pay rent");

        TaskItemsAdapter.OnLongClickListener longClickListener = listItemIndex -> {
            tasks.remove(listItemIndex);
            adapter.notifyItemRemoved(listItemIndex);
            Toast.makeText(getApplicationContext(), "Task removed from list", Toast.LENGTH_LONG).show();
        };

        adapter = new TaskItemsAdapter(tasks, longClickListener);
        rvTasksList.setAdapter(adapter);
        rvTasksList.setLayoutManager(new LinearLayoutManager(this));

        btnAdd.setOnClickListener(v -> {
            String newTask = etTaskItem.getText().toString();
            tasks.add(newTask);
            adapter.notifyItemInserted(tasks.size() - 1);
            etTaskItem.setText("");
            Toast.makeText(getApplicationContext(), "Task added successfully", Toast.LENGTH_SHORT).show();
        });
    }
}