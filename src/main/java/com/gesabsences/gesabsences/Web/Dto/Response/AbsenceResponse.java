package com.gesabsences.gesabsences.Web.Dto.Response;

public class AbsenceResponse {
    private String nom;
    private String classe;
    private String date;
    private String matiere;
    private String type;
    private String heureArrivee;
    private String heureDebut;
    private boolean justifiee;

    public AbsenceResponse(String nom, String classe, String date, String matiere, String type,
                           String heureArrivee, String heureDebut, boolean justifiee) {
        this.nom = nom;
        this.classe = classe;
        this.date = date;
        this.matiere = matiere;
        this.type = type;
        this.heureArrivee = heureArrivee;
        this.heureDebut = heureDebut;
        this.justifiee = justifiee;
    }

    
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getClasse() { return classe; }
    public void setClasse(String classe) { this.classe = classe; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getMatiere() { return matiere; }
    public void setMatiere(String matiere) { this.matiere = matiere; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getHeureArrivee() { return heureArrivee; }
    public void setHeureArrivee(String heureArrivee) { this.heureArrivee = heureArrivee; }

    public String getHeureDebut() { return heureDebut; }
    public void setHeureDebut(String heureDebut) { this.heureDebut = heureDebut; }

    public boolean isJustifiee() { return justifiee; }
    public void setJustifiee(boolean justifiee) { this.justifiee = justifiee; }
}
