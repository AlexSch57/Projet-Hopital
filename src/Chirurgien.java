/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kormli18
 */
public enum Chirurgien {
	
	LAWRENCE_KUTNER("LAWRENCE KUTNER",1),
	ROBERT_CHASE("ROBERT CHASE",2),
	GREGORY_HOUSE("GREGORY HOUSE",3),
	JAMES_WILSON("JAMES WILSON",4),
	ERIC_FOREMAN("ERIC FOREMAN",5),
	LISA_CUDDY("LISA CUDDY",6),
	CHRIS_TAUB("CHRIS TAUB",7),
	REMY_HADLEY("REMY HADLEY",8),
	PRESTON_BURKE("PRESTON BURKE",9),
	DEREK_SHEPHERD("DEREK SHEPHERD",10),
	MEREDITH_GREY("MEREDITH GREY",11),
	ALEX_KAREV("ALEX KAREV",12),
	KATHERINE_HEIGL("KATHERINE HEIGL",13),
	OWEN_HUNT("OWEN HUNT",14),
	MIRANDA_BAILEY("MIRANDA BAILEY",15),
	RICHARD_WEBBER("RICHARD WEBBER",16), 
	Chirurgien_Mystere("Joker",17);
	
	private String nom = "";
	private int value;
    
    Chirurgien(String name, int value)
    {
    	this.nom = name;
        this.value = value;
    }
    
    public int getValue()
    {
        return this.value;
    }
	  

    public String getNom() {
        return nom;
    }

    public String toString() {
        return nom;
    }

    public boolean equals(Chirurgien c) {
        return (this.getNom().equals(c.getNom()));
    }
    
    public static Chirurgien getChirurgienById(int id) {
    	for(Chirurgien c : Chirurgien.values()) {
    		if(c.getValue() == id) {
    			return c;
    		}
    	}
        return null;
    }
    
    public static Chirurgien getChirurgienByName(String s) {
    	for(Chirurgien c : Chirurgien.values()) {
    		if(c.getNom() == s) {
    			return c;
    		}
    	}
        return null;
    }
}
