/** Clasă pentru definirea structurii și a regulilor de validare ale unui abonament.
 * @author Șerban Dan-Adrian
 * @version 11 Ianuarie 2026
 */


package com.example.sala.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "Abonament")
public class Abonament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_abonament")
    private Integer id;

    @NotBlank(message = "Tip abonament este obligatoriu.")
    @Pattern(
            regexp = "^(Lunar|Anual)[A-Za-zĂÂÎȘȚăâîșț ]*$",
            message = "Tipul abonamentului trebuie să înceapă cu «lunar» sau «anual» și să conțină doar litere."
    )
    @Size(max = 15, message = "Tip abonament max 15 caractere.")
    @Column(name = "Tip_abonament", length = 15, nullable = false)
    private String tipAbonament;

    @NotBlank(message = "Prețul este obligatoriu.")
    @Pattern(
            regexp = "^[0-9]+$",
            message = "Prețul trebuie să conțină doar cifre."
    )
    @Column(name = "Pret", nullable = false)
    private String pret;

    @NotNull(message = "Acces sala este obligatoriu.")
    @Column(name = "Acces_sala_grup", nullable = false)
    private Boolean accesSalaGrup;

    @NotNull(message = "Acces trainer este obligatoriu.")
    @Column(name = "Acces_personal_trainer", nullable = false)
    private Boolean accesPersonalTrainer;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTipAbonament() { return tipAbonament; }
    public void setTipAbonament(String tipAbonament) { this.tipAbonament = tipAbonament; }

    public String getPret() { return pret; }
    public void setPret(String pret) { this.pret = pret; }

    public Boolean getAccesSalaGrup() { return accesSalaGrup; }
    public void setAccesSalaGrup(Boolean accesSalaGrup) { this.accesSalaGrup = accesSalaGrup; }

    public Boolean getAccesPersonalTrainer() { return accesPersonalTrainer; }
    public void setAccesPersonalTrainer(Boolean accesPersonalTrainer) { this.accesPersonalTrainer = accesPersonalTrainer; }
}
