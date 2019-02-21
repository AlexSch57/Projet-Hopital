import java.util.ArrayList;

/**
 * type énuméré représentant un Chirurgien
 * @author Alexandre Schwitthal
 * @version 1.0
 */

public enum Chirurgien {

    LAWRENCE_KUTNER("LAWRENCE KUTNER", 1),
    ROBERT_CHASE("ROBERT CHASE", 2),
    GREGORY_HOUSE("GREGORY HOUSE", 3),
    JAMES_WILSON("JAMES WILSON", 4),
    ERIC_FOREMAN("ERIC FOREMAN", 5),
    LISA_CUDDY("LISA CUDDY", 6),
    CHRIS_TAUB("CHRIS TAUB", 7),
    REMY_HADLEY("REMY HADLEY", 8),
    PRESTON_BURKE("PRESTON BURKE", 9),
    DEREK_SHEPHERD("DEREK SHEPHERD", 10),
    MEREDITH_GREY("MEREDITH GREY", 11),
    ALEX_KAREV("ALEX KAREV", 12),
    KATHERINE_HEIGL("KATHERINE HEIGL", 13),
    OWEN_HUNT("OWEN HUNT", 14),
    MIRANDA_BAILEY("MIRANDA BAILEY", 15),
    RICHARD_WEBBER("RICHARD WEBBER", 16),
    Chirurgien_Mystere("Joker", 17);

    private String nom = "";
    private int value;
    private long tempsDeTravail;

    Chirurgien(String name, int value) {
        this.nom = name;
        this.value = value;
        this.tempsDeTravail = 0;
    }

    public int getValue() {
        return this.value;
    }

    public String getNom() {
        return this.nom;
    }

    public long getTempsDeTravail() {
        return this.tempsDeTravail;
    }

    public String toString() {
        return this.nom;
    }

    public void ajoutTempsDeTravail(long i) {
        this.tempsDeTravail += i;
    }

    public void retraitTempsDeTravail(long i) {
    	this.tempsDeTravail -= i;
    }
    public boolean equals(Chirurgien c) {
        return (this.getNom().equals(c.getNom()));
    }

    /**
     * 
     * @param id : int correspondant à l'id d'un Chirurgien
     * @return un Objet Chirurgien, ayant pour id, l'id passé en paramètre
     */
    public static Chirurgien getChirurgienById(int id) {
        for (Chirurgien c : Chirurgien.values()) {
            if (c.getValue() == id) {
                return c;
            }
        }
        return null;
    }

    /**
     * 
     * @param s : String correspondant au nom d'un Chirurgien
     * @return un Objet Chirurgient, ayant pour nom, le nom passé en paramètre
     */
    public static Chirurgien getChirurgienByName(String s) {
        for (Chirurgien c : Chirurgien.values()) {
            if (c.getNom().equals(s)) {
                return c;
            }
        }
        return null;
    }

    public static void printListeChirurgiens() {
        for (Chirurgien c : Chirurgien.values()) {
            System.out.println(c);
        }
    }
    
    public static ArrayList<Chirurgien> getListeChirurgiens() {
        ArrayList<Chirurgien> listeChirurgiens = new ArrayList<>();
        for(Chirurgien c : Chirurgien.values()) {
       		if(!(listeChirurgiens.contains(c))) {
       			if(!(c.nom.equals("Joker"))) {
       				listeChirurgiens.add(c);
       			}
       		}
       	}
        return listeChirurgiens;
    }
    
    public static ArrayList<Chirurgien> triListeChirurgiens(ArrayList<Chirurgien> lesChirurgiens) {
    	for(int i = 0; i < lesChirurgiens.size(); i++) {
    		for (int j = 0; j < lesChirurgiens.size(); j++) {
    			if(lesChirurgiens.get(i).tempsDeTravail < lesChirurgiens.get(j).tempsDeTravail) {
    				Chirurgien temp = lesChirurgiens.get(i);
    				lesChirurgiens.set(i, lesChirurgiens.get(j));
    				lesChirurgiens.set(j, temp);
    			}
    		}
    	}
    	return lesChirurgiens;
    }
}

