package com.example.renthouse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class listRecycler extends RecyclerView.Adapter<listRecycler.ViewHolder>{
    ArrayList<HouseDetail> all = new ArrayList<HouseDetail>();
    Activity context;

    // RecyclerView recyclerView;
    public listRecycler(Activity context, ArrayList<HouseDetail> all) {
        this.all = all;
        this.context = context;
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
        holder.name.setText("Owner Name:- " + all.get(position).getOwnerNames());
        /*holder.email.setText(all.get(position).getOwnerEmail());
        holder.number.setText(all.get(position).getEmployeeContactNumber());
        holder.city.setText(all.get(position).getCity());
        holder.area.setText(all.get(position).getArea());*/
        holder.address.setText("Address:- " + all.get(position).getAddress());
        holder.bhk.setText(all.get(position).getBHK() + " BHK");
        holder.price.setText("Rent Price:- " + all.get(position).getPrice()+"");

        Log.d("Hit",myListData.getArea());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context,all.get(position).getImage().get(0),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, GiveNameActivty.class);
                //intent.putParcelableArrayListExtra("all",  all.get(position));
               /* Bundle args = new Bundle();
                args.putSerializable("all",(Serializable)myListData);
                intent.putExtra("bundle",args);*/

                intent.putExtra("ARRAYLIST",all);
                intent.putExtra("position",position);

               /* Bundle bundle = new Bundle();
                bundle.putSerializable("all", myListData);
                intent.putExtras(bundle);*/
                context.startActivity(intent);

            }
        });
    }


    @Override
    public int getItemCount() {
        return all.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name , email , number , city , area , bhk ,price , address;
        public LinearLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.name);
            /*this.email = (TextView) itemView.findViewById(R.id.email);
            this.number = (TextView) itemView.findViewById(R.id.number);
            this.city = (TextView) itemView.findViewById(R.id.city);
            this.area = (TextView) itemView.findViewById(R.id.area);*/
            this.bhk = (TextView) itemView.findViewById(R.id.bhk);
            this.address = (TextView) itemView.findViewById(R.id.address);
            this.price = (TextView) itemView.findViewById(R.id.price);
            relativeLayout = (LinearLayout)itemView.findViewById(R.id.relative);
            Log.d("Hit","Hit2");
        }
    }
}
