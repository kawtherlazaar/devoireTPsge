package com.example.sge.service;

import com.example.sge.dto.BulletinDTO;
import com.example.sge.model.Etudiant;
import com.example.sge.model.Note;

import java.util.List;

public interface NoteService {

    // Lister toutes les notes
    List<Note> listerTous();

    // Ajouter note
    Note ajouterNote(Note note);

    // Liste notes par étudiant
    List<Note> listerParEtudiant(Long etudiantId);

    // Liste notes par module
    List<Note> listerParModule(Long moduleId);

    // Calcul moyenne étudiant
    Double calculerMoyenne(Long etudiantId);

    // Mettre à jour moyenne étudiant
    void mettreAJourMoyenne(Long etudiantId);

    // Modifier note
    Note modifier(Long id, Note note);

    // Supprimer note
    void supprimer(Long id);

    // Générer bulletin
    BulletinDTO genererBulletin(Etudiant etudiant);
}