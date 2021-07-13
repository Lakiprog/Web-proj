package domain;

public class Lokacija {
	private int id;
	private double sirina, duzina;
	private Adresa adresa;
	private boolean obrisan;
	
	
	public Lokacija(int id, double sirina, double duzina, Adresa adresa) {
		super();
		this.sirina = sirina;
		this.duzina = duzina;
		this.adresa = adresa;
		this.id = id;
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
	
	
}
