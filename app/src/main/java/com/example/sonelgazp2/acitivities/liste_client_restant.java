package com.example.sonelgazp2.acitivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.sonelgazp2.R;
import com.example.sonelgazp2.adapters.RecyclerViewInterface;
import com.example.sonelgazp2.adapters.tout_client_adapter;
import com.example.sonelgazp2.bone.client;

import org.bson.Document;

import java.util.ArrayList;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.RealmEventStreamAsyncTask;
import io.realm.mongodb.RealmResultTask;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;
import io.realm.mongodb.mongo.iterable.MongoCursor;

public class liste_client_restant extends AppCompatActivity implements RecyclerViewInterface {

    App app;
    RecyclerView rcvRestant;
    ImageView restohome2;
    ArrayList<client> clients;
    tout_client_adapter adapter;
    String appId = "sonalgazep-jjill";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_client_restant);
        restohome2= findViewById(R.id.restohome2);
        rcvRestant= findViewById(R.id.rcvRestant);
        rcvRestant = findViewById(R.id.rcvRestant);
        clients= new ArrayList<client>();
        app= new App(new AppConfiguration.Builder(appId).build());

        adapter = new tout_client_adapter(this,clients);


        User user = app.currentUser();
        MongoClient mongoClient = user.getMongoClient("mongodb-atlas");
        MongoDatabase mongoDatabase = mongoClient.getDatabase("SONALGAZ");
        MongoCollection<Document> mongoCollection=mongoDatabase.getCollection("listeclients");





        rcvRestant.setLayoutManager(new LinearLayoutManager(this));
        tout_client_adapter adapter = new tout_client_adapter(this,clients);
        rcvRestant.setAdapter(adapter);


        Document queryFilter  = new Document("a", "").append("validation",false);
        RealmResultTask<MongoCursor<Document>> findTask = mongoCollection.find(queryFilter).iterator();
        findTask.getAsync(task -> {
            if (task.isSuccess()) {
                MongoCursor<Document> results = task.get();

                while (results.hasNext()) {
                    Document doc = results.next();

                    clients.add(new client(doc.getString("ref"),doc.getString("name"),doc.getString("adresse"),doc.getString("num_compteur"),doc.getString("Date") ));
                    Log.v("size1", "name :"+clients.size());

                }
                adapter.notifyDataSetChanged();
                Log.v("workplz", "name :"+clients.size());
            } else {
                Log.e("EXAMPLE_find", "failed to find documents with: ", task.getError());
            }
        });


        RealmEventStreamAsyncTask<Document> watcher = mongoCollection.watchAsync();
        watcher.get(result -> {
            if (result.isSuccess()) {

                Document queryFilter1  = new Document("a", "").append("validation",false);
                RealmResultTask<MongoCursor<Document>> findTask1 = mongoCollection.find(queryFilter1).iterator();
                Handler handler = new Handler(Looper.getMainLooper());

                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        findTask1.getAsync(task -> {
                            if (task.isSuccess()) {
                                MongoCursor<Document> results = task.get();
                                Log.v("EXAMPLE_find", "successfully found all plants for Store 42:");
                                while (results.hasNext()) {
                                    Document doc = results.next();

                                    Log.v("workplz", "name :"+doc.getString("name"));
                                    clients.add(new client(doc.getString("ref"),doc.getString("name"),doc.getString("adresse"),doc.getString("num_compteur"),doc.getString("Date") ));
                                    Log.v("size1", "name :"+clients.size());

                                }
                                adapter.notifyDataSetChanged();
                                Log.v("workplz", "name :"+clients.size());
                            } else {
                                Log.e("EXAMPLE_find", "failed to find documents with: ", task.getError());
                            }
                        });
                    }
                });

            }
            else {
                Log.e("EXAMPLE_watcher", "failed to subscribe to changes in the collection with : ",
                        result.getError());
            }
        });



        restohome2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(liste_client_restant.this, validerclient.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onItemClick(int postion) {

        Intent intent=new Intent(liste_client_restant.this, validerclient.class);
        intent.putExtra("REF",clients.get(postion).getReference());
        intent.putExtra("NOM CLIENT",clients.get(postion).getNom_client());
        intent.putExtra("ADRESSE",clients.get(postion).getAdresse());
        startActivity(intent);
        finish();


    }
}