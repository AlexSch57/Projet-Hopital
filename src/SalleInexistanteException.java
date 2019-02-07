
public class SalleInexistanteException extends Exception {
	public SalleInexistanteException() {
		System.err.println("La salle écrite dans la base de données n'existe pas");
	}
	
}
