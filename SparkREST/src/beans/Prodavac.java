package beans;

import java.util.ArrayList;

public class Prodavac extends Korisnik {
	private ArrayList<Manifestacija> manifestacije;

	public ArrayList<Manifestacija> getManifestacije() {
		return manifestacije;
	}

	public void setManifestacije(ArrayList<Manifestacija> manifestacije) {
		this.manifestacije = manifestacije;
	}

	public Prodavac(String kIme, String lozinka, String ime, String prezime, String datumRodjenja, Pol pol) {
		super(kIme, lozinka, ime, prezime, datumRodjenja, pol);
	}

}
