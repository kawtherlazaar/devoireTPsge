package com.example.sge.service;

import com.example.sge.Repositories.EtudiantRepository;
import com.example.sge.Repositories.FiliereRepository;

import com.example.sge.event.InscriptionEvent;

import com.example.sge.model.Etudiant;
import com.example.sge.model.Filiere;
import com.example.sge.model.Module;

import org.springframework.context.ApplicationEventPublisher;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EtudiantService {

    private final EtudiantRepository etudiantRepository;
    private final FiliereRepository filiereRepository;

    private final ApplicationEventPublisher publisher;

    public EtudiantService(
            EtudiantRepository etudiantRepository,
            FiliereRepository filiereRepository,
            ApplicationEventPublisher publisher
    ) {

        this.etudiantRepository = etudiantRepository;
        this.filiereRepository = filiereRepository;
        this.publisher = publisher;
    }

    // =========================
    // CREATE
    // =========================

    public Etudiant ajouter(Etudiant e) {

        // récupérer filière complète depuis DB
        if (e.getFiliere() != null &&
                e.getFiliere().getId() != null) {

            Filiere filiere = filiereRepository
                    .findById(e.getFiliere().getId())
                    .orElseThrow(() ->
                            new RuntimeException("Filière introuvable")
                    );

            e.setFiliere(filiere);
        }

        Etudiant saved = etudiantRepository.save(e);

        // EVENT
        if (saved.getFiliere() != null) {

            publisher.publishEvent(

                    new InscriptionEvent(
                            this,
                            saved.getNom() + " " + saved.getPrenom(),
                            saved.getFiliere().getNom()
                    )
            );
        }

        return saved;
    }

    // =========================
    // READ ALL
    // =========================

    public List<Etudiant> listerTous() {

        return etudiantRepository.findAll();
    }

    // =========================
    // READ BY ID
    // =========================

    public Optional<Etudiant> trouverParId(Long id) {

        return etudiantRepository.findById(id);
    }

    // =========================
    // DELETE
    // =========================

    public void supprimer(Long id) {

        etudiantRepository.deleteById(id);
    }

    // =========================
    // UPDATE
    // =========================

    public Etudiant modifier(Long id, Etudiant e) {

        Optional<Etudiant> existing =
                etudiantRepository.findById(id);

        if (existing.isPresent()) {

            Etudiant etu = existing.get();

            etu.setNom(e.getNom());
            etu.setPrenom(e.getPrenom());
            etu.setEmail(e.getEmail());
            etu.setCin(e.getCin());
            etu.setDateNaissance(e.getDateNaissance());
            etu.setGroupe(e.getGroupe());
            etu.setMoyenne(e.getMoyenne());

            // récupérer filière complète
            if (e.getFiliere() != null &&
                    e.getFiliere().getId() != null) {

                Filiere filiere = filiereRepository
                        .findById(e.getFiliere().getId())
                        .orElseThrow(() ->
                                new RuntimeException("Filière introuvable")
                        );

                etu.setFiliere(filiere);
            }

            return etudiantRepository.save(etu);
        }

        return null;
    }

    // =========================
    // SEARCH
    // =========================

    public List<Etudiant> rechercherParNom(String nom) {

        return etudiantRepository
                .findByNomContainingIgnoreCase(nom);
    }

    public List<Etudiant> trouverEtudiantParGroupe(String groupe) {

        return etudiantRepository.findByGroupe(groupe);
    }

    public List<Etudiant> trouverAdmis(double seuil) {

        return etudiantRepository
                .findByMoyenneGreaterThanEqual(seuil);
    }

    public List<Etudiant> trouverMeilleurs(double seuil) {

        return etudiantRepository.findMeilleurs(seuil);
    }

    public Etudiant trouverTop() {

        return etudiantRepository
                .findTopByOrderByMoyenneDesc();
    }

    public List<Etudiant> trouverEtudiantParModule(Module module) {

        return etudiantRepository.findByModule(module);
    }

    // =========================
    // PAGINATION
    // =========================

    public Page<Etudiant> listerAvecPagination(
            int page,
            int size,
            String sortBy
    ) {

        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        Sort.by(sortBy)
                );

        return etudiantRepository.findAll(pageable);
    }
}