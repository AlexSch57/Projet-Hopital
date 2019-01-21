/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projethopital;

/**
 *
 * @author kormli18
 */
public class Salle {
    private String nom;

    public Salle(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }
    
    public boolean equals(Salle s) {
       return (this.getNom().equals(s.getNom()));
    }
}
