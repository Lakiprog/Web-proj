package handlers;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import DTO.ManifestacijaSortiranjeDTO;
import domain.Lokacija;
import domain.Manifestacija;
import domain.StatusManifestacije;
import util.ManifestacijePoCeniSort;
import util.ManifestacijePoDatumuSort;
import util.ManifestacijePoLokacijiSort;
import util.ManifestacijePoNazivuSort;

public class ManifestacijaHandler {
	
	private static class Manifestacije{
		public HashMap<Integer, Manifestacija> manifestacije = new HashMap<>();
	}
	
	private ArrayList<Manifestacija> manifestacije;
	private HashMap<Integer, Manifestacija> ucitani;
	private final String MANIFESTACIJE_FAJL = "data/manifestacije.json";
	
	public ManifestacijaHandler() {
		manifestacije = new ArrayList<>();
		ucitajManifestacije();
	}
	
	public ArrayList<Manifestacija> getManifestacije() {
		return manifestacije;
	}
	
	private void ucitajManifestacije() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			ucitani = mapper.readValue(new File(MANIFESTACIJE_FAJL), Manifestacije.class).manifestacije;
			for (Manifestacija Manifestacija : ucitani.values()) {
				if(!Manifestacija.isObrisan()) {
					manifestacije.add(Manifestacija);
				}
			}
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}
	}
	
	public void dodajManifestaciju(Manifestacija k) {
		manifestacije.add(k);
		ucitani.put(k.getId(), k);
		sacuvaj();
	}
	
	public void azurirajManifestaciju(Manifestacija m) {
		ucitani.put(m.getId(), m);
		for (int i = 0; i < manifestacije.size(); i++) {
			if(m.getId() == manifestacije.get(i).getId()){
				manifestacije.set(i, m);
			}
		}
		sacuvaj();
	}
	
	public void brisiManifestacijuLogicki(int id) {
		for(int i = 0; i < manifestacije.size(); i++) {
			if(manifestacije.get(i).getId() == id) {
				manifestacije.remove(i);
			}
		}
		for (Manifestacija Manifestacija : ucitani.values()) {
			if(Manifestacija.getId() == id) {
				Manifestacija.setObrisan(true);
			}
		}
		sacuvaj();
	}
	
	public void brisiManifestacijuFizicki(int id) {
		ucitani.remove(id);
		manifestacije.clear();
		for (Manifestacija Manifestacija : ucitani.values()) {
			if(!Manifestacija.isObrisan()) {
				manifestacije.add(Manifestacija);
			}
		}
		sacuvaj();
	}
	
	private void sacuvaj() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		try {
			Manifestacije k = new Manifestacije();
			k.manifestacije = ucitani;
			mapper.writeValue(new File(MANIFESTACIJE_FAJL), k);
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
		for (Manifestacija Manifestacija : ucitani.values()) {
			if(Manifestacija.getId() > next) {
				next = Manifestacija.getId();
			}
		}
		return next+1;
	}
	
	
	public Manifestacija poId(Integer id) {
		return ucitani.get(id);
	}
	
	public void aktiviraj(int id) {
		ucitani.get(id).setStatus(StatusManifestacije.AKTIVNO);
		for (Manifestacija manifestacija : manifestacije) {
			if(manifestacija.getId() == id) {
				manifestacija.setStatus(StatusManifestacije.AKTIVNO);
			}
		}
		sacuvaj();
	}
	
	public Manifestacija poNazivu(String naziv) {
		for (Manifestacija Manifestacija : manifestacije) {
			if(Manifestacija.getNaziv().equals(naziv) && !Manifestacija.isObrisan()) {
				return Manifestacija;
			}
		}
		return null;
	}
	
	public ArrayList<Manifestacija> sortiranje(ManifestacijaSortiranjeDTO kriterijumi, ArrayList<Manifestacija> ms) {
		ArrayList<Manifestacija> m = new ArrayList<>();
		
		if(ms == null) {
			ms = manifestacije;
		}
		
		for (Manifestacija manifestacija : ms) {
			if(manifestacija.getNaziv().toLowerCase().contains(kriterijumi.getNaziv().toLowerCase())) {
				
				if(manifestacija.getLokacija().getAdresa().toString().toLowerCase().contains(kriterijumi.getAdresa().toLowerCase())) {
					
					if(manifestacija.getCenaRegular() <= kriterijumi.getCenaMax() && manifestacija.getCenaRegular() >= kriterijumi.getCenaMin()) {
						
						if(manifestacija.getTip().toString().equals(kriterijumi.getTip()) || kriterijumi.getTip().equals("SVE")) {
							
							if((kriterijumi.getRasprodate().equals("SVE")) || (kriterijumi.getRasprodate().equals("RASPRODATE") && manifestacija.getBrSlobodnihMesta() == 0)
									|| (kriterijumi.getRasprodate().equals("NERASPRODATE") && manifestacija.getBrSlobodnihMesta() > 0)) {
								
								LocalDateTime pocetak = LocalDateTime.parse(manifestacija.getDatumVremePocetka());
								boolean datumi = true;
								
								if(!kriterijumi.getDatumOd().equals("")) {
									LocalDateTime kriterijumOd = LocalDateTime.parse(kriterijumi.getDatumOd() + "T00:00:00");
									if(pocetak.isBefore(kriterijumOd)) {
										datumi = false;
									}
								}
								
								if(!kriterijumi.getDatumDo().equals("")) {
									LocalDateTime kriterijumDo = LocalDateTime.parse(kriterijumi.getDatumDo() + "T00:00:00");
									if(pocetak.isAfter(kriterijumDo)) {
										datumi = false;
									}
								}
								
								if(kriterijumi.getDatumOd().equals("") && kriterijumi.getDatumDo().equals("")){
									m.add(manifestacija);
								}else if(datumi){
									m.add(manifestacija);
								}
								
							}
							
							
						}
						
					}
					
				}
				
			}
		}
		
		switch(kriterijumi.getSortirajPo()) {
			case "CENA":
				Collections.sort(m, new ManifestacijePoCeniSort());
				break;
			case "DATUM":
				Collections.sort(m, new ManifestacijePoDatumuSort());
				break;
			case "LOKACIJA":
				Collections.sort(m, new ManifestacijePoLokacijiSort());
				break;
			case "NAZIV":
				Collections.sort(m, new ManifestacijePoNazivuSort());
				break;
		}
		
		if(kriterijumi.getSortiraj().equals("OPADAJUCE")) {
			Collections.reverse(m);
		}
		
		return m;
	}
	
	public ArrayList<Manifestacija> manifestacijePoIdima(ArrayList<Integer> ids){
		ArrayList<Manifestacija> m = new ArrayList<>();
		
		for (Integer id : ids) {
			m.add(poId(id));
		}
		
		return m;
	}
	
	public boolean checkBadTimes(String s, String e, Lokacija lokacija, int id) {
		LocalDateTime start = LocalDateTime.parse(s);
		LocalDateTime end = LocalDateTime.parse(e);
		
		for (Manifestacija manifestacija : manifestacije) {
			
			if(manifestacija.getId() == id) {
				continue;
			}
			
			if(manifestacija.getLokacija().equals(lokacija)) {
				//System.out.println("tu sam");
				
				LocalDateTime startOther = LocalDateTime.parse(manifestacija.getDatumVremePocetka());
				LocalDateTime endOther = LocalDateTime.parse(manifestacija.getDatumVremeKraja());
				
				if(start.isBefore(startOther) && end.isAfter(startOther)) {
					return true;
				}if(start.isAfter(startOther) && end.isBefore(endOther)) {
					return true;
				}if(start.isEqual(startOther) || end.isEqual(endOther) || start.isEqual(endOther) || end.isEqual(startOther)) {
					return true;
				}
				
			}
			
		}
		return false;
	}
}
