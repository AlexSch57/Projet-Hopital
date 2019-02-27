
public class ChirurgienInexistantException extends Exception {

    public ChirurgienInexistantException() {
        System.err.println("Le chirurgien écrit dans la base de données n'existe pas");
    }
}
