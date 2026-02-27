/** Clasă adăugarea, editarea, salvarea și ștergerea antrenorilor.
 * @author Șerban Dan-Adrian
 * @version 11 Ianuarie 2026
 */


package com.example.sala.controller;

import com.example.sala.model.Antrenor;
import com.example.sala.service.AntrenorService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/antrenori")
public class AntrenorController {

    private final AntrenorService service;

    public AntrenorController(AntrenorService service) {
        this.service = service;
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        Antrenor a = new Antrenor();
        model.addAttribute("antrenor", a);
        return "antrenori/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable int id, Model model) {
        model.addAttribute("antrenor", service.findById(id));
        return "antrenori/form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("antrenor") Antrenor antrenor,
                       BindingResult br) {
        if (br.hasErrors()) return "antrenori/form";
        service.save(antrenor);
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
