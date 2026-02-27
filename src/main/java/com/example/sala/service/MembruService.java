/** Clasă pentru implementarea operațiilor CRUD asupra membrilor și pentru reînnoirea abonamentelor acestora.
 * @author Șerban Dan-Adrian
 * @version 11 Ianuarie 2026
 */

package com.example.sala.service;

import com.example.sala.model.Membru;
import com.example.sala.repository.MembruRepository;
import com.example.sala.repository.ProgramareRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MembruService {
    private final MembruRepository repo;
    private final ProgramareRepository programari;

    public MembruService(MembruRepository repo, ProgramareRepository programari) {
        this.repo = repo;
        this.programari = programari;
    }

    public List<Membru> findAll() { return repo.findAll(); }
    public Membru findById(int id) { return repo.findById(id).orElseThrow(); }
    public Membru save(Membru m) { return repo.save(m); }

    public boolean canDelete(int id) {
        return programari.countByMembru_Id(id) == 0;
    }

    public void delete(int id) {
        if (!canDelete(id)) throw new IllegalStateException("Nu poți șterge membrul: există programări asociate.");
        repo.deleteById(id);
    }

    public void renewAbonament(int id) {
        Membru m = findById(id);

        if (m.getAbonament() == null || m.getAbonament().getTipAbonament() == null) {
            throw new IllegalStateException("Membrul nu are abonament.");
        }

        LocalDate azi = LocalDate.now();

        String tip = m.getAbonament()
                .getTipAbonament()
                .trim()
                .toLowerCase();

        String primulCuvant = tip.split("\\s+")[0];

        int luni;

        switch (primulCuvant) {
            case "lunar" -> luni = 1;
            case "anual" -> luni = 12;
            default -> throw new IllegalStateException(
                    "Tip abonament necunoscut: " + primulCuvant
            );
        }

        m.setDataStart(azi);
        m.setDataExpirare(azi.plusMonths(luni));

        repo.save(m);
    }


}
