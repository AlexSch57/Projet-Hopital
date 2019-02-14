
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
    private Hopital notreHopital;
    private Scanner sc;
    
    public Menu() {
        this.currentFile = "Aucun";
        this.notreHopital = null;
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
        if(this.notreHopital != null) {
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
                while(this.notreHopital.getTailleListeErreurs() > 0) {
                    nbErreursParEtape.add(this.notreHopital.getTailleListeErreurs());
                    if(nbErreursParEtape.size() > 2) {
                        if(((nbErreursParEtape.get(nbErreursParEtape.size() - 1)).equals(nbErreursParEtape.get(nbErreursParEtape.size() - 2)))
                            && ((nbErreursParEtape.get(nbErreursParEtape.size() - 1)).equals(nbErreursParEtape.get(nbErreursParEtape.size() - 3)))) {

                                System.out.println("Impossible de resoudre toutes les erreurs\n\n");
                                break;
                        }
                    }
                    System.out.println( " étape " + (nbEtape) + " :");
                    this.notreHopital.findErreur();

                    this.notreHopital.resolveErreur();
                    System.out.println("erreur(s) restante(s) : " + this.notreHopital.getTailleListeErreurs());
                    nbEtape++;
                }
                if(this.notreHopital.getTailleListeErreurs() == 0) {
                    System.out.println("Toutes les erreurs ont été corrigées !\n");
                }
                break;
                
            case 3:
                this.createOutput(notreHopital);               
                break;

            case 4:
                System.out.println("ERREUR");
                break;

            case 5:
                System.out.println("ERREUR");
                break;
                
            case 6:
                System.out.println("Fin du programme");
                sc.close();
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
       
        int nombre = 0;
        int limite = 6;
        if(this.notreHopital == null) {
            limite = 1;
        }
        do {
            try {
                nombre = sc.nextInt();
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
       
        File f = new File(System.getProperty("user.dir"));
        f = new File(f.getAbsoluteFile() + File.separator + "files");
        File[] tab = f.listFiles();
        ArrayList<String> listeFic = new ArrayList<String>();
        for (File file : tab) {
            if (file.isFile()) {
                listeFic.add(file.getName());
            }
        }
        int indexFichier = 0; 
        System.out.println("Liste des fichiers :\n");
        for(String s : listeFic) {
            System.out.println(++indexFichier + ". " + s);
        }        
        System.out.println("Sélectionner un fichier par son index :");       
        int reponse;
		try {
			reponse = sc.nextInt();
			String nomFichier = listeFic.get(--reponse);
			f = new File(System.getProperty("user.dir"));
            f = new File(f.getAbsolutePath() + File.separator + "files" + File.separator + nomFichier);
            this.currentFile = nomFichier;
            this.notreHopital = new Hopital();                 
            this.notreHopital.init(nomFichier);            
		} catch (IndexOutOfBoundsException | InputMismatchException e) {				
			System.err.println("Le fichier demandé n'existe pas\n");
			this.selectFile();
		}            
    }
    
    public void createOutput(Hopital h) throws IOException {
    	String contenuFichier = "ID CHIRURGIE;DATE CHIRURGIE;HEURE_DEBUT CHIRURGIE;HEURE_FIN CHIRURGIE;SALLE;CHIRURGIEN";
    	//contenuFichier += h.toString();
        
        String formalisationMinute = "" + LocalTime.now().getMinute();
        if(LocalTime.now().getMinute() < 10) {
            formalisationMinute = "0" + LocalTime.now().getMinute();
        }
        String nomBase = currentFile.replaceFirst(".csv", "");;
        String nomFichier = nomBase + " " + LocalDate.now() + " " + LocalTime.now().getHour() + "h" + formalisationMinute +  ".csv";
        
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
