package com.example.todolist;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> taskList;
    private ArrayAdapter<String> adapter;
    private ListView listView;
    private Button addTaskButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskList = new ArrayList<>();
        listView = findViewById(R.id.listView);
        addTaskButton = findViewById(R.id.addTaskButton);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, taskList);
        listView.setAdapter(adapter);

        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTaskDialog(-1);
            }
        });

        listView.setOnItemClickListener((parent, view, position, id) -> showTaskDialog(position));
    }

    private void showTaskDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(position == -1 ? "Add Task" : "Edit Task");

        final EditText input = new EditText(this);
        input.setText(position == -1 ? "" : taskList.get(position));
        builder.setView(input);

        builder.setPositiveButton(position == -1 ? "Add" : "Save", (dialog, which) -> {
            String task = input.getText().toString().trim();
            if (!task.isEmpty()) {
                if (position == -1) {
                    taskList.add(task);
                } else {
                    taskList.set(position, task);
                }
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(MainActivity.this, "Task cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }
}
