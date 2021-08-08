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

import DTO.KarteSortiranjeDTO;
import domain.Admin;
import domain.Karta;
import util.KartePoCeniSort;
import util.KartePoDatumuSort;
import util.KartePoManifestacijiSort;

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
		for (Karta Karta : ucitani.values()) {
			if(Karta.getId() > next) {
				next = Karta.getId();
			}
		}
		return next+1;
	}
	
	
	public Karta poId(Integer id) {
		return ucitani.get(id);
	}
	
	public ArrayList<Karta> sortiranje(KarteSortiranjeDTO kriterijumi, ArrayList<Karta> ks){
		ArrayList<Karta> k = new ArrayList<>();
		
		if(ks == null) {
			ks = karte;
		}
		
		for (Karta karta : ks) {
			if(karta.getManifestacija().getNaziv().contains(kriterijumi.getNaziv())) {
				
				if(kriterijumi.getTip().equals("SVE") || karta.getTip().toString().equals(kriterijumi.getTip())) {
					
					if(kriterijumi.getStatus().equals("SVE") || karta.getStatus().toString().equals(kriterijumi.getStatus())) {
						
						if(karta.getCena() <= kriterijumi.getCenaMax() && karta.getCena() >= kriterijumi.getCenaMin()) {
							
							if(kriterijumi.getDatumOd().equals("") || kriterijumi.getDatumDo().equals("")) {
								k.add(karta);
							}else {
								
								LocalDateTime pocetak = LocalDateTime.parse(karta.getDatumVreme());
								LocalDateTime kriterijumOd = LocalDateTime.parse(kriterijumi.getDatumOd() + "T00:00:00");
								LocalDateTime kriterijumDo = LocalDateTime.parse(kriterijumi.getDatumDo() + "T00:00:00");
								
								if(pocetak.isAfter(kriterijumOd) && pocetak.isBefore(kriterijumDo)) {
									k.add(karta);
								}
								
							}
							
						}
						
					}
					
				}
				
			}
		}
		
		
		switch(kriterijumi.getSortirajPo()) {
			case "CENA":
				Collections.sort(k, new KartePoCeniSort());
				break;
			case "DATUM":
				Collections.sort(k, new KartePoDatumuSort());
				break;
			case "NAZIV":
				Collections.sort(k, new KartePoManifestacijiSort());
				break;
		}
		
		if(kriterijumi.getSortiraj().equals("OPADAJUCE")) {
			Collections.reverse(k);
		}
		
		return k;
	}
	
	public ArrayList<Karta> kartePoManifestacijama(ArrayList<Integer> ids){
		ArrayList<Karta> k = new ArrayList<>();
		
		for (Integer id : ids) {
			for (Karta karta : karte) {
				
				if(karta.getManifestacija().getId() == id) {
					k.add(karta);
				}
				
			}
		}
		
		return k;
	}
	
	public ArrayList<Karta> kartePoIdima(ArrayList<Integer> ids){
		ArrayList<Karta> k = new ArrayList<>();
		
		for (Integer id : ids) {
			k.add(poId(id));
		}
		
		return k;
	}
}
