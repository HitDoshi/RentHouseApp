package com.example.renthouse;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class listRecycler extends RecyclerView.Adapter<listRecycler.ViewHolder>{
    List<HouseDetail> all = new ArrayList<HouseDetail>();

    // RecyclerView recyclerView;
    public listRecycler(List<HouseDetail> all) {
        this.all = all;
        Log.d("Hit","Hit");
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.list, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        Log.d("Hit","Hit");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final HouseDetail myListData = all.get(position);
        holder.name.setText(all.get(position).getOwnerNames());
        holder.email.setText(all.get(position).getOwnerEmail());
        holder.number.setText(all.get(position).getEmployeeContactNumber());

        holder.city.setText(all.get(position).getCity());
        holder.city.setText(all.get(position).getCity());
        holder.area.setText(all.get(position).getArea());
        holder.bhk.setText(all.get(position).getBHK());
        holder.price.setText(all.get(position).getPrice()+"");

        Log.d("Hit",myListData.getArea());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


    @Override
    public int getItemCount() {
        return all.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name , email , number , city , area , bhk ,price;
        public LinearLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.name);
            this.email = (TextView) itemView.findViewById(R.id.email);
            this.number = (TextView) itemView.findViewById(R.id.number);
            this.city = (TextView) itemView.findViewById(R.id.city);
            this.area = (TextView) itemView.findViewById(R.id.area);
            this.bhk = (TextView) itemView.findViewById(R.id.bhk);
            this.price = (TextView) itemView.findViewById(R.id.price);
            relativeLayout = (LinearLayout)itemView.findViewById(R.id.relative);
            Log.d("Hit","Hit2");
        }
    }
}
