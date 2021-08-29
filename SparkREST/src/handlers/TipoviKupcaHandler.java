package handlers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import domain.TipKupca;
import domain.TipMedalje;


public class TipoviKupcaHandler {
	private static class Tipovi{
		public HashMap<Integer, TipKupca> tipovi = new HashMap<>();
	}
	
	private ArrayList<TipKupca> tipovi;
	private HashMap<Integer, TipKupca> ucitani;
	private final String TIPOVI_FAJL = "data/tipovikupca.json";
	
	public TipoviKupcaHandler() {
		tipovi = new ArrayList<>();
		ucitajTipKupce();
	}
	
	public ArrayList<TipKupca> gettipovi() {
		return tipovi;
	}
	
	private void ucitajTipKupce() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			ucitani = mapper.readValue(new File(TIPOVI_FAJL), Tipovi.class).tipovi;
			for (TipKupca tipKupca : ucitani.values()) {
				if(!tipKupca.isObrisan()) {
					tipovi.add(tipKupca);
				}
			}
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}
	}
	
	public void dodajTipKupca(TipKupca k) {
		tipovi.add(k);
		ucitani.put(k.getId(), k);
		sacuvaj();
	}
	
	public void azurirajTipKupca(TipKupca k) {
		ucitani.put(k.getId(), k);
		for (TipKupca tipKupca : tipovi) {
			if(k.getId() == tipKupca.getId()){
				tipKupca = k;
			}
		}
		sacuvaj();
	}
	
	public void brisiTipKupcaLogicki(int id) {
		for(int i = 0; i < tipovi.size(); i++) {
			if(tipovi.get(i).getId() == id) {
				tipovi.remove(i);
			}
		}
		for (TipKupca tipKupca : ucitani.values()) {
			if(tipKupca.getId() == id) {
				tipKupca.setObrisan(true);
			}
		}
		sacuvaj();
	}
	
	public void brisiTipKupcaaFizicki(int id) {
		ucitani.remove(id);
		tipovi.clear();
		for (TipKupca tipKupca : ucitani.values()) {
			if(!tipKupca.isObrisan()) {
				tipovi.add(tipKupca);
			}
		}
		sacuvaj();
	}
	
	private void sacuvaj() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		try {
			Tipovi k = new Tipovi();
			k.tipovi = ucitani;
			mapper.writeValue(new File(TIPOVI_FAJL), k);
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
		for (TipKupca tipKupca : ucitani.values()) {
			if(tipKupca.getId() > next) {
				next = tipKupca.getId();
			}
		}
		return next+1;
	}
	
	
	public TipKupca poId(Integer id) {
		return ucitani.get(id);
	}
	
	public TipKupca getSrebrni() {
		for (TipKupca tip : tipovi) {
			if(tip.getTip() == TipMedalje.SREBRNI) {
				return tip;
			}
		}
		return null;
	}
	
	public TipKupca getZlatni() {
		for (TipKupca tip : tipovi) {
			if(tip.getTip() == TipMedalje.ZLATNI) {
				return tip;
			}
		}
		return null;
	}
	
	public TipKupca getBronzani() {
		for (TipKupca tip : tipovi) {
			if(tip.getTip() == TipMedalje.BRONZANI) {
				return tip;
			}
		}
		return null;
	}
}
