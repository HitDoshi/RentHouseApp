package com.example.renthouse;

import android.content.Context;
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

public class listitem extends RecyclerView.Adapter<listitem.ViewHolder>{

    List<HouseDetail> all = new ArrayList<HouseDetail>();
    Context context;
    // RecyclerView recyclerView;
    public listitem(List<HouseDetail> all, Context context) {
        this.context = context;
        this.all = all;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view= layoutInflater.inflate(R.layout.list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
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
        holder.price.setText(all.get(position).getPrice());

        Log.d("bind","bind");
        //holder.imageView.setImageResource(listdata[position].getImgId());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(view.getContext(),"click on item: "+ all.get(position).getEmployeeContactNumber(),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return all.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //public ImageView imageView;
        public TextView name , email , number , city , area , bhk ,price;
        public LinearLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            //this.imageView = (ImageView) itemView.findViewById(R.id.imageView);
            this.name = (TextView) itemView.findViewById(R.id.name);
            this.email = (TextView) itemView.findViewById(R.id.email);
            //this.number = (TextView) itemView.findViewById(R.id.number);
            this.city = (TextView) itemView.findViewById(R.id.city);
            this.area = (TextView) itemView.findViewById(R.id.area);
            this.bhk = (TextView) itemView.findViewById(R.id.bhk);
            this.price = (TextView) itemView.findViewById(R.id.price);

            relativeLayout = (LinearLayout)itemView.findViewById(R.id.relative);
            Log.d("view","view");
        }
    }
}