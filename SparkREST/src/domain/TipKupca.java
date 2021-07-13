package domain;

public class TipKupca {
	private int id;
	private TipMedalje tip;
	private double popust;
	private int brBodova;
	private boolean obrisan;
	
	public TipMedalje getTip() {
		return tip;
	}
	public void setTip(TipMedalje tip) {
		this.tip = tip;
	}
	public double getPopust() {
		return popust;
	}
	public void setPopust(double popust) {
		this.popust = popust;
	}
	public int getBrBodova() {
		return brBodova;
	}
	public void setBrBodova(int brBodova) {
		this.brBodova = brBodova;
	}
	
	public void setForTip(TipMedalje tip) {
		if(tip == TipMedalje.BRONZANI) {
			popust = 5;
			brBodova = 2000;
		}else if(tip == TipMedalje.SREBRNI) {
			popust = 10;
			brBodova = 10000;
		}else {
			popust = 20;
			brBodova = 100000;
		}
	}
	
	public TipKupca(int id, TipMedalje tip, double popust, int brBodova) {
		super();
		this.tip = tip;
		this.popust = popust;
		this.brBodova = brBodova;
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
	
	
}
