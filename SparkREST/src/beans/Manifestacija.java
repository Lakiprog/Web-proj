package beans;

public class Manifestacija {
	private String naziv, datumVreme, posterLink;
	private Lokacija lokacija;
	private int brMesta;
	private double cenaRegular;
	private StatusManifestacije status;
	private TipManifestacije tip;
	
	
	public Manifestacija(String naziv, String datumVreme, String posterLink, Lokacija lokacija, int brMesta,
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
