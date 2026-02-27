/** Clasă pentru definirea structurii unui membru și a regulilor de validare.
 * @author Șerban Dan-Adrian
 * @version 11 Ianuarie 2026
 */


package com.example.sala.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name="Membru")
public class Membru {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID_membru")
    private Integer id;

    @NotBlank(message="Numele este obligatoriu.")
    @Pattern(
            regexp = "^[A-Za-zĂÂÎȘȚăâîșț ]+$",
            message = "Numele poate conține doar litere."
    )
    @Size(max=50)
    @Column(name="Nume", length=50, nullable=false)
    private String nume;

    @NotBlank(message="Prenumele este obligatoriu.")
    @Pattern(
            regexp = "^[A-Za-zĂÂÎȘȚăâîșț ]+$",
            message = "Prenumele poate conține doar litere."
    )
    @Size(max=50)
    @Column(name="Prenume", length=50, nullable=false)
    private String prenume;

    @NotBlank(message = "CNP este obligatoriu.")
    @Pattern(
            regexp = "^[0-9]{13}$",
            message = "CNP trebuie să conțină exact 13 cifre."
    )
    @Column(name="CNP", length=13)
    private String cnp;

    @NotBlank(message = "Sex este obligatoriu.")
    @Pattern(regexp="^[MF]?$", message="Sex invalid (M/F).")
    @Column(name="Sex", length=1)
    private String sex;

    @NotBlank(message = "Numarul de telefon este obligatoriu.")
    @Pattern(
            regexp = "^[0-9]{10}$",
            message = "Telefonul trebuie să conțină exact 10 cifre."
    )
    @Column(name="Telefon", length=10)
    private String telefon;

    @NotNull(message = "Data de început este obligatorie.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name="Data_start")
    private LocalDate dataStart;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name="Data_expirare")
    private LocalDate dataExpirare;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ID_abonament")
    private Abonament abonament;

    @Transient
    private Integer idAbonament;

    public Integer getIdAbonament() { return idAbonament; }
    public void setIdAbonament(Integer idAbonament) { this.idAbonament = idAbonament; }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNume() { return nume; }
    public void setNume(String nume) { this.nume = nume; }

    public String getPrenume() { return prenume; }
    public void setPrenume(String prenume) { this.prenume = prenume; }

    public String getCnp() { return cnp; }
    public void setCnp(String cnp) { this.cnp = cnp; }

    public String getSex() { return sex; }
    public void setSex(String sex) { this.sex = sex; }

    public String getTelefon() { return telefon; }
    public void setTelefon(String telefon) { this.telefon = telefon; }

    public LocalDate getDataStart() { return dataStart; }
    public void setDataStart(LocalDate dataStart) { this.dataStart = dataStart; }

    public LocalDate getDataExpirare() { return dataExpirare; }
    public void setDataExpirare(LocalDate dataExpirare) { this.dataExpirare = dataExpirare; }

    public Abonament getAbonament() { return abonament; }
    public void setAbonament(Abonament abonament) { this.abonament = abonament; }

    @Transient
    public String getNumeComplet() {
        return (nume == null ? "" : nume) + " " + (prenume == null ? "" : prenume);
    }

    @Transient
    public String getStatusAbonament() {
        if (dataStart == null || dataExpirare == null) {
            return "Fără abonament";
        }

        LocalDate azi = LocalDate.now();

        if (azi.isBefore(dataStart)) {
            return "În viitor";
        }
        if (azi.isAfter(dataExpirare)) {
            return "Finalizat";
        }
        return "În desfășurare";
    }
}
