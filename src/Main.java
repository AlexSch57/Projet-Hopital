
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

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
    public static void main(String[] args) throws IOException, FileNotFoundException, ParseException {
        Hopital h = new Hopital();
        h.init("MiniBase.csv");
        h.TriParJour();
        //h.printListeChirurgies();

        Chirurgie c1 = h.getChirurgieById("13");
        
        System.out.println(c1);
        System.out.println(h.getChirurgieById("14"));
        System.out.println("\n\n");
        System.out.println(h.changementHeureChirurgie(h.getChirurgieById("13"), 13, 0, "retarder"));
        System.out.println(h.getChirurgieById("13"));
        
        System.out.println(h.getDureeMoyenneChirurgie());
        
//        Chirurgie c2 = h.getChirurgieById("25");
//
//
//        
//        for (int i = 0; i < 3; i++) {
//            System.out.println( " étape " + (i+1) + " : \n");
//            h.findErreur();
//            System.out.println("avant résolution : \n");
//            h.printListeErreurs();
//            h.resolveErreur();
//
//            System.out.println("après résolution : \n");
//            h.findErreur();
//            h.printListeErreurs();
//        }
//
//        System.out.println("\n\n\n\n LISTE FIN : \n");
//        h.printListeChirurgies();
//        
//        

    }
}
