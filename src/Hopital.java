
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
 * Classe représentant l'hopital, disposant de l'ensemble des chirurgies
 *
 * @author Alexandre Schwitthal
 * @version 1.0
 */
public class Hopital {

    private ArrayList<Chirurgie> listeChirurgies;
    private ArrayList<Erreur> listeErreurs;
    private LocalTime dureeMoyenneChirurgies;

    public Hopital() {
        this.listeChirurgies = new ArrayList<>();
        this.listeErreurs = new ArrayList<>();
        this.dureeMoyenneChirurgies = LocalTime.of(0, 0);
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
                //if(!(this.listeChirurgies.contains(chir))) {
                this.listeChirurgies.add(chir);
                c.ajoutTempsDeTravail(chir.getDuree());
                //}  
            }
            ligne = reader.readLine();
        }
        Collections.sort(this.listeChirurgies);
        this.findErreur();
        this.dureeMoyenneChirurgies = this.getDureeMoyenneChirurgie();

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
                }
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
            ArrayList<Salle> listeSalles = Salle.getListeSalles();

            // ----- INTERFERENCE ------ //
            if (e instanceof ErreurInterference) {
                // tentative de recherche d'une autre salle disponible
                if (this.changementSalle(listeSalles, listeChirurgiesErreur, dateDuJour)) {

                }

                // tentative de recherche d'un autre chirurgien disponible
                else if (this.changementChirurgien(listeChirurgiesErreur, chirurgiensDuJour)) {

                }
                // tentative de changement de l'heure de la chirurgie 
                else {
                    this.deplacementHeureChirurgie(listeChirurgiesErreur, 10);
                }
            }

            // ----- CHEVAUCHEMENT ------ //
            else if (e instanceof ErreurChevauchement) {
                // tentative de recherche d'une autre salle disponible
                if (this.changementSalle(listeSalles, listeChirurgiesErreur, dateDuJour)) {

                }
                // tentative de changement de l'heure de la chirurgie 
                else {
                    this.deplacementHeureChirurgie(listeChirurgiesErreur, 10);
                }

            }

            // ----- UBIQUITE ------ //
            else if (e instanceof ErreurUbiquite) {
                // tentative de recherche d'un autre chirurgien disponible
                if (this.changementChirurgien(listeChirurgiesErreur, chirurgiensDuJour)) {

                }
                // tentative de changement de l'heure de la chirurgie 
                else {
                    this.deplacementHeureChirurgie(listeChirurgiesErreur, 10);
                }
            }
        }
        Collections.sort(this.listeChirurgies);
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

    public boolean changementChirurgien(ArrayList<Chirurgie> listeChirurgies, ArrayList<Chirurgien> listeChirurgiens) {
        for (Chirurgie c : listeChirurgies) {
            Chirurgien.triListeChirurgiens(listeChirurgiens);
            for (Chirurgien ch : listeChirurgiens) {
                if (this.estDisponibleChirurgien(ch, c)) {
                    c.getChirurgien().retraitTempsDeTravail(c.getDuree());
                    c.setChirurgien(ch);
                    c.getChirurgien().ajoutTempsDeTravail(c.getDuree());
                    return true;
                }
            }
        }
        return false;
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

    public void normalisationHeureChirurgie() {
        LocalTime moyenne = this.dureeMoyenneChirurgies;
        int moyenneEnSeconde = moyenne.getHour() * 3600 + moyenne.getMinute() * 60 + moyenne.getSecond();
        for (Chirurgie c : this.listeChirurgies) {
            if (c.getHeureFin().equals(LocalTime.of(0, 0))) {
                if (c.getDuree() > 120) {
                    c.getChirurgien().retraitTempsDeTravail(c.getDuree());
                    LocalTime newHeureFin = c.getHeureDebut().plusMinutes(120);
                    c.setHeureFin(newHeureFin);
                    c.getChirurgien().ajoutTempsDeTravail(c.getDuree());
                }
            }
            else {
                long dureeLong = c.getDuree();
                int dureeChirurgieEnSeconde = (int) dureeLong * 60;

                if (dureeChirurgieEnSeconde >= moyenneEnSeconde * 1.5) {

                    if (dureeChirurgieEnSeconde >= ((moyenneEnSeconde * 1.5) + (moyenne.getHour() * 3600) + (moyenne.getMinute() * 60))) {
                        c.getChirurgien().retraitTempsDeTravail(c.getDuree());
                        c.setHeureFin(c.getHeureFin().minusHours(moyenne.getHour()));
                        c.setHeureFin(c.getHeureFin().minusMinutes(moyenne.getMinute()));
                        c.setHeureFin(c.getHeureFin().minusSeconds(moyenne.getSecond()));
                        c.getChirurgien().ajoutTempsDeTravail(c.getDuree());
                    }
                    else {
                        long toLong = (long) (moyenneEnSeconde * 1.5);
                        c.getChirurgien().retraitTempsDeTravail(c.getDuree());
                        c.setHeureFin(c.getHeureDebut().plusSeconds(toLong));
                        c.setHeureFin(LocalTime.of(c.getHeureFin().getHour(), c.getHeureFin().getMinute()));
                        c.getChirurgien().ajoutTempsDeTravail(c.getDuree());
                    }
                }
            }
        }
    }

    public void normalisationHeureChirurgieDuJour(LocalDate jour) {
        LocalTime moyenne = this.dureeMoyenneChirurgies;
        int moyenneEnSeconde = moyenne.getHour() * 3600 + moyenne.getMinute() * 60 + moyenne.getSecond();
        for (Chirurgie c : this.listeChirurgies) {
            if (c.getDate().equals(jour)) {
                if (c.getHeureFin().equals(LocalTime.of(0, 0))) {
                    if (c.getDuree() > 120) {
                        c.getChirurgien().retraitTempsDeTravail(c.getDuree());
                        LocalTime newHeureFin = c.getHeureDebut().plusMinutes(120);
                        c.setHeureFin(newHeureFin);
                        c.getChirurgien().ajoutTempsDeTravail(c.getDuree());

                    }
                }
                else {
                    long dureeLong = c.getDuree();
                    int dureeChirurgieEnSeconde = (int) dureeLong * 60;

                    if (dureeChirurgieEnSeconde >= moyenneEnSeconde * 1.5) {

                        if (dureeChirurgieEnSeconde >= ((moyenneEnSeconde * 1.5) + (moyenne.getHour() * 3600) + (moyenne.getMinute() * 60))) {
                            c.getChirurgien().retraitTempsDeTravail(c.getDuree());
                            c.setHeureFin(c.getHeureFin().minusHours(moyenne.getHour()));
                            c.setHeureFin(c.getHeureFin().minusMinutes(moyenne.getMinute()));
                            c.setHeureFin(c.getHeureFin().minusSeconds(moyenne.getSecond()));
                            c.getChirurgien().ajoutTempsDeTravail(c.getDuree());
                        }
                        else {
                            long toLong = (long) (moyenneEnSeconde * 1.5);
                            c.getChirurgien().retraitTempsDeTravail(c.getDuree());
                            c.setHeureFin(c.getHeureDebut().plusSeconds(toLong));
                            c.setHeureFin(LocalTime.of(c.getHeureFin().getHour(), c.getHeureFin().getMinute()));
                            c.getChirurgien().ajoutTempsDeTravail(c.getDuree());
                        }
                    }
                }
            }
        }
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
            if (tentativeChirurgie.getHeureDebut().isBefore(LocalTime.of(7, 0))) {
                return false;
            }
            if (tentativeChirurgie.getHeureFin().isAfter(LocalTime.of(23, 0))) {
                return false;
            }
            if (tentativeChirurgie.getHeureFin().isBefore(LocalTime.of(7, 0))) {
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

    public void deplacementHeureChirurgie(ArrayList<Chirurgie> lesChirurgies, int tempsDecalage) {
        boolean ChangementChirurgie = false;
        for (Chirurgie c : lesChirurgies) {
            int i = 0;
            int limite = 12 * 60 / tempsDecalage;
            while (!((this.changementHeureChirurgie(c, 0, tempsDecalage, "avancer"))
                    || (this.changementHeureChirurgie(c, 0, tempsDecalage, "retarder")))) {
                tempsDecalage += 10;
                i++;
                if (i >= limite) {
                    break;
                }
            }
            if (i < limite) {
                System.out.println(c);
                ChangementChirurgie = true;
                break;
            }
        }
        if (!(ChangementChirurgie)) {
            this.normalisationHeureChirurgieDuJour(lesChirurgies.get(0).getDate());
        }
    }

    public LocalTime getMoyenneHoraireChirurgie() {
        LocalTime lt = LocalTime.of(0, 0);
        long total = 0;
        for (Chirurgie c : this.listeChirurgies) {
            long duree = c.getDuree() / 2;
            LocalTime heureMediane = c.getHeureDebut().plusMinutes(duree);
            total += Chirurgie.getDuree(lt, heureMediane);
        }

        total = total / this.listeChirurgies.size();
        lt = lt.plusMinutes(total);
        return lt;
    }

}
