package beans;

public class Lokacija {
	private double sirina, duzina;
	private Adresa adresa;
	
	
	public Lokacija(double sirina, double duzina, Adresa adresa) {
		super();
		this.sirina = sirina;
		this.duzina = duzina;
		this.adresa = adresa;
	}
	
	public double getSirina() {
		return sirina;
	}
	public void setSirina(double sirina) {
		this.sirina = sirina;
	}
	public double getDuzina() {
		return duzina;
	}
	public void setDuzina(double duzina) {
		this.duzina = duzina;
	}
	public Adresa getAdresa() {
		return adresa;
	}
	public void setAdresa(Adresa adresa) {
		this.adresa = adresa;
	}
	
	
}
