package com.example.sonelgazp2.bone;
import org.bson.types.ObjectId;

import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;

public class client {


    private String reference;
    private String nom_client;
    private String adresse;
    private String Ancien_index,Date, Nouveau_index;

    public client(String reference, String nom_client, String adresse, String ancien_index, String date, String nouveau_index) {
        this.reference = reference;
        this.nom_client = nom_client;
        this.adresse = adresse;
        Ancien_index = ancien_index;
        Date = date;
        Nouveau_index = nouveau_index;
    }
    public client(String reference, String nom_client, String adresse, String ancien_index, String date) {
        this.reference = reference;
        this.nom_client = nom_client;
        this.adresse = adresse;
        Ancien_index = ancien_index;
        Date = date;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getNom_client() {
        return nom_client;
    }

    public void setNom_client(String nom_client) {
        this.nom_client = nom_client;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getAncien_index() {
        return Ancien_index;
    }

    public void setAncien_index(String ancien_index) {
        Ancien_index = ancien_index;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getNouveau_index() {
        return Nouveau_index;
    }

    public void setNouveau_index(String nouveau_index) {
        Nouveau_index = nouveau_index;
    }


}
