package com.example.sonelgazp2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sonelgazp2.R;
import com.example.sonelgazp2.bone.client;

import java.util.ArrayList;

public class valide_adapter extends RecyclerView.Adapter<valide_adapter.ViewHolder> {
    private  final RecyclerViewInterface recyclerViewInterface;
    private ArrayList<client> clients;

    public valide_adapter(RecyclerViewInterface recyclerViewInterface, ArrayList<client> clients) {
        this.recyclerViewInterface = recyclerViewInterface;
        this.clients = clients;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.client_valide_item_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(view,recyclerViewInterface);
        return viewHolder;

    }

    public void onBindViewHolder(@NonNull valide_adapter.ViewHolder holder, int position) {
        client data = clients.get(position);
        holder.adresseval.setText(data.getAdresse());
        holder.nomval.setText(data.getNom_client());
        holder.refval.setText(String.valueOf(data.getReference()));
        holder.ancienindexval.setText(String.valueOf(data.getReference()));
        holder.nouveauindexval.setText(String.valueOf(data.getReference()));

    }


    public int getItemCount() {
        return clients.size();

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView refval,nomval,adresseval,ancienindexval,nouveauindexval;

        public ViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            refval = itemView.findViewById(R.id.refval);
            nomval = itemView.findViewById(R.id.nomval);
            adresseval = itemView.findViewById(R.id.adresseval);
            ancienindexval = itemView.findViewById(R.id.ancienindexval);
            nouveauindexval = itemView.findViewById(R.id.nouveauindexval);



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
