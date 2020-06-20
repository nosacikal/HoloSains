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
import com.nosacikal.holosains.activity.VideoSimulasiActivity;
import com.nosacikal.holosains.models.Simulasi;

import java.util.List;

public class SimulasiAdapter extends RecyclerView.Adapter<SimulasiAdapter.SimulasiViewHolder> {

    private List<Simulasi> simulasi;
    private Context context;

    public SimulasiAdapter(List<Simulasi> simulasi, Context context) {
        this.simulasi = simulasi;
        this.context = context;
    }

    @NonNull
    @Override
    public SimulasiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SimulasiViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.list_simulasi, parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull SimulasiViewHolder holder, int position) {
        Simulasi simulasiModel = simulasi.get(position);

        holder.judulSimulasi.setText(simulasiModel.getJudul_simulasi());
        holder.subTema.setText(simulasiModel.getSub_tema());

        switch (simulasiModel.getSub_tema()) {

            case "Organ Gerak Hewan":
                holder.simulasiImage.setImageResource(R.drawable.tema1);
                break;
            case "Manusia dan Lingkungan":
                holder.simulasiImage.setImageResource(R.drawable.tema2);
                break;
            case "Lingkungan dan Manfaatnya":
                holder.simulasiImage.setImageResource(R.drawable.tema3);
                break;

        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, VideoSimulasiActivity.class);
            intent.putExtra("Simulasi", simulasiModel);
            intent.putExtra("SimulasiIndex", position);
            context.startActivity(intent);
            Animatoo.animateSlideUp(context);
        });
    }

    @Override
    public int getItemCount() {
        return simulasi.size();
    }

    public class SimulasiViewHolder extends RecyclerView.ViewHolder {
        private TextView judulSimulasi;
        private TextView subTema;
        private ImageView simulasiImage;

        public SimulasiViewHolder(@NonNull View itemView) {
            super(itemView);

            judulSimulasi = itemView.findViewById(R.id.judulSimulasi);
            subTema = itemView.findViewById(R.id.simulasiSubTema);
            simulasiImage = itemView.findViewById(R.id.simulasiImage);
        }
    }
}
