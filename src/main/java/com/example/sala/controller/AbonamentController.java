/** Clasă pentru afișarea formularelor, crearea, editarea, salvarea și ștergerea abonamentelor din aplicație.
 * @author Șerban Dan-Adrian
 * @version 11 Ianuarie 2026
 */


package com.example.sala.controller;

import com.example.sala.model.Abonament;
import com.example.sala.service.AbonamentService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/abonamente")
public class AbonamentController {

    private final AbonamentService service;

    public AbonamentController(AbonamentService service) {
        this.service = service;
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("abonament", new Abonament());
        return "abonamente/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable int id, Model model) {
        model.addAttribute("abonament", service.findById(id));
        return "abonamente/form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("abonament") Abonament abonament,
                       BindingResult br) {
        if (br.hasErrors()) return "abonamente/form";
        service.save(abonament);
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
