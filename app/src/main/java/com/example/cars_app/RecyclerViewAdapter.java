package com.example.cars_app;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.CarsViewHolder> {

    ArrayList<Cars> carsList;
    OnRecyclerViewListener listener;

    public RecyclerViewAdapter(ArrayList<Cars> carsList, OnRecyclerViewListener listener) {
        this.carsList = carsList;
        this.listener = listener;
    }//end constructor()

    @NonNull
    @Override
    public CarsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_car_layout, null, false);
        CarsViewHolder viewHolder = new CarsViewHolder(view);
        return viewHolder;
    }//end onCreateViewHolder()

    @Override
    public void onBindViewHolder(@NonNull CarsViewHolder holder, int position) {
        Cars cars = carsList.get(position);
        if (cars.getImage() != null && !cars.getImage().isEmpty())
            holder.image.setImageURI(Uri.parse(cars.getImage()));
        holder.txtColor.setText(cars.getColor());
        holder.txtModel.setText(cars.getModel());
        holder.txtDpl.setText(cars.getDpl() + "");

        //an imaginary object i used to get id
        holder.image.setTag(cars.getId());
    }//end onBindViewHolder()

    @Override
    public int getItemCount() {
        return carsList.size();
    }//end getItemCount()

    public void setCarsList(ArrayList<Cars> carsList) {
        this.carsList = carsList;
    }//end setCarsList()

    class CarsViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView txtModel, txtColor, txtDpl;

        public CarsViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.img_car);
            txtModel = itemView.findViewById(R.id.txtModel);
            txtColor = itemView.findViewById(R.id.txtColor);
            txtDpl = itemView.findViewById(R.id.txtDpl);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id = (int) image.getTag();
                    listener.onItemClickListener(id);
                }//end onClick()
            });
        }//end constructor()
    }//end class

}//end class