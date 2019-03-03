
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author kormli18
 */
public enum Salle {

    BLOC_E1("BLOC-E1", 1),
    BLOC_E2("BLOC-E2", 2),
    BLOC_E3("BLOC-E3", 3),
    BLOC_U3("BLOC-U3", 4),
    BLOC_U4("BLOC-U4", 5),
    BLOC_U5("BLOC-U5", 6),
    BLOC_U6("BLOC-U6", 7),
    BLOC_U7("BLOC-U7", 8),
    BLOC_U8("BLOC-U8", 9),
    BLOC_U9("BLOC-U9", 10),
    BLOC_U10("BLOC-U10", 11);

    private String nom;
    private int value;

    Salle(String nom, int value) {
        this.nom = nom;
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public String getNom() {
        return nom;
    }

    public String toString() {
        return nom;
    }

    public boolean equals(Salle s) {
        return (this.getNom().equals(s.getNom()));
    }

    public static Salle getSalleById(int id) {
        for (Salle s : Salle.values()) {
            if (s.getValue() == id) {
                return s;
            }
        }
        return null;
    }

    /**
     *
     * @param str : String correspondant à un nom de Salle
     * @return un Objet Salle, ayant pour nom la String passé en paramètre
     */
    public static Salle getSalleByName(String str) {
        for (Salle s : Salle.values()) {
            if (s.getNom().equals(str)) {
                return s;
            }
        }
        return null;
    }

    /**
     *
     * @return l'ArrayList de toute les Salle existantes
     */
    public static ArrayList<Salle> getListeSalles() {
        ArrayList<Salle> listeSalles = new ArrayList<>();
        for (Salle s : Salle.values()) {
            if (!(listeSalles.contains(s))) {
                listeSalles.add(s);
            }
        }
        return listeSalles;
    }
}
