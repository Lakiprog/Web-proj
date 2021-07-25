package domain;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Prodavac extends Korisnik {
	private ArrayList<Integer> manifestacije = new ArrayList<Integer>();
	private boolean blokiran;

	public ArrayList<Integer> getManifestacije() {
		return manifestacije;
	}

	public void setManifestacije(ArrayList<Integer> manifestacije) {
		this.manifestacije = manifestacije;
	}
	
	public Prodavac() {
		
	}

	public Prodavac(Integer id, String kIme, String lozinka, String ime, String prezime, String datumRodjenja, Pol pol, Uloga uloga) {
		super(id, kIme, lozinka, ime, prezime, datumRodjenja, pol, uloga);
	}

	public boolean isBlokiran() {
		return blokiran;
	}

	public void setBlokiran(boolean blokiran) {
		this.blokiran = blokiran;
	}

}
