package com.example.sge.Repositories;

import com.example.sge.model.Etudiant;
import com.example.sge.model.Note;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoteRepository
        extends JpaRepository<Note, Long> {

    // =========================
    // NOTES PAR ETUDIANT ID
    // =========================
    List<Note> findByEtudiantId(Long etudiantId);

    // =========================
    // NOTES PAR MODULE ID
    // =========================
    List<Note> findByModuleId(Long moduleId);

    // =========================
    // NOTES PAR ETUDIANT
    // =========================
    List<Note> findByEtudiant(Etudiant etudiant);

    // =========================
    // MOYENNE
    // =========================
    @Query("""
           SELECT SUM(n.valeur * n.module.coefficient) / SUM(n.module.coefficient)
           FROM Note n
           WHERE n.etudiant.id = :id
           """)
    Double calculerMoyenne(
            @Param("id") Long etudiantId
    );
}