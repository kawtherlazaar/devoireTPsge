package com.example.sge.controller;

import com.example.sge.model.Note;
import com.example.sge.service.NoteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/notes", produces = MediaType.APPLICATION_JSON_VALUE)
public class NoteController {

    @Autowired
    private NoteService noteService;

    // =========================
    // AJOUTER NOTE
    // =========================
    @PostMapping
    public ResponseEntity<Note> ajouter(
            @RequestBody Note note) {

        return ResponseEntity.status(201)
                .body(noteService.ajouterNote(note));
    }

    // =========================
    // NOTES PAR ETUDIANT
    // =========================
    @GetMapping("/etudiant/{id}")
    public List<Note> notesEtudiant(
            @PathVariable Long id) {

        return noteService.listerParEtudiant(id);
    }

    // =========================
    // NOTES PAR MODULE
    // =========================
    @GetMapping("/module/{id}")
    public List<Note> notesModule(
            @PathVariable Long id) {

        return noteService.listerParModule(id);
    }

    // =========================
    // MOYENNE
    // =========================
    @GetMapping("/moyenne/{id}")
    public Double moyenne(
            @PathVariable Long id) {

        return noteService.calculerMoyenne(id);
    }

    // =========================
    // MODIFIER NOTE
    // =========================
    @PutMapping("/{id}")
    public Note modifier(
            @PathVariable Long id,
            @RequestBody Note note) {

        return noteService.modifier(id, note);
    }

    // =========================
    // SUPPRIMER NOTE
    // =========================
    @DeleteMapping("/{id}")
    public void supprimer(
            @PathVariable Long id) {

        noteService.supprimer(id);
    }
}