/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//test Pull

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author kormli18
 */
public class Hopital {
    private ArrayList<Chirurgie> listeChirurgies;
    private ArrayList<Erreur> listeErreurs;

    public Hopital() {
        this.listeChirurgies = new ArrayList<Chirurgie>();
        this.listeErreurs = new ArrayList<Erreur>();
    }
    
    public void init(String nomFichier) throws FileNotFoundException, IOException, ParseException{
        BufferedReader reader = new BufferedReader(new FileReader(new File("."+File.separator+"files"+File.separator+nomFichier)));
        
        String ligne = reader.readLine();
        while(ligne!=null){
            if (!ligne.contains("ID CHIRURGIE")){
                String[] tab = ligne.split(";");
                boolean existChirurgien = false;
                boolean existSalle = false;
                Chirurgien chirurgien = null;
                Salle salle = null;
                for (Chirurgie ch : this.listeChirurgies){
                    if (ch.getChirurgien().getNom().equals(tab[5])){
                        existChirurgien = true;  
                        chirurgien = ch.getChirurgien();
                    }
                    if (ch.getSalle().getNom().equals(tab[4])){
                        existSalle = true;
                        salle = ch.getSalle();
                    }
                }
                
                if (!existChirurgien){
                    chirurgien = new Chirurgien(tab[5]);                    
                }
                if (!existSalle){
                    salle = new Salle(tab[4]);
                }
                String[] tabDate = tab[1].split("/");
                LocalDate dateChirurgie = LocalDate.of(Integer.parseInt(tabDate[2]),Integer.parseInt(tabDate[1]),Integer.parseInt(tabDate[0]));

                tabDate = tab[2].split(":");                
                LocalTime heureDeb = LocalTime.of(Integer.parseInt(tabDate[0]), Integer.parseInt(tabDate[1]), Integer.parseInt(tabDate[2]));
                
                tabDate = tab[3].split(":");
                LocalTime heureFin = LocalTime.of(Integer.parseInt(tabDate[0]), Integer.parseInt(tabDate[1]), Integer.parseInt(tabDate[2]));
                
                this.listeChirurgies.add(new Chirurgie(tab[0],dateChirurgie,heureDeb,heureFin,salle,chirurgien));           
            }          
            ligne = reader.readLine();
        } 
        Collections.sort(this.listeChirurgies);
    }
    
    public void printListeChirurgies(){
        for (Chirurgie c : this.listeChirurgies){
            System.out.println(c.toString());
        }
    }
    
    /**
     * 
     * @return retourne une map ayant pour clé une date 
     */
    public TreeMap<LocalDate, ArrayList<Chirurgie>> TriParJour(){
    	HashMap<LocalDate, ArrayList<Chirurgie>> m = new HashMap<>();
    	
    	LocalDate dateDuJour = LocalDate.now();
    	ArrayList<Chirurgie> chirurgieDuJour = new ArrayList<>();
    	
    	Iterator it = this.listeChirurgies.iterator();
    	while(it.hasNext()) {
    		Chirurgie c = (Chirurgie) it.next();
    		if(dateDuJour.equals(c.getDate())){
    			chirurgieDuJour.add(c);
    		}
    		else {
    			if(chirurgieDuJour.size() != 0)
    			{
        			m.put(dateDuJour, chirurgieDuJour);
    			}
    			
    			dateDuJour = c.getDate();

    			chirurgieDuJour = new ArrayList<>();
    			chirurgieDuJour.add(c);
    		}
    		if(!(it.hasNext())){
				m.put(dateDuJour, chirurgieDuJour);
			}
    	}
    	
    	TreeMap<LocalDate, ArrayList<Chirurgie>> map = new TreeMap<>(m);
    	return map;  	
    }
    
    /**
     * 
     * @param c1 un Objet Chirurgie
     * @param c2 un Objet Chirurgie, différent de c1
     * @return return un booléen :
     * - true si c1 se déroule en même temps que c2 (partiellement ou complètement)
     * - false sinon
     */
    public boolean estParallele(Chirurgie c1, Chirurgie c2){
        if(c1.getDate().equals(c2.getDate())){
            if(c1.getHeureDebut().equals(c2.getHeureDebut())){
                return true;
            }
            if(((c2.getHeureDebut().isAfter(c1.getHeureDebut())) &&
                c2.getHeureDebut().isBefore(c1.getHeureFin())) ||
                    (c1.getHeureDebut().isAfter(c2.getHeureDebut())) &&
                    c1.getHeureDebut().isBefore(c2.getHeureFin()))
            {
                return true;
            } 
        }
        return false;
    }  
    
    public Chirurgie getChirurgieById(String s)
    {
        for(Chirurgie c : this.listeChirurgies){
            if(c.getId().equals(s)){
                return c;
            }
        }
        return null;
    }
    
    public void findErreur() {
    	TreeMap <LocalDate, ArrayList<Chirurgie>> dateChirurgies = this.TriParJour();
    	
    	for (Map.Entry<LocalDate, ArrayList<Chirurgie>> entree : dateChirurgies.entrySet()) {
    		LocalDate dateDujour = entree.getKey();
    		ArrayList<Chirurgie> chirurgiesDuJour = entree.getValue();
    		
    		for(int i = 0; i < chirurgiesDuJour.size(); i++) {
    			//Erreur e = new Erreur();
    			Chirurgie c1 = chirurgiesDuJour.get(i);
				while((chirurgiesDuJour.indexOf(c1) != chirurgiesDuJour.size() - 1) && (this.estParallele(c1, chirurgiesDuJour.get(++i))))  {
					Chirurgie c2 = chirurgiesDuJour.get(i);
					
					if((c1.getChirurgien().equals(c2.getChirurgien())) &&
    						c1.getSalle().equals(c2.getSalle())) {
						Erreur e = new ErreurChevauchement();
    					e.addChirurgie(c1);
    					e.addChirurgie(c2);
    					this.listeErreurs.add(e);
    				}
					else if(c1.getChirurgien().equals(c2.getChirurgien())) {
						Erreur e = new ErreurUbiquite();
						e.addChirurgie(c1);
    					e.addChirurgie(c2);
    					this.listeErreurs.add(e);
					}
					else if(c1.getSalle().equals(c2.getSalle())) {
						Erreur e = new ErreurInterference();
						e.addChirurgie(c1);
    					e.addChirurgie(c2);
    					this.listeErreurs.add(e);
					}
					c1 = c2;        				
				}
    		}
		}
    	for(Erreur e : this.listeErreurs) {
    		System.out.println(e.toString());
    	}
    	
    }
}
