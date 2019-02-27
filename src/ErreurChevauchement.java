
/**
 * Classe représentant les erreurs de Chevauchement, héritant d'Erreur
 * Erreur généré par les conditions de l'interférence et de l'ubiquité réuni
 *
 * @author Liam Kormann
 * @version 1.0
 */
public class ErreurChevauchement extends Erreur {

    /**
     * @return une String, correspondant à son type d'erreur, ainsi qu'une liste de ses chirurgies
     */
    @Override
    public String toString() {
        String s = "Erreur Chevauchement : ";
        for (Chirurgie c : listeChirurgiesErreur) {
            s += "\n" + c.toString();
        }
        s += "\n";
        return s;
    }
}
