/** Clasă pentru definirea structurii unui antrenament și a regulilor de validare.
 * @author Șerban Dan-Adrian
 * @version 11 Ianuarie 2026
 */


package com.example.sala.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name="Antrenament")
public class Antrenament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID_antrenament")
    private Integer id;

    @NotBlank(message="Denumirea este obligatorie.")
    @Pattern(
            regexp = "^[A-Za-zĂÂÎȘȚăâîșț ]+$",
            message = "Denumirea poate conține doar litere."
    )
    @Column(name="Denumire_antrenament", length=50, nullable=false)
    private String denumireAntrenament;

    @NotBlank(message="Tip antrenament este obligatoriu.")
    @Column(name="Tip_antrenament", length=1, nullable=false)
    private String tipAntrenament; // G/M/U

    @NotBlank(message="Durata este obligatorie.")
    @Pattern(
            regexp = "^[0-9]+$",
            message = "Durata trebuie să conțină doar cifre."
    )
    @Column(name="Durata", nullable=false)
    private String durata;

    @NotBlank(message="Capacitatea este obligatorie.")
    @Pattern(
            regexp = "^[0-9]+$",
            message = "Capacitate trebuie să conțină doar cifre."
    )
    @Column(name="Capacitate", nullable=false)
    private String capacitate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ID_sala")
    private Sala sala;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ID_antrenor")
    private Antrenor antrenor;

    @Transient
    private Integer idSala;

    @Transient
    private Integer idAntrenor;

    public Integer getIdSala() { return idSala; }
    public void setIdSala(Integer idSala) { this.idSala = idSala; }

    public Integer getIdAntrenor() { return idAntrenor; }
    public void setIdAntrenor(Integer idAntrenor) { this.idAntrenor = idAntrenor; }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getDenumireAntrenament() { return denumireAntrenament; }
    public void setDenumireAntrenament(String denumireAntrenament) { this.denumireAntrenament = denumireAntrenament; }

    public String getTipAntrenament() { return tipAntrenament; }
    public void setTipAntrenament(String tipAntrenament) { this.tipAntrenament = tipAntrenament; }

    public String getDurata() { return durata; }
    public void setDurata(String durata) { this.durata = durata; }

    public String getCapacitate() { return capacitate; }
    public void setCapacitate(String capacitate) { this.capacitate = capacitate; }

    public Sala getSala() { return sala; }
    public void setSala(Sala sala) { this.sala = sala; }

    public Antrenor getAntrenor() { return antrenor; }
    public void setAntrenor(Antrenor antrenor) { this.antrenor = antrenor; }
}
