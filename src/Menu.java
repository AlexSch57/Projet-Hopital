
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
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
    //private Scanner sc;
    private boolean actif;

    public Menu() {
        this.currentFile = "Aucun";
        this.notreHopital = null;
        this.actif = true;
        //this.sc = new Scanner(System.in);
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
                + "4. Ajouter une chirurgie (non implémenté) \n"
                + "5. Modifier une chirurgie (non implémenté) \n"
                + "6. Supprimer une chirurgie (non implémenté) \n"
                + "7. Quittez l'application \n");
    }

    /**
     ** Methode principale de la classe Mneu, elle appelle toutes les autres
     * selon le choix de l'utilisateur, effectuera une action différente
     */
    public void switchChoix() throws IOException, FileNotFoundException, ParseException, ChirurgienInexistantException, SalleInexistanteException {
        switch (choixUtilisateur()) {
            case 1:
                this.selectFile();
                break;

            case 2:
                int nbEtape = 1;
                ArrayList<Integer> nbErreursParEtape = new ArrayList<>();
                while (this.notreHopital.getTailleListeErreurs() > 0) {
                    nbErreursParEtape.add(this.notreHopital.getTailleListeErreurs());
                    if (nbErreursParEtape.size() > 2) {
                        if (((nbErreursParEtape.get(nbErreursParEtape.size() - 1)).equals(nbErreursParEtape.get(nbErreursParEtape.size() - 2)))
                                && ((nbErreursParEtape.get(nbErreursParEtape.size() - 1)).equals(nbErreursParEtape.get(nbErreursParEtape.size() - 3)))) {

                            System.out.println("Impossible de resoudre toutes les erreurs\n\n");
                            this.notreHopital.printListeErreurs();
                            break;
                        }
                    }
                    System.out.println(" étape " + (nbEtape) + " :");
                    this.notreHopital.findErreur();

                    this.notreHopital.resolveErreur();
                    System.out.println("erreur(s) restante(s) : " + this.notreHopital.getTailleListeErreurs());
                    nbEtape++;
                }
                if (this.notreHopital.getTailleListeErreurs() == 0) {
                    System.out.println("Toutes les erreurs ont été corrigées !\n");
                }
                break;

            case 3:
                this.createOutput();
                break;

            case 4:
                this.addChirurgie();
                break;

            case 5:
                System.out.println("ERREUR");
                break;

            case 6:
                this.removeChirurgie();
                break;

            case 7:
                System.out.println("Fin du programme");
                //System.exit(0);
                this.switchActif();
                break;

            default:
                System.out.println("ERREUR");
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
                Scanner sc = new Scanner(System.in);
                nombre = sc.nextInt();
                if ((nombre < 1 || nombre > limite) && nombre != 7) {
                    if (limite == 1) {
                        System.out.println("Vous ne pouvez pas faire d'autres choix que le n°1 tant que vous n'avez pas choisi de fichier");
                    } 
                    else {
                        System.out.println("Veuillez séléctionner un nombre entre 1 et 7");
                    }
                }
            } 
            catch (InputMismatchException ex) {
                if (limite == 1) {
                    System.out.println("Vous ne pouvez pas faire d'autres choix que le n°1 tant que vous n'avez pas choisi de fichier");
                } 
                else {
                    System.out.println("Veuillez séléctionner un nombre entre 1 et 7");
                }
                return choixUtilisateur();
            }
        } 
        while ((nombre < 1 || nombre > limite) && nombre != 7);

        return nombre;
    }

    /**
     ** Methode gérant la selection et le chargement du fichier utilisé pour la
     * création du graphe
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
    
    @SuppressWarnings("unused")
	public void addChirurgie() {
    	int idChirurgie = getIdMax()+1;
    	LocalDate dateChirugie = null;
    	LocalTime heureDeb = null;
    	LocalTime heureFin = null;
    	Salle salle = null;
    	Chirurgien chirurgien = null;    	    	
    	while((dateChirugie==null) || (heureDeb==null) || (heureFin==null) || (salle==null) || (chirurgien==null)) {
    		Scanner sc = new Scanner(System.in);
    		String choix = "";
	    	System.out.println("Veuillez saisir les attributs de la chirurgie à définir : ");
	    	System.out.println("1. ID_CHIRURGIE : " + getIdMax()+1 + "(affecté automatiquement)");
	    	if (dateChirugie!=null) {
	    		System.out.println("2. DATE_CHIRURGIE : " + dateChirugie);
	    	}else {
	    		System.out.println("2. DATE_CHIRURGIE : Non défini" );
	    	}
	    	if (heureDeb!=null) {
	    		System.out.println("3. HEURE_DEBUT_CHIRURGIE : " + heureDeb);
	    	}else {
	    		System.out.println("3. HEURE_DEBUT_CHIRURGIE : Non défini" );
	    	}
	    	if (heureFin!=null) {
	    		System.out.println("4. HEURE_FIN_CHIRURGIE : " + heureFin);
	    	}else {
	    		System.out.println("4. HEURE_FIN_CHIRURGIE : Non défini" );
	    	}
	    	if (salle!=null) {
	    		System.out.println("5. SALLE : " + salle);
	    	}else {
	    		System.out.println("5. SALLE : Non défini" );
	    	}
	    	if (chirurgien!=null) {
	    		System.out.println("6. CHIRURGIEN : " + chirurgien);
	    	}else {
	    		System.out.println("6. CHIRURGIEN : Non défini" );
	    	}	    	
	    	choix = sc.nextLine();	
	    	sc.close();
	    	switch(choix) {
	    	case "2" :
	    		dateChirugie = this.setDateChirurgie();
	    		break;
	    	case "3" :
	    		heureDeb = this.setHeureChirurgie();
	    		break;
	    	case "4" :
	    		heureFin = this.setHeureChirurgie();
	    		break;
	    	case "5" :
	    		salle = this.setSalleChirurgie();
	    		break;
	    	case "6" :
	    		chirurgien = this.setChirurgienChirurgie();
	    		break;
	    	default :
	    		break;
	    	}	    	
    	}    	
    	notreHopital.getListeChirurgies().add(new Chirurgie(Integer.toString((idChirurgie)),dateChirugie, heureDeb, heureFin, salle, chirurgien));
    	System.out.println("Chirurgie ajoutée");
    	
    }
    
	public void removeChirurgie() {
        System.out.println("Veuillez saisir l'identifiant de la chirurgie à supprimer : ");
        Scanner sc = new Scanner(System.in);
        String reponse = sc.nextLine();
        Iterator<Chirurgie> it = notreHopital.getListeChirurgies().iterator();
        while (it.hasNext()) {
            Chirurgie c = it.next();
            if(c.getId().equals(reponse)) {
                it.remove();
                System.out.println("la chirurgie n°" + reponse + " à bien été supprimé !");
                this.notreHopital.findErreur();
            }
        }
    }
    
    public int getIdMax(){
    	ArrayList<Chirurgie> listeChirurgies = notreHopital.getListeChirurgies();
    	int idMax = 0;
    	for (Chirurgie c : listeChirurgies) {
    		if (Integer.parseInt(c.getId()) > idMax){
    			idMax = Integer.parseInt(c.getId());
    		}
    	}
    	return idMax;
    }
    
    public LocalDate setDateChirurgie(){
    	Scanner sc = new Scanner(System.in);
    	System.out.println("Veuillez saisir une date au format jj/mm/aaaa");
    	String stringDate = sc.nextLine();
    	sc.close();
    	String[] tab = stringDate.split("/");    	
    	try {
			return LocalDate.of(Integer.parseInt(tab[2]),Integer.parseInt(tab[1]),Integer.parseInt(tab[0]));
		} catch (DateTimeException | ArrayIndexOutOfBoundsException e) {
			this.setDateChirurgie();
		}
    	return null;
    }
    
    public LocalTime setHeureChirurgie(){
    	Scanner sc = new Scanner(System.in);
    	System.out.println("Veuillez saisir une heure au format hh:mm:ss");
    	String stringDate = sc.nextLine();
    	sc.close();
    	String[] tab = stringDate.split(":");    	
    	try {
			return LocalTime.of(Integer.parseInt(tab[0]),Integer.parseInt(tab[1]),Integer.parseInt(tab[2]));
		} catch (DateTimeException e) {
			this.setHeureChirurgie();
		}
    	return null;
    }
    
    public Salle setSalleChirurgie() {
    	Scanner scanSalle = new Scanner(System.in);
		System.out.println("Veuillez choisir une salle parmi les salles disponibles : ");
		List<Salle> listeSalles = Arrays.asList(Salle.values());
		int index = 0;
		for (Salle s :listeSalles) {
			System.out.println(++index + " : " + s.getNom());
		}
		try {
			int choix = scanSalle.nextInt();
			scanSalle.close();
			return listeSalles.get(--choix);
		} catch (InputMismatchException | NullPointerException e) {
			scanSalle.close();
			this.setSalleChirurgie();
		}
		return null;
	}
    
    public Chirurgien setChirurgienChirurgie() {
    	Scanner sc = new Scanner(System.in);
		System.out.println("Veuillez choisir un chirurgien parmi les chirurgiens disponibles : ");
		List<Chirurgien> listeChirurgiens = Arrays.asList(Chirurgien.values());
		int index = 0;
		for (Chirurgien c :listeChirurgiens) {
			System.out.println(++index + " : " + c.getNom());
		}
		try {
			int choix = sc.nextInt();
			sc.close();
			return listeChirurgiens.get(--choix);
		} catch (InputMismatchException | NullPointerException e) {
			sc.close();
			this.setSalleChirurgie();
		}
		return null;	
    }

	public boolean isActif() {
		return actif;
	}

	public void switchActif() {
		this.actif = !actif; 
	}
}
