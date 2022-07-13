package it.polito.tdp.crimes.model;

import java.time.LocalDate;
import java.util.List;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.crimes.model.Evento.EventType;


public class Simulatore {
	
	//INPUT
	Graph<Long, DefaultWeightedEdge> grafo;
	private List<Event> eventi;
	private int mese;
	private int anno;
	private int giorno;
	private int nAgenti;
	
	//OUTPUT
	public int nEventiMalGestiti;
	
	//modello del mondo
	public int agentiLiberi;
	
	//coda degli eventi
	private PriorityQueue<Evento> queue;

	public Simulatore( List<Event> eventi,int anno,Graph<Long, DefaultWeightedEdge> grafo, int mese, int giorno, int nAgenti) {
		this.eventi = eventi;
		this.anno=anno;
		this.grafo = grafo;
		this.mese = mese;
		this.giorno = giorno;
		this.nAgenti = nAgenti;
	}
	
	public void init() {
		
		agentiLiberi = nAgenti;
		queue= new PriorityQueue<>();
		//inizializzo output
		nEventiMalGestiti=0;
		for(int i=0;i<this.nAgenti;i++) {
			
			queue.add(new Evento(0,eventi.get(i),EventType.INIZIO_INTERVENTO));
		}
		
	}
	
	public void run() {
		
		while(!queue.isEmpty()) {
			Evento e = this.queue.poll();
			
			processEvent(e);
		}
		
	}

	private void processEvent(Evento e) {
		if(!this.eventi.isEmpty()) {
		
		switch(e.getType()) {
		
		case INIZIO_INTERVENTO:
			
			//if(agentiLiberi >0) {
				//agentiLiberi--;
				if(e.evento.getOffense_category_id().compareTo("all-other-crimes")==0) {
					
					if(Math.random()<0.5) {
						
						int tempoMinuti = e.getTime()+this.tempoSpost(e.evento, eventi.get(0))+60;
						
						int orarioE = e.evento.getReported_date().getMinute();
						if(orarioE < e.getTime()+15+this.tempoSpost(e.evento, eventi.get(0))) nEventiMalGestiti++;
						
						eventi.remove(e.evento);
						queue.add(new Evento(tempoMinuti,e.evento,EventType.FINE_INTERVENTO));
						
						
					}else {
						
						int tempoMinuti = e.getTime()+this.tempoSpost(e.evento, eventi.get(0))+120;
						
						int orarioE = e.evento.getReported_date().getMinute();
						if(orarioE < e.getTime()+15+this.tempoSpost(e.evento, eventi.get(0))) nEventiMalGestiti++;
						
						eventi.remove(e.evento);
						queue.add(new Evento(tempoMinuti,e.evento,EventType.FINE_INTERVENTO));
						
						
					}
					
//				queue.add(new Evento(e.getTime()+))
//				eventi.remove(e.evento);
				}else {
					int tempoMinuti = e.getTime()+this.tempoSpost(e.evento, eventi.get(0))+120;
					
					int orarioE = e.evento.getReported_date().getMinute();
					if(orarioE < e.getTime()+15+this.tempoSpost(e.evento, eventi.get(0))) nEventiMalGestiti++;
					
					eventi.remove(e.evento);
					queue.add(new Evento(tempoMinuti,e.evento,EventType.FINE_INTERVENTO));
					
				}
				
//			}else {
//				//se non ho agenti liberi non faccio nulla
//			}
			break;
		
		case FINE_INTERVENTO:
			
			//if(agentiLiberi > 0)
			//agentiLiberi++;
			queue.add(new Evento(e.getTime(),eventi.get(0),EventType.INIZIO_INTERVENTO));
			
			
			
		}
		}
		
	}
	
	private int tempoSpost(Event e1,Event e2) {
		LatLng p1 = e1.coord;
		LatLng p2 = e2.coord;
		if(e1.coord != e2.coord) {
		double distanza = LatLngTool.distance(p1, p2, LengthUnit.KILOMETER);
		int minuti = (int)(distanza /(60*60));
//		if(minuti > 15) {
//			nEventiMalGestiti++;
//		}
		return minuti;
	}else return 0;

	}
	
	
}
