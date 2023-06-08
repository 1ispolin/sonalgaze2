package com.example.sonelgazp2.acitivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class client_ratees extends AppCompatActivity {

    TextView refval,adresseval,nomval,type,description;
    ImageView prblm_image;
    Intent recieve;

    User user;
    MongoClient mongoClient;
    MongoDatabase mongoDatabase;
    MongoCollection<Document> mongoCollection;
    App app;
    String appId = "sonalgazep-jjill";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_ratees);

        refval=(TextView) findViewById(R.id.refval);
        adresseval=(TextView) findViewById(R.id.adresseval);
        nomval=(TextView) findViewById(R.id.nomval);
        type=(TextView) findViewById(R.id.type);
        description=(TextView) findViewById(R.id.description);
        prblm_image=(ImageView) findViewById(R.id.prblm_image);

         recieve =getIntent();

        app= new App(new AppConfiguration.Builder(appId).build());

        user = app.currentUser();
        mongoClient = user.getMongoClient("mongodb-atlas");
        mongoDatabase = mongoClient.getDatabase("SONALGAZ");
        mongoCollection=mongoDatabase.getCollection("listeclientsigs");


        Document queryFilter  = new Document("ref",recieve.getStringExtra("REF"));
        mongoCollection.findOne(queryFilter).getAsync(task -> {
            if (task.isSuccess()) {
                Document doc1= task.get();

                refval.setText(""+recieve.getStringExtra("REF"));
                adresseval.setText(""+recieve.getStringExtra("ADRESSE"));
                nomval.setText(""+recieve.getStringExtra("NOM CLIENT"));
                type.setText(""+doc1.getString("Type"));
                description.setText(""+doc1.getString("description"));

                Log.v("EXAMPLE", "successfully found a document ");
            } else {
                Log.e("EXAMPLE", "failed to find document with: ", task.getError());
            }
        });




    }
}