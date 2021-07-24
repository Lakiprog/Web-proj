package util;

import java.util.Comparator;

import domain.Korisnik;

public class KorisniciPoPrezimenuSort implements Comparator<Korisnik> {

	@Override
	public int compare(Korisnik first, Korisnik second) {
		return first.getPrezime().compareTo(second.getPrezime());
	}

}
