package DTO;

import domain.Komentar;

public class KomentarDTO {
	private String kIme, ime, prezime, manifestacija, komentar;
	private int ocena;
	
	public KomentarDTO(Komentar k) {
		this.kIme = k.getKupac().getkIme();
		this.ime = k.getKupac().getIme();
		this.prezime = k.getKupac().getPrezime();
		this.manifestacija = k.getManifestacija().getNaziv();
		this.komentar = k.getKomentar();
		this.ocena = k.getOcena();
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
	
	
}
