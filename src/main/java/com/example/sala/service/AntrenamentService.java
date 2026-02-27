/** Clasă pentru implementarea operațiilor CRUD asupra antrenamentelor.
 * @author Șerban Dan-Adrian
 * @version 11 Ianuarie 2026
 */

package com.example.sala.service;

import com.example.sala.model.Antrenament;
import com.example.sala.repository.AntrenamentRepository;
import com.example.sala.repository.ProgramareRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AntrenamentService {
    private final AntrenamentRepository repo;
    private final ProgramareRepository programari;

    public AntrenamentService(AntrenamentRepository repo, ProgramareRepository programari) {
        this.repo = repo;
        this.programari = programari;
    }

    public List<Antrenament> findAll() { return repo.findAll(); }
    public Antrenament findById(int id) { return repo.findById(id).orElseThrow(); }
    public Antrenament save(Antrenament a) {
        int durataInt = Integer.parseInt(a.getDurata());
        int capacitateInt = Integer.parseInt(a.getCapacitate());
        return repo.save(a);
    }

    public boolean canDelete(int id) {
        return programari.countByAntrenament_Id(id) == 0;
    }

    public void delete(int id) {
        if (!canDelete(id)) throw new IllegalStateException("Nu poți șterge antrenamentul: există programări asociate.");
        repo.deleteById(id);
    }
}
