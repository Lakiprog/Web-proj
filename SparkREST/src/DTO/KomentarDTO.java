package DTO;

import domain.Komentar;
import domain.Kupac;
import domain.Manifestacija;

public class KomentarDTO {
	private String kIme, ime, prezime, manifestacija, komentar;
	private int ocena, id;
	private boolean odobren;
	
	public KomentarDTO(Komentar k, Kupac kupac, Manifestacija m) {
		this.id = k.getId();
		this.kIme = kupac.getkIme();
		this.ime = kupac.getIme();
		this.prezime = kupac.getPrezime();
		this.manifestacija = m.getNaziv();
		this.komentar = k.getKomentar();
		this.ocena = k.getOcena();
		this.odobren = k.isOdobren();
	}
	
	public String getkIme() {
		return kIme;
	}
	public void setkIme(String kIme) {
		this.kIme = kIme;
	}
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public String getPrezime() {
		return prezime;
	}
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}
	public String getManifestacija() {
		return manifestacija;
	}
	public void setManifestacija(String manifestacija) {
		this.manifestacija = manifestacija;
	}
	public String getKomentar() {
		return komentar;
	}
	public void setKomentar(String komentar) {
		this.komentar = komentar;
	}
	public int getOcena() {
		return ocena;
	}
	public void setOcena(int ocena) {
		this.ocena = ocena;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isOdobren() {
		return odobren;
	}

	public void setOdobren(boolean odobren) {
		this.odobren = odobren;
	}
	
	
}
