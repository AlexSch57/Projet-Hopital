
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author schwal180
 */
public class Menu {
    private String currentFile;
    private Hopital lHopital;
    
    public Menu() {
        this.currentFile = "Aucun";
        this.lHopital = null;
    }
    
    public void setCurrentFile(String currentFile) {
        this.currentFile = currentFile;
    }

    public Hopital getLHopital() {
        return lHopital;
    }

    public void setLHopital(Hopital lHopital) {
        this.lHopital = lHopital;
    }
    
    public void displayMenu() {
        int tailleListeChirurgies = 0;
        int tailleListeErreurs = 0;
        if(this.lHopital != null) {
            tailleListeChirurgies = this.lHopital.getListeChirurgies().size();
            tailleListeErreurs = this.lHopital.getTailleListeErreurs();
        }
        System.out.println("\nBienvenue dans le projet-Hopital ! \n"
                + "Fichier courant : " + this.currentFile + " \n"
                + "Nombre(s) de Chirurgie(s) du fichier : " + tailleListeChirurgies + "\n"
                + "Nombre(s) d'erreur(s) présente(s) : " + tailleListeErreurs + "\n\n"
                + "Veuillez selectionner une des options suivantes : \n"
                + "1. Choisir le fichier à utiliser \n"
                + "2. Corriger les erreurs du fichier \n"
                + "3. Exporter la base vers un autre fichier \n"
                + "4. (??) \n"
                + "5. (??) \n"
                + "6. Quittez l'application \n");
    }
    
    
     /**
    ** Methode principale de la classe Mneu, elle appelle toutes les autres
    *  selon le choix de l'utilisateur, effectuera une action différente
    */
    public void switchChoix() throws IOException, FileNotFoundException, ParseException, ChirurgienInexistantException, SalleInexistanteException {
        switch (choixUtilisateur()) {
            case 1:
                this.selectFile();
                break;

            case 2:
                int nbEtape = 1;
                ArrayList<Integer> nbErreursParEtape = new ArrayList<>();
                while(this.lHopital.getTailleListeErreurs() > 0) {
                    nbErreursParEtape.add(this.lHopital.getTailleListeErreurs());
                    if(nbErreursParEtape.size() > 2) {
                        if(((nbErreursParEtape.get(nbErreursParEtape.size() - 1)).equals(nbErreursParEtape.get(nbErreursParEtape.size() - 2)))
                            && ((nbErreursParEtape.get(nbErreursParEtape.size() - 1)).equals(nbErreursParEtape.get(nbErreursParEtape.size() - 3)))) {

                                System.out.println("Impossible de resoudre toutes les erreurs\n\n");
                                break;
                        }
                    }
                    System.out.println( " étape " + (nbEtape) + " :");
                    this.lHopital.findErreur();

                    this.lHopital.resolveErreur();
                    System.out.println("erreur(s) restante(s) : " + this.lHopital.getTailleListeErreurs());
                    nbEtape++;
                }
                if(this.lHopital.getTailleListeErreurs() == 0) {
                    System.out.println("Toutes les erreurs ont été corrigées !\n");
                }
                break;
                
            case 3:
                this.createOutput(lHopital);               
                break;

            case 4:
                System.out.println("ERREUR");
                break;

            case 5:
                System.out.println("ERREUR");
                break;
                
            case 6:
                System.out.println("fin du programme");
                System.exit(0);
                break;
                
            default:
                System.out.println("ERREUR");
                System.exit(0);
                break;
        }
    }
    
    /**
    ** Methode gérant le choix de l'utilisateur sur le menu 
    * @return un entier correspondant au choix de l'utilisateur
    */
    public int choixUtilisateur()
    {
        Scanner scan = new Scanner(System.in);
        int nombre = 0;
        int limite = 6;
        if(this.lHopital == null) {
            limite = 1;
        }
        do {
            try {
                nombre = scan.nextInt();
                if((nombre < 1 || nombre > limite) && nombre != 6) {
                    if(limite == 1) {
                        System.out.println("Vous ne pouvez pas faire d'autres choix que le n°1 tant que vous n'avez pas choisi de fichier");
                    }
                    else {
                        System.out.println("Veuillez séléctionner un nombre entre 1 et 6");
                    }
                }
            } 
            catch (InputMismatchException ex) {
                if(limite == 1) {
                    System.out.println("Vous ne pouvez pas faire d'autres choix que le n°1 tant que vous n'avez pas choisi de fichier");
                }
                else {
                    System.out.println("Veuillez séléctionner un nombre entre 1 et 6");
                }
                return choixUtilisateur();
            }
        } while ((nombre < 1 || nombre > limite) && nombre != 6);

        return nombre;
    }
    
    
     /**
    ** Methode gérant la selection et le chargement du fichier utilisé pour la création du graphe
    */
    public void selectFile() throws IOException, FileNotFoundException, ParseException, ChirurgienInexistantException, SalleInexistanteException {
        boolean exist = false;
        File f = new File(System.getProperty("user.dir"));
        f = new File(f.getAbsoluteFile() + "\\" + "files");
        String[] tab = f.list();
        System.out.println("Liste des fichiers :");
        for(String s : tab) {
            if(s.contains(".")) {
                System.out.println(s);
            }
        }
        System.out.println("\n");
        while(!(exist)) {
            System.out.println("Sélectionner un fichier :");
            Scanner sc = new Scanner(System.in);
            String reponse = sc.nextLine();
            f = new File(System.getProperty("user.dir"));
            f = new File(f.getAbsolutePath() + "\\" + "files" + "\\" + reponse);
            if((f.exists() && f.isFile())) {
                this.currentFile = reponse;
                this.lHopital = new Hopital();
                this.lHopital.init(reponse);
                exist = true;
            }
            else {
                reponse += ".csv";
                f = new File(System.getProperty("user.dir"));
                f = new File(f.getAbsolutePath() + "\\" + "files" + "\\" + reponse);
                if((f.exists() && f.isFile())) {
                    this.currentFile = reponse;
                    this.lHopital = new Hopital();
                    this.lHopital.init(reponse);
                    exist = true;
                }
            }
        } 
    }
    
    public void createOutput(Hopital h) throws IOException {
    	String contenuFichier = "ID CHIRURGIE;DATE CHIRURGIE;HEURE_DEBUT CHIRURGIE;HEURE_FIN CHIRURGIE;SALLE;CHIRURGIEN";
    	//contenuFichier += h.toString();
        
        String formalisationMinute = "" + LocalTime.now().getMinute();
        if(LocalTime.now().getMinute() < 10) {
            formalisationMinute = "0" + LocalTime.now().getMinute();
        }
        String nomFichier = this.currentFile + " " + LocalDate.now() + " " + LocalTime.now().getHour() + "h" + formalisationMinute +  ".csv";
        
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
