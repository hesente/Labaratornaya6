package com.example.laba6s;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ReminderListActivity extends AppCompatActivity {

    private ReminderDatabaseHelper dbHelper;
    private ReminderAdapter adapter;
    private List<Reminder> reminders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_list);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        dbHelper = new ReminderDatabaseHelper(this);
        reminders = new ArrayList<>();

        loadReminders();

        adapter = new ReminderAdapter(reminders, id -> {
            dbHelper.deleteReminder(id);
            loadReminders();
            Toast.makeText(this, "Напоминание удалено", Toast.LENGTH_SHORT).show();
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadReminders() {
        reminders.clear();
        reminders.addAll(dbHelper.getAllReminders());
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}