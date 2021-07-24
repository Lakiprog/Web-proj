package util;

import java.util.Comparator;

import domain.Korisnik;
import domain.Kupac;

public class KorisniciPoBodovimaSort implements Comparator<Korisnik>{

	@Override
	public int compare(Korisnik first, Korisnik second) {
		if(first instanceof Kupac) {
			if(second instanceof Kupac) {
				return Integer.compare(((Kupac) first).getBrBodova(), ((Kupac) second).getBrBodova());
			}
		}
		return 1000;
	}

}
