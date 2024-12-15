package com.example.laba6s;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class ReminderDetailActivity extends AppCompatActivity {

    private TextView titleTextView, descriptionTextView, timeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_detail);

        titleTextView = findViewById(R.id.titleTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        timeTextView = findViewById(R.id.timeTextView);

        // Получаем данные из Intent
        String title = getIntent().getStringExtra("title");
        String description = getIntent().getStringExtra("description");
        long dateTime = getIntent().getLongExtra("dateTime", 0);

        // Устанавливаем текст в TextView
        titleTextView.setText(title);
        descriptionTextView.setText(description);
        timeTextView.setText(formatDate(dateTime));
    }

    // Метод для форматирования времени в строку
    private String formatDate(long dateTime) {
        // Форматируем время в день.месяц.год часы:минуты:секунды
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault());
        return sdf.format(dateTime);
    }

}
