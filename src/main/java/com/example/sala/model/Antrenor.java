/** Clasă pentru definirea structurii unui antrenor și a regulilor de validare.
 * @author Șerban Dan-Adrian
 * @version 11 Ianuarie 2026
 */

package com.example.sala.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name="Antrenor")
public class Antrenor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID_antrenor")
    private Integer id;

    @NotBlank(message="Numele este obligatoriu.")
    @Pattern(
            regexp = "^[A-Za-zĂÂÎȘȚăâîșț ]+$",
            message = "Numele poate conține doar litere."
    )
    @Column(name="Nume", length=50, nullable=false)
    private String nume;

    @NotBlank(message="Prenumele este obligatoriu.")
    @Pattern(
            regexp = "^[A-Za-zĂÂÎȘȚăâîșț ]+$",
            message = "Prenumele poate conține doar litere."
    )
    @Column(name="Prenume", length=50, nullable=false)
    private String prenume;

    @NotBlank(message="Numarul de telefon este obligatoriu.")
    @Pattern(
            regexp = "^[0-9]{10}$",
            message = "Telefonul trebuie să conțină exact 10 cifre."
    )
    @Column(name="Telefon", length=10)
    private String telefon;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNume() { return nume; }
    public void setNume(String nume) { this.nume = nume; }

    public String getPrenume() { return prenume; }
    public void setPrenume(String prenume) { this.prenume = prenume; }

    public String getTelefon() { return telefon; }
    public void setTelefon(String telefon) { this.telefon = telefon; }

    @Transient
    public String getNumeComplet() {
        return (nume == null ? "" : nume) + " " + (prenume == null ? "" : prenume);
    }
}

