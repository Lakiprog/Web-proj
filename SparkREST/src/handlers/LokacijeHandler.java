package handlers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import domain.Lokacija;

public class LokacijeHandler {
	private static class Lokacije{
		public HashMap<Integer, Lokacija> lokacije = new HashMap<>();
	}
	
	private ArrayList<Lokacija> lokacije;
	private HashMap<Integer, Lokacija> ucitani;
	private final String LOKACIJE_FAJL = "data/lokacije.json";
	
	public LokacijeHandler() {
		lokacije = new ArrayList<>();
		ucitajLokacije();
	}
	
	public ArrayList<Lokacija> getLokacije() {
		return lokacije;
	}
	
	private void ucitajLokacije() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			ucitani = mapper.readValue(new File(LOKACIJE_FAJL), Lokacije.class).lokacije;
			for (Lokacija lokacija : ucitani.values()) {
				if(!lokacija.isObrisan()) {
					lokacije.add(lokacija);
				}
			}
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}
	}
	
	public void dodajLokaciju(Lokacija k) {
		lokacije.add(k);
		ucitani.put(k.getId(), k);
		sacuvaj();
	}
	
	public void azurirajLokaciju(Lokacija k) {
		ucitani.put(k.getId(), k);
		for (Lokacija lokacija : lokacije) {
			if(k.getId() == lokacija.getId()){
				lokacija = k;
			}
		}
		sacuvaj();
	}
	
	public void brisiLokacijuLogicki(int id) {
		for(int i = 0; i < lokacije.size(); i++) {
			if(lokacije.get(i).getId() == id) {
				lokacije.remove(i);
			}
		}
		for (Lokacija lokacija : ucitani.values()) {
			if(lokacija.getId() == id) {
				lokacija.setObrisan(true);
			}
		}
		sacuvaj();
	}
	
	public void brisiLokacijuFizicki(int id) {
		ucitani.remove(id);
		lokacije.clear();
		for (Lokacija lokacija : ucitani.values()) {
			if(!lokacija.isObrisan()) {
				lokacije.add(lokacija);
			}
		}
		sacuvaj();
	}
	
	private void sacuvaj() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		try {
			Lokacije k = new Lokacije();
			k.lokacije = ucitani;
			mapper.writeValue(new File(LOKACIJE_FAJL), k);
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
		for (Lokacija lokacija : lokacije) {
			if(lokacija.getId() > next) {
				next = lokacija.getId();
			}
		}
		return next+1;
	}
	
	
	public Lokacija poId(Integer id) {
		return ucitani.get(id);
	}
}
