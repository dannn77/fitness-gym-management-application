/** Clasă pentru crearea, editarea, salvarea și ștergerea programărilor membrilor la antrenamente.
 * @author Șerban Dan-Adrian
 * @version 11 Ianuarie 2026
 */


package com.example.sala.controller;

import com.example.sala.model.Programare;
import com.example.sala.repository.AntrenamentRepository;
import com.example.sala.repository.MembruRepository;
import com.example.sala.service.ProgramareService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/programari")
public class ProgramareController {

    private final ProgramareService service;
    private final MembruRepository membri;
    private final AntrenamentRepository antrenamente;

    public ProgramareController(ProgramareService service, MembruRepository membri, AntrenamentRepository antrenamente) {
        this.service = service;
        this.membri = membri;
        this.antrenamente = antrenamente;
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("programare", new Programare());
        model.addAttribute("membri", membri.findByAbonament_AccesPersonalTrainerTrue());
        model.addAttribute("antrenamente", antrenamente.findAll());
        return "programari/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable int id, Model model) {
        Programare p = service.findById(id);

        p.setIdMembru(p.getMembru() != null ? p.getMembru().getId() : null);
        p.setIdAntrenament(p.getAntrenament() != null ? p.getAntrenament().getId() : null);

        var listaMembri = new java.util.ArrayList<>(membri.findByAbonament_AccesPersonalTrainerTrue());

        if (p.getMembru() != null && p.getMembru().getId() != null
                && listaMembri.stream().noneMatch(m -> m.getId().equals(p.getMembru().getId()))) {
            listaMembri.add(p.getMembru());
        }

        model.addAttribute("programare", p);
        model.addAttribute("membri", listaMembri);
        model.addAttribute("antrenamente", antrenamente.findAll());

        return "programari/form";
    }


    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("programare") Programare programare,
                       BindingResult br,
                       Model model) {

        var listaMembri = new java.util.ArrayList<>(membri.findByAbonament_AccesPersonalTrainerTrue());
        var listaAntrenamente = antrenamente.findAll();

        if (programare.getIdMembru() != null) {
            membri.findById(programare.getIdMembru()).ifPresent(m -> {
                if (listaMembri.stream().noneMatch(x -> x.getId().equals(m.getId()))) {
                    listaMembri.add(m);
                }
            });
        }

        if (programare.getIdMembru() == null) {
            br.rejectValue("idMembru", "NotNull", "Membrul este obligatoriu.");
        }
        if (programare.getIdAntrenament() == null) {
            br.rejectValue("idAntrenament", "NotNull", "Antrenamentul este obligatoriu.");
        }

        if (br.hasErrors()) {
            model.addAttribute("membri", listaMembri);
            model.addAttribute("antrenamente", listaAntrenamente);
            return "programari/form";
        }

        programare.setMembru(
                programare.getIdMembru() == null ? null
                        : membri.findById(programare.getIdMembru()).orElse(null)
        );

        programare.setAntrenament(
                programare.getIdAntrenament() == null ? null
                        : antrenamente.findById(programare.getIdAntrenament()).orElse(null)
        );

        try {
            service.save(programare);
            return "redirect:/";
        } catch (IllegalStateException ex) {
            model.addAttribute("membri", listaMembri);
            model.addAttribute("antrenamente", listaAntrenamente);
            model.addAttribute("flashMsg", ex.getMessage());
            return "programari/form";
        }
    }


    @PostMapping("/{id}/delete")
    public String delete(@PathVariable int id) {
        service.delete(id);
        return "redirect:/";
    }
}
