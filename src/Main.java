
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
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
       h.printListeChirurgies();
//       Chirurgie c1 = h.getChirurgieById("23");
//       Chirurgie c2 = h.getChirurgieById("25");
//       
//       System.out.println(h.estParallele(c1, c2));
    }   
}
