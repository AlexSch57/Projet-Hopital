
/**
 * Classe représentant les erreurs d'Ubiquité, héritant d'Erreur
 * Erreur généré par la présence d'un chirurgien à 2 endroits en même temps
 *
 * @author Liam Kormann
 * @version 1.0
 */
public class ErreurUbiquite extends Erreur {

    /**
     * @return une String, correspondant à son type d'erreur, ainsi qu'une liste de ses chirurgies
     */
    @Override
    public String toString() {
        String s = "Erreur Ubiquite : ";
        for (Chirurgie c : listeChirurgiesErreur) {
            s += "\n" + c.toString();
        }
        s += "\n";
        return s;
    }
}
