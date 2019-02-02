
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Collections;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author kormli18
 */
public class Main {
	private static String fichierBase;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, FileNotFoundException, ParseException {
        Hopital h = new Hopital();
        fichierBase = "MiniBase";
        h.init(fichierBase+".csv");        
        h.TriParJour();
        //h.printListeChirurgies();
        //LocalTime moyenne = h.getDureeMoyenneChirurgie();
        //System.out.println(moyenne.getMinute());
        //Chirurgie c1 = h.getChirurgieById("13");
        //h.printListeChirurgies();
        //System.out.println(c1);
        //System.out.println(h.getChirurgieById("14"));
        //System.out.println("\n\n");
        //System.out.println(h.changementHeureChirurgie(h.getChirurgieById("13"), 13, 0, "retarder"));
//        System.out.println(h.getChirurgieById("6"));
//        h.changementHeureChirurgie(h.getChirurgieById("6"), 0, 240, "retarder");
//        System.out.println(h.getChirurgieById("6"));
//        
//        
        System.out.println("GNEU GNEU GNEU GNEU CA MARCHE PAS \n\n\n\n\n\n");
        //System.out.println(h.getDureeMoyenneChirurgie());
       
        h.normalisationHeureChirurgie();
        
        //h.printListeChirurgies();
//        Chirurgie c2 = h.getChirurgieById("25");
//
//
//        
        for (int i = 0; i < 3; i++) {
            System.out.println( " étape " + (i+1) + " : \n");
            h.findErreur();
            System.out.println("avant résolution : \n");
            h.printListeErreurs();
            h.resolveErreur();

            System.out.println("après résolution : \n");
            h.findErreur();
            h.printListeErreurs();
        }
////
        System.out.println("\n\n\n\n LISTE FIN : \n");
        
        h.printListeChirurgies();
//        
//        
        //createOutput(h);
    }
    
    public static void createOutput(Hopital h) throws IOException {
    	String contenuFichier = "ID CHIRURGIE;DATE CHIRURGIE;HEURE_DEBUT CHIRURGIE;HEURE_FIN CHIRURGIE;SALLE;CHIRURGIEN\n";
    	contenuFichier += h.toString();
    	File fichier = new File("files" + File.separator + "outputs" + File.separator + fichierBase + " " + LocalDate.now() + " " + 
                LocalTime.now().getHour() + "h" + LocalTime.now().getMinute() +  ".csv");
    	fichier.createNewFile();
    	PrintWriter writer = new PrintWriter(fichier, "UTF-8");
    	writer.println(contenuFichier);    	
    	writer.close();
    }
}
