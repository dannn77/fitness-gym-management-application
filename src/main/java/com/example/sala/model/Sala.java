/** Clasă pentru definirea structurii unei săli și a regulilor de validare.
 * @author Șerban Dan-Adrian
 * @version 11 Ianuarie 2026
 */

package com.example.sala.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name="Sala")
public class Sala {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID_sala")
    private Integer id;

    @NotBlank(message="Denumirea sălii este obligatorie.")
    @Pattern(
            regexp = "^[A-Za-zĂÂÎȘȚăâîșț0-9 ]+$",
            message = "Denumirea poate conține doar litere si cifre."
    )
    @Column(name="Denumire_sala", length=50, nullable=false)
    private String denumireSala;

    @NotBlank(message="Capacitatea este obligatorie.")
    @Pattern(
            regexp = "^[0-9]+$",
            message = "Capacitatea trebuie să conțină doar cifre."
    )
    @Column(name="Capacitate", nullable=false)
    private String capacitate;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getDenumireSala() { return denumireSala; }
    public void setDenumireSala(String denumireSala) { this.denumireSala = denumireSala; }

    public String getCapacitate() { return capacitate; }
    public void setCapacitate(String capacitate) { this.capacitate = capacitate; }
}
