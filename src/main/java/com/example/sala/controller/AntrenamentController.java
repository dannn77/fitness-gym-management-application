/** Clasă pentru crearea, editarea, salvarea și ștergerea antrenamentelor.
 * @author Șerban Dan-Adrian
 * @version 11 Ianuarie 2026
 */


package com.example.sala.controller;

import com.example.sala.model.Antrenament;
import com.example.sala.repository.AntrenorRepository;
import com.example.sala.repository.SalaRepository;
import com.example.sala.service.AntrenamentService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/antrenamente")
public class AntrenamentController {

    private final AntrenamentService service;
    private final SalaRepository sali;
    private final AntrenorRepository antrenori;

    public AntrenamentController(AntrenamentService service, SalaRepository sali, AntrenorRepository antrenori) {
        this.service = service;
        this.sali = sali;
        this.antrenori = antrenori;
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("antrenament", new Antrenament());
        model.addAttribute("sali", sali.findAll());
        model.addAttribute("antrenori", antrenori.findAll());
        return "antrenamente/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable int id, Model model) {
        Antrenament a = service.findById(id);

        a.setIdSala(a.getSala() != null ? a.getSala().getId() : null);
        a.setIdAntrenor(a.getAntrenor() != null ? a.getAntrenor().getId() : null);

        model.addAttribute("antrenament", a);
        model.addAttribute("sali", sali.findAll());
        model.addAttribute("antrenori", antrenori.findAll());
        return "antrenamente/form";
    }


    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("antrenament") Antrenament antrenament,
                       BindingResult br,
                       Model model) {

        if (antrenament.getIdSala() == null) {
            br.rejectValue("idSala", "error.idSala", "Sala este obligatorie.");
        }

        if (antrenament.getIdAntrenor() == null) {
            br.rejectValue("idAntrenor", "error.idAntrenor", "Antrenorul este obligatoriu.");
        }

        if (br.hasErrors()) {
            model.addAttribute("sali", sali.findAll());
            model.addAttribute("antrenori", antrenori.findAll());
            return "antrenamente/form";
        }

        antrenament.setSala(
                sali.findById(antrenament.getIdSala()).orElse(null)
        );
        antrenament.setAntrenor(
                antrenori.findById(antrenament.getIdAntrenor()).orElse(null)
        );

        service.save(antrenament);
        return "redirect:/";
    }



    @PostMapping("/{id}/delete")
    public String delete(@PathVariable int id, RedirectAttributes ra) {
        try {
            service.delete(id);
        } catch (IllegalStateException ex) {
            ra.addFlashAttribute("flashMsg", ex.getMessage());
        }
        return "redirect:/";
    }
}
