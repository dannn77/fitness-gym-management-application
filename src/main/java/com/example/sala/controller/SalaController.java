/** Clasă pentru adăugarea, editarea, salvarea și ștergerea sălilor din cadrul sălii de fitness.
 * @author Șerban Dan-Adrian
 * @version 11 Ianuarie 2026
 */


package com.example.sala.controller;

import com.example.sala.model.Sala;
import com.example.sala.service.SalaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/sali")
public class SalaController {

    private final SalaService service;

    public SalaController(SalaService service) {
        this.service = service;
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("sala", new Sala());
        return "sali/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable int id, Model model) {
        model.addAttribute("sala", service.findById(id));
        return "sali/form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("sala") Sala sala,
                       BindingResult br) {
        if (br.hasErrors()) return "sali/form";
        service.save(sala);
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
