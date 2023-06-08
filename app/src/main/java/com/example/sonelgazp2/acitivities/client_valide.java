package com.example.sonelgazp2.acitivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
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

    TextView ENERGIE_REACTIF_EXPORTEE,Energie_Réactif_exporté,PMA,total_tarif_3,total_tarif_2,total_tarif_1,ancienindexval,nouveauindexval,nomval,adresseval,refval;
    User user;
    MongoClient mongoClient;
    MongoDatabase mongoDatabase;
    MongoCollection<Document> mongoCollection;
    App app;
    String appId = "sonalgazep-jjill";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_valide);

        ENERGIE_REACTIF_EXPORTEE = (TextView) findViewById(R.id.ENERGIE_REACTIF_EXPORTEE);
        Energie_Réactif_exporté = (TextView) findViewById(R.id.Energie_Réactif_exporté);
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



        Document queryFilter  = new Document("ref",recieve.getIntExtra("REF",0000));
        mongoCollection.findOne(queryFilter).getAsync(task -> {
            if (task.isSuccess()) {
                Document doc1= task.get();

                ENERGIE_REACTIF_EXPORTEE.setText(""+String.valueOf(doc1.getInteger("Energie Réactif exporté")));
                Energie_Réactif_exporté.setText(""+String.valueOf(doc1.getInteger("Energie Réactif importé")));
                PMA.setText(""+String.valueOf(doc1.getInteger("P M A")));
                total_tarif_3.setText(""+String.valueOf(doc1.getInteger("total tarif 3")));
                total_tarif_2.setText(""+String.valueOf(doc1.getInteger("total tarif 2")));
                total_tarif_1.setText(""+String.valueOf(doc1.getInteger("total tarif 1")));
                nouveauindexval.setText(""+String.valueOf(doc1.getInteger("")));
                ancienindexval.setText(""+String.valueOf(doc1.getInteger("")));
                adresseval.setText(""+recieve.getStringExtra("ADRESSE"));
                nomval.setText(""+recieve.getStringExtra("NOM CLIENT"));
                refval.setText(""+String.valueOf(recieve.getIntExtra("REF",0000)));



                Log.v("EXAMPLE", "successfully found a document ");
            } else {
                Log.e("EXAMPLE", "failed to find document with: ", task.getError());
            }
        });

    }
}