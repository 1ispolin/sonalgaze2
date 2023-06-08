package com.example.sonelgazp2.acitivities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.sonelgazp2.R;

import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.User;

public class Principal_activity extends AppCompatActivity {
    TextView nomutilisateur2;
    App app;
    String appId = "sonalgazep-jjill";
    CardView listeaff,listerel, listerat,listerest;
    ImageButton sedeconnecter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        nomutilisateur2=findViewById(R.id.nomutilisateur2);
        sedeconnecter=findViewById(R.id.sedeconnecter);
        listeaff=findViewById(R.id.listeaff);
        listerel=findViewById(R.id.listerel);
        listerat =findViewById(R.id.listerat);
        listerest=findViewById(R.id.listeres);
        app= new App(new AppConfiguration.Builder(appId).build());

        User user = app.currentUser();
        String name =getIntent().getStringExtra("name");

        nomutilisateur2.setText("Mr "+name);

        sedeconnecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Principal_activity.this, login.class);
                startActivity(intent);
                finish();
            }
        });

        listeaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Principal_activity.this, liste_clients_affectes.class);
                startActivity(intent);
                finish();
            }
        });

        listerel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Principal_activity.this, liste_clients_valide.class);
                startActivity(intent);
                finish();
            }
        });
        listerat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Principal_activity.this, liste_clients_ratees.class);
                startActivity(intent);
                finish();
            }
        });
        listerest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Principal_activity.this, liste_client_restant.class);
                startActivity(intent);
                finish();
            }
        });

        sedeconnecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear user authentication information

                user.logOutAsync( result -> {
                    if (result.isSuccess()) {
                        Log.v("AUTH", "Successfully logged out.");
                        clearUserAuthenticationData();
                        Intent intent=new Intent(Principal_activity.this, login.class);
                        startActivity(intent);

                    } else {
                        Log.e("AUTH", result.getError().toString());
                    }
                });
            }
        });

    }

    private void clearUserAuthenticationData() {
        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("username");
        editor.remove("token");
        editor.apply();
    }


    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        finish();
    }
}