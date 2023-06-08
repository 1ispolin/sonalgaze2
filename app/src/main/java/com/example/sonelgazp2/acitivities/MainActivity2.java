package com.example.sonelgazp2.acitivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;


import com.example.sonelgazp2.R;
import com.example.sonelgazp2.bone.client;

import java.util.List;

public class MainActivity2 extends AppCompatActivity  {

    private RecyclerView rcv;
    private com.example.sonelgazp2.adapters.tout_client_adapter tout_client_adapter;
    private List<client> clients;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

    /*    rcv = findViewById(R.id.rcv);
        clients = new ArrayList<>();
        clients.add(new client("4089541","abd alkader","bab alwad "));
        clients.add(new client("8056954","mohamed ","chraga"));
        clients.add(new client("0408972","youssef ","airoport"));
        clients.add(new client("8056954","fatima ","oran"));
        clients.add(new client("0564372","hocine","costontine"));

        rcv.setLayoutManager(new LinearLayoutManager(this));

        tout_client_adapter = new tout_client_adapter(clients,this);
        rcv.setAdapter(tout_client_adapter);

    }

    @Override
    public void onItemClick(int postion) {
       Intent intent= new Intent(MainActivity2.this, validerclient2222222.class);
       intent.putExtra("ref",clients.get(postion).getReference());
        intent.putExtra("nom client",clients.get(postion).getNom_client());
        intent.putExtra("Adress",clients.get(postion).getAdresse());

        startActivity(intent);
        */

    }
}