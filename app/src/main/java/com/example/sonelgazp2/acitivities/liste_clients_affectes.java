package com.example.sonelgazp2.acitivities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class liste_clients_affectes extends AppCompatActivity implements RecyclerViewInterface {
    User user;
    MongoClient mongoClient;
    MongoDatabase mongoDatabase;
    MongoCollection<Document> mongoCollection;
    App app;
    RecyclerView rvrestant;
    ImageView restohome3;
    String appId = "sonalgazep-jjill";
    ArrayList<client> clients;
    tout_client_adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_clients_affectes);
        restohome3= findViewById(R.id.restohome3);
        rvrestant = findViewById(R.id.rvrestant);
        clients= new ArrayList<client>();
        adapter = new tout_client_adapter(this,clients);

        app= new App(new AppConfiguration.Builder(appId).build());

         user = app.currentUser();
         mongoClient = user.getMongoClient("mongodb-atlas");
         mongoDatabase = mongoClient.getDatabase("SONALGAZ");
         mongoCollection=mongoDatabase.getCollection("listeclients");

         rvrestant.setLayoutManager(new LinearLayoutManager(this));
         rvrestant.setAdapter(adapter);


        Document queryFilter  = new Document().append("a", "").append("validation",true);
        RealmResultTask<MongoCursor<Document>> findTask = mongoCollection.find(queryFilter).iterator();
        findTask.getAsync(task -> {
            if (task.isSuccess()) {
                MongoCursor<Document> results = task.get();
                Log.e("EXAMPLE_retrieve", "failed to find documents with: ", task.getError());

                while (results.hasNext()) {
                    Document doc = results.next();

                    clients.add(new client(doc.getString("ref"),doc.getString("name"),doc.getString("adresse"),doc.getString("num_compteur"),doc.getString("Date") ));
                    Log.v("size_in_find", "name :"+clients.size());

                }
                adapter.notifyDataSetChanged();
                Log.v("size_in_find1", "name :"+clients.size());
            } else {
                Log.e("EXAMPLE_retrieve", "failed to find documents with: "+ task.getError());
            }
        });


        RealmEventStreamAsyncTask<Document> watcher = mongoCollection.watchAsync();
        watcher.get(result -> {
            if (result.isSuccess()) {

                Document queryFilter1  = new Document("a", "").append("validation",true);
                RealmResultTask<MongoCursor<Document>> findTask1 = mongoCollection.find(queryFilter1).iterator();
                Handler handler = new Handler(Looper.getMainLooper());

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        app= new App(new AppConfiguration.Builder(appId).build());
                        user = app.currentUser();
                        mongoClient = user.getMongoClient("mongodb-atlas");
                        mongoDatabase = mongoClient.getDatabase("SONALGAZ");
                        mongoCollection=mongoDatabase.getCollection("listeclients");

                        findTask1.getAsync(task -> {
                            if (task.isSuccess()) {
                                MongoCursor<Document> results = task.get();
                                Log.v("EXAMPLE_find", "successfully found all plants for Store 42:");
                                clients.clear();
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


        Log.v("EXAMPLE_watcher", "done ");

        restohome3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(liste_clients_affectes.this, Principal_activity.class);
                startActivity(intent);
            }
        });
  }

    @Override
    public void onItemClick(int postion) {
        Intent intent=new Intent(liste_clients_affectes.this, validerclient.class);
        intent.putExtra("REF",clients.get(postion).getReference());
        intent.putExtra("NOM CLIENT",clients.get(postion).getNom_client());
        intent.putExtra("num_compteur",clients.get(postion).getNom_client());
        intent.putExtra("Date",clients.get(postion).getNom_client());
        intent.putExtra("ADRESSE",clients.get(postion).getAdresse());
        startActivity(intent);
        finish();

    }
}