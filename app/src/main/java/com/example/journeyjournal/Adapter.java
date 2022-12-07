package com.example.journeyjournal;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.journeyjournal.helper.Jinfo;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.RecycleViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;

    Context context;
    ArrayList<Jinfo> list;

    public Adapter(Context context, ArrayList<Jinfo> list, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.list = list;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_journal_design, null);
        return new RecycleViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewHolder holder, int position) {
        Jinfo jinfo = list.get(position);
        holder.title.setText(jinfo.title);
        holder.details.setText(jinfo.details);
        holder.date.setText(jinfo.date);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailJourney.class);
                intent.putExtra("id", jinfo.id);
                context.startActivity(intent);
            }
        });
        holder.imageView.setImageBitmap(DatabaseHelperj.getBitmap(jinfo.image));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class RecycleViewHolder extends RecyclerView.ViewHolder {
        TextView title, details, date;
        ImageView imageView;

        public RecycleViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            title = itemView.findViewById(R.id.titleview);
            details = itemView.findViewById(R.id.detailview);
            imageView = itemView.findViewById(R.id.imageview);
            date = itemView.findViewById(R.id.datetimeview);

        }
    }
}
