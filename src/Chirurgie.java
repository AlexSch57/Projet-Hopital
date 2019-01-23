/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author kormli18
 */
// test commit eclipse
// test pull eclipse
public class Chirurgie {
    private String id;
    private LocalDate date;
    private LocalTime heureDebut;
    private LocalTime heureFin;
    private Salle salle;
    private Chirurgien chirurgien;

    public String getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getHeureDebut() {
        return heureDebut;
    }

    public LocalTime getHeureFin() {
        return heureFin;
    }

    public Salle getSalle() {
        return salle;
    }

    public Chirurgien getChirurgien() {
        return chirurgien;
    }

    public Chirurgie(String id, LocalDate date, LocalTime heureDebut, LocalTime heureFin, Salle salle, Chirurgien chirurgien) {
        this.id = id;
        this.date = date;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.salle = salle;
        this.chirurgien = chirurgien;
    }
    
    public String toString(){
        return this.getId()+";"+this.getDate().toString()+";"
                +this.getHeureDebut().toString()+";"+this.getHeureFin().toString()+";"+this.getSalle().toString()+";"+this.getChirurgien().toString();       
    }
    
}
