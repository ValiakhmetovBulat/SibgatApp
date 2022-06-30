package com.example.mainactivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainactivity.Models.Apartament;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ApartamentRVAdapter extends RecyclerView.Adapter<ApartamentRVAdapter.ViewHolder> {

    private ArrayList<Apartament> apartamentArrayList;
    private Context context;
    int lastPos = -1;
    private IApartamentClick iApartamentClick;

    public ApartamentRVAdapter(ArrayList<Apartament> apartamentArrayList, Context context, IApartamentClick iApartamentClick) {
        this.apartamentArrayList = apartamentArrayList;
        this.context = context;
        this.iApartamentClick = iApartamentClick;
    }

    @NonNull
    @Override
    public ApartamentRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.apartament_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApartamentRVAdapter.ViewHolder holder, int position) {
        Apartament apartament = apartamentArrayList.get(position);

        holder.apartamentNameTV.setText(apartament.getApartamentName());
        holder.apartamentTypeTV.setText(apartament.getApartamentType());
        Picasso.get().load(apartament.getApartamentImage()).into(holder.apartamentImageTV);

        setAnimation(holder.itemView, position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iApartamentClick.onApartamentClick(position);
            }
        });
    }

    private void setAnimation(View v, int position) {
        if (position > lastPos) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            v.setAnimation(animation);

            lastPos = position;
        }
    }

    @Override
    public int getItemCount() {
        return apartamentArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView apartamentNameTV, apartamentTypeTV;
        private ImageView apartamentImageTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            apartamentNameTV = itemView.findViewById(R.id.idTVName);
            apartamentTypeTV = itemView.findViewById(R.id.idTVType);
            apartamentImageTV = itemView.findViewById(R.id.idIVApartament);
        }
    }

    public interface IApartamentClick {
        void onApartamentClick(int position);
    }
}
