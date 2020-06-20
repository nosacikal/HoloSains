package com.nosacikal.holosains.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.nosacikal.holosains.R;
import com.nosacikal.holosains.activity.EvaluasiActivity;
import com.nosacikal.holosains.activity.SoalEvaluasiActivity;
import com.nosacikal.holosains.activity.MateriActivity;
import com.nosacikal.holosains.activity.SimulasiActivity;
import com.nosacikal.holosains.models.MainModel;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

    private List<MainModel> mainModels;
    private Context context;

    public MainAdapter(List<MainModel> mainModels, Context context) {
        this.mainModels = mainModels;
        this.context = context;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.list_main, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        final MainModel model = mainModels.get(position);

        holder.setMainData(mainModels.get(position));

        holder.itemView.setOnClickListener(v -> {

            Intent intent;

            switch (model.getId()) {
                case "1":
                    intent = new Intent(context, MateriActivity.class);
                    context.startActivity(intent);
                    Animatoo.animateSlideUp(context);
                    break;
                case "2":
                    intent = new Intent(context, SimulasiActivity.class);
                    context.startActivity(intent);
                    Animatoo.animateSlideUp(context);
                    break;
                case "3":
                    intent = new Intent(context, EvaluasiActivity.class);
                    context.startActivity(intent);
                    Animatoo.animateSlideUp(context);
                    break;
            }

        });
    }

    @Override
    public int getItemCount() {
        return mainModels.size();
    }


    public class MainViewHolder extends RecyclerView.ViewHolder {

        private TextView judulMenu;
        private TextView deskripsiMenu;
        private ImageView imageMenu;


        public MainViewHolder(@NonNull View itemView) {
            super(itemView);

            judulMenu = itemView.findViewById(R.id.judulMenu);
            deskripsiMenu = itemView.findViewById(R.id.deskripsiMenu);
            imageMenu = itemView.findViewById(R.id.imageMenu);
        }

        void setMainData(MainModel mainData) {
            judulMenu.setText(mainData.getTitle());
            deskripsiMenu.setText(mainData.getDeskripsi());
            imageMenu.setImageResource(mainData.getImage());
        }
    }
}
