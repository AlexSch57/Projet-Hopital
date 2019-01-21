/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projethopital;

import java.util.Objects;

/**
 *
 * @author kormli18
 */
public class Chirurgien {
    private String nom;

    public Chirurgien(String s) {
        this.nom = s;
    }

    public String getNom() {
        return nom;
    }
  

    
    public boolean equals(Chirurgien c) {
       return (this.getNom().equals(c.getNom()));
    }
    
    
    
}
