
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * Classe représentant le menu, qui sera l'interface d'accès aux diverses fonctionnalités
 *
 * @author Liam Kormann
 * @version 1.0
 */

public class Menu {

    private String currentFile;
    private Hopital notreHopital;
    private Scanner sc;
    private boolean actif;
    
    // attributs de statistiques
    private int dureeTotal;
    private int nbErreurs;

    public Menu() {
        this.currentFile = "Aucun";
        this.notreHopital = null;
        this.actif = true;
        this.sc = new Scanner(System.in);
    }

    public void setCurrentFile(String currentFile) {
        this.currentFile = currentFile;
    }

    public Hopital getLHopital() {
        return notreHopital;
    }

    public void setLHopital(Hopital lHopital) {
        this.notreHopital = lHopital;
    }

    public void displayMenu() {
        int tailleListeChirurgies = 0;
        int tailleListeErreurs = 0;
        if (this.notreHopital != null) {
            tailleListeChirurgies = this.notreHopital.getListeChirurgies().size();
            tailleListeErreurs = this.notreHopital.getTailleListeErreurs();
        }
        System.out.println("\nBienvenue dans le projet-Hopital ! \n"
                + "Fichier courant : " + this.currentFile + " \n"
                + "Nombre(s) de Chirurgie(s) du fichier : " + tailleListeChirurgies + "\n"
                + "Nombre(s) d'erreur(s) présente(s) : " + tailleListeErreurs + "\n\n"
                + "Veuillez selectionner une des options suivantes : \n"
                + "1. Choisir le fichier à utiliser \n"
                + "2. Corriger les erreurs du fichier \n"
                + "3. Exporter la base vers un autre fichier \n"
                + "4. Ajouter une chirurgie\n"
                + "5. Modifier une chirurgie\n"
                + "6. Supprimer une chirurgie\n"
                + "7. Quittez l'application \n");
    }

    /**
     ** Methode principale de la classe Mneu, elle appelle toutes les autres selon le choix de l'utilisateur,
     * effectuera une action différente
     */
    public void switchChoix() throws IOException, FileNotFoundException, ParseException, ChirurgienInexistantException, SalleInexistanteException {
        switch (choixUtilisateur()) {
            case 1:
                this.selectFile();
                this.dureeTotal = this.notreHopital.getDureeTotalChirurgies();
                this.nbErreurs = this.notreHopital.getTailleListeErreurs();
                break;

            case 2:
                int nbEtape = 1;
                while (this.notreHopital.getTailleListeErreurs() > 0) {
                    if (nbEtape > 20) {
                        System.out.println("\nImpossible de resoudre toutes les erreurs\n\n");
                        this.notreHopital.printListeErreurs();
                        break;
                    }
                    this.notreHopital.findErreur();
                    if (this.notreHopital.getTailleListeErreurs() == 0) {
                        System.out.println("\nErreur(s) restante(s) : " + this.notreHopital.getTailleListeErreurs());
                        System.out.println("Toutes les erreurs ont été corrigées !\n");
                        this.printStats();
                        break;
                    }
                    System.out.println("\nétape " + (nbEtape) + " :");
                    System.out.println("Erreur(s) restante(s) : " + this.notreHopital.getTailleListeErreurs());
                    this.notreHopital.printNbErreursParType();
                    System.out.println("Resolution : ");
                    this.notreHopital.resolveErreur(); 
                    nbEtape++;
                }
                break;

            case 3:
                this.createOutput();
                break;

            case 4:
                this.addChirurgie();
                break;

            case 5:
                this.modifierChirurgie(notreHopital.getChirurgieById(this.selectIdChirurgie()));
                break;

            case 6:
                this.removeChirurgie();
                break;

            case 7:
                System.out.println("Fin du programme");
                sc.close();
                this.switchActif();
                break;

            default:
                System.out.println("Saisie incorrecte");
                System.exit(0);
                break;
        }
    }

    /**
     ** Methode gérant le choix de l'utilisateur sur le menu
     *
     * @return un entier correspondant au choix de l'utilisateur
     */
    public int choixUtilisateur() {

        int nombre = 0;
        int limite = 7;
        if (this.notreHopital == null) {
            limite = 1;
        }
        do {
            try {
                nombre = sc.nextInt();
                sc.nextLine();
                if ((nombre < 1 || nombre > limite) && nombre != 7) {
                    if (limite == 1) {
                        System.out.println("Vous ne pouvez pas faire d'autres choix que le n°1 tant que vous n'avez pas choisi de fichier");
                    }
                    else {
                        System.out.println("Veuillez séléctionner un nombre entre 1 et 7");
                    }
                }
            } catch (InputMismatchException ex) {
                if (limite == 1) {
                    System.out.println("Vous ne pouvez pas faire d'autres choix que le n°1 tant que vous n'avez pas choisi de fichier");
                }
                else {
                    System.out.println("Veuillez séléctionner un nombre entre 1 et 7");
                }

                return choixUtilisateur();
            }
        } while ((nombre < 1 || nombre > limite) && nombre != 7);

        return nombre;
    }

    /**
     ** Methode gérant la selection et le chargement du fichier utilisé pour la création du graphe
     */
    public void selectFile() throws IOException, FileNotFoundException, ParseException, ChirurgienInexistantException, SalleInexistanteException {

        File f = new File(System.getProperty("user.dir"));
        f = new File(f.getAbsoluteFile() + File.separator + "files");
        File[] tab = f.listFiles();
        ArrayList<String> listeFic = new ArrayList<>();
        for (File file : tab) {
            if (file.isFile()) {
                listeFic.add(file.getName());
            }
        }
        int indexFichier = 0;
        System.out.println("Liste des fichiers :\n");
        for (String s : listeFic) {
            System.out.println(++indexFichier + ". " + s);
        }
        System.out.println("Sélectionner un fichier par son index :");
        int reponse;
        try {
            Scanner sc = new Scanner(System.in);
            reponse = sc.nextInt();
            String nomFichier = listeFic.get(--reponse);
            f = new File(System.getProperty("user.dir"));
            f = new File(f.getAbsolutePath() + File.separator + "files" + File.separator + nomFichier);
            this.currentFile = nomFichier;
            this.notreHopital = new Hopital();
            this.notreHopital.init(nomFichier);
        } 
        catch (IndexOutOfBoundsException | InputMismatchException e) {
            System.err.println("Le fichier demandé n'existe pas\n");
            this.selectFile();
        }
    }

    /**
     ** Methode créant un fichier au format csv définissant la base de l'hopital courant dans le dossier files/outputs
     */
    public void createOutput() throws IOException {
        String contenuFichier = "ID CHIRURGIE;DATE CHIRURGIE;HEURE_DEBUT CHIRURGIE;HEURE_FIN CHIRURGIE;SALLE;CHIRURGIEN";
        //contenuFichier += h.toString();

        String formalisationMinute = "" + LocalTime.now().getMinute();
        if (LocalTime.now().getMinute() < 10) {
            formalisationMinute = "0" + LocalTime.now().getMinute();
        }
        String nomBase = currentFile.replaceFirst(".csv", "");
        String nomFichier = nomBase + " " + LocalDate.now() + " " + LocalTime.now().getHour() + "h" + formalisationMinute + ".csv";

        File fichier = new File("files" + File.separator + "outputs" + File.separator + nomFichier);
        fichier.createNewFile();
        PrintWriter writer = new PrintWriter(fichier, "UTF-8");
        writer.println(contenuFichier);
        for (Chirurgie c : notreHopital.getListeChirurgies()) {
            writer.println(c.toString());
        }
        writer.close();
        System.out.println("fichier exporté ! nom : " + nomFichier);
    }

    /**
     ** Methode permettant l'ajout d'une nouvelle chirurgie dans la base où l'utilisateur peut définir chacun des
     * attributs (hors id qui est auto-défini)
     */
    public void addChirurgie() {
        Chirurgie c = new Chirurgie();
        c.setId(Integer.toString(this.getIdMax() + 1));
        String choix = "";
        while (((c.getDate() == null) || (c.getHeureDebut() == null) || (c.getHeureFin() == null) || (c.getSalle() == null) || (c.getChirurgien() == null)) && (!choix.equals("7"))) {
            System.out.println("Veuillez saisir les attributs de la chirurgie à définir : ");
            this.printInfosChirurgie(c);
            System.out.println("Pour quitter la création, appuyez sur 7.");
            choix = sc.nextLine();
            switch (choix) {
                case "2":
                    c.setDate(this.setDateChirurgie());
                    break;
                case "3":
                    c.setHeureDebut(this.setHeureChirurgie());
                    break;
                case "4":
                    c.setHeureFin(this.setHeureChirurgie());
                    break;
                case "5":
                    c.setSalle(this.setSalleChirurgie());
                    break;
                case "6":
                    c.setChirurgien(this.setChirurgienChirurgie());
                    break;
                default:
                    break;
            }
        }
        if (!choix.equals("7")) {
            notreHopital.getListeChirurgies().add(c);
            System.out.println("Chirurgie ajoutée");
        }

    }

    /**
     ** Methode permettant la modification d'une chirurgie de la base où l'utilisateur peut modifier chacun des
     * attributs (hors id)
     */
    public void modifierChirurgie(Chirurgie c) {
        String choix = "";
        while (!choix.equals("7")) {
            System.out.println("Veuillez saisir les attributs de la chirurgie à modifier : ");
            this.printInfosChirurgie(c);
            System.out.println("Pour quitter la modifictation appuyez sur 7.");
            choix = sc.nextLine();
            switch (choix) {
                case "2":
                    c.setDate(this.setDateChirurgie());
                    break;
                case "3":
                    c.setHeureDebut(this.setHeureChirurgie());
                    break;
                case "4":
                    c.setHeureFin(this.setHeureChirurgie());
                    break;
                case "5":
                    c.setSalle(this.setSalleChirurgie());
                    break;
                case "6":
                    c.setChirurgien(this.setChirurgienChirurgie());
                    break;
                default:
                    break;
            }
        }
    }

    /**
     ** Permet de choisir un id de Chirurgie (en vue d'une modification par exemple)
     *
     * @return String : Correspond à l'id séléctionné
     * @see Hopital.getChirurgieById(String)
     * @see Menu.modifierChirurgie(Chirurgie)
     */
    public String selectIdChirurgie() {
        String choix = "";
        do {
            System.out.println("Veuillez séléctionner un id de Chirurgie >");
            choix = sc.nextLine();
        } while (notreHopital.getChirurgieById(choix) == null);
        return choix;
    }

    /**
     ** Permet d'afficher dans la console les attributs d'une chirurgie
     *
     * @param c : Chirurgie dont on souhaite voir la valeur des attributs
     * @see Menu.addChirurgie()
     * @see Menu.modifierChirurgie(Chirurgie)
     */
    public void printInfosChirurgie(Chirurgie c) {
        if (c.getId() != null) {
            System.out.println("1. ID_CHIRURGIE : " + c.getId());
        }
        else {
            System.out.println("1. ID_CHIRURGIE : " + (getIdMax() + 1) + " (affecté automatiquement)");
        }
        if (c.getDate() != null) {
            System.out.println("2. DATE_CHIRURGIE : " + c.getDate());
        }
        else {
            System.out.println("2. DATE_CHIRURGIE : Non défini");
        }
        if (c.getHeureDebut() != null) {
            System.out.println("3. HEURE_DEBUT_CHIRURGIE : " + c.getHeureDebut());
        }
        else {
            System.out.println("3. HEURE_DEBUT_CHIRURGIE : Non défini");
        }
        if (c.getHeureFin() != null) {
            System.out.println("4. HEURE_FIN_CHIRURGIE : " + c.getHeureFin());
        }
        else {
            System.out.println("4. HEURE_FIN_CHIRURGIE : Non défini");
        }
        if (c.getSalle() != null) {
            System.out.println("5. SALLE : " + c.getSalle());
        }
        else {
            System.out.println("5. SALLE : Non défini");
        }
        if (c.getChirurgien() != null) {
            System.out.println("6. CHIRURGIEN : " + c.getChirurgien());
        }
        else {
            System.out.println("6. CHIRURGIEN : Non défini");
        }
    }

    /**
     ** Permet de supprimer une Chirurgie *
     */
    public void removeChirurgie() {
        System.out.println("Veuillez saisir l'identifiant de la chirurgie à supprimer : ");
        String reponse = sc.nextLine();
        Iterator<Chirurgie> it = notreHopital.getListeChirurgies().iterator();
        while (it.hasNext()) {
            Chirurgie c = it.next();
            if (c.getId().equals(reponse)) {
                it.remove();
                System.out.println("la chirurgie n°" + reponse + " à bien été supprimée !");
                this.notreHopital.findErreur();
            }
        }
    }

    /**
     ** Permet d'obtenir l'Id maximum attribué à une Chirurgie dans la base
     *
     * @return int : Correspond à l'id maximum attribué dans la base
     * @see Menu.addChirurgie()
     */
    public int getIdMax() {
        ArrayList<Chirurgie> listeChirurgies = notreHopital.getListeChirurgies();
        int idMax = 0;
        for (Chirurgie c : listeChirurgies) {
            if (Integer.parseInt(c.getId()) > idMax) {
                idMax = Integer.parseInt(c.getId());
            }
        }
        return idMax;
    }

    /**
     ** Permet d'initialier un objet LocalDate qui pourra être affecté à la date d'une Chirurgie
     *
     * @return LocalDate : Correspond à la date saisie
     * @see Menu.addChirurgie()
     * @see Menu.modifierChirurgie()
     */
    public LocalDate setDateChirurgie() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Veuillez saisir une date au format jj/mm/aaaa");
        String stringDate = sc.nextLine();
        String[] tab = stringDate.split("/");
        try {
            return LocalDate.of(Integer.parseInt(tab[2]), Integer.parseInt(tab[1]), Integer.parseInt(tab[0]));
        } catch (DateTimeException | ArrayIndexOutOfBoundsException | NumberFormatException e) {
            System.err.println("Sasie incorrecte");
            return this.setDateChirurgie();
        }
    }

    /**
     ** Permet d'initialier un objet LocalTime qui pourra être affecté à l'heure de début ou de fin d'une Chirurgie
     *
     * @return LocalTime : Correspond à l'heure saisie (au format hh:mm:ss)
     * @see Menu.addChirurgie()
     * @see Menu.modifierChirurgie()
     */
    public LocalTime setHeureChirurgie() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Veuillez saisir une heure au format hh:mm:ss");
        String stringDate = sc.nextLine();
        String[] tab = stringDate.split(":");
        try {
            return LocalTime.of(Integer.parseInt(tab[0]), Integer.parseInt(tab[1]), Integer.parseInt(tab[2]));
        } catch (DateTimeException | ArrayIndexOutOfBoundsException | NumberFormatException e) {
            System.err.println("Sasie incorrecte");
            return this.setHeureChirurgie();
        }
    }

    /**
     ** Permet de séléctionner un objet Salle parmi les instances existantes pouvant être affecté à une Chirurgie
     *
     * @return Salle : Correspond à la salle séléctionnée
     * @see Menu.addChirurgie()
     * @see Menu.modifierChirurgie()
     */
    public Salle setSalleChirurgie() {
        System.out.println("Veuillez choisir une salle parmi les salles disponibles : ");
        List<Salle> listeSalles = Arrays.asList(Salle.values());
        int index = 0;
        for (Salle s : listeSalles) {
            System.out.println(++index + " : " + s.getNom());
        }
        try {
            String choix = sc.nextLine();
            int indexChoix = Integer.parseInt(choix);
            return listeSalles.get(--indexChoix);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException | InputMismatchException e) {
            System.err.println("Sasie incorrecte");
            return this.setSalleChirurgie();
        }
    }

    /**
     ** Permet de séléctionner un objet Chirurgien parmi les instances existantes pouvant être affecté à une Chirurgie
     *
     * @return Salle : Correspond à la salle séléctionnée
     * @see Menu.addChirurgie()
     * @see Menu.modifierChirurgie()
     */
    public Chirurgien setChirurgienChirurgie() {
        System.out.println("Veuillez choisir un chirurgien parmi les chirurgiens disponibles : ");
        List<Chirurgien> listeChirurgiens = (Chirurgien.getListeChirurgiens());
        int index = 0;
        for (Chirurgien c : listeChirurgiens) {
            System.out.println(++index + " : " + c.getNom());
        }
        try {
            String choix = sc.nextLine();
            int indexChoix = Integer.parseInt(choix);
            return listeChirurgiens.get(--indexChoix);
        } catch (InputMismatchException | IndexOutOfBoundsException | NumberFormatException e) {
            System.err.println("Sasie incorrecte");
            return this.setChirurgienChirurgie();
        }
    }

    /**
     ** Vérifie si le menu est actif
     *
     * @see Menu.switchActif()
     */
    public boolean isActif() {
        return actif;
    }

    /**
     ** Change la valeur de actif Cette méthode est appelée quand l'utilisateur saisit "7" dans le menu principal
     *
     * @see Menu.switchChoix()
     */
    public void switchActif() {
        this.actif = !actif;
    }

    /**
     * Affiche les statistiques des corrections et des chirurgies
     */
    public void printStats() {
        double differenceDuree = this.dureeTotal - this.notreHopital.getDureeTotalChirurgies();
        double reductionMoyenne = differenceDuree / this.nbErreurs;

        DecimalFormat df = new DecimalFormat("#0.00");
        String s = df.format(reductionMoyenne) + " minute(s)";

        System.out.println("réduction de durée total de : " + differenceDuree + " minute(s)");
        System.out.println("soit une reduction moyenne par chirurgie de : " + s);
    }
}
