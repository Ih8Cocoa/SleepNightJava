package com.meow.sleepnightjava.sleeptracker;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

final class TextItemViewHolder extends RecyclerView.ViewHolder {
    // requires a TextView instance to be adapted to RecyclerView
    final TextView textView;

    public TextItemViewHolder(@NonNull TextView itemView) {
        super(itemView);
        this.textView = itemView;
    }
}
