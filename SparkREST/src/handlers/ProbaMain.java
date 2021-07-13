package handlers;

import java.util.HashMap;

import domain.Adresa;
import domain.Karta;
import domain.Komentar;
import domain.Lokacija;
import domain.Manifestacija;
import domain.StatusKarte;
import domain.StatusManifestacije;
import domain.TipKarte;
import domain.TipKupca;
import domain.TipManifestacije;
import domain.TipMedalje;


public class ProbaMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//KarteHandler handler = new KarteHandler();
		//handler.dodajKartu(new Karta(handler.nextId(), "2021.08.10", 1, null, 1000, StatusKarte.REZERVISANA, TipKarte.REGULAR));
		//handler.dodajKartu(new Karta(handler.nextId(), "2021.09.10", 2, null, 300, StatusKarte.REZERVISANA, TipKarte.FANPIT));
		//System.out.println(handler.getKarte().size());
		

		TipoviKupcaHandler handler = new TipoviKupcaHandler();
		handler.dodajTipKupca(new TipKupca(handler.nextId(), TipMedalje.BRONZANI, 0.1, 100));
		System.out.println(handler.gettipovi().size());
	}

}
