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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kormli18
 */
public class Hopital {
    private ArrayList<Chirurgie> listeChirurgies;

    public Hopital() {
        this.listeChirurgies = new ArrayList<Chirurgie>();
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
//                dateChirurgie = new LocalDate(1, 1, 1);
//                //this.listeChirurgies.add(new Chirurgie(tab[0],tab[1],tab[2],tab[3],salle,chirurgien));
//                dateChirurgie = new LocalDate(Integer.parseInt(tabDate[2]),Integer.parseInt(tabDate[1]),Integer.parseInt(tabDate[0]));
                String time = tab[2];                
                DateFormat hms = new SimpleDateFormat("HH:mm:ss");                
                Date heureDeb = hms.parse(time);               
                time = tab[3];
                Date heureFin = hms.parse(time);
                this.listeChirurgies.add(new Chirurgie(tab[0],dateChirurgie,heureDeb,heureFin,salle,chirurgien));
                
               
            }          
            ligne = reader.readLine();
        }
        for (Chirurgie c : this.listeChirurgies){
            System.out.println(c.toString());
        }
    }
    
}
