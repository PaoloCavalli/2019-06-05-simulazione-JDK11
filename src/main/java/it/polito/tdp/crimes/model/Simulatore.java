package it.polito.tdp.crimes.model;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.crimes.db.EventsDao;
import it.polito.tdp.crimes.model.Evento.TipoEvento;

public class Simulatore {

	//INPUT 
	Integer N; // Agenti disponibili
	Integer anno;
	Integer mese;
	Integer giorno;
	
	//Stato del Mondo
	private Graph <Integer, DefaultWeightedEdge> grafo;
	private Map<Integer,Integer> agenti; //CHIAVE: DISTRETTO VALORE: N: AGENTI LIBERI
	//Coda degli eventi
	private PriorityQueue<Evento> queue;
	
	//OUTPUT
	private Integer malGestiti;
	
	public void init(Integer N, Integer anno,Integer mese,Integer giorno, Graph<Integer, DefaultWeightedEdge> grafo  ) {
		this.N=N;
		this.anno= anno;
		this.mese= mese;
		this.giorno= giorno;
		this.grafo=grafo;
		
		this.malGestiti=0;
		this.agenti = new HashMap<Integer, Integer>();
		for(Integer d: this.grafo.vertexSet()) {
			agenti.put(d, 0);
		}
		
		//Una volta che ho la centrale meno criminosa la popolo di sbirri e popolo quindi la mappa
		EventsDao dao = new EventsDao();
		Integer minD = dao.getDistrettoMenoCriminoso(anno);
		this.agenti.put(minD, N);
		
		//creo la coda e la riempio di eventi
		this.queue = new PriorityQueue<Evento>();
		
		for(Event e : dao.listAllEventsByDate(anno, mese, giorno)) {
			queue.add(new Evento(TipoEvento.CRIMINE, e.getReported_date(), e));
		}
		
		
	}
	
	public int run() {
		Evento e ;
		while ((e = queue.poll()) != null) {
			switch(e.getTipo()) {
			case CRIMINE:
				System.out.println("NUOVO CRIMINE! "+ e.getCrimine().getIncident_id());
				//cerco l'agente più vicino
				Integer partenza = null;
				partenza = cercaAgente(e.getCrimine().getDistrict_id());
			    
				if(partenza!= null) {
					//agente viene occupato
					
					this.agenti.put(partenza, this.agenti.get(partenza)-1);
					Double distanza;
					if(partenza.equals(e.getCrimine().getDistrict_id()))
						distanza = 0.0;
					else {
						distanza = this.grafo.getEdgeWeight(this.grafo.getEdge(partenza, e.getCrimine().getDistrict_id()));
						
						Long secondi = (long ) ((distanza*1000)/ (60/3.6));
						this.queue.add(new Evento(TipoEvento.ARRIVA_GENTE, e.getData().plusSeconds(secondi),e.getCrimine() ));
					}
					
				}else {
					//nessun agente libero al momento crimine mal gestito
					System.out.println("CRIMINE:" +e.getCrimine().getIncident_id()+"MAL GESTITO!");
					malGestiti++;
				}
				break;
			case ARRIVA_GENTE:
				System.out.println("ARRIVA AGENTE PER CRIMINE:" +e.getCrimine().getIncident_id());
				Long duration = getDurata(e.getCrimine().getOffense_category_id());
				this.queue.add(new Evento(TipoEvento.GESTITO, e.getData().plusSeconds(duration),e.getCrimine() ));
				//controllo se è mal gestito 
				if(e.getData().isAfter(e.getCrimine().getReported_date().plusMinutes(15))) {
				System.out.println("CRIMINE:" +e.getCrimine().getIncident_id()+"MAL GESTITO!");
			    this.malGestiti++;
				}
				break;
			case GESTITO:
				System.out.println("CRIMINE:" +e.getCrimine().getIncident_id()+"BEN GESTITO!");
				this.agenti.put(e.getCrimine().getDistrict_id(), this.agenti.get(e.getCrimine().getDistrict_id())+1);
				break;
				
			}
		}
		return malGestiti;
		
	}
	
	public Long getDurata(String offense_category_type) {
		if(offense_category_type.equals("all_other_crimes")) {
			Random r = new Random();
			if(r.nextDouble() > 0.5) 
				return Long.valueOf(2*60*60);
			else
				return Long.valueOf(1*60*60);
		
		}else 
			return Long.valueOf(2*60*60);
	}
	public Integer cercaAgente(Integer distretto_id) {
		Double distanza = Double.MAX_VALUE;
		Integer distretto = null;
		
		for(Integer d : this.agenti.keySet()) {
			if(this.agenti.get(d) >0) {
				if(distretto_id.equals(d)) {
					distanza = 0.0;
					distretto = d;
					
				}else if (this.grafo.getEdgeWeight(this.grafo.getEdge(distretto_id, d))< distanza) {
					distanza = this.grafo.getEdgeWeight(this.grafo.getEdge(distretto_id, d));
					distretto = d;
					
				}
			}
		}
		return distretto;
		
		
	}
	
}
