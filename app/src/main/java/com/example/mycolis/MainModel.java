package com.example.mycolis;

public class MainModel {
    String name,adresse,turl,prix;
    Integer  id;
MainModel(){

}
    public MainModel(String name, String adresse, String turl, Integer id, String prix) {
        this.name = name;
        this.adresse = adresse;
        this.turl = turl;
        this.id = id;
        this.prix = prix;
    }

    public String getName() {
        return name;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getTurl() {
        return turl;
    }

    public Integer getId() {
        return id;
    }

    public String getPrix() {
        return prix;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setTurl(String turl) {
        this.turl = turl;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }
}
