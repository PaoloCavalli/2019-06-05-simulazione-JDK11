package it.polito.tdp.crimes.model;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.crimes.db.EventsDao;

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
		
		this.queue = new PriorityQueue<Evento>();
		
		
	}
	
	public void run() {
		
	}
	
}
