package it.polito.tdp.crimes.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.crimes.db.EventsDao;

public class Model {
	private EventsDao dao;
	private List<Integer> vertici;
	private Graph<Integer, DefaultWeightedEdge> grafo;
	
	public Model() {
		dao = new EventsDao();
	}
	
	public List<Integer> getAnni(){
		return this.dao.getAnni();
	}
	public void creaGrafo(Integer anno) {
		vertici = this.dao.getVertici();
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(this.grafo, vertici);
		for(Integer v1 : this.grafo.vertexSet()) {
			for(Integer v2 : this.grafo.vertexSet()) {
				if(!v1.equals(v2)) {
					if(grafo.getEdge(v1, v2) == null) {
						Double latMediaV1 = dao.getLatMedia(anno, v1);
						Double latMediaV2 = dao.getLonMedia(anno, v2);
						
						Double lonMediaV1 = dao.getLonMedia(anno, v1);
						Double lonMediaV2 = dao.getLonMedia(anno, v1);
						
						Double distanzaMedia = LatLngTool.distance(new LatLng(latMediaV1,lonMediaV1),
								                                   new LatLng(latMediaV2,lonMediaV2), LengthUnit.KILOMETER);
						Graphs.addEdgeWithVertices(this.grafo, v1, v2, distanzaMedia);
					}
				}
			}
		}
	}
	public int nVertici() {
		return this.grafo.vertexSet().size();
		}

		public int nArchi() {
		return this.grafo.edgeSet().size();
		}
		public Set<Integer> getVertici(){
			return this.grafo.vertexSet();
		}
		public List<Vicino> getVicini(Integer v){
			
		List<Vicino> vicini = new LinkedList<Vicino>();
			
			for(Integer i : Graphs.neighborListOf(this.grafo, v)) {
				Double peso = this.grafo.getEdgeWeight(this.grafo.getEdge(i, v));
				vicini.add(new Vicino(i,peso));
			}
		Collections.sort(vicini);
		return vicini;
		}
}
