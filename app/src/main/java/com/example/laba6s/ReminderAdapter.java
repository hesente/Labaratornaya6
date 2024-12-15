package com.example.laba6s;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder> {

    private List<Reminder> reminderList;
    private OnItemClickListener onItemClickListener;

    public ReminderAdapter(List<Reminder> reminderList, OnItemClickListener onItemClickListener) {
        this.reminderList = reminderList;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ReminderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reminder, parent, false);
        return new ReminderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReminderViewHolder holder, int position) {
        Reminder reminder = reminderList.get(position);
        holder.titleTextView.setText(reminder.getTitle());
        holder.descriptionTextView.setText(reminder.getDescription());

        // Форматируем дату
        String formattedDate = formatDate(reminder.getDateTime());
        holder.timeTextView.setText(formattedDate); // отображаем дату и время

        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(reminder.getId()));
    }


    private String formatDate(long dateTime) {
        // Создаем объект SimpleDateFormat с нужным форматом
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault());

        // Преобразуем метку времени в строку
        return sdf.format(dateTime);
    }

    @Override
    public int getItemCount() {
        return reminderList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int reminderId);
    }

    public static class ReminderViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, descriptionTextView, timeTextView;

        public ReminderViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView); // добавьте timeTextView
        }
    }

}
