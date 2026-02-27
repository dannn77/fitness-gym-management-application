/** Clasă pentru implementarea operațiilor CRUD asupra programărilor.
 * @author Șerban Dan-Adrian
 * @version 11 Ianuarie 2026
 */

package com.example.sala.service;

import com.example.sala.model.Programare;
import com.example.sala.repository.ProgramareRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProgramareService {
    private final ProgramareRepository repo;

    public ProgramareService(ProgramareRepository repo) { this.repo = repo; }

    public List<Programare> findAll() { return repo.findAll(); }
    public Programare findById(int id) { return repo.findById(id).orElseThrow(); }


    public Programare save(Programare p) {

        if (p.getMembru() == null || p.getMembru().getAbonament() == null) {
            throw new IllegalStateException("Membrul nu are abonament.");
        }

        LocalDate dataProg = p.getDataProgramare();
        if (dataProg == null) {
            throw new IllegalStateException("Data programării este obligatorie.");
        }

        LocalDate start = p.getMembru().getDataStart();
        LocalDate expirare = p.getMembru().getDataExpirare();

        if (start == null || expirare == null) {
            throw new IllegalStateException("Membrul nu are perioada abonamentului setată.");
        }

        boolean activInZiuaRespectiva =
                ( !dataProg.isBefore(start) ) && ( !dataProg.isAfter(expirare) );

        if (!activInZiuaRespectiva) {
            throw new IllegalStateException("Nu se poate crea programare: abonamentul nu este activ la data selectată.");
        }

        return repo.save(p);
    }

    public void delete(int id) { repo.deleteById(id); }
}
