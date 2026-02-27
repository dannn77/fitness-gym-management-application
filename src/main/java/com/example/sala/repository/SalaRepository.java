/** Clasă pentru accesul și gestionarea datelor de tip sală din baza de date folosind JPA Repository.
 * @author Șerban Dan-Adrian
 * @version 11 Ianuarie 2026
 */

package com.example.sala.repository;
import com.example.sala.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
public interface SalaRepository extends JpaRepository<Sala, Integer> { }
