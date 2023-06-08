package com.example.sonelgazp2.acitivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sonelgazp2.R;

import org.bson.Document;

import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;

public class client_valide extends AppCompatActivity {

    TextView ENERGIE_REACTIF_EXPORTEE,Energie_Réactif_IMPORTEE,PMA,total_tarif_3,total_tarif_2,total_tarif_1,ancienindexval,nouveauindexval,nomval,adresseval,refval;
    User user;
    MongoClient mongoClient;
    MongoDatabase mongoDatabase;
    MongoCollection<Document> mongoCollection;
    App app;
    String appId = "sonalgazep-jjill";
    ImageView restohome7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_valide);


        restohome7 = (ImageView) findViewById(R.id.restohome7);
        ENERGIE_REACTIF_EXPORTEE = (TextView) findViewById(R.id.ENERGIE_REACTIF_EXPORTEE);
        Energie_Réactif_IMPORTEE = (TextView) findViewById(R.id.Energie_Réactif_IMPORTEE);
        PMA = (TextView) findViewById(R.id.PMA);
        total_tarif_3 = (TextView) findViewById(R.id.total_tarif_3);
        total_tarif_2 = (TextView) findViewById(R.id.total_tarif_2);
        total_tarif_1 =(TextView) findViewById(R.id.total_tarif_1);
        nouveauindexval=(TextView) findViewById(R.id.nouveauindexval);
        ancienindexval=(TextView) findViewById(R.id.ancienindexval);
        adresseval=(TextView) findViewById(R.id.adresseval);
        nomval=(TextView) findViewById(R.id.nomval);
        refval=(TextView) findViewById(R.id.refval);

        Intent recieve =getIntent();


        app= new App(new AppConfiguration.Builder(appId).build());

        user = app.currentUser();
        mongoClient = user.getMongoClient("mongodb-atlas");
        mongoDatabase = mongoClient.getDatabase("SONALGAZ");
        mongoCollection=mongoDatabase.getCollection("listeclientsvalides");



        Document queryFilter  = new Document("ref",recieve.getStringExtra("REF"));
        mongoCollection.findOne(queryFilter).getAsync(task -> {
            if (task.isSuccess()) {
                Document doc1= task.get();

                ENERGIE_REACTIF_EXPORTEE.setText("ENERGIE REACTIF EXPORTEE : "+doc1.getString("ere"));
                Energie_Réactif_IMPORTEE.setText("ENERGIE REACTIF IMPORTEE :  "+doc1.getString("eri"));
                PMA.setText("PMA :  "+doc1.getString("pma"));
                total_tarif_3.setText("TOTAL TARIF 3 :  "+doc1.getString("totalt3"));
                total_tarif_2.setText("TOTAL TARIF 2 :  "+doc1.getString("totalt2"));
                total_tarif_1.setText("TOTAL TARIF 1 :  "+doc1.getString("totalt1"));
                nouveauindexval.setText("Nouveau index:  "+doc1.getString("nouveau_index"));
                ancienindexval.setText("Ancien index:  "+doc1.getString("ancien_index"));
                adresseval.setText("Adresse: "+recieve.getStringExtra("ADRESSE"));
                nomval.setText("Nom:"+recieve.getStringExtra("NOM CLIENT"));
                refval.setText("REF:"+recieve.getStringExtra("REF"));



                Log.v("EXAMPLE", "successfully found a document ");
            } else {
                Log.e("EXAMPLE", "failed to find document with: ", task.getError());
            }
        });

        restohome7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(client_valide.this, liste_clients_valide.class);
                startActivity(intent);
            }
        });

    }
}