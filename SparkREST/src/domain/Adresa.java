package domain;

public class Adresa {
	private String ulica, broj, mesto;
	private int postanskiBr;
	
	public Adresa(String ulica, String broj, String mesto, int postanskiBr) {
		super();
		this.ulica = ulica;
		this.broj = broj;
		this.mesto = mesto;
		this.postanskiBr = postanskiBr;
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
	public int getPostanskiBr() {
		return postanskiBr;
	}
	public void setPostanskiBr(int postanskiBr) {
		this.postanskiBr = postanskiBr;
	}
}
