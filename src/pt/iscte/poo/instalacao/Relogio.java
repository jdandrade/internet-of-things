package pt.iscte.poo.instalacao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pt.iscte.poo.instalacao.aparelhos.utility.Evento;

public class Relogio {

	private static Relogio instance;
	private int time;
	private List<Evento> myEvents = new ArrayList<Evento>();

	public static Relogio getInstanciaUnica() {
		if (instance == null)
			instance = new Relogio();
		return instance;
	}

	public void tique() {
		time++;
	}

	public int getTempoAtual() {
		return time;
	}

	public List<Evento> getMySortedEvents() {
		Collections.sort(myEvents);
		return this.myEvents;
	}

	public void addEvent(Evento event) {
		myEvents.add(event);
	}

	public void removeFirstEvent() {
		myEvents.remove(0);

	}
}
