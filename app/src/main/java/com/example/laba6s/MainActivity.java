package com.example.laba6s;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private EditText titleEditText, descriptionEditText, timeEditText;
    private ReminderDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "reminderChannel",
                    "Напоминания",
                    NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }


        titleEditText = findViewById(R.id.titleEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        timeEditText = findViewById(R.id.timeEditText);
        Button saveButton = findViewById(R.id.saveButton);
        Button viewRemindersButton = findViewById(R.id.viewRemindersButton);

        dbHelper = new ReminderDatabaseHelper(this);

        saveButton.setOnClickListener(view -> saveReminder());
        viewRemindersButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ReminderListActivity.class);
            startActivity(intent);
        });


    }

    private void saveReminder() {
        String title = titleEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        String time = timeEditText.getText().toString();

        if (title.isEmpty() || description.isEmpty() || time.isEmpty()) {
            Toast.makeText(this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        long reminderTime = parseTimeToMillis(time);
        if (reminderTime == -1) {
            Toast.makeText(this, "Некорректное время", Toast.LENGTH_SHORT).show();
            return;
        }

        dbHelper.insertReminder(title, description, reminderTime);
        setAlarm(title, description, reminderTime);
        Toast.makeText(this, "Напоминание сохранено", Toast.LENGTH_SHORT).show();

        titleEditText.setText("");
        descriptionEditText.setText("");
        timeEditText.setText("");
    }

    private long parseTimeToMillis(String time) {
        try {
            String[] parts = time.split(":");
            int hour = Integer.parseInt(parts[0]);
            int minute = Integer.parseInt(parts[1]);
            int second = parts.length > 2 ? Integer.parseInt(parts[2]) : 0;  // добавление секунд, если они есть

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, second); // учитываем секунды

            return calendar.getTimeInMillis();
        } catch (Exception e) {
            return -1;
        }
    }

    @SuppressLint("ScheduleExactAlarm")
    private void setAlarm(String title, String description, long reminderTime) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Intent intent = new Intent(this, ReminderReceiver.class);
        intent.putExtra("title", title);
        intent.putExtra("description", description);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, reminderTime, pendingIntent);
    }
}