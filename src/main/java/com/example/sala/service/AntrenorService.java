/** Clasă pentru implementarea operațiilor CRUD asupra antrenorilor.
 * @author Șerban Dan-Adrian
 * @version 11 Ianuarie 2026
 */

package com.example.sala.service;

import com.example.sala.model.Antrenor;
import com.example.sala.repository.AntrenamentRepository;
import com.example.sala.repository.AntrenorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AntrenorService {
    private final AntrenorRepository repo;
    private final AntrenamentRepository antrenamente;

    public AntrenorService(AntrenorRepository repo, AntrenamentRepository antrenamente) {
        this.repo = repo;
        this.antrenamente = antrenamente;
    }

    public List<Antrenor> findAll() { return repo.findAll(); }
    public Antrenor findById(int id) { return repo.findById(id).orElseThrow(); }
    public Antrenor save(Antrenor a) { return repo.save(a); }

    public boolean canDelete(int id) {
        return antrenamente.countByAntrenor_Id(id) == 0;
    }

    public void delete(int id) {
        if (!canDelete(id)) throw new IllegalStateException("Nu poți șterge antrenorul: există antrenamente asociate.");
        repo.deleteById(id);
    }
}
