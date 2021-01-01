package com.karanrajpal.simpletodo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_TASK_CONTENT = "task_content";
    public static final String KEY_TASK_POSITION = "task_position";
    public static final int EDIT_ACTIVITY_CODE = 20;

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

        loadTasks();

        TaskItemsAdapter.OnClickListener clickListener = listItemIndex -> {
            Log.d("MainActivity", "Clicked at position " + listItemIndex);
            Intent i = new Intent(this, EditActivity.class);
            i.putExtra(KEY_TASK_CONTENT, tasks.get(listItemIndex));
            i.putExtra(KEY_TASK_POSITION, listItemIndex);
            startActivityForResult(i, EDIT_ACTIVITY_CODE);
        };

        TaskItemsAdapter.OnLongClickListener longClickListener = listItemIndex -> {
            tasks.remove(listItemIndex);
            adapter.notifyItemRemoved(listItemIndex);
            Toast.makeText(getApplicationContext(), "Task removed from list", Toast.LENGTH_LONG).show();
            saveTasks();
        };

        adapter = new TaskItemsAdapter(tasks, clickListener, longClickListener);
        rvTasksList.setAdapter(adapter);
        rvTasksList.setLayoutManager(new LinearLayoutManager(this));

        btnAdd.setOnClickListener(v -> {
            String newTask = etTaskItem.getText().toString();
            tasks.add(newTask);
            adapter.notifyItemInserted(tasks.size() - 1);
            etTaskItem.setText("");
            Toast.makeText(getApplicationContext(), "Task added successfully", Toast.LENGTH_SHORT).show();
            saveTasks();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == EDIT_ACTIVITY_CODE) {
            int position = data.getIntExtra(KEY_TASK_POSITION, -1);
            tasks.set(position, data.getStringExtra(KEY_TASK_CONTENT));
            adapter.notifyItemChanged(position);
            Toast.makeText(getApplicationContext(), "Task updated successfully", Toast.LENGTH_SHORT).show();
            saveTasks();
        } else {
            Log.e("MainActivity", "onActivityResult: Incorrect Activity Code or Result Code");
        }
    }

    private File getDataFile() {
        return new File(getFilesDir(), "data.txt");
    }

    private void loadTasks() {
        try {
            tasks = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity-loadTasks", "Error reading tasks", e);
            Toast.makeText(getApplicationContext(), "Error loading tasks. Please try again later.", Toast.LENGTH_LONG).show();
            tasks = new ArrayList<>();
        }
    }

    private void saveTasks() {
        try {
            FileUtils.writeLines(getDataFile(), tasks);
        } catch (IOException e) {
            Log.e("MainActivity-saveTasks", "Error writing tasks", e);
            Toast.makeText(getApplicationContext(), "Error saving tasks.", Toast.LENGTH_LONG).show();
        }
    }
}