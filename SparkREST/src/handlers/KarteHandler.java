package handlers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import domain.Admin;
import domain.Karta;

public class KarteHandler {
	
	private static class Karte{
		public HashMap<Integer, Karta> karte = new HashMap<>();
	}
	
	private ArrayList<Karta> karte;
	private HashMap<Integer, Karta> ucitani;
	private final String KARTE_FAJL = "data/karte.json";
	
	public KarteHandler() {
		karte = new ArrayList<>();
		ucitajKarte();
	}
	
	public ArrayList<Karta> getKarte() {
		return karte;
	}
	
	private void ucitajKarte() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			ucitani = mapper.readValue(new File(KARTE_FAJL), Karte.class).karte;
			for (Karta Karta : ucitani.values()) {
				if(!Karta.isObrisan()) {
					karte.add(Karta);
				}
			}
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}
	}
	
	public void dodajKartu(Karta k) {
		karte.add(k);
		ucitani.put(k.getId(), k);
		sacuvaj();
	}
	
	public void azurirajKartu(Karta k) {
		ucitani.put(k.getId(), k);
		for (Karta karta : karte) {
			if(k.getId() == karta.getId()){
				karta = k;
			}
		}
		sacuvaj();
	}
	
	public void brisiKartuLogicki(int id) {
		for(int i = 0; i < karte.size(); i++) {
			if(karte.get(i).getId() == id) {
				karte.remove(i);
			}
		}
		for (Karta Karta : ucitani.values()) {
			if(Karta.getId() == id) {
				Karta.setObrisan(true);
			}
		}
		sacuvaj();
	}
	
	public void brisiKartuFizicki(int id) {
		ucitani.remove(id);
		karte.clear();
		for (Karta Karta : ucitani.values()) {
			if(!Karta.isObrisan()) {
				karte.add(Karta);
			}
		}
		sacuvaj();
	}
	
	private void sacuvaj() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		try {
			Karte k = new Karte();
			k.karte = ucitani;
			mapper.writeValue(new File(KARTE_FAJL), k);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int nextId() {
		int next = 0;
		for (Karta Karta : karte) {
			if(Karta.getId() > next) {
				next = Karta.getId();
			}
		}
		return next+1;
	}
	
	
	public Karta poId(Integer id) {
		return ucitani.get(id);
	}
	
	public Karta poIdKupca(int id) {
		for (Karta Karta : karte) {
			if(Karta.getIdKupca() == id && !Karta.isObrisan()) {
				return Karta;
			}
		}
		return null;
	}
}
