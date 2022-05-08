package fr.martin.weparty;


import com.google.firebase.firestore.Exclude;

public class Evenement {
    @Exclude
    private int id =0;
    private String nom;
    private String description;
    private String date;
    private String lieu;


    public Evenement(String nom, String description, String date, String lieu) {
        this.nom = nom;
        this.description = description;
        this.date = date;
        this.lieu = lieu;
    }

    public int getId() {
        return id;
    }

    @Exclude
    public void setId(int id) {
        this.id = id;
    }

    public String getNomEvenement() {
        return nom;
    }

    public void setnomEvenement(String nom) {
        this.nom = nom;
    }

    public String getdescEvenement() {
        return description;
    }

    public void setdescEvenement(String description) {
        this.description = description;
    }

    public String getdateEvenement() {
        return date;
    }

    public void setdateEvenement(String date) {
        this.date = date;
    }

    public String getlieuEvenement() {
        return lieu;
    }

    public void setlieuEvenement(String lieu) {
        this.lieu = lieu;
    }
}
