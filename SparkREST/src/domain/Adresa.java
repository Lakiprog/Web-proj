package domain;

public class Adresa {
	private String ulica, broj, mesto;
	
	public Adresa() {
		
	}
	
	public Adresa(String ulica, String broj, String mesto) {
		super();
		this.ulica = ulica;
		this.broj = broj;
		this.mesto = mesto;
	}
	
	public String getUlica() {
		return ulica;
	}
	public void setUlica(String ulica) {
		this.ulica = ulica;
	}
	public String getBroj() {
		return broj;
	}
	public void setBroj(String broj) {
		this.broj = broj;
	}
	public String getMesto() {
		return mesto;
	}
	public void setMesto(String mesto) {
		this.mesto = mesto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((broj == null) ? 0 : broj.hashCode());
		result = prime * result + ((mesto == null) ? 0 : mesto.hashCode());
		result = prime * result + ((ulica == null) ? 0 : ulica.hashCode());
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
		Adresa other = (Adresa) obj;
		if (broj == null) {
			if (other.broj != null)
				return false;
		} else if (!broj.equals(other.broj))
			return false;
		if (mesto == null) {
			if (other.mesto != null)
				return false;
		} else if (!mesto.equals(other.mesto))
			return false;
		if (ulica == null) {
			if (other.ulica != null)
				return false;
		} else if (!ulica.equals(other.ulica))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.mesto + ", " + this.ulica + " " + this.broj;
	}
	
}
