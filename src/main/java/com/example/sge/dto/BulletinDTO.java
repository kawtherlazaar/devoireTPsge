package com.example.sge.dto;

import com.example.sge.model.Etudiant;
import com.example.sge.model.Note;

import java.util.List;

public class BulletinDTO {

    private Etudiant etudiant;

    private List<Note> notes;

    private double moyenneGenerale;

    private String mention;

    private boolean admis;


    // =========================
    // CONSTRUCTEURS
    // =========================

    public BulletinDTO() {
    }

    public BulletinDTO(Etudiant etudiant, List<Note> notes,
                       double moyenneGenerale, String mention,
                       boolean admis) {
        this.etudiant = etudiant;
        this.notes = notes;
        this.moyenneGenerale = moyenneGenerale;
        this.mention = mention;
        this.admis = admis;
    }


    // =========================
    // GETTERS & SETTERS
    // =========================

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public double getMoyenneGenerale() {
        if (notes == null || notes.isEmpty()) {
            return 0.0; // pas de notes
        }

        double somme = 0.0;
        int totalCoeff = 0;

        for (Note note : notes) {
            if (note.getModule() != null) {
                somme += note.getValeur() * note.getModule().getCoefficient();
                totalCoeff += note.getModule().getCoefficient();
            }
        }

        return totalCoeff > 0 ? somme / totalCoeff : 0.0;
    }


    public void setMoyenneGenerale(double moyenneGenerale) {
        this.moyenneGenerale = moyenneGenerale;
    }

    public String getMention() {
        return mention;
    }

    public int getTotalCoefficient() {
        if (notes == null) {
            return 0;
        }
        return notes.stream()
                .mapToInt(note -> note.getModule() != null ? note.getModule().getCoefficient() : 0)
                .sum();
    }

    public double getTotalSomme() {
        if (notes == null) {
            return 0.0;
        }
        return notes.stream()
                .mapToDouble(note -> note.getModule() != null ? note.getValeur() * note.getModule().getCoefficient() : 0.0)
                .sum();
    }

    public void setMention(String mention) {
        this.mention = mention;
    }

    public boolean isAdmis() {
        return admis;
    }

    public void setAdmis(boolean admis) {
        this.admis = admis;
    }
}