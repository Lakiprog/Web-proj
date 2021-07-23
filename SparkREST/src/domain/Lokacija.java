package domain;

public class Lokacija {
	private int id;
	private double sirina, duzina;
	private Adresa adresa;
	private boolean obrisan;
	
	public Lokacija(){
		
	}
	
	
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adresa == null) ? 0 : adresa.hashCode());
		long temp;
		temp = Double.doubleToLongBits(duzina);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + id;
		result = prime * result + (obrisan ? 1231 : 1237);
		temp = Double.doubleToLongBits(sirina);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lokacija other = (Lokacija) obj;
		if (adresa == null) {
			if (other.adresa != null)
				return false;
		} else if (!adresa.equals(other.adresa))
			return false;
		if (Double.doubleToLongBits(duzina) != Double.doubleToLongBits(other.duzina))
			return false;
		if (id != other.id)
			return false;
		if (obrisan != other.obrisan)
			return false;
		if (Double.doubleToLongBits(sirina) != Double.doubleToLongBits(other.sirina))
			return false;
		return true;
	}
	
	
}
