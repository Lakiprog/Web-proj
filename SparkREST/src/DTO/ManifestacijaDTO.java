package DTO;

import domain.Manifestacija;
import domain.StatusManifestacije;
import domain.TipManifestacije;

public class ManifestacijaDTO {

	private int id;
	private String naziv, posterLink;
	private String datumVremePocetka;
	private String datumVremeKraja;
	private String adresa;
	private double geoSirina;
	private double geoDuzina;
	private int brMesta;
	private int brSlobodnihMesta;
	private double cenaRegular;
	private StatusManifestacije status;
	private TipManifestacije tip;
	private boolean obrisan;
	private int brojOcena;
	private int sumaOcena;

	public ManifestacijaDTO() {}

	public ManifestacijaDTO(Manifestacija m) {
		this.id = m.getId();
		this.naziv = m.getNaziv();
		this.posterLink = m.getPosterLink();
		this.datumVremePocetka = m.getDatumVremePocetka();
		this.datumVremeKraja = m.getDatumVremeKraja();
		this.adresa = m.getLokacija().getAdresa().toString();
		this.geoSirina = m.getLokacija().getSirina();
		this.geoDuzina = m.getLokacija().getDuzina();
		this.brMesta = m.getBrMesta();
		this.brSlobodnihMesta = m.getBrSlobodnihMesta();
		this.cenaRegular = m.getCenaRegular();
		this.status = m.getStatus();
		this.tip = m.getTip();
		this.obrisan = m.isObrisan();
		this.brojOcena = m.getBrojOcena();
		this.sumaOcena = m.getSumaOcena();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getPosterLink() {
		return posterLink;
	}

	public void setPosterLink(String posterLink) {
		this.posterLink = posterLink;
	}

	public String getDatumVremePocetka() {
		return datumVremePocetka;
	}

	public void setDatumVremePocetka(String datumVremePocetka) {
		this.datumVremePocetka = datumVremePocetka;
	}

	public String getDatumVremeKraja() {
		return datumVremeKraja;
	}

	public void setDatumVremeKraja(String datumVremeKraja) {
		this.datumVremeKraja = datumVremeKraja;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public double getGeoSirina() {
		return geoSirina;
	}

	public void setGeoSirina(double geoSirina) {
		this.geoSirina = geoSirina;
	}

	public double getGeoDuzina() {
		return geoDuzina;
	}

	public void setGeoDuzina(double geoDuzina) {
		this.geoDuzina = geoDuzina;
	}

	public int getBrMesta() {
		return brMesta;
	}

	public void setBrMesta(int brMesta) {
		this.brMesta = brMesta;
	}

	public double getCenaRegular() {
		return cenaRegular;
	}

	public void setCenaRegular(double cenaRegular) {
		this.cenaRegular = cenaRegular;
	}

	public StatusManifestacije getStatus() {
		return status;
	}

	public void setStatus(StatusManifestacije status) {
		this.status = status;
	}

	public TipManifestacije getTip() {
		return tip;
	}

	public void setTip(TipManifestacije tip) {
		this.tip = tip;
	}

	public boolean isObrisan() {
		return obrisan;
	}

	public void setObrisan(boolean obrisan) {
		this.obrisan = obrisan;
	}

	public int getBrojOcena() {
		return brojOcena;
	}

	public void setBrojOcena(int brojOcena) {
		this.brojOcena = brojOcena;
	}

	public int getSumaOcena() {
		return sumaOcena;
	}

	public void setSumaOcena(int sumaOcena) {
		this.sumaOcena = sumaOcena;
	}

	public int getBrSlobodnihMesta() {
		return brSlobodnihMesta;
	}

	public void setBrSlobodnihMesta(int brSlobodnihMesta) {
		this.brSlobodnihMesta = brSlobodnihMesta;
	}

}
