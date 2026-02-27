/** Clasă pentru definirea structurii unei programări și a regulilor de validare.
 * @author Șerban Dan-Adrian
 * @version 11 Ianuarie 2026
 */


package com.example.sala.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name="Programare")
public class Programare {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID_programare")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ID_membru", nullable=false)
    private Membru membru;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ID_antrenament", nullable=false)
    private Antrenament antrenament;

    @NotNull(message="Data programării este obligatorie.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name="Data_programare", nullable=false)
    private LocalDate dataProgramare;

    @Transient
    private Integer idMembru;

    @Transient
    private Integer idAntrenament;

    public Integer getIdMembru() { return idMembru; }
    public void setIdMembru(Integer idMembru) { this.idMembru = idMembru; }

    public Integer getIdAntrenament() { return idAntrenament; }
    public void setIdAntrenament(Integer idAntrenament) { this.idAntrenament = idAntrenament; }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Membru getMembru() { return membru; }
    public void setMembru(Membru membru) { this.membru = membru; }

    public Antrenament getAntrenament() { return antrenament; }
    public void setAntrenament(Antrenament antrenament) { this.antrenament = antrenament; }

    public LocalDate getDataProgramare() { return dataProgramare; }
    public void setDataProgramare(LocalDate dataProgramare) { this.dataProgramare = dataProgramare; }
}
