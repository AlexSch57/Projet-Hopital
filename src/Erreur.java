
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Classe abstraite, représentant les erreurs elle est composé d'une liste de chirurgies les chirurgies de la liste sont
 * en conflit entre-elles
 *
 * @author Liam Kormann
 * @version 1.0
 */
public abstract class Erreur {

    protected ArrayList<Chirurgie> listeChirurgiesErreur;

    public Erreur() {
        this.listeChirurgiesErreur = new ArrayList<>();
    }

    public Erreur(ArrayList<Chirurgie> listeChirurgiesErreur) {
        this.listeChirurgiesErreur = listeChirurgiesErreur;
    }

    public void addChirurgie(Chirurgie c) {
        this.listeChirurgiesErreur.add(c);
    }

    public ArrayList<Chirurgie> getListeChirurgiesErreur() {
        return listeChirurgiesErreur;
    }

    public abstract String toString();

    /**
     * @param e : Object étant une Erreur
     * @return true si les éléments de la liste des chirurgies de l'objet courant et de l'objet en paramètre sont
     * équivalent false sinon
     */
    public boolean equals(Object e) {
        if (this.getClass().equals(e.getClass())) {
            if (new HashSet<Chirurgie>(this.getListeChirurgiesErreur()).equals(new HashSet<Chirurgie>(((Erreur) e).getListeChirurgiesErreur()))) {
                return true;
            }
        }
        return false;
    }
}
