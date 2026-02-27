/** Clasă pentru accesul și gestionarea datelor de tip antrenament din baza de date folosind JPA Repository.
 * @author Șerban Dan-Adrian
 * @version 11 Ianuarie 2026
 */


package com.example.sala.repository;

import com.example.sala.model.Antrenament;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AntrenamentRepository extends JpaRepository<Antrenament, Integer> {

    long countBySala_Id(Integer salaId);

    long countByAntrenor_Id(Integer antrenorId);
}
