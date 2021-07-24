package util;

import java.util.Comparator;

import domain.Korisnik;

public class KorisniciPoKorisnickomImenuSort implements Comparator<Korisnik>{

	@Override
	public int compare(Korisnik first, Korisnik second) {
		return first.getkIme().compareTo(second.getkIme());
	}

}
