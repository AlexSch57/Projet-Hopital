/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * @author kormli18
 */
public class Chirurgie {
    private String id;
    private LocalDate date;
    private Date heureDebut;
    private Date heureFin;
    private Salle salle;
    private Chirurgien chirurgien;

    public String getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Date getHeureDebut() {
        return heureDebut;
    }

    public Date getHeureFin() {
        return heureFin;
    }

    public Salle getSalle() {
        return salle;
    }

    public Chirurgien getChirurgien() {
        return chirurgien;
    }

    public Chirurgie(String id, LocalDate date, Date heureDebut, Date heureFin, Salle salle, Chirurgien chirurgien) {
        this.id = id;
        this.date = date;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.salle = salle;
        this.chirurgien = chirurgien;
    }
    
    public String toString(){
        return this.getId();       
    }
    
}
