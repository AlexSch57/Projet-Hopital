
import java.util.ArrayList;
import java.util.HashSet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author schwal180
 */
public abstract class Erreur {

    protected ArrayList<Chirurgie> listeChirurgiesErreur;

    public Erreur() {
        this.listeChirurgiesErreur = new ArrayList<Chirurgie>();
    }

    public Erreur(ArrayList<Chirurgie> listeChirurgiesErreur) {
        this.listeChirurgiesErreur = listeChirurgiesErreur;
    }

    public void addChirurgie(Chirurgie c) {
        this.listeChirurgiesErreur.add(c);
    }

    public ArrayList<Chirurgie> getListeChirurgiesErreur() {
        return listeChirurgiesErreur;
    }

    public abstract String toString();
    
    public boolean equals(Object e) {    	
    	if (this.getClass().equals(e.getClass())){
    		if (new HashSet<Chirurgie>(this.getListeChirurgiesErreur()).equals(new HashSet<Chirurgie>(((Erreur) e).getListeChirurgiesErreur()))){
    			return true;
    		}
    	}
    	return false;
    }
}
