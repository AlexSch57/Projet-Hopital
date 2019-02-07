
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

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
    public static void main(String[] args){
       //mdr
        Hopital h = new Hopital();
        
        
        Chirurgien c = Chirurgien.getChirurgienById(1);
        fichierBase = "Chirurgies_v2";
        try {
			h.init(fichierBase + ".csv");
		} catch (ChirurgienInexistantException | SalleInexistanteException | IOException | ParseException e) {			
			e.printStackTrace();
		}        
        h.TriParJour();
        //h.printListeChirurgies();
        
        ArrayList<Salle> lesSalles = Paire_Chirurgien_Salle.getSallesDuChirurgien(c);
        
        for(Salle s : lesSalles) {
        	System.out.println(s);
        }
        
        System.out.println("\n\n\n");
        Salle s = Salle.getSalleById(1);
        ArrayList<Chirurgien> lesChirurgiens = Paire_Chirurgien_Salle.getChirurgiensDeLaSalle(s);
        
        for(Chirurgien chir : lesChirurgiens) {
        	System.out.println(chir);
        }
//        //LocalTime moyenne = h.getDureeMoyenneChirurgie();
//        //System.out.println(moyenne.getMinute());
//        //Chirurgie c1 = h.getChirurgieById("13");
//        //h.printListeChirurgies();
//        //System.out.println(c1);
//        //System.out.println(h.getChirurgieById("14"));
//        //System.out.println("\n\n");
//        //System.out.println(h.changementHeureChirurgie(h.getChirurgieById("13"), 13, 0, "retarder"));
////        System.out.println(h.getChirurgieById("6"));
////        h.changementHeureChirurgie(h.getChirurgieById("6"), 0, 240, "retarder");
////        System.out.println(h.getChirurgieById("6"));
////        
////        
//        
//        //System.out.println(h.getDureeMoyenneChirurgie());
//       
//        //h.normalisationHeureChirurgie();
//        
//        //h.printListeChirurgies();
////        Chirurgie c2 = h.getChirurgieById("25");
////
////
//        h.findErreur();
//        //h.printListeErreurs();
//        System.out.println("nombres d'erreurs dans le fichier : " + h.getTailleListeErreurs() + "\n\n");
//        for (int i = 1; i <= 7; i++) {
//            System.out.println( " étape " + (i) + " :");
//            h.findErreur();
//           
//            h.resolveErreur();
//            System.out.println("erreur(s) restante(s) : " + h.getTailleListeErreurs());
//        }
//////
//        System.out.println("\n\n\n\n LISTE FIN : \n");
//        
//        //h.printListeChirurgies();
////        for(Chirurgie c : h.getListeChirurgies()) {
////        	System.out.println(c.toString());
////        }
////        
////        
//        //createOutput(h);
    }
    
    public static void createOutput(Hopital h) throws IOException {
    	String contenuFichier = "ID CHIRURGIE;DATE CHIRURGIE;HEURE_DEBUT CHIRURGIE;HEURE_FIN CHIRURGIE;SALLE;CHIRURGIEN";
    	//contenuFichier += h.toString();
        
    	File fichier = new File("files" + File.separator + "outputs" + File.separator + fichierBase + " " + LocalDate.now() + " " + 
                LocalTime.now().getHour() + "h" + LocalTime.now().getMinute() +  ".csv");
    	fichier.createNewFile();
    	PrintWriter writer = new PrintWriter(fichier, "UTF-8");
        writer.println(contenuFichier);
        for(Chirurgie c : h.getListeChirurgies()) {
            writer.println(c.toString());
        }	
    	writer.close();
    }
}
