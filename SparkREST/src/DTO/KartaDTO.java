package DTO;

import domain.Karta;
import domain.Kupac;
import domain.StatusKarte;
import domain.TipKarte;

public class KartaDTO {
	private String kIme, ime, prezime, manifestacija, datumVreme;
	private int brMesta, id;
	private double cena;
	private TipKarte tip;
	private StatusKarte status;
	
	public KartaDTO(Karta ka, Kupac k) {
		this.id = ka.getId();
		this.kIme = k.getkIme();
		this.ime = k.getIme();
		this.prezime = k.getPrezime();
		this.manifestacija = ka.getManifestacija().getNaziv();
		this.datumVreme = ka.getManifestacija().getDatumVremePocetka();
		this.tip = ka.getTip();
		this.status = ka.getStatus();
		this.brMesta = ka.getManifestacija().getBrMesta();
		this.cena = ka.getCena();
	}
	
	

	public double getCena() {
		return cena;
	}



	public void setCena(double cena) {
		this.cena = cena;
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

	public String getDatumVreme() {
		return datumVreme;
	}

	public void setDatumVreme(String datumVreme) {
		this.datumVreme = datumVreme;
	}

	public TipKarte getTip() {
		return tip;
	}

	public void setTip(TipKarte tip) {
		this.tip = tip;
	}

	public StatusKarte getStatus() {
		return status;
	}

	public void setStatus(StatusKarte status) {
		this.status = status;
	}

	public int getBrMesta() {
		return brMesta;
	}

	public void setBrMesta(int brMesta) {
		this.brMesta = brMesta;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}
