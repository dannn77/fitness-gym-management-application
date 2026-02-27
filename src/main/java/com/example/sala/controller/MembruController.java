/** Clasă pentru crearea, editarea, salvarea, ștergerea membrilor și reînnoirea abonamentelor acestora.
 * @author Șerban Dan-Adrian
 * @version 11 Ianuarie 2026
 */


package com.example.sala.controller;

import com.example.sala.model.Membru;
import com.example.sala.repository.AbonamentRepository;
import com.example.sala.service.MembruService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/membri")
public class MembruController {

    private final MembruService service;
    private final AbonamentRepository abonamente;

    public MembruController(MembruService service, AbonamentRepository abonamente) {
        this.service = service;
        this.abonamente = abonamente;
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("membru", new Membru());
        model.addAttribute("abonamente", abonamente.findAll());
        return "membri/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable int id, Model model) {
        Membru m = service.findById(id);

        m.setIdAbonament(m.getAbonament() != null ? m.getAbonament().getId() : null);

        model.addAttribute("membru", m);
        model.addAttribute("abonamente", abonamente.findAll());
        return "membri/form";
    }


    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("membru") Membru membru,
                       BindingResult br,
                       Model model) {
        if (membru.getIdAbonament() == null) {
            br.rejectValue("idAbonament", "error.idAbonament", "Abonamentul este obligatoriu.");
        }

        if (br.hasErrors()) {
            model.addAttribute("abonamente", abonamente.findAll());
            return "membri/form";
        }



        if (membru.getIdAbonament() == null) {
            membru.setAbonament(null);
        } else {
            membru.setAbonament(abonamente.findById(membru.getIdAbonament()).orElse(null));
        }

        service.save(membru);
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

    @PostMapping("/{id}/renew")
    public String renew(@PathVariable int id) {
        service.renewAbonament(id);
        return "redirect:/";
    }

}
