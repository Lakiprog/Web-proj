package domain;

import java.time.LocalDateTime;

public class Manifestacija {
	private int id;
	private String naziv, posterLink;
	private String datumVreme;
	private Lokacija lokacija;
	private int brMesta;
	private double cenaRegular;
	private StatusManifestacije status;
	private TipManifestacije tip;
	private boolean obrisan;
	
	public Manifestacija() {
		
	}
	
	
	public Manifestacija(int id, String naziv, String datumVreme, String posterLink, Lokacija lokacija, int brMesta,
			double cenaRegular, StatusManifestacije status, TipManifestacije tip) {
		super();
		this.naziv = naziv;
		this.datumVreme = datumVreme;
		this.posterLink = posterLink;
		this.lokacija = lokacija;
		this.brMesta = brMesta;
		this.cenaRegular = cenaRegular;
		this.status = status;
		this.tip = tip;
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isObrisan() {
		return obrisan;
	}

	public void setObrisan(boolean obrisan) {
		this.obrisan = obrisan;
	}

	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public String getDatumVreme() {
		return datumVreme;
	}
	public void setDatumVreme(String datumVreme) {
		this.datumVreme = datumVreme;
	}
	public String getPosterLink() {
		return posterLink;
	}
	public void setPosterLink(String posterLink) {
		this.posterLink = posterLink;
	}
	public Lokacija getLokacija() {
		return lokacija;
	}
	public void setLokacija(Lokacija lokacija) {
		this.lokacija = lokacija;
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
	
	
}
