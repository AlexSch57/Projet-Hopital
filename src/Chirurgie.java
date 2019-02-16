/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Locale;

/**
 *
 * @author kormli18
 */
public class Chirurgie implements Comparable {

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

    public void setChirurgien(Chirurgien chirurgien) {
        this.chirurgien = chirurgien;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    public Chirurgien getChirurgien() {
        return chirurgien;
    }

    public void setHeureDebut(LocalTime heureDebut) {
        this.heureDebut = heureDebut;
    }

    public void setHeureFin(LocalTime heureFin) {
        this.heureFin = heureFin;
    }
    

    public Chirurgie(String id, LocalDate date, LocalTime heureDebut, LocalTime heureFin, Salle salle, Chirurgien chirurgien) {
        this.id = id;
        this.date = date;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.salle = salle;
        this.chirurgien = chirurgien;
    }

    public Chirurgie() {
        
    }

    public Long getDuree() {
    	 if (this.heureDebut.isBefore(this.heureFin)) {
             return Duration.between(this.heureDebut, this.heureFin).toMinutes();
         } 
    	 else { 
            return Duration.ofMinutes(1440).minus(Duration.between(this.heureFin, this.heureDebut)).toMinutes();
         }
    }
    
    public static Long getDuree(LocalTime lt1, LocalTime lt2) {
    	 if (lt1.isBefore(lt2)) {
             return Duration.between(lt1, lt2).toMinutes();
         } 
    	 else { 
            return Duration.ofMinutes(1440).minus(Duration.between(lt2, lt1)).toMinutes();
         }
    }
    
    public String toString() {
        String today = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH).format(this.getDate());
        return this.getId() + ";" + today + ";"
                + this.getHeureDebut().toString() + ":00" + ";" + this.getHeureFin().toString() + ":00" + ";" + this.getSalle().toString() + ";" + this.getChirurgien().toString();
    }
    
    /**
    *
    * @param c un Objet Chirurgie, différent de la chirurgie actuel
    * @return return un booléen : - true si la chirurgie c se déroule en même temps que la chirurgie courante (partiellement ou complètement) 
    * - false sinon
    */
   public boolean estParallele(Chirurgie c) {
       if (this.getDate().equals(c.getDate())) {
           if (this.getHeureDebut().equals(c.getHeureDebut())) {
               return true;
           }
           if ((c.getHeureDebut().isAfter(this.getHeureDebut()))
                   && (getDuree(this.getHeureDebut(), c.getHeureDebut()) < this.getDuree())) {
               return true;
           }
           if((this.getHeureDebut().isAfter(c.getHeureDebut()))
                   && (getDuree(c.getHeureDebut(), this.getHeureDebut()) < c.getDuree())) {
        	   return true;
           }
       }
       return false;
   }
   
    public boolean equals(Object e) {
        if (this.getClass().equals(e.getClass())) {
            Chirurgie c = (Chirurgie) e;
            if((this.date.equals(c.date)) && (this.heureDebut.equals(c.heureDebut)) && (this.heureFin.equals(c.heureFin)) && (this.chirurgien.equals(c.chirurgien)) && 
                    (this.salle.equals(c.salle))) {
                return true;
            }
        }
        return false;
    }
        
    
    @Override
    public int compareTo(Object obj) {
        if (obj instanceof Chirurgie) {
            Chirurgie c = (Chirurgie) obj;
            if (this.getDate().isBefore(c.getDate())) {
                return -1;
            } 
            else if (this.getDate().equals(c.getDate())) {
                if (this.getHeureDebut().isBefore(c.getHeureDebut())) {
                    return -1;
                } 
                else if (this.getHeureDebut().equals(c.getHeureDebut())) {
                    if (this.getHeureFin().isBefore(c.getHeureFin())) {
                        return -1;
                    } 
                    else {
                        return 1;
                    }
                } 
                else {
                    return 1;
                }
            } 
            else {
                return 1;
            }
        } 
        else {
            return 0;
        }
    }

}
