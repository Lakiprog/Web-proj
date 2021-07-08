package handlers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import beans.Prodavac;

public class ProdavacHandler {
	
	private static class Prodavci{
		public HashMap<Integer, Prodavac> prodavci = new HashMap<>();
	}
	
	private ArrayList<Prodavac> prodavci;
	private HashMap<Integer, Prodavac> ucitani;
	private final String PRODAVCI_FAJL = "data/prodavci.json";
	
	public ProdavacHandler() {
		prodavci = new ArrayList<>();
		ucitajProdavce();
	}
	
	public ArrayList<Prodavac> getProdavci() {
		return prodavci;
	}
	
	private void ucitajProdavce() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			ucitani = mapper.readValue(new File(PRODAVCI_FAJL), Prodavci.class).prodavci;
			for (Prodavac prodavac : ucitani.values()) {
				if(!prodavac.isObrisan()) {
					prodavci.add(prodavac);
				}
			}
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}
	}
	
	public void dodajProdavca(Prodavac k) {
		prodavci.add(k);
		ucitani.put(k.getId(), k);
		sacuvaj();
	}
	
	public void brisiProdavcaFizicki(Integer id) {
		ucitani.remove(id);
		prodavci.clear();
		for (Prodavac prodavac : ucitani.values()) {
			if(!prodavac.isObrisan()) {
				prodavci.add(prodavac);
			}
		}
		sacuvaj();
	}
	
	private void sacuvaj() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		try {
			Prodavci k = new Prodavci();
			k.prodavci = ucitani;
			mapper.writeValue(new File(PRODAVCI_FAJL), k);
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
	
	public Prodavac poId(Integer id) {
		return ucitani.get(id);
	}
	
	public Prodavac poKorisnickomImenu(String kIme) {
		for (Prodavac prodavac : prodavci) {
			if(prodavac.getkIme().equals(kIme) && !prodavac.isObrisan()) {
				return prodavac;
			}
		}
		return null;
	}
	
}
