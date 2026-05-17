package com.example.sge.controller;

import com.example.sge.model.Etudiant;
import com.example.sge.model.Module;
import com.example.sge.model.Note;
import com.example.sge.service.EtudiantService;
import com.example.sge.service.ModuleService;
import com.example.sge.service.NoteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class WebViewController {

    @Autowired
    private EtudiantService etudiantService;

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private NoteService noteService;

    // =========================
    // AFFICHER LISTE ETUDIANTS
    // =========================
    @GetMapping(value = "/etudiants", produces = MediaType.TEXT_HTML_VALUE)
    public String listerEtudiants(Model model) {
        model.addAttribute("etudiants", etudiantService.listerTous());
        return "etudiants/liste";
    }

    // =========================
    // AFFICHER LISTE MODULES
    // =========================
    @GetMapping(value = "/modules", produces = MediaType.TEXT_HTML_VALUE)
    public String listerModules(Model model) {
        model.addAttribute("modules", moduleService.listerTous());
        return "modules/liste";
    }

    // =========================
    // AFFICHER LISTE NOTES
    // =========================
    @GetMapping(value = "/notes", produces = MediaType.TEXT_HTML_VALUE)
    public String listerNotes(Model model) {
        model.addAttribute("notes", noteService.listerTous());
        return "notes/liste";
    }

    // =========================
    // FORMULAIRE AJOUT NOTE
    // =========================
    @GetMapping(value = "/notes/form", produces = MediaType.TEXT_HTML_VALUE)
    public String afficherFormulaireNote(Model model) {
        model.addAttribute("note", new Note());
        model.addAttribute("etudiants", etudiantService.listerTous());
        model.addAttribute("modules", moduleService.listerTous());
        return "notes/form";
    }

    @PostMapping(value = "/notes", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String creerNote(Note note) {
        if (note.getEtudiant() != null && note.getEtudiant().getId() != null) {
            Etudiant etudiant = etudiantService.trouverParId(note.getEtudiant().getId())
                    .orElseThrow(() -> new RuntimeException("Étudiant introuvable"));
            note.setEtudiant(etudiant);
        }

        if (note.getModule() != null && note.getModule().getId() != null) {
            Module module = moduleService.trouverParId(note.getModule().getId())
                    .orElseThrow(() -> new RuntimeException("Module introuvable"));
            note.setModule(module);
        }

        noteService.ajouterNote(note);
        return "redirect:/notes";
    }

    // =========================
    // LOGIN FORM
    // =========================
    @GetMapping(value = "/auth/login", produces = MediaType.TEXT_HTML_VALUE)
    public String loginPage() {
        return "auth/login";
    }
}
