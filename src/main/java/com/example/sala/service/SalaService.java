/** Clasă pentru implementarea operațiilor CRUD asupra sălilor.
 * @author Șerban Dan-Adrian
 * @version 11 Ianuarie 2026
 */

package com.example.sala.service;

import com.example.sala.model.Sala;
import com.example.sala.repository.AntrenamentRepository;
import com.example.sala.repository.SalaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaService {
    private final SalaRepository repo;
    private final AntrenamentRepository antrenamente;

    public SalaService(SalaRepository repo, AntrenamentRepository antrenamente) {
        this.repo = repo;
        this.antrenamente = antrenamente;
    }

    public List<Sala> findAll() { return repo.findAll(); }
    public Sala findById(int id) { return repo.findById(id).orElseThrow(); }
    public Sala save(Sala s) {
        int capacitateInt = Integer.parseInt(s.getCapacitate());
        return repo.save(s);
    }

    public boolean canDelete(int id) {
        return antrenamente.countBySala_Id(id) == 0;
    }

    public void delete(int id) {
        if (!canDelete(id)) throw new IllegalStateException("Nu poți șterge sala: există antrenamente asociate.");
        repo.deleteById(id);
    }
}
