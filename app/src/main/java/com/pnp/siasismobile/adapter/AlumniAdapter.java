package com.pnp.siasismobile.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pnp.siasismobile.R;
import com.pnp.siasismobile.model.Alumni;
import com.pnp.siasismobile.ui.alumni.DetailAlumniFragment;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AlumniAdapter extends RecyclerView.Adapter<AlumniAdapter.alumniViewHolder> {
    private Context context;
    private List<Alumni> alumniList;

    public AlumniAdapter(Context context, List<Alumni> alumniList) {
        this.context = context;
        this.alumniList = alumniList;
    }

    @NonNull
    @Override
    public AlumniAdapter.alumniViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_alumni_fragment, parent,false);
        return new alumniViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AlumniAdapter.alumniViewHolder holder, int position) {
        Alumni alumni = alumniList.get(position);
        Glide.with(context)
                .load("http://192.168.56.1/adm_siasis/admin/alumni/"+alumni.getFoto())
                .into(holder.imgalumni);
        holder.nama_alumni.setText(alumni.getNama());
        holder.pekerjaan_alumni.setText("Pekerjaan\t: "+alumni.getPekerjaan());
        holder.angkatan_alumni.setText("Angkatan\t : "+alumni.getAngkatan());
        holder.crdalumni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailAlumniFragment.class);
                intent.putExtra("id_alumni", alumniList.get(holder.getAdapterPosition()).getId_alumni());
                intent.putExtra("nama",alumniList.get(holder.getAdapterPosition()).getNama());
                intent.putExtra("angkatan",alumniList.get(holder.getAdapterPosition()).getAngkatan());
                intent.putExtra("pekerjaan",alumniList.get(holder.getAdapterPosition()).getPekerjaan());
                intent.putExtra("alamat",alumniList.get(holder.getAdapterPosition()).getAlamat());
                intent.putExtra("foto",alumniList.get(holder.getAdapterPosition()).getFoto());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return alumniList.size();
    }

    public class alumniViewHolder extends RecyclerView.ViewHolder {
        CardView crdalumni;
        TextView nama_alumni,pekerjaan_alumni,angkatan_alumni;
        CircleImageView imgalumni;

        public alumniViewHolder(@NonNull View itemView) {
            super(itemView);
            imgalumni=itemView.findViewById(R.id.imgalumni);
            nama_alumni= itemView.findViewById(R.id.nama_alumni);
            pekerjaan_alumni= itemView.findViewById(R.id.pekerjaan_alumni);
            crdalumni = itemView.findViewById(R.id.crdalumni);
            angkatan_alumni = itemView.findViewById(R.id.angkatan_alumni);
        }
    }
}
