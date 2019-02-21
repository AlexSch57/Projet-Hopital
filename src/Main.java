
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.time.Duration;
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
      
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, FileNotFoundException, ParseException, ChirurgienInexistantException, SalleInexistanteException {
        
        Menu m = new Menu();
        //boolean mdr = true;
        while(m.isActif()) {
            m.displayMenu();
            m.switchChoix();
        }
       //test nouvelle branche
        Hopital h = new Hopital();
//        
//
        String fichierBase = "Chirurgies_v2";
        try {
            h.init(fichierBase + ".csv");
        } 
        catch (ChirurgienInexistantException | SalleInexistanteException | IOException | ParseException e) {			
            e.printStackTrace();
        }        

        h.TriParJour();
        h.findErreur();

        LocalTime moyenne = h.getDureeMoyenneChirurgie();
        
        
        
        //System.out.println(h.getTailleListeErreurs());
//        
//        h.verificationCouple();
//        //h.printListeChirurgies();
//
//        

////        //LocalTime moyenne = h.getDureeMoyenneChirurgie();
////        //System.out.println(moyenne.getMinute());
////        //Chirurgie c1 = h.getChirurgieById("13");
////        //h.printListeChirurgies();
////        //System.out.println(c1);
////        //System.out.println(h.getChirurgieById("14"));
////        //System.out.println("\n\n");
////        //System.out.println(h.changementHeureChirurgie(h.getChirurgieById("13"), 13, 0, "retarder"));
//////        System.out.println(h.getChirurgieById("6"));
//////        h.changementHeureChirurgie(h.getChirurgieById("6"), 0, 240, "retarder");
//////        System.out.println(h.getChirurgieById("6"));
//////        
//////        
////        
////        //System.out.println(h.getDureeMoyenneChirurgie());
////       
        	h.normalisationHeureChirurgie();
////        
////        //h.printListeChirurgies();
//////        Chirurgie c2 = h.getChirurgieById("25");
//////
//////
//        h.findErreur();
//        

//        
//        //h.printListeErreurs();
        System.out.println("nombres d'erreurs dans le fichier : " + h.getTailleListeErreurs() + "\n\n");
        //h.printListeErreurs();
        int nbEtape = 1;
        ArrayList<Integer> nbErreursParEtape = new ArrayList<>();
       
        while(h.getTailleListeErreurs() > 0) {
            nbErreursParEtape.add(h.getTailleListeErreurs());
            if(nbErreursParEtape.size() > 2) {
                if(((nbErreursParEtape.get(nbErreursParEtape.size() - 1)).equals(nbErreursParEtape.get(nbErreursParEtape.size() - 2)))
                    && ((nbErreursParEtape.get(nbErreursParEtape.size() - 1)).equals(nbErreursParEtape.get(nbErreursParEtape.size() - 3)))) {
                    
                        System.out.println("Impossible de resoudre toutes les erreurs");
                        h.printListeErreurs();
                        break;
                }
                if(nbEtape > 50)  {
                	h.printListeErreurs();
                	break;
                }
            }
            System.out.println( " étape " + (nbEtape) + " :");
            h.findErreur();
//           
            h.resolveErreur();
            System.out.println("erreur(s) restante(s) : " + h.getTailleListeErreurs());
            nbEtape++;
        }        
        
        //createOutput(h);
//    	Chirurgie c1 = h.getChirurgieById("12031");
//    	Chirurgie c2 = h.getChirurgieById("12023");
//    
//    	
//    	System.out.println(c1);
//    	System.out.println(c2);
//    
//    	
//
//    	System.out.println(c1.estParallele(c2));
//    	System.out.println(c2.estParallele(c1));

//////
//        System.out.println("\n\n\n\n LISTE FIN : \n");
//        
//        //h.printListeChirurgies();
////        for(Chirurgie c : h.getListeChirurgies()) {
////        	System.out.println(c.toString());
////        }
////        
////        
//        try {
//            createOutput(h);	
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//        h.verificationCouple();
    	
    }
    
    public static void createOutput(Hopital h) throws IOException {
    	String contenuFichier = "ID CHIRURGIE;DATE CHIRURGIE;HEURE_DEBUT CHIRURGIE;HEURE_FIN CHIRURGIE;SALLE;CHIRURGIEN";
    	//contenuFichier += h.toString();
        
        String formalisationMinute = "" + LocalTime.now().getMinute();
        if(LocalTime.now().getMinute() < 10) {
            formalisationMinute = "0" + LocalTime.now().getMinute();
        }
        String nomFichier = "abc" + " " + LocalDate.now() + " " + LocalTime.now().getHour() + "h" + formalisationMinute +  ".csv";
        
    	File fichier = new File("files" + File.separator + "outputs" + File.separator + nomFichier);
    	fichier.createNewFile();
    	PrintWriter writer = new PrintWriter(fichier, "UTF-8");
        writer.println(contenuFichier);
        for(Chirurgie c : h.getListeChirurgies()) {
            writer.println(c.toString());
        }	
    	writer.close();
        System.out.println("fichier exporté ! nom : " + nomFichier);
    }
}
