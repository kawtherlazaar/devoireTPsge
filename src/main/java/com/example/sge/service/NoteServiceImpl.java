package com.example.sge.service;

import com.example.sge.Repositories.EtudiantRepository;
import com.example.sge.Repositories.NoteRepository;
import com.example.sge.dto.BulletinDTO;
import com.example.sge.model.Etudiant;
import com.example.sge.model.Note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private EtudiantRepository etudiantRepository;

    // =========================
    // LISTER TOUTES NOTES
    // =========================
    @Override
    public List<Note> listerTous() {

        return noteRepository.findAll();
    }

    // =========================
    // AJOUTER NOTE
    // =========================
    @Override
    public Note ajouterNote(Note note) {

        // Validation : valeur doit être entre 0 et 20
        if (note.getValeur() < 0 || note.getValeur() > 20) {
            throw new RuntimeException("La note doit être comprise entre 0 et 20");
        }

        Note savedNote = noteRepository.save(note);

        // Mettre à jour la moyenne de l'étudiant
        if (note.getEtudiant() != null) {
            mettreAJourMoyenne(note.getEtudiant().getId());
        }

        return savedNote;
    }

    // =========================
    // NOTES PAR ETUDIANT
    // =========================
    @Override
    public List<Note> listerParEtudiant(Long etudiantId) {

        return noteRepository.findByEtudiantId(etudiantId);
    }

    // =========================
    // NOTES PAR MODULE
    // =========================
    @Override
    public List<Note> listerParModule(Long moduleId) {

        return noteRepository.findByModuleId(moduleId);
    }

    // =========================
    // CALCULER MOYENNE
    // =========================
    @Override
    public Double calculerMoyenne(Long etudiantId) {

        Double moyenne = noteRepository.calculerMoyenne(etudiantId);
        return moyenne != null ? moyenne : 0.0;
    }

    // =========================
    // METTRE A JOUR MOYENNE
    // =========================
    @Override
    public void mettreAJourMoyenne(Long etudiantId) {

        Etudiant etudiant = etudiantRepository
                .findById(etudiantId)
                .orElse(null);

        if (etudiant != null) {
            double moyenne = calculerMoyenne(etudiantId);
            etudiant.setMoyenne(moyenne);
            etudiantRepository.save(etudiant);
        }
    }

    // =========================
    // MODIFIER NOTE
    // =========================
    @Override
    public Note modifier(Long id, Note note) {

        // Validation : valeur doit être entre 0 et 20
        if (note.getValeur() < 0 || note.getValeur() > 20) {
            throw new RuntimeException("La note doit être comprise entre 0 et 20");
        }

        Note ancienneNote =
                noteRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Note introuvable"));

        ancienneNote.setValeur(note.getValeur());
        ancienneNote.setEtudiant(note.getEtudiant());
        ancienneNote.setModule(note.getModule());

        Note savedNote = noteRepository.save(ancienneNote);

        // Mettre à jour la moyenne de l'étudiant
        if (note.getEtudiant() != null) {
            mettreAJourMoyenne(note.getEtudiant().getId());
        }

        return savedNote;
    }

    // =========================
    // SUPPRIMER NOTE
    // =========================
    @Override
    public void supprimer(Long id) {

        Note note = noteRepository.findById(id).orElse(null);

        if (note != null) {
            Long etudiantId = note.getEtudiant().getId();
            noteRepository.deleteById(id);

            // Mettre à jour la moyenne de l'étudiant
            mettreAJourMoyenne(etudiantId);
        }
    }

    // =========================
    // CALCULER MENTION
    // =========================
    private String calculerMention(double moyenne) {

        if (moyenne >= 16) {
            return "Excellent";
        } else if (moyenne >= 14) {
            return "Très bien";
        } else if (moyenne >= 12) {
            return "Bien";
        } else if (moyenne >= 10) {
            return "Passable";
        } else {
            return "Ajourné";
        }
    }

    // =========================
    // GENERER BULLETIN
    // =========================
    @Override
    public BulletinDTO genererBulletin(Etudiant etudiant) {

        List<Note> notes =
                noteRepository.findByEtudiant(etudiant);

        double moyenneGenerale = calculerMoyenne(etudiant.getId());

        String mention = calculerMention(moyenneGenerale);

        BulletinDTO bulletin = new BulletinDTO();

        bulletin.setEtudiant(etudiant);
        bulletin.setNotes(notes);
        bulletin.setMoyenneGenerale(moyenneGenerale);
        bulletin.setMention(mention);

        // ADMISSION
        bulletin.setAdmis(moyenneGenerale >= 10);

        return bulletin;
    }
}