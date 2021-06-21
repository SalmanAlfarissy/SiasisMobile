package com.pnp.siasismobile.adapter;

import android.content.Context;
import android.content.Intent;
import android.nfc.cardemulation.CardEmulation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.pnp.siasismobile.MainActivity;
import com.pnp.siasismobile.R;
import com.pnp.siasismobile.model.Event;
import com.pnp.siasismobile.ui.home.DetailHomeFragment;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.eventViewHolder> {
    private Context context;
    private List<Event> eventList;

    public EventAdapter(Context context, List<Event> eventList) {
        this.context = context;
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public EventAdapter.eventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_home_fragment, parent,false);
        return new eventViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.eventViewHolder holder, int position) {
        Event event = eventList.get(position);
        holder.title_event.setText(event.getNama_event());
        holder.crdview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailHomeFragment.class);
                intent.putExtra("id_info", eventList.get(holder.getAdapterPosition()).getId_info());
                intent.putExtra("nama_event", eventList.get(holder.getAdapterPosition()).getNama_event());
                intent.putExtra("gambar_event", eventList.get(holder.getAdapterPosition()).getGambar_event());
                intent.putExtra("deskripsi", eventList.get(holder.getAdapterPosition()).getDeskripsi());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class eventViewHolder extends RecyclerView.ViewHolder {
        CardView crdview;
        TextView title_event;
        public eventViewHolder(@NonNull View itemView) {
            super(itemView);
            crdview = itemView.findViewById(R.id.crdview);
            title_event = itemView.findViewById(R.id.title_event);
        }
    }
}
