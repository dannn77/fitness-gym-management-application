/** Clasă pentru afișarea datelor și statisticilor de pe dashboard.
 * @author Șerban Dan-Adrian
 * @version 11 Ianuarie 2026
 */

package com.example.sala.controller;

import com.example.sala.model.Programare;
import com.example.sala.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Comparator;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class PageController {

    private final MembruService membri;
    private final AbonamentService abonamente;
    private final AntrenorService antrenori;
    private final SalaService sali;
    private final AntrenamentService antrenamente;
    private final ProgramareService programari;

    public PageController(
            MembruService membri,
            AbonamentService abonamente,
            AntrenorService antrenori,
            SalaService sali,
            AntrenamentService antrenamente,
            ProgramareService programari
    ) {
        this.membri = membri;
        this.abonamente = abonamente;
        this.antrenori = antrenori;
        this.sali = sali;
        this.antrenamente = antrenamente;
        this.programari = programari;
    }

    @GetMapping("/")
    public String index(
            Model model,
            @RequestParam(name = "abonamentId", required = false) Integer abonamentId,
            @RequestParam(name = "luna", required = false) Integer luna
    ) {
        model.addAttribute("appName", "Sala Fitness");

        var listaMembri = membri.findAll();
        var listaAbonamente = abonamente.findAll();
        var listaProgramari = programari.findAll();

        model.addAttribute("membri", listaMembri);
        model.addAttribute("abonamente", listaAbonamente);
        model.addAttribute("antrenori", antrenori.findAll());
        model.addAttribute("sali", sali.findAll());
        model.addAttribute("antrenamente", antrenamente.findAll());
        model.addAttribute("programari", listaProgramari);

        model.addAttribute("countMembri", listaMembri.size());
        model.addAttribute("countAbonamente", listaAbonamente.size());
        model.addAttribute("countAntrenori", antrenori.findAll().size());
        model.addAttribute("countProgramari", listaProgramari.size());


        // Filtru membri după abonament

        var membriFiltrati = listaMembri.stream()
                .filter(m -> abonamentId != null
                        && m.getAbonament() != null
                        && abonamentId.equals(m.getAbonament().getId()))
                .toList();

        model.addAttribute("abonamentIdSelectat", abonamentId);
        model.addAttribute("membriFiltrati", membriFiltrati);


        // Următoarele 3 programări

        LocalDate azi = LocalDate.now();

        var urmatoarele3Programari = listaProgramari.stream()
                .filter(p -> p.getDataProgramare() != null && !p.getDataProgramare().isBefore(azi))
                .sorted(Comparator.comparing(Programare::getDataProgramare))
                .limit(3)
                .toList();

        model.addAttribute("urmatoarele3Programari", urmatoarele3Programari);


        //  Antrenori peste medie (luna selectată)

        int lunaSelectata = (luna == null ? azi.getMonthValue() : luna);
        int anSelectat = azi.getYear();
        Locale ro = new Locale("ro", "RO");

        model.addAttribute("luni", IntStream.rangeClosed(1, 12).boxed().toList());
        model.addAttribute("lunaSelectata", lunaSelectata);
        model.addAttribute("anSelectat", anSelectat);

        String numeLuna = Month.of(lunaSelectata).getDisplayName(TextStyle.FULL, ro);
        model.addAttribute("numeLunaSelectata", numeLuna);

        Map<String, Long> programariPeAntrenor = listaProgramari.stream()
                .filter(p -> p.getDataProgramare() != null
                        && p.getDataProgramare().getYear() == anSelectat
                        && p.getDataProgramare().getMonthValue() == lunaSelectata)
                .filter(p -> p.getAntrenament() != null && p.getAntrenament().getAntrenor() != null)
                .collect(Collectors.groupingBy(
                        p -> p.getAntrenament().getAntrenor().getNumeComplet(),
                        Collectors.counting()
                ));

        double media = programariPeAntrenor.isEmpty()
                ? 0
                : programariPeAntrenor.values().stream().mapToLong(Long::longValue).average().orElse(0);

        model.addAttribute("mediaProgramari", media);

        var antrenoriPesteMedie = programariPeAntrenor.entrySet().stream()
                .filter(e -> e.getValue() >= Math.ceil(media))
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .toList();

        model.addAttribute("antrenoriPesteMedie", antrenoriPesteMedie);

        return "index";
    }
}
