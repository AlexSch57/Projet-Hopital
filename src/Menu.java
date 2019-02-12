
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
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
    
    public Menu()
    {
        this.currentFile = "Aucun";
        this.lHopital = null;
    }
    
    public void setCurrentFile(String currentFile)
    {
        this.currentFile = currentFile;
    }

    public Hopital getLHopital()
    {
        return lHopital;
    }

    public void setLHopital(Hopital lHopital)
    {
        this.lHopital = lHopital;
    }
    
    public void displayMenu()
    {
        System.out.println("\nBienvenue dans le projet-Hopital ! \n"
                + "Fichier courant : " + this.currentFile + " \n"
                + "Nombre(s) de Chirurgie(s) du fichier : " + this.lHopital.getListeChirurgies().size() + "\n"
                + "Nombre(s) d'erreur(s) présente(s) : " + this.lHopital.getTailleListeErreurs() + "\n\n"
                + "Veuillez selectionner une des options suivantes : \n"
                + "1. Choisir le fichier à utiliser \n"
                + "2. Corriger les erreurs du fichier \n"
                + "3. Exporter le fichier vers un autre fichier \n"
                + "4. (??) \n"
                + "5. (??) \n"
                + "6. Quittez l'application \n");
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
        if(this.lHopital == null)
        {
            limite = 1;
        }
        do
        {
            try
            {
                nombre = scan.nextInt();
                if((nombre < 1 || nombre > limite) && nombre != 6)
                {
                    if(limite == 1)
                    {
                        System.out.println("Vous ne pouvez pas faire d'autres choix que le n°1 tant que vous n'avez pas choisi de fichier");
                    }
                    else
                    {
                        System.out.println("Veuillez séléctionner un nombre entre 1 et 6");
                    }
                }
            } 
            catch (InputMismatchException ex)
            {
                if(limite == 1)
                {
                    System.out.println("Vous ne pouvez pas faire d'autres choix que le n°1 tant que vous n'avez pas choisi de fichier");
                }
                else
                {
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
    public void selectFile() throws IOException, FileNotFoundException, ParseException, ChirurgienInexistantException, SalleInexistanteException
    {
        boolean exist = false;
        File f = new File(System.getProperty("user.dir"));
        f = new File(f.getAbsoluteFile() + "\\" + "DossierTxt");
        String[] tab = f.list();
        System.out.println("Liste des fichiers :");
        for(String s : tab)
        {
            System.out.println(s);
        }
        System.out.println("\n");
        while(!(exist))
        {
            System.out.println("Sélectionner un fichier :");
            Scanner sc = new Scanner(System.in);
            String reponse = sc.nextLine();
            f = new File(System.getProperty("user.dir"));
            f = new File(f.getAbsolutePath() + "\\" + "DossierTxt" + "\\" + reponse);
            if((f.exists() && f.isFile()))
            {
                this.currentFile = reponse;
                this.lHopital = new Hopital();
                this.lHopital.init(reponse);
                exist = true;
            }    
        } 
    }
}
