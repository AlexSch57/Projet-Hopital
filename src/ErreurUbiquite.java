/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author schwal180
 */

public class ErreurUbiquite extends Erreur {
	
	@Override
	public String toString() {
		String s = "Erreur Ubiquite : ";
		for(Chirurgie c : listeChirurgiesErreur) {
			s += "\n" + c.toString();
		}
		s+= "\n";
		return s;
	}
}
