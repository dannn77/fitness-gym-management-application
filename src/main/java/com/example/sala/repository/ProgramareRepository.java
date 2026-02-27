/** Clasă pentru accesul și gestionarea datelor de tip programare din baza de date folosind JPA Repository.
 * @author Șerban Dan-Adrian
 * @version 11 Ianuarie 2026
 */

package com.example.sala.repository;
import com.example.sala.model.Programare;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ProgramareRepository extends JpaRepository<Programare, Integer> {
    long countByMembru_Id(Integer membruId);
    long countByAntrenament_Id(Integer antrenamentId);
}
