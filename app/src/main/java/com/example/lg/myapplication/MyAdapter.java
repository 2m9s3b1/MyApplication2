package com.example.lg.myapplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<RecyclerItem> recyclerItemArrayList;

    public MyAdapter(ArrayList<RecyclerItem> recyclerItemArrayList) {
        this.recyclerItemArrayList = recyclerItemArrayList;
    } // 생성자

    // Viewholder 초기화
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitle, mDate, mStart, mFin;
        public ViewHolder(View v) {
            super(v);
            mTitle = (TextView) itemView.findViewById(R.id.title);
            mDate = (TextView) itemView.findViewById(R.id.date);
            mStart = (TextView) itemView.findViewById(R.id.start);
            mFin = (TextView) itemView.findViewById(R.id.fin);
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View menuView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleritem, parent,false);
        ViewHolder viewHolder = new ViewHolder(menuView);

        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ViewHolder viewholder = (ViewHolder) holder;

        RecyclerItem recyclerItem = recyclerItemArrayList.get(position);

        holder.mTitle.setText(recyclerItemArrayList.get(position).title);
        holder.mDate.setText(recyclerItemArrayList.get(position).date);
        holder.mStart.setText(recyclerItemArrayList.get(position).start);
        holder.mFin.setText(recyclerItemArrayList.get(position).fin);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return recyclerItemArrayList.size();
    }
}