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
public class Chirurgie implements Comparable{
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

	@Override
	public int compareTo(Object obj) {
		if(obj instanceof Chirurgie) {
			Chirurgie c = (Chirurgie) obj;
			if(this.getDate().isBefore(c.getDate())) {
				return -1;
			}
			else if(this.getDate().equals(c.getDate())){
				if(this.getHeureDebut().isBefore(c.getHeureDebut())) {
					return -1;
				}
				else if(this.getHeureDebut().equals(c.getHeureDebut())) {
					if(this.getHeureFin().isBefore(c.getHeureFin())) {
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
