package com.example.sonelgazp2.acitivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sonelgazp2.R;

import org.bson.Document;

import java.util.List;

import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;

public class validerclient extends AppCompatActivity {

    MongoCollection<Document> mongoCollectionInsert,mongoCollectionClient,mongoCollectionDelete;
    EditText numcopmteur,nouveauindex,ancienindex,tottarif1,tottarif2,tottarif3,pma,ere,eri;
    Button valider_btn;
    ImageView restohome5;
    TextView signaler,refvc,nomvc,adressevc;
    MongoDatabase mongoDatabase;
    App app;
    MongoClient mongoClient;
    String appId = "sonalgazep-jjill", numCompterInserter;
    Intent recieve;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valider_client);

        adressevc = (TextView) findViewById(R.id.adressevc);
        refvc = (TextView) findViewById(R.id.refvc);
        nomvc = (TextView) findViewById(R.id.nomvc);
        signaler = (TextView) findViewById(R.id.signaler);
        valider_btn = (Button) findViewById(R.id.valider_btn);
        eri = (EditText) findViewById(R.id.eri);
        numcopmteur =(EditText) findViewById(R.id.numcopmteur);
        nouveauindex=(EditText) findViewById(R.id.nouveauindex);
        ancienindex=(EditText) findViewById(R.id.ancienindex);
        tottarif1=(EditText) findViewById(R.id.tottarif1);
        tottarif2=(EditText) findViewById(R.id.tottarif2);
        tottarif3=(EditText) findViewById(R.id.tottarif3);
        pma=(EditText) findViewById(R.id.pma);
        ere=(EditText) findViewById(R.id.ere);
        restohome5= findViewById(R.id.restohome5);


        recieve= getIntent();

        refvc.setText("REF: "+ recieve.getStringExtra("REF"));
        nomvc.setText("Nom: "+recieve.getStringExtra("NOM CLIENT"));



        App app = new App(new AppConfiguration.Builder(appId).build());
        User user = app.currentUser();
        mongoClient= user.getMongoClient("mongodb-atlas");
        mongoDatabase= mongoClient.getDatabase("SONALGAZ");
        mongoCollectionInsert =mongoDatabase.getCollection("listeclientsvalides");
        mongoCollectionDelete =mongoDatabase.getCollection("listClientAffectes");
        mongoCollectionClient =mongoDatabase.getCollection("listeclients");



        // find numero compteur
        Document queryFilter  = new Document("ref",recieve.getStringExtra("REF"));
        mongoCollectionClient.findOne(queryFilter).getAsync(task -> {
            if (task.isSuccess()) {
                Document doc1= task.get();

            numCompterInserter =recieve.getStringExtra("num_compteur");


                Log.v("EXAMPLE", "successfully found a document ");
            } else {
                Log.e("EXAMPLE", "failed to find document with: ", task.getError());
            }
        });


        valider_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                   if (numcopmteur.getText().toString().equals(numCompterInserter)){

                                Document doc1 = new Document().append("ref", recieve.getStringExtra("REF"))
                                        .append("name", recieve.getStringExtra("NOM CLIENT"))
                                        .append("adresse", "alger")
                                        .append("Date", "20022")
                                        .append("num_compteur", numcopmteur.getText().toString())
                                        .append("nouveau_index", nouveauindex.getText().toString())
                                        .append("ancien_index", ancienindex.getText().toString())
                                        .append("totalt1", tottarif1.getText().toString())
                                        .append("totalt2", tottarif2.getText().toString())
                                        .append("totalt3", tottarif3.getText().toString())
                                        .append("pma", pma.getText().toString())
                                        .append("ere", ere.getText().toString())
                                        .append("eri", eri.getText().toString())
                                        .append("a", "");

                                mongoCollectionInsert.insertOne(doc1).getAsync(result -> {
                                    if (result.isSuccess()) {
                                        Toast.makeText(validerclient.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                                        Log.v("Data", "Data Inserted Successfully");


                                        Document queryFilterupdate = new Document()
                                                .append("num_copmteur", numcopmteur.getText().toString());


                                        Document queryFilterupdated = new Document().append("validation", true);

                                        mongoCollectionClient.updateOne(queryFilterupdate, queryFilterupdated)
                                                .getAsync(task -> {
                                                    if (task.isSuccess()) {
                                                        long count = task.get().getModifiedCount();
                                                        if (count == 1) {
                                                            Log.v("EXAMPLE", "successfully updated a document.");
                                                        } else {
                                                            Log.v("EXAMPLE", "did not update a document.");
                                                        }
                                                    } else {
                                                        Log.e("EXAMPLE", "failed to update document with: ", task.getError());
                                                    }
                                                });


                                    } else {
                                        Toast.makeText(validerclient.this, "Error:" + result.getError().toString(), Toast.LENGTH_SHORT).show();
                                        Log.v("Data", "Error:" + result.getError().toString());
                                    }


                                });
                 }else{
                       Toast.makeText(validerclient.this, "numero de compteur est faux ", Toast.LENGTH_SHORT).show();
                   }
            }
        });



        signaler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opendialogue(recieve);
            }
        });


        restohome5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(validerclient.this, Principal_activity.class);
                startActivity(intent);
            }
        });

        adressevc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String location = adressevc.getText().toString();
                openGoogleMaps(location);

            }
        });

   }

    private void openGoogleMaps(String location) {
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(location));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");

        // Check if there is an app to handle the intent
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(mapIntent, 0);
        boolean isIntentSafe = activities.size() > 0;

        if (isIntentSafe) {
            startActivity(mapIntent);
        } else {
            Toast.makeText(this, "Google Maps is not installed", Toast.LENGTH_SHORT).show();
        }
    }

   private void opendialogue(Intent recieve) {

       String string1 = recieve.getStringExtra("REF");
       String string2 = recieve.getStringExtra("NOM CLIENT");
       String string3 = recieve.getStringExtra("ADRESSE");
       String string4 =recieve.getStringExtra("num_compteur");
       String string5 = recieve.getStringExtra("Date");

       signaler_problem_dialogue signaler_problem_dialogue = new signaler_problem_dialogue().newInstance(string1, string2, string3, string4,string5);
       signaler_problem_dialogue.show(getSupportFragmentManager(),"signaler");

    }
}