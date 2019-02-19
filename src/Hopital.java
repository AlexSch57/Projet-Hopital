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
import java.time.Duration;
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
        this.listeChirurgies = new ArrayList<>();
        this.listeErreurs = new ArrayList<>();
    }

    public void init(String nomFichier) throws FileNotFoundException, IOException, ParseException, ChirurgienInexistantException, SalleInexistanteException {
        BufferedReader reader = new BufferedReader(new FileReader(new File("." + File.separator + "files" + File.separator + nomFichier)));

        String ligne = reader.readLine();
        while (ligne != null) {
            if (!ligne.contains("ID CHIRURGIE")) {
                String[] tab = ligne.split(";");
                if (Chirurgien.getChirurgienByName(tab[5]) == null) {
                    throw new ChirurgienInexistantException();
                }
                if (Salle.getSalleByName(tab[4]) == null) {
                    throw new SalleInexistanteException();
                }
                String[] tabDate = tab[1].split("/");

                LocalDate dateChirurgie = LocalDate.of(Integer.parseInt(tabDate[2]), Integer.parseInt(tabDate[1]), Integer.parseInt(tabDate[0]));

                tabDate = tab[2].split(":");
                LocalTime heureDeb = LocalTime.of(Integer.parseInt(tabDate[0]), Integer.parseInt(tabDate[1]), Integer.parseInt(tabDate[2]));

                tabDate = tab[3].split(":");
                LocalTime heureFin = LocalTime.of(Integer.parseInt(tabDate[0]), Integer.parseInt(tabDate[1]), Integer.parseInt(tabDate[2]));
                
                Chirurgien c = Chirurgien.getChirurgienByName(tab[5]);
                Chirurgie chir = new Chirurgie(tab[0], dateChirurgie, heureDeb, heureFin, Salle.getSalleByName(tab[4]), Chirurgien.getChirurgienByName(tab[5]));
                if(!(this.listeChirurgies.contains(chir))) {
                    this.listeChirurgies.add(chir);
                    c.ajoutTempsDeTravail(chir.getDuree());
                }  
            }
            ligne = reader.readLine();
        }
        Collections.sort(this.listeChirurgies);
        this.findErreur();
        this.normalisationHeureChirurgie();
    }

    public void printListeChirurgies() {
        for (Chirurgie c : this.listeChirurgies) {
            System.out.println(c.toString());
        }
    }

    public ArrayList<Chirurgie> getListeChirurgies() {
        return this.listeChirurgies;
    }

    public void printListeErreurs() {
        System.out.println("Nombre d'erreur(s) : " + this.listeErreurs.size());
        for (Erreur e : this.listeErreurs) {
            System.out.println(e.toString());
        }
    }

    public int getTailleListeErreurs() {
        return this.listeErreurs.size();
    }

    public String toString() {
        this.TriParJour();
        String result = "";
        for (Chirurgie c : this.listeChirurgies) {
            result += c.toString() + "\n";
        }
        return result;
    }

    /**
     *
     * @return retourne une map ayant pour clé une date
     */
    public TreeMap<LocalDate, ArrayList<Chirurgie>> TriParJour() {
        HashMap<LocalDate, ArrayList<Chirurgie>> m = new HashMap<>();

        LocalDate dateDuJour = LocalDate.now();
        ArrayList<Chirurgie> chirurgieDuJour = new ArrayList<>();

        Iterator<Chirurgie> it = this.listeChirurgies.iterator();
        while (it.hasNext()) {
            Chirurgie c = it.next();
            if (dateDuJour.equals(c.getDate())) {
                chirurgieDuJour.add(c);
            } 
            else {
                if (chirurgieDuJour.size() != 0) {
                    m.put(dateDuJour, chirurgieDuJour);
                }

                dateDuJour = c.getDate();

                chirurgieDuJour = new ArrayList<>();
                chirurgieDuJour.add(c);
            }
            if (!(it.hasNext())) {
                m.put(dateDuJour, chirurgieDuJour);
            }
        }

        TreeMap<LocalDate, ArrayList<Chirurgie>> map = new TreeMap<>(m);
        return map;
    }

    public Chirurgie getChirurgieById(String s) {
        for (Chirurgie c : this.listeChirurgies) {
            if (c.getId().equals(s)) {
                return c;
            }
        }
        return null;
    }

    public void findErreur() {
        this.listeErreurs = new ArrayList<>();
        TreeMap<LocalDate, ArrayList<Chirurgie>> dateChirurgies = this.TriParJour();

        for (Map.Entry<LocalDate, ArrayList<Chirurgie>> entree : dateChirurgies.entrySet()) {
            ArrayList<Chirurgie> chirurgiesDuJour = entree.getValue();

            for (int i = 0; i < chirurgiesDuJour.size(); i++) {
                Chirurgie c1 = chirurgiesDuJour.get(i);

                if (chirurgiesDuJour.indexOf(c1) != chirurgiesDuJour.size() - 1) {
                    for (int j = 0; j < chirurgiesDuJour.size(); j++) {
                        Chirurgie c2 = chirurgiesDuJour.get(j);
                        if (c1 != c2) {
                            if (c1.estParallele(c2)) {
                                if ((c1.getChirurgien().equals(c2.getChirurgien()))
                                        && c1.getSalle().equals(c2.getSalle())) {
                                    Erreur e = new ErreurChevauchement();
                                    e.addChirurgie(c1);
                                    e.addChirurgie(c2);
                                    if (!this.listeErreurs.contains(e)) {
                                        this.listeErreurs.add(e);
                                    }
                                } 
                                else if (c1.getChirurgien().equals(c2.getChirurgien())) {
                                    Erreur e = new ErreurUbiquite();
                                    e.addChirurgie(c1);
                                    e.addChirurgie(c2);
                                    if (!this.listeErreurs.contains(e)) {
                                        this.listeErreurs.add(e);
                                    }
                                } 
                                else if (c1.getSalle().equals(c2.getSalle())) {
                                    Erreur e = new ErreurInterference();
                                    e.addChirurgie(c1);
                                    e.addChirurgie(c2);
                                    if (!this.listeErreurs.contains(e)) {
                                        this.listeErreurs.add(e);
                                    }
                                }
                            }
                        }
                    }
//                    Chirurgie c2 = chirurgiesDuJour.get(i+1);
//                    if(this.estParallele(c1, c2)){
//                        if ((c1.getChirurgien().equals(c2.getChirurgien()))
//                            && c1.getSalle().equals(c2.getSalle())) {
//                            Erreur e = new ErreurChevauchement();
//                            e.addChirurgie(c1);
//                            e.addChirurgie(c2);
//                            this.listeErreurs.add(e);
//                        } 
//                        else if (c1.getChirurgien().equals(c2.getChirurgien())) {
//                            Erreur e = new ErreurUbiquite();
//                            e.addChirurgie(c1);
//                            e.addChirurgie(c2);
//                            this.listeErreurs.add(e);
//                        } 
//                        else if (c1.getSalle().equals(c2.getSalle())) {
//                            Erreur e = new ErreurInterference();
//                            e.addChirurgie(c1);
//                            e.addChirurgie(c2);
//                            this.listeErreurs.add(e);
//                        }
//                    }
                }
//                while ((chirurgiesDuJour.indexOf(c1) != chirurgiesDuJour.size() - 1) && (this.estParallele(c1, chirurgiesDuJour.get(++i)))) {
//                    Chirurgie c2 = chirurgiesDuJour.get(i);
//
//                    if ((c1.getChirurgien().equals(c2.getChirurgien()))
//                            && c1.getSalle().equals(c2.getSalle())) {
//                        Erreur e = new ErreurChevauchement();
//                        e.addChirurgie(c1);
//                        e.addChirurgie(c2);
//                        this.listeErreurs.add(e);
//                    } 
//                    else if (c1.getChirurgien().equals(c2.getChirurgien())) {
//                        Erreur e = new ErreurUbiquite();
//                        e.addChirurgie(c1);
//                        e.addChirurgie(c2);
//                        this.listeErreurs.add(e);
//                    } 
//                    else if (c1.getSalle().equals(c2.getSalle())) {
//                        Erreur e = new ErreurInterference();
//                        e.addChirurgie(c1);
//                        e.addChirurgie(c2);
//                        this.listeErreurs.add(e);
//                    }
//                    c1 = c2;
//                }
            }
        }
    }

    public void resolveErreur() {
        Iterator<Erreur> it = this.listeErreurs.iterator();
        while (it.hasNext()) {
            Erreur e = it.next();
            ArrayList<Chirurgie> listeChirurgiesErreur = e.getListeChirurgiesErreur();
            LocalDate dateDuJour = listeChirurgiesErreur.get(0).getDate();
            ArrayList<Chirurgien> chirurgiensDuJour = this.getChirurgiensDuJour(dateDuJour);
            ArrayList<Salle> listeSalles = this.getListeSalles();

            // ----- INTERFERENCE ------ //
            if (e instanceof ErreurInterference) {
                // tentative de recherche d'une autre salle disponible
                if (this.changementSalle(listeSalles, listeChirurgiesErreur, dateDuJour)) {
                    
                }

                // tentative de recherche d'un autre chirurgien disponible
                else if (this.changementChirurgien2(listeChirurgiesErreur, chirurgiensDuJour)) {

                } 
                // tentative de changement de l'heure de la chirurgie 
                else {
                    for (Chirurgie c : e.listeChirurgiesErreur) {
                        int i = 0;
                        int tempsDecalage = 10;
                        while (!((this.changementHeureChirurgie(c, 0, tempsDecalage, "avancer")) 
                                || (this.changementHeureChirurgie(c, 0, tempsDecalage, "retarder")))) {
                            tempsDecalage += 10;
                            i++;
                            if(i >= 72) {
                                break;
                            }
                        }
                        if(i < 72) {
                            System.out.println(c);
                            i = 0;
                            break;   
                        }
                    }
                }
            } 
            // ----- CHEVAUCHEMENT ------ //
            else if (e instanceof ErreurChevauchement) {
                // tentative de recherche d'une autre salle disponible
                if (this.changementSalle(listeSalles, listeChirurgiesErreur, dateDuJour)) {
                    
                }
                
            } 
            // ----- UBIQUITE ------ //
            else if (e instanceof ErreurUbiquite) {
                // tentative de recherche d'un autre chirurgien disponible
                if (this.changementChirurgien2(listeChirurgiesErreur, chirurgiensDuJour)) {
                    
                } 
                // tentative de changement de l'heure de la chirurgie 
                else {
                    for (Chirurgie c : e.listeChirurgiesErreur) {
                        int i = 0;
                        int tempsDecalage = 10;
                        while (!((this.changementHeureChirurgie(c, 0, tempsDecalage, "avancer")) 
                                || (this.changementHeureChirurgie(c, 0, tempsDecalage, "retarder")))) {
                            tempsDecalage += 10;
                            i++;
                            if(i >= 72) {
                                break;
                            }
                        }
                        if( i < 72) {
                            System.out.println(c);
                            break;
                        }
                    }
                }
            }
        }
        Collections.sort(this.listeChirurgies);
    }

    /**
     * @Depreciated
     */
    public boolean isActifSalle(Salle s, LocalDate jour, LocalTime heureDebut, LocalTime heureFin) {

        for (Chirurgie ch : this.listeChirurgies) {
            if (ch.getDate().equals(jour)) {
                if (ch.getSalle().equals(s)) {
                    if (ch.getHeureDebut().equals(heureDebut)) {
                        return true;
                    }
                    if ((ch.getHeureDebut().isAfter(heureDebut))
                            && ch.getHeureDebut().isBefore(heureFin)
                            || ((heureDebut.isAfter(ch.getHeureDebut()))
                            && heureDebut.isBefore(ch.getHeureFin()))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean estDisponibleSalle(Salle s, Chirurgie c) {
        for (Chirurgie ch : this.listeChirurgies) {
            if (ch.getDate().equals(c.getDate())) {
                if (ch.getSalle().equals(s)) {
                    if (ch.estParallele(c)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * @Depreciated
     */
    public boolean isActifChirurgien(Chirurgien c, LocalDate jour, LocalTime heureDebut, LocalTime heureFin) {

        for (Chirurgie ch : this.listeChirurgies) {
            if (ch.getDate().equals(jour)) {
                if (ch.getChirurgien().equals(c)) {
                    if (ch.getHeureDebut().equals(heureDebut)) {
                        return true;
                    } 
                    else if ((ch.getHeureDebut().isAfter(heureDebut))
                            && ch.getHeureDebut().isBefore(heureFin)
                            || ((heureDebut.isAfter(ch.getHeureDebut()))
                            && heureDebut.isBefore(ch.getHeureFin()))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean estDisponibleChirurgien(Chirurgien c, Chirurgie chir) {

        for (Chirurgie ch : this.listeChirurgies) {
            if (ch.getDate().equals(chir.getDate())) {
                if (ch.getChirurgien().equals(c)) {
                    if (ch.estParallele(chir)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public ArrayList<Chirurgien> getChirurgiensDuJour(LocalDate ld) {
        ArrayList<Chirurgien> listeChirurgiens = new ArrayList<>();
        for (Chirurgie c : this.listeChirurgies) {
            if (c.getDate().equals(ld)) {
                if (!(listeChirurgiens.contains(c.getChirurgien()))) {
                    listeChirurgiens.add(c.getChirurgien());
                }
            }
        }
        return listeChirurgiens;
    }

    public ArrayList<Chirurgie> getChirurgiesDuJour(LocalDate ld) {
        ArrayList<Chirurgie> listeChirurgies = new ArrayList<>();
        for (Chirurgie c : this.listeChirurgies) {
            if (c.getDate().equals(ld)) {
                if (!(listeChirurgies.contains(c))) {
                    listeChirurgies.add(c);
                }
            }
        }
        return listeChirurgies;
    }

    public ArrayList<Salle> getListeSalles() {
        ArrayList<Salle> listeSalles = new ArrayList<>();
        for (Chirurgie c : this.listeChirurgies) {
            if (!(listeSalles.contains((c.getSalle())))) {
                listeSalles.add(c.getSalle());
            }
        }
        return listeSalles;
    }

    public boolean changementSalle(ArrayList<Salle> listeSalles, ArrayList<Chirurgie> listeChirurgies, LocalDate dateDuJour) {
        for (Salle s : listeSalles) {
            for (Chirurgie ch : listeChirurgies) {
                if (!(s.equals(ch.getSalle()))) {
                    if (estDisponibleSalle(s, ch)) {

                        ArrayList<Salle> lesSallesDuChirurgien = Paire_Chirurgien_Salle.getSallesDuChirurgien(ch.getChirurgien());

                        if (lesSallesDuChirurgien.contains(s)) {
                            ch.setSalle(s);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean changementChirurgien(ArrayList<Chirurgien> listeChirurgiens, ArrayList<Chirurgie> listeChirurgies, LocalDate dateDuJour) {
        for (Chirurgien c : listeChirurgiens) {
            for (Chirurgie ch : listeChirurgies) {
                if (!(c.equals(ch.getChirurgien()))) {
                    if (estDisponibleChirurgien(c, ch)) {

                        ArrayList<Chirurgien> lesChirurgiensDeLaSalle = Paire_Chirurgien_Salle.getChirurgiensDeLaSalle(ch.getSalle());

                        if (lesChirurgiensDeLaSalle.contains(c)) {
                            ch.setChirurgien(c);
                            return true;
                        }
                    }
                }
            }

        }
        return false;
    }

    public boolean changementChirurgien2(ArrayList<Chirurgie> listeChirurgies, ArrayList<Chirurgien> listeChirurgiens) {
        for (Chirurgie c : listeChirurgies) {
            for (Chirurgien ch : listeChirurgiens) {
                if (this.estDisponibleChirurgien(ch, c)) {
                    c.setChirurgien(ch);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean verificationCouple() {
        int i = 0;
        for (Chirurgie c : this.listeChirurgies) {

            boolean coupleExistant = false;
            for (Paire_Chirurgien_Salle pcs : Paire_Chirurgien_Salle.values()) {
                if (pcs.getChirurgien().equals(c.getChirurgien()) && pcs.getSalle().equals(c.getSalle())) {
                    coupleExistant = true;
                    i++;
                }
            }
            if (!(coupleExistant)) {
                return false;
            }
        }
        System.out.println("couple valide : " + i + "/" + this.listeChirurgies.size());
        return true;
    }

    public LocalTime getDureeMoyenneChirurgie() {
        float total = 0;
        for (Chirurgie c : this.listeChirurgies) {
            total += c.getDuree() * 60;
        }
        float moyenne = total / this.listeChirurgies.size();

        int min = (int) moyenne / 60;
        min = min % 60;
        int heure = (int) moyenne / 3600;

        return LocalTime.of(heure, min);
    }

    /* A FINIR */
    public void normalisationHeureChirurgie() {
        LocalTime moyenne = this.getDureeMoyenneChirurgie();
        int moyenneEnSeconde = moyenne.getHour() * 3600 + moyenne.getMinute() * 60 + moyenne.getSecond();
        //System.out.println(moyenneEnSeconde);
        for (Chirurgie c : this.listeChirurgies) {
            if(c.getHeureFin().equals(LocalTime.of(0, 0))) {
                if(c.getDuree() > 120) {
                    LocalTime newHeureFin = c.getHeureDebut().plusMinutes(120);
                    c.setHeureFin(newHeureFin);
                }
            }
            else {
                long dureeLong = c.getDuree();
                int dureeChirurgieEnSeconde = (int) dureeLong * 60;

                //Duration dureeChirurgie = Duration.between(c.getHeureDebut(), c.getHeureFin());
                if (dureeChirurgieEnSeconde >= moyenneEnSeconde * 1.5) {
                    //System.out.println(c);

                    if (dureeChirurgieEnSeconde >= ((moyenneEnSeconde * 1.5) + (moyenne.getHour() * 3600) + (moyenne.getMinute() * 60))) {
                        //System.out.println((moyenneEnSeconde * 1.5) + (moyenne.getHour() * 3600) + (moyenne.getMinute() * 60));
                        c.setHeureFin(c.getHeureFin().minusHours(moyenne.getHour()));
                        c.setHeureFin(c.getHeureFin().minusMinutes(moyenne.getMinute()));
                        c.setHeureFin(c.getHeureFin().minusSeconds(moyenne.getSecond()));
                    } 
                    else {
                        /* A FAIRE */
                        long toLong = (long) (moyenneEnSeconde * 1.5);
                        //System.out.println(toLong);
                        c.setHeureFin(c.getHeureDebut().plusSeconds(toLong));
                        c.setHeureFin(LocalTime.of(c.getHeureFin().getHour(), c.getHeureFin().getMinute()));
                        //System.out.println(c + " mdr changement");
                    }
                } 
            }
        }
    }

    public void normalisationHeureChirurgie(LocalDate jour) {

    }

    public boolean changementHeureChirurgie(Chirurgie c, int heure, int minute, String typeChangement) {

        minute = minute + (heure * 60);
        LocalTime newHeureDebut = LocalTime.of(0, 0);
        LocalTime newHeureFin = LocalTime.of(0, 0);

        if (typeChangement.equals("avancer")) {
            newHeureDebut = c.getHeureDebut().minusMinutes(minute);
            newHeureFin = c.getHeureFin().minusMinutes(minute);
        } 
        else if (typeChangement.equals("retarder")) {
            newHeureDebut = c.getHeureDebut().plusMinutes(minute);
            newHeureFin = c.getHeureFin().plusMinutes(minute);
        } 
        else {
            throw new IllegalArgumentException("le 4ème argument de la methode changementHeureChirurgie doit être \"avancer\" ou \"retarder\"");
        }

        ArrayList<Chirurgie> listeChirurgiesDuJour = this.getChirurgiesDuJour(c.getDate());
        Chirurgie tentativeChirurgie = new Chirurgie(c.getId(), c.getDate(), newHeureDebut, newHeureFin, c.getSalle(), c.getChirurgien());
        boolean datePossible = true;

        for (Chirurgie ch : listeChirurgiesDuJour) {
            if ((tentativeChirurgie.estParallele(ch)
                    && ((tentativeChirurgie.getChirurgien().equals(ch.getChirurgien())) || (tentativeChirurgie.getSalle().equals(ch.getSalle()))))) {
              
                    datePossible = false;            
            }           
        }

        if (datePossible) {
            if(tentativeChirurgie.getHeureDebut().isBefore(LocalTime.of(7,0))) {
                return false;
            }
            if(tentativeChirurgie.getHeureFin().isAfter(LocalTime.of(23, 0))) {
                return false;
            }
            if(tentativeChirurgie.getHeureFin().isBefore(LocalTime.of(7,0))) {
                return false;
            }
            c.setHeureDebut(newHeureDebut);
            c.setHeureFin(newHeureFin);
            return true;
        } 
        else {
            return false;
        }
    }

    public LocalTime getHeureLimiteDebut() {
        LocalTime lt = LocalTime.of(23, 59);
        for (Chirurgie c : this.listeChirurgies) {
            if (c.getHeureDebut().isBefore(lt)) {
                lt = c.getHeureDebut();
            }
        }
        return lt;
    }

    public LocalTime getHeureLimiteFin() {
        LocalTime lt = LocalTime.of(0, 1);
        for (Chirurgie c : this.listeChirurgies) {
            if (c.getHeureFin().isAfter(lt)) {
                lt = c.getHeureFin();
            }
        }
        return lt;
    }
    
    public LocalTime getMoyenneHoraireChirurgie() {
        LocalTime lt = LocalTime.of(0,0);
        long total = 0;
        for(Chirurgie c : this.listeChirurgies) {
            long duree = c.getDuree() / 2;
            LocalTime heureMediane = c.getHeureDebut().plusMinutes(duree);
            total += Chirurgie.getDuree(lt, heureMediane);
        }
        
        total = total / this.listeChirurgies.size();
        lt = lt.plusMinutes(total);
        return lt;
    }

}
