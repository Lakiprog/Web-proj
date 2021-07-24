package util;

import java.util.Comparator;

import domain.Korisnik;

public class KorisniciPoImenuSort implements Comparator<Korisnik>{

	@Override
	public int compare(Korisnik first, Korisnik second) {
		return first.getIme().compareTo(second.getIme());
	}

}
