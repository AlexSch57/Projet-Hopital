
/**
 * Classe représentant les erreurs d'Interference, héritant d'Erreur
 * Erreur généré par l'utilisation d'une même salle 2 fois au même moment
 *
 * @author Liam Kormann
 * @version 1.0
 */
public class ErreurInterference extends Erreur {

    /**
     * @return une String, correspondant à son type d'erreur, ainsi qu'une liste de ses chirurgies
     */
    @Override
    public String toString() {
        String s = "Erreur Interférence : ";
        for (Chirurgie c : listeChirurgiesErreur) {
            s += "\n" + c.toString();
        }
        s += "\n";
        return s;
    }
}
