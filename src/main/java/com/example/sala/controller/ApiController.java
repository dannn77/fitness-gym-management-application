/** Clasă pentru gestionarea endpoint-urilor CRUD pentru abonamente, săli, antrenori, antrenamente, membri și programări prin REST API.
 * @author Șerban Dan-Adrian
 * @version 11 Ianuarie 2026
 */



package com.example.sala.controller;

import com.example.sala.dto.ApiResponseDto;
import com.example.sala.model.*;
import com.example.sala.service.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final AbonamentService abonamente;
    private final SalaService sali;
    private final AntrenorService antrenori;
    private final AntrenamentService antrenamente;
    private final MembruService membri;
    private final ProgramareService programari;

    public ApiController(AbonamentService abonamente, SalaService sali, AntrenorService antrenori,
                         AntrenamentService antrenamente, MembruService membri, ProgramareService programari) {
        this.abonamente = abonamente;
        this.sali = sali;
        this.antrenori = antrenori;
        this.antrenamente = antrenamente;
        this.membri = membri;
        this.programari = programari;
    }

    // Abonamente
    @GetMapping("/abonamente") public Object listAbon() { return abonamente.findAll(); }
    @PostMapping("/abonamente") public Object addAbon(@Valid @RequestBody Abonament a){ return abonamente.save(a); }
    @PutMapping("/abonamente/{id}") public Object updAbon(@PathVariable int id, @Valid @RequestBody Abonament a){ a.setId(id); return abonamente.save(a); }
    @DeleteMapping("/abonamente/{id}") public ResponseEntity<?> delAbon(@PathVariable int id){
        if(!abonamente.canDelete(id)) return ResponseEntity.status(409).body(new ApiResponseDto("Există membri asociați."));
        abonamente.delete(id); return ResponseEntity.ok().build();
    }

    // Sali
    @GetMapping("/sali") public Object listSali(){ return sali.findAll(); }
    @PostMapping("/sali") public Object addSala(@Valid @RequestBody Sala s){ return sali.save(s); }
    @PutMapping("/sali/{id}") public Object updSala(@PathVariable int id, @Valid @RequestBody Sala s){ s.setId(id); return sali.save(s); }
    @DeleteMapping("/sali/{id}") public ResponseEntity<?> delSala(@PathVariable int id){
        if(!sali.canDelete(id)) return ResponseEntity.status(409).body(new ApiResponseDto("Există antrenamente asociate."));
        sali.delete(id); return ResponseEntity.ok().build();
    }

    // Antrenori
    @GetMapping("/antrenori") public Object listAnt(){ return antrenori.findAll(); }
    @PostMapping("/antrenori") public Object addAnt(@Valid @RequestBody Antrenor a){ return antrenori.save(a); }
    @PutMapping("/antrenori/{id}") public Object updAnt(@PathVariable int id, @Valid @RequestBody Antrenor a){ a.setId(id); return antrenori.save(a); }
    @DeleteMapping("/antrenori/{id}") public ResponseEntity<?> delAnt(@PathVariable int id){
        if(!antrenori.canDelete(id)) return ResponseEntity.status(409).body(new ApiResponseDto("Există antrenamente asociate."));
        antrenori.delete(id); return ResponseEntity.ok().build();
    }

    // Antrenamente
    @GetMapping("/antrenamente") public Object listAntr(){ return antrenamente.findAll(); }
    @PostMapping("/antrenamente") public Object addAntr(@Valid @RequestBody Antrenament a){ return antrenamente.save(a); }
    @PutMapping("/antrenamente/{id}") public Object updAntr(@PathVariable int id, @Valid @RequestBody Antrenament a){ a.setId(id); return antrenamente.save(a); }
    @DeleteMapping("/antrenamente/{id}") public ResponseEntity<?> delAntr(@PathVariable int id){
        if(!antrenamente.canDelete(id)) return ResponseEntity.status(409).body(new ApiResponseDto("Există programări asociate."));
        antrenamente.delete(id); return ResponseEntity.ok().build();
    }

    // Membri
    @GetMapping("/membri") public Object listMemb(){ return membri.findAll(); }
    @PostMapping("/membri") public Object addMemb(@Valid @RequestBody Membru m){ return membri.save(m); }
    @PutMapping("/membri/{id}") public Object updMemb(@PathVariable int id, @Valid @RequestBody Membru m){ m.setId(id); return membri.save(m); }
    @DeleteMapping("/membri/{id}") public ResponseEntity<?> delMemb(@PathVariable int id){
        if(!membri.canDelete(id)) return ResponseEntity.status(409).body(new ApiResponseDto("Există programări asociate."));
        membri.delete(id); return ResponseEntity.ok().build();
    }

    // Programari
    @GetMapping("/programari") public Object listProg(){ return programari.findAll(); }
    @PostMapping("/programari") public Object addProg(@Valid @RequestBody Programare p){ return programari.save(p); }
    @PutMapping("/programari/{id}") public Object updProg(@PathVariable int id, @Valid @RequestBody Programare p){ p.setId(id); return programari.save(p); }
    @DeleteMapping("/programari/{id}") public ResponseEntity<?> delProg(@PathVariable int id){ programari.delete(id); return ResponseEntity.ok().build(); }
}
