package com.example.sonelgazp2.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;


import com.example.sonelgazp2.R;
import com.example.sonelgazp2.adapters.RecyclerViewInterface;
import com.example.sonelgazp2.bone.client;

import java.util.ArrayList;
import java.util.List;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

public class tout_client_adapter extends RecyclerView.Adapter<tout_client_adapter.ViewHolder> {
   private  final RecyclerViewInterface recyclerViewInterface;
    private ArrayList<client> clients;

    public tout_client_adapter(RecyclerViewInterface recyclerViewInterface, ArrayList<client> clients) {
        this.recyclerViewInterface = recyclerViewInterface;
        this.clients = clients;
    }

    public tout_client_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.client_restant, parent, false);
        ViewHolder viewHolder = new ViewHolder(view,recyclerViewInterface);
        return viewHolder;

    }

    public void onBindViewHolder(@NonNull tout_client_adapter.ViewHolder holder, int position) {
        client data = clients.get(position);
        holder.adresse.setText(data.getAdresse());
        holder.nom_client.setText(data.getNom_client());
        holder.reference.setText(String.valueOf(data.getReference()));
    }


    public int getItemCount() {
            return clients.size();

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView adresse,nom_client,reference;

        public ViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            adresse = itemView.findViewById(R.id.adresseres);
            nom_client = itemView.findViewById(R.id.nomres);
            reference = itemView.findViewById(R.id.refres);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerViewInterface!=null){

                        int position = getAdapterPosition();
                        if(position !=RecyclerView.NO_POSITION){

                            recyclerViewInterface.onItemClick(position);

                        }
                    }
                }
            });

        }
    }

}
