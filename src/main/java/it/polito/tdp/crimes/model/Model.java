package it.polito.tdp.crimes.model;

import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.crimes.db.EventsDao;

public class Model {
	
	private EventsDao dao;
	private Map<Long,Event> mappaEventi;
	private Graph<Long, DefaultWeightedEdge> grafo;
	
	//risulatati simulazione
	public int eventiMalGestiti;
	
	public Model () {
		dao = new EventsDao();
	}
	
	
	
	public void creaGrafo(int anno) {
		
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		for(Adiacenza a : this.dao.getAdiacenze(anno)) {
			Graphs.addEdgeWithVertices(this.grafo, a.getDistrettoId1(),a.getDistrettoId2(), a.peso);
		}
			
		
	}
	
	public String getVertici() {
		return "#VERTICI : "+this.grafo.vertexSet().size()+"\n";
	}
		
	public String getEdge() {
		return "#ARCHI : "+this.grafo.edgeSet().size()+"\n";
	}
	
	public String getAdiacenze(){
		
		String s = "";
		
		for(long l : this.grafo.vertexSet()) {
			s += "Adiacenti a  "+l+"\n";
			for(long l2 : Graphs.neighborListOf(this.grafo, l)) {
				s += "-"+l2+"\n";
			}
		}
		return s;
	}
	
	public void simula(int N,int anno,int mese,int giorno) {
		Simulatore sim = new Simulatore(dao.listEventSimulazione(anno, mese, giorno),anno,this.grafo,mese,giorno,N);
		sim.init();
		sim.run();
		this.eventiMalGestiti = sim.nEventiMalGestiti;
		
	}
	
	public void setMappe() {
		for(Event e : this.dao.listAllEvents()) {
			mappaEventi.put(e.getIncident_id(), e);
		}
	}
}
