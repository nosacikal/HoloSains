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
import com.nosacikal.holosains.activity.DetailMateriActivity;
import com.nosacikal.holosains.models.Materi;

import java.util.List;

public class MateriAdapter extends RecyclerView.Adapter<MateriAdapter.MateriViewHolder> {

    private List<Materi> materi;
    private Context context;

    public MateriAdapter(List<Materi> materi, Context context) {
        this.materi = materi;
        this.context = context;
    }

    @NonNull
    @Override
    public MateriViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MateriViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.list_materi, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull MateriViewHolder holder, int position) {
        Materi materiModel = materi.get(position);

        holder.judulMateri.setText(materiModel.getJudul_materi());
        holder.subTema.setText(materiModel.getSub_tema());

        switch (materiModel.getSub_tema()) {

            case "Organ Gerak Hewan":
                holder.materiImage.setImageResource(R.drawable.tema1);
                break;
            case "Manusia dan Lingkungan":
                holder.materiImage.setImageResource(R.drawable.tema2);
                break;
            case "Lingkungan dan Manfaatnya":
                holder.materiImage.setImageResource(R.drawable.tema3);
                break;

        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailMateriActivity.class);
            intent.putExtra("Materi", materiModel);
            intent.putExtra("index", position);
            context.startActivity(intent);
            Animatoo.animateSlideUp(context);
        });
    }

    @Override
    public int getItemCount() {
        return materi.size();
    }

    class MateriViewHolder extends RecyclerView.ViewHolder {
        private TextView judulMateri;
        private TextView subTema;
        private ImageView materiImage;


        MateriViewHolder(@NonNull View itemView) {
            super(itemView);
            judulMateri = itemView.findViewById(R.id.judulMateri);
            subTema = itemView.findViewById(R.id.materiSubTema);
            materiImage = itemView.findViewById(R.id.materiImage);

        }

    }
}
