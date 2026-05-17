package com.example.sge.controller;

import com.example.sge.dto.BulletinDTO;
import com.example.sge.model.Etudiant;
import com.example.sge.Repositories.EtudiantRepository;
import com.example.sge.service.NoteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/bulletins")
public class BulletinController {

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private NoteService noteService;

    // =========================
    // LISTE DES BULLETINS
    // =========================
    @GetMapping
    public String liste(Model model) {

        List<Etudiant> etudiants = etudiantRepository.findAll();

        List<BulletinDTO> bulletins = new ArrayList<>();

        for (Etudiant e : etudiants) {
            bulletins.add(noteService.genererBulletin(e));
        }

        model.addAttribute("bulletins", bulletins);

        return "bulletins/liste";
    }

    // =========================
    // DETAIL BULLETIN
    // =========================
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {

        Etudiant etudiant = etudiantRepository
                .findById(id)
                .orElse(null);

        if (etudiant == null) {
            model.addAttribute("message", "Étudiant introuvable");
            return "error";
        }

        BulletinDTO bulletin = noteService.genererBulletin(etudiant);

        model.addAttribute("bulletin", bulletin);

        return "bulletins/detail";
    }
}