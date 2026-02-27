/** Clasă pentru implementarea operațiilor CRUD asupra abonamentelor.
 * @author Șerban Dan-Adrian
 * @version 11 Ianuarie 2026
 */

package com.example.sala.service;

import com.example.sala.model.Abonament;
import com.example.sala.repository.MembruRepository;
import com.example.sala.repository.AbonamentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbonamentService {
    private final AbonamentRepository repo;
    private final MembruRepository membri;

    public AbonamentService(AbonamentRepository repo, MembruRepository membri) {
        this.repo = repo;
        this.membri = membri;
    }

    public List<Abonament> findAll() { return repo.findAll(); }
    public Abonament findById(int id) { return repo.findById(id).orElseThrow(); }
    public Abonament save(Abonament a) {
        int pretInt = Integer.parseInt(a.getPret());
        return repo.save(a);
    }

    public boolean canDelete(int id) {
        return membri.countByAbonament_Id(id) == 0;
    }

    public void delete(int id) {
        if (!canDelete(id)) throw new IllegalStateException("Nu poți șterge abonamentul: există membri asociați.");
        repo.deleteById(id);
    }
}
