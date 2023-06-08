package com.example.sonelgazp2.acitivities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sonelgazp2.R;

import java.util.concurrent.atomic.AtomicReference;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoDatabase;

public class login extends AppCompatActivity {

    Button seconnecter;
    String appId = "sonalgazep-jjill";

    EditText nomutilisateur,motdepasse;
    Credentials credentials;
    App app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        app= new App(new AppConfiguration.Builder(appId).build());



        seconnecter=findViewById(R.id.seconnecter);
        nomutilisateur=findViewById(R.id.nomutilisateur);
        motdepasse=findViewById(R.id.motdepasse);





        seconnecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom_utilisateur = nomutilisateur.getText().toString();
                String password =motdepasse.getText().toString() ;
                credentials = Credentials.emailPassword(nom_utilisateur,password);
                if(nom_utilisateur.isEmpty() || password.isEmpty()){
                    Toast.makeText(login.this, "l'email ou le mot de passe est vide", Toast.LENGTH_LONG).show();
                }
                else{
                    // Save user credentials in SharedPreferences
                    SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("username", nom_utilisateur);
                    editor.putString("token", password);
                    editor.apply();


                    app.loginAsync(credentials, new App.Callback<User>() {
                        @Override
                        public void onResult(App.Result<User> result) {
                            Log.e("AUTH", "credentials ");
                            if (result.isSuccess()) {
                                nomutilisateur.setText("");
                                motdepasse.setText("");
                                Log.e("AUTH", "successful");
                                Intent intent=new Intent(login.this, Principal_activity.class);
                                String name =nomutilisateur.getText().toString();
                                intent.putExtra("name",name);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(login.this, "somethings is wrong", Toast.LENGTH_LONG).show();
                                Log.e("AUTH", result.getError().toString());
                            }
                        }
                    });
                }


            }
        });
    }
}
