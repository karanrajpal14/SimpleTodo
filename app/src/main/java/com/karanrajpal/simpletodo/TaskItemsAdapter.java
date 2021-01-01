package com.karanrajpal.simpletodo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskItemsAdapter extends RecyclerView.Adapter<TaskItemsAdapter.ViewHolder> {

    public interface OnClickListener {
        void broadcastClick(int listItemIndex);
    }

    public interface OnLongClickListener {
        void broadcastLongClick(int listItemIndex);
    }

    List<String> tasks;
    OnClickListener onClickListener;
    OnLongClickListener onLongClickListener;

    public TaskItemsAdapter(List<String> tasks, OnClickListener onClickListener, OnLongClickListener onLongClickListener) {
        this.tasks = tasks;
        this.onClickListener = onClickListener;
        this.onLongClickListener = onLongClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View taskView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(taskView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String task = tasks.get(position);
        holder.bind(task);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTaskView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTaskView = itemView.findViewById(android.R.id.text1);
        }

        public void bind(String task) {
            tvTaskView.setText(task);
            tvTaskView.setOnClickListener(v -> onClickListener.broadcastClick(getAdapterPosition()));
            tvTaskView.setOnLongClickListener(v -> {
                onLongClickListener.broadcastLongClick(getAdapterPosition());
                return true;
            });
        }
    }
}
