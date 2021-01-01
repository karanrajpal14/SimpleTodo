package com.karanrajpal.simpletodo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {

    EditText etTask;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        etTask = findViewById(R.id.etEditTask);
        btnSave = findViewById(R.id.btnSave);

        getSupportActionBar().setTitle("Edit Task");
        etTask.setText(getIntent().getStringExtra(MainActivity.KEY_TASK_CONTENT));

        btnSave.setOnClickListener(v -> {
            Intent i = new Intent();
            i.putExtra(MainActivity.KEY_TASK_CONTENT, etTask.getText().toString());
            i.putExtra(MainActivity.KEY_TASK_POSITION, getIntent().getIntExtra(MainActivity.KEY_TASK_POSITION, -1));
            setResult(RESULT_OK, i);
            finish();
        });
    }
}