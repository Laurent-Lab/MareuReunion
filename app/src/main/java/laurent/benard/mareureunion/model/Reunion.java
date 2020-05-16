package laurent.benard.mareureunion.model;

import android.widget.ImageView;

public class Reunion {

    private int color;
    private String heure;
    private String lieu;
    private String sujet;
    private String participants;
    private String date;

    /**
     * Constructeur
     * @param heure
     * @param lieu
     * @param sujet
     * @param participants
     * @param date
     */
    public Reunion(int color, String heure, String lieu, String sujet, String participants, String date) {
        this.color = color;
        this.heure = heure;
        this.lieu = lieu;
        this.sujet = sujet;
        this.participants = participants;
        this.date = date;
    }

    public int getColor(){return color;}

    public String getHeure() {
        return heure;
    }

    public String getLieu() {
        return lieu;
    }

    public String getSujet() {
        return sujet;
    }

    public String getParticipants() { return participants; }

    public String getDate() {
        return date;
    }
}
