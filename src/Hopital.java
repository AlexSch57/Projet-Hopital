
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
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

    /**
     * Initialise la classe Hopital, à l'aide du fichier passé en paramètre, il doit être de type CSV
     *
     * @param nomFichier : String étant le nom d'un fichier, elle doit être présente dans le dossier files
     */
    public void init(String nomFichier) throws FileNotFoundException, IOException, ParseException, ChirurgienInexistantException, SalleInexistanteException {
        BufferedReader reader = new BufferedReader(new FileReader(new File("." + File.separator + "files" + File.separator + nomFichier)));

        String ligne = reader.readLine();
        while (ligne != null) {
            if (!ligne.contains("ID CHIRURGIE")) {
                String[] tab = ligne.split(";");

                // récupération du chirurgien
                if (Chirurgien.getChirurgienByName(tab[5]) == null) {
                    throw new ChirurgienInexistantException();
                }

                // récupération de la salle
                if (Salle.getSalleByName(tab[4]) == null) {
                    throw new SalleInexistanteException();
                }

                // récupération de la date
                String[] tabDate = tab[1].split("/");
                LocalDate dateChirurgie = LocalDate.of(Integer.parseInt(tabDate[2]), Integer.parseInt(tabDate[1]), Integer.parseInt(tabDate[0]));

                // récupération de l'heure de début
                tabDate = tab[2].split(":");
                LocalTime heureDeb = LocalTime.of(Integer.parseInt(tabDate[0]), Integer.parseInt(tabDate[1]), Integer.parseInt(tabDate[2]));

                // récupération de l'heure de fin
                tabDate = tab[3].split(":");
                LocalTime heureFin = LocalTime.of(Integer.parseInt(tabDate[0]), Integer.parseInt(tabDate[1]), Integer.parseInt(tabDate[2]));

                // ajout de la chirurgie à la liste des chirurgies
                Chirurgien c = Chirurgien.getChirurgienByName(tab[5]);
                Chirurgie chir = new Chirurgie(tab[0], dateChirurgie, heureDeb, heureFin, Salle.getSalleByName(tab[4]), Chirurgien.getChirurgienByName(tab[5]));
                this.listeChirurgies.add(chir);

                // ajout du temps de travail au chirurgien, égal à la durée de la chirurgie
                c.ajoutTempsDeTravail(chir.getDuree());
            }
            ligne = reader.readLine();
        }
        Collections.sort(this.listeChirurgies);

        // initialisation des possibles erreurs et de la durée moyenne des chirurgies
        this.findErreur();
        this.dureeMoyenneChirurgies = this.getDureeMoyenneChirurgie();
        int total = 0;
        for (Chirurgie c : this.listeChirurgies) {
            total += c.getDuree();
        }
    }

    /**
     * Affiche la liste des chrirugies
     */
    public void printListeChirurgies() {
        for (Chirurgie c : this.listeChirurgies) {
            System.out.println(c.toString());
        }
    }

    public ArrayList<Chirurgie> getListeChirurgies() {
        return this.listeChirurgies;
    }

    /**
     * Affiche la liste des erreurs
     */
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
     * Retourne une map, ayant pour clé une date et pour valeur une liste des chirurgies le but est de récupérer la
     * liste des chirurgies (valeur) de chaque jour (clé)
     *
     * @return TreeMap <LocalDate, ArrayList<Chirurgie>>
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

    /**
     *
     * @param s : String correspond à un id d'une chirurgie
     * @return Chirurgie : retourne une Chirurgie, ayant pour id la string passé en paramètre. Elle doit être présente
     * dans la liste des chirurgies de l'hopital
     */
    public Chirurgie getChirurgieById(String s) {
        for (Chirurgie c : this.listeChirurgies) {
            if (c.getId().equals(s)) {
                return c;
            }
        }
        return null;
    }

    /**
     *
     * Cherche les erreurs présente dans la base, elles sont stocké dans l'attribut listeErreurs
     */
    public void findErreur() {
        this.listeErreurs = new ArrayList<>();
        TreeMap<LocalDate, ArrayList<Chirurgie>> dateChirurgies = this.TriParJour();

        for (Map.Entry<LocalDate, ArrayList<Chirurgie>> entree : dateChirurgies.entrySet()) {
            ArrayList<Chirurgie> chirurgiesDuJour = entree.getValue();

            // parcours les chirurgies de chaques jours
            for (int i = 0; i < chirurgiesDuJour.size(); i++) {
                Chirurgie c1 = chirurgiesDuJour.get(i);

                /* 
                	on vérifie qu'il ne s'agit pas de la dernière chirurgie du jour
                	car l'on effectue une comparaison entre la chirurgie courante et sa suivante
                 */
                if (chirurgiesDuJour.indexOf(c1) != chirurgiesDuJour.size() - 1) {
                    // l'on parcours à nouveau, de façon imbriqué, la liste des chirurgies du jour
                    for (int j = 0; j < chirurgiesDuJour.size(); j++) {
                        Chirurgie c2 = chirurgiesDuJour.get(j);
                        if (c1 != c2) {
                            /* 
                        	 * si c1 et c2 sont parallèle (partage un temps donné)
                        	 * 	alors l'on effectue des vérifications d'égalité d'attributs
                        	 * 		si ils n'ont pas d'attributs communs (autres que la date et l'heure)
                        	 * 			alors il n'y a pas d'erreur
                        	 * 	  	sinon 
                        	 * 			si :
                        	 * 				le chirurgien et la salle sont communs : Chevauchement
                        	 * 				le chirurgien est commun : Ubiquite
                        	 * 				la salle est commune : Interference
                             */
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

    /**
     *
     * Resout les erreurs présentes dans l'attribut listeErreurs
     */
    public void resolveErreur() {
        int nbChangementSalle = 0;
        int nbChangementChirurgien = 0;
        int nbChangementHeure = 0;
        int nbReduction = 0;
        // parcours de la liste des erreurs
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
                if (this.changementSalle(listeSalles, listeChirurgiesErreur)) {
                    nbChangementSalle++;
                }

                // tentative de changement de l'heure de la chirurgie 
                else if (this.deplacementHeureChirurgie(listeChirurgiesErreur, 10)) {
                    nbChangementHeure++;
                }

                else {
                    this.normalisationAleatoire(listeChirurgiesErreur);
                    nbReduction++;
                }
            }

            // ----- CHEVAUCHEMENT ------ //
            else if (e instanceof ErreurChevauchement) {
                // tentative de recherche d'une autre salle disponible
                if (this.changementSalle(listeSalles, listeChirurgiesErreur)) {
                    nbChangementSalle++;
                }
                
                // tentative de recherche d'un autre chirurgien disponible
                else if (this.changementChirurgien(chirurgiensDuJour, listeChirurgiesErreur)) {
                    nbChangementChirurgien++;
                }
                
                // tentative de changement de l'heure de la chirurgie 
                else if (this.deplacementHeureChirurgie(listeChirurgiesErreur, 10)) {
                    nbChangementHeure++;
                }
                
                else {
                    this.normalisationAleatoire(listeChirurgiesErreur);
                    nbReduction++;
                }

            }

            // ----- UBIQUITE ------ //
            else if (e instanceof ErreurUbiquite) {

                // tentative de recherche d'un autre chirurgien disponible
                if (this.changementChirurgien(chirurgiensDuJour, listeChirurgiesErreur)) {
                    nbChangementChirurgien++;
                }

                // tentative de changement de l'heure de la chirurgie 
                else if (this.deplacementHeureChirurgie(listeChirurgiesErreur, 10)) {
                    nbChangementHeure++;
                }

                else {
                    this.normalisationAleatoire(listeChirurgiesErreur);
                    nbReduction++;
                }
            }
        }
        System.out.println("Nombre de changement de salle : " + nbChangementSalle);
        System.out.println("Nombre de changement de chirurgien : " + nbChangementChirurgien);
        System.out.println("Nombre de changement d'heure : " + nbChangementHeure);
        System.out.println("Nombre de reduction d'heure : " + nbReduction);
        Collections.sort(this.listeChirurgies);
    }

    /**
     *
     * @param s : objet de type Salle
     * @param c : objet de type Chirurgie
     * @return true : si la Salle s est disponible durant la durée de la chirurgie c false sinon
     */
    public boolean estDisponibleSalle(Salle s, Chirurgie c) {
        // parcoure la liste des chirurgies
        for (Chirurgie ch : this.listeChirurgies) {
            // si la date de la chirurgie courante est égal à la chirurgie passé en paramètre
            if (ch.getDate().equals(c.getDate())) {
                // si elles partagent la même salle
                if (ch.getSalle().equals(s)) {
                    // si elles sont en parallèle, alors l'on renvoie false
                    if (ch.estParallele(c)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     *
     * @param chir : objet de type Chirurgien
     * @param c : objet de type Chirurgie
     * @return true : si le chirurgien chir est disponible durant la durée de la chirurgie c false sinon
     */
    public boolean estDisponibleChirurgien(Chirurgien chir, Chirurgie c) {
        for (Chirurgie ch : this.listeChirurgies) {
            if (ch.getDate().equals(c.getDate())) {
                if (ch.getChirurgien().equals(chir)) {
                    if (ch.estParallele(c)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     *
     * @param ld : objet de type LocalDate
     * @return ArrayList<Chirurgien> : retourne la liste des chirurgien pour la journée passé en paramètre
     */
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

    /**
     *
     * @param ld : objet de type LocalDate
     * @return ArrayList<Chirurgie> : retourne la liste des chirurgie pour la journée passé en paramètre
     */
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

    /**
     *
     * @param listeSalles : ArrayList de Salle
     * @param listeChirurgies : ArrayList de Chirurgie, qui correspond à la liste des chirurgies d'une erreur
     * @return true: si une des salles est disponible à la fois durant la durée d'une des chirurgies, et sa journée si
     * c'est le cas, alors l'on change la salle de la chirurgie et l'on renvoie true false sinon
     */
    public boolean changementSalle(ArrayList<Salle> listeSalles, ArrayList<Chirurgie> listeChirurgies) {
        for (Salle s : listeSalles) {
            for (Chirurgie ch : listeChirurgies) {
                if (!(s.equals(ch.getSalle()))) {
                    if (estDisponibleSalle(s, ch)) {
                        ArrayList<Salle> lesSallesDuChirurgien = Paire_Chirurgien_Salle.getSallesDuChirurgien(ch.getChirurgien());
                        if (lesSallesDuChirurgien.contains(s)) {
                            System.out.print("Chirurgie n°" + ch.getId() + " : \t" + ch.getSalle() + "\t\t -> ");
                            ch.setSalle(s);
                            System.out.println("\t" + ch.getSalle());
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     *
     * @param listeChirurgiens : ArrayList de Chirurgien, il s'agit des chirurgies présent durant la journée
     * @param listeChirurgies : ArrayList de Chirurgie, qui correspond à la liste des chirurgies d'une erreur
     * @return true: si un des Chirurgien est disponible à la fois durant la durée d'une des chirurgies, et sa journée
     * si c'est le cas, alors l'on change le chirurgien de la chirurgie et l'on renvoie true false sinon
     */
    public boolean changementChirurgien(ArrayList<Chirurgien> listeChirurgiens, ArrayList<Chirurgie> listeChirurgies) {
        for (Chirurgie c : listeChirurgies) {
            Chirurgien.triListeChirurgiens(listeChirurgiens);
            for (Chirurgien ch : listeChirurgiens) {
                if (this.estDisponibleChirurgien(ch, c)) {
                    c.getChirurgien().retraitTempsDeTravail(c.getDuree());
                    System.out.print("Chirurgie n°" + c.getId() + " : \t" + c.getChirurgien() + "\t -> ");
                    c.setChirurgien(ch);
                    System.out.println("\t" + c.getChirurgien());
                    c.getChirurgien().ajoutTempsDeTravail(c.getDuree());
                    return true;
                }
            }
        }
        return false;
    }

    /**
     *
     * @return LocalTime : un objet de type LocalTime, qui correspond à la durée moyenne des chirurgies présente dans
     * l'attribut listeChirurgies
     */
    public LocalTime getDureeMoyenneChirurgie() {
        // calcul de la durée total des chirurgies, exprimé en secondes
        float total = 0;
        for (Chirurgie c : this.listeChirurgies) {
            total += c.getDuree() * 60;
        }

        // calcul de la moyenne, exprimé en secondes
        float moyenne = total / this.listeChirurgies.size();

        // calcul de la durée en minute, sur laquel l'on effectue un modulo 60, pour ne garder que les minutes "en plus" des heures
        int min = (int) moyenne / 60;
        min = min % 60;
        // calcul de la durée moyenne en heure
        int heure = (int) moyenne / 3600;

        return LocalTime.of(heure, min);
    }

    /**
     * "normalise" une chirurgie donné, pour réduire sa durée si elle dépasse de façon anormale la durée moyenne rapport
     * à la durée moyenne des chirurgies
     *
     * @param c : Objet de type Chirurgie
     */
    public void normalisationHeureChirurgie(Chirurgie c) {
        // récupération et conversion de la durée moyenne des chirurgies en seconde
        LocalTime moyenne = this.dureeMoyenneChirurgies;
        int moyenneEnSeconde = moyenne.getHour() * 3600 + moyenne.getMinute() * 60 + moyenne.getSecond();
        // qu'elle est présente dans au moins 1 erreur
        if (this.estErreur(c)) {
            /*
            *  si son heure de fin est égal à minuit, sachant qu'il s'agit de "l'heure par défaut" de fin
            *  l'on peut alors considérer qu'il s'agit d'une erreur de saisie
             */
            if (c.getHeureFin().equals(LocalTime.of(0, 0))) {
                // si sa durée est supérieur à 4 heures, alors l'on passe sa durée à 2 heures
                if (c.getDuree() > 240) {
                    c.getChirurgien().retraitTempsDeTravail(c.getDuree());
                    LocalTime newHeureFin = c.getHeureDebut().plusMinutes(120);
                    c.setHeureFin(newHeureFin);
                    c.getChirurgien().ajoutTempsDeTravail(c.getDuree());
                }
            }
            else {
                long dureeLong = c.getDuree();
                int dureeChirurgieEnSeconde = (int) dureeLong * 60;

                // sinon, si sa durée est supérieur ou égal à 150% de la durée moyenne des chirurgies :
                if (dureeChirurgieEnSeconde >= moyenneEnSeconde * 1.5) {

                    /*
                    * si sa durée est supérieur  à plus de 150% de la moyenne de la durée des chirurgies PLUS la durée de la moyenne
                    * alors l'on soustrait la durée de la moyenne
                     */
                    if (dureeChirurgieEnSeconde >= ((moyenneEnSeconde * 1.5) + (moyenne.getHour() * 3600) + (moyenne.getMinute() * 60))) {
                        c.getChirurgien().retraitTempsDeTravail(c.getDuree());
                        c.setHeureFin(c.getHeureFin().minusHours(moyenne.getHour()));
                        c.setHeureFin(c.getHeureFin().minusMinutes(moyenne.getMinute()));
                        c.setHeureFin(c.getHeureFin().minusSeconds(moyenne.getSecond()));
                        c.getChirurgien().ajoutTempsDeTravail(c.getDuree());
                    }
                    /*
                    * sinon, l'on soustrait la différence, pour ramener la durée de la chirurgie à 150% de la durée moyenne 
                    * 
                     */
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

    /**
     * choisis aléatoirement une chirurgie parmis celle de la liste passé en paramètre puis, effectue une normalisation
     * dessus
     *
     * @param lesChirurgies : ArrayList de Chirurgie
     */
    public void normalisationAleatoire(ArrayList<Chirurgie> lesChirurgies) {
        double nb = Math.random();
        int valeur;
        if (nb > 0.5) {
            valeur = 1;
        }
        else {
            valeur = 0;
        }
        Chirurgie c = lesChirurgies.get(valeur);
        System.out.print("Chirurgie n°" + c.getId() + " : \t" + c.getHeureDebut() + ";" + c.getHeureFin() + "\t -> ");
        this.normalisationHeureChirurgie(lesChirurgies.get(valeur));
        System.out.println("\t" + c.getHeureDebut() + ";" + c.getHeureFin() + "\t" + " [REDUCTION HEURE]");
    }

    /**
     *
     * @param c : Objet de type Chirurgie
     * @param heure : Entier représentant des heures
     * @param minute : Entier représentant des minutes
     * @param typeChangement : String représentant le type de changement à effectuer il dois s'agir soit de "avancer",
     * soit de "retarder"
     * @return true: si une des salles est disponible à la fois durant la durée d'une des chirurgies, et sa journée si
     * c'est le cas, alors l'on change la salle de la chirurgie et l'on renvoie true false sinon
     */
    public boolean changementHeureChirurgie(Chirurgie c, int heure, int minute, String typeChangement) {

        // conversion des heures en minute
        minute = minute + (heure * 60);
        // initialisation d'une heure de début et de fin
        LocalTime newHeureDebut = LocalTime.of(0, 0);
        LocalTime newHeureFin = LocalTime.of(0, 0);

        /*
         *  selon le typeChangement passé en paramètre, l'on affecte une valeur différente à newHeureDebut et newHeureFin
         *  avancer : l'on affecte l'heure de début (et de fin) de c aux nouvelles valeurs plus les minutes et heure passé en paramètre
         *  reculer : l'on affecte l'heure de début (et de fin) de c aux nouvelles valeurs moins les minutes et heure passé en paramètre
         */
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

        // récupération de la liste des chirurgies du jour 
        ArrayList<Chirurgie> listeChirurgiesDuJour = this.getChirurgiesDuJour(c.getDate());
        // instanciation d'une nouvelle chirurgie, ayant les mêmes attributs que c, sauf l'heure de début et de fin
        Chirurgie tentativeChirurgie = new Chirurgie(c.getId(), c.getDate(), newHeureDebut, newHeureFin, c.getSalle(), c.getChirurgien());
        boolean datePossible = true;

        /*
         *  parcours des chirurgies du jour, et l'on vérifie, si la nouvelle chirurgie, pourrait-être placer durant la journée
         *  si elle est parallèle et dispose d'attributs communs (chirurgien et/ou salle) avec au moins 1 chirurgie, elle ne peut alors pas être placer
         *  
         */
        for (Chirurgie ch : listeChirurgiesDuJour) {
            if ((tentativeChirurgie.estParallele(ch)
                    && ((tentativeChirurgie.getChirurgien().equals(ch.getChirurgien())) || (tentativeChirurgie.getSalle().equals(ch.getSalle()))))) {

                datePossible = false;
            }
        }

        /* si le placement de la chirurgie est possible, l'on vérifie alors des contraintes choisis arbitrairement :
        *	- l'heure de début ne dois pas être avant 7 heures du matin
        *	- l'heure de fin ne dois dépasser 23 heures
        *	le but étant d'éviter au maximum, le placement de chirurgie à des heures contraignantes (de 03:00 à 05:00 par exemple)
         */
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
            // si toutes les conditions sont vérifié, alors l'on change l'heure de début et de fin de la chirurgie passé en paramètre
            System.out.print("Chirurgie n°" + c.getId() + " : \t" + c.getHeureDebut() + ";" + c.getHeureFin() + "\t -> ");
            c.setHeureDebut(newHeureDebut);
            c.setHeureFin(newHeureFin);
            System.out.println("\t" + c.getHeureDebut() + ";" + c.getHeureFin() + "\t" + " [CHANGEMENT HEURE]");
            return true;
        }
        else {
            return false;
        }
    }

    /**
     *
     * Boucle cherchant à déplacer les chirurgies d'une liste, une par une, au fur et à mesure, selon un pas donné en
     * paramètre
     *
     * @param lesChirurgies : ArrayList de Chirurgie
     * @param tempsDecalage : Entier réprésentant les pas de temps de décalage (exprimé en minute)
     *
     */
    public boolean deplacementHeureChirurgie(ArrayList<Chirurgie> lesChirurgies, int tempsDecalage) {
        int tempsDecalageInitial = tempsDecalage;
        // parcours de la liste des chirurgies
        for (Chirurgie c : lesChirurgies) {
            boolean changementPossible = false;
            int i = 0;
            /*
            * l'on impose une limite, dans le but de ne pas boucler indéfiniment :
            * elle correspond au nombres d'itérations requises pour atteindre 24h
             */
            int limite = 24 * 60 / tempsDecalage;

            /*
            * cette boucle gère le déplacement et la modification du pas, si d'un coté ou de l'autre (avancer ou retarder), la limite est atteinte
            * alors l'on ignore ce type de déplacement, afin d'éviter des itérations inutiles
             */
            boolean stopAvancer = false;
            boolean stopRetarder = false;
            while (!(changementPossible)) {
                LocalTime limiteAvancer = c.getHeureDebut().minusMinutes(tempsDecalage);
                LocalTime limiteRetarder = c.getHeureFin().plusMinutes(tempsDecalage);
                if (limiteAvancer.isBefore(LocalTime.of(7, 0))) {
                    stopAvancer = true;
                }

                if ((limiteRetarder.isAfter(LocalTime.of(23, 0))) || (limiteRetarder.isBefore(LocalTime.of(7, 0)))) {
                    stopRetarder = true;
                }

                if (!(stopRetarder)) {
                    if (this.changementHeureChirurgie(c, 0, tempsDecalage, "retarder")) {
                        return true;
                    }
                }
                if (!(stopAvancer)) {
                    if (this.changementHeureChirurgie(c, 0, tempsDecalage, "avancer")) {
                        return true;
                    }
                }

                tempsDecalage += tempsDecalageInitial;
                i++;
                if (i >= limite) {
                    break;
                }
            }
        }
        return false;
    }

    /**
     *
     * @param c : Objet de type Chirurgie
     * @return true: si la chirurgie passé en paramètre est présente dans la liste des erreurs false sinon
     */
    public boolean estErreur(Chirurgie c) {
        for (Erreur e : this.listeErreurs) {
            for (Chirurgie ch : e.getListeChirurgiesErreur()) {
                if (ch.equals(c)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Réalise des statistiques sur le temps de travail de chacun des chirurgiens, proportionnellement au temps de
     * travail total
     *
     * @return TreeMap<Chirurgien, String> : renvoie une map ayant pour clé un chirurgien et pour valeur une chaine
     * représentant le temps de travail du chirurgien en %
     */
    public HashMap<Chirurgien, String> statTempsTravailChirurgien() {
        HashMap<Chirurgien, String> m = new HashMap<>();
        double total = 0;

        for (Chirurgie c : this.listeChirurgies) {
            total += c.getDuree();
        }

        for (Chirurgien c : Chirurgien.getListeChirurgiens()) {
            if (!(c.getNom().equals("Joker"))) {
                Double moyenne = c.getTempsDeTravail() / total * 100;
                DecimalFormat df = new DecimalFormat("#0.00");
                String s = df.format(moyenne) + "%";
                m.put(c, s);
            }
        }
        return m;
    }

    /*
     *
     * @return int : retourne la durée total de toutes les chirurgies
     * 
     */
    public int getDureeTotalChirurgies() {
        int total = 0;
        for (Chirurgie c : this.listeChirurgies) {
            total += c.getDuree();
        }
        return total;
    }

    /*
     * affiche le nombre d'erreurs pour chacun des types, présent dans la liste d'erreurs
     */
    public void printNbErreursParType() {
        int nbChevauchement = 0;
        int nbInterference = 0;
        int nbUbiquite = 0;

        for (Erreur e : this.listeErreurs) {
            if (e instanceof ErreurChevauchement) {
                nbChevauchement++;
            }
            else if (e instanceof ErreurInterference) {
                nbInterference++;
            }
            else if (e instanceof ErreurUbiquite) {
                nbUbiquite++;
            }
        }
        System.out.println("Nombre de chevauchement : " + nbChevauchement);
        System.out.println("Nombre d'interférence : " + nbInterference);
        System.out.println("Nombre d'ubiquité : " + nbUbiquite);
    }
}
