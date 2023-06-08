package com.example.sonelgazp2.acitivities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.sonelgazp2.R;

import org.bson.Document;

import java.util.ArrayList;

import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;

public class signaler_problem_dialogue extends AppCompatDialogFragment {
    private EditText  editTextTextMultiLine;
    private ImageView ImageButton;
    private TextView titre;

    private Button ajouter_photo,buttn;
    private RadioGroup myRadioGroup;
    private ImageView go;
    private ActivityResultLauncher<String> imagePickerLauncher;
    private final int GALLERY_REQ_CODE=1000;
    String appId = "sonalgazep-jjill";
    MongoDatabase mongoDatabase;
    MongoClient mongoClient;
    MongoCollection<Document> mongoCollection;
    App app;
    ArrayList<Boolean>a;
    String x;
    String REF,NOM_CLIENT,ADRESSE,num_compteur,Date;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

         REF = getArguments().getString("REF");
         NOM_CLIENT = getArguments().getString("NOM CLIENT");
         ADRESSE = getArguments().getString("ADRESSE");
         num_compteur = getArguments().getString("num_compteur");
         Date = getArguments().getString("Date");


        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        LayoutInflater inflater= getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialogue,null);
        builder.setView(view);

        buttn = (Button) view.findViewById(R.id.buttn);

        editTextTextMultiLine = (EditText) view.findViewById(R.id.editTextTextMultiLine);
        myRadioGroup = view.findViewById(R.id.myRadioGroup);
        ajouter_photo = view.findViewById(R.id.ajouter_photo);
        go = view.findViewById(R.id.go);
        a = new ArrayList<>();

        app= new App(new AppConfiguration.Builder(appId).build());





         User user = app.currentUser();
         mongoClient = user.getMongoClient("mongodb-atlas");
         mongoDatabase = mongoClient.getDatabase("SONALGAZ");
         mongoCollection=mongoDatabase.getCollection("listeclientsigs");

        buttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Document doc1=new Document().append("description",editTextTextMultiLine.getText().toString())
                        .append("Type", x)
                        .append("ref",REF)
                        .append("name",NOM_CLIENT)
                        .append("adresse",ADRESSE)
                        .append("Date",Date)
                        .append("num_compteur",num_compteur);

                mongoCollection.insertOne(doc1).getAsync(result -> {
                    if(result.isSuccess())
                    {
                        Toast.makeText(getActivity(), "Data Inserted Successfully", Toast.LENGTH_SHORT).show();

                        Log.v("Data","Data Inserted Successfully");
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                        Log.v("Data","Error:"+result.getError().toString());
                    }
                });

            }
        });


        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        go.setImageURI(result);
                    }
                });

        ajouter_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePickerLauncher.launch("image/*");

            }
        });

        myRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Perform actions based on the selected RadioButton
                switch (checkedId) {
                    case R.id.checkBox1:

                         x ="PAS D'INDEX.";

                        break;
                    case R.id.checkBox2:
                         x ="CONSOMATION NULLE.";
                        break;
                    // Handle other RadioButton options if needed
                    case R.id.checkBox3:
                         x ="PAS D'ATTEIN.";
                        break;
                }
            }
        });

        return builder.create();

    }


    public static signaler_problem_dialogue newInstance(String string1, String string2, String string3, String string4,String string5) {
        signaler_problem_dialogue fragment = new signaler_problem_dialogue();
        Bundle args = new Bundle();
        args.putString("REF", string1);
        args.putString("NOM CLIENT", string2);
        args.putString("ADRESSE", string3);
        args.putString("num_compteur", string4);
        args.putString("Date", string5);

        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
