package pt.iscte.poo.instalacao.aparelhos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import pt.iscte.poo.instalacao.Relogio;
import pt.iscte.poo.instalacao.aparelhos.utility.Ciclo;
import pt.iscte.poo.instalacao.aparelhos.utility.Evento;
import pt.iscte.poo.instalacao.aparelhos.utility.Programa;

public class MaqLavar extends AparelhoPotenciaVariavel {

	private List<Programa> myPrograms = new ArrayList<Programa>();

	public MaqLavar(JSONObject obj) {
		super(obj.get("id").toString(), Double.parseDouble(obj.get(
				"potenciaMaxima").toString()));

		JSONArray jarray = (JSONArray) obj.get("programas");
		Iterator<?> iterPrograms = jarray.listIterator();
		
		while(iterPrograms.hasNext()){
			Programa program = new Programa((JSONObject)iterPrograms.next());
			myPrograms.add(program);
		}
		
	}

	public List<Programa> getProgram() {
		return this.myPrograms;

	}

	public void setProgram(Evento e, Relogio relogio) {
		
		for(Programa program : myPrograms){
			if(e.getProgramName().equals(program.getId())){
				List<Ciclo> myCicles = program.getCicles();
				int tempo = relogio.getTempoAtual();
				for (Ciclo cicle : myCicles) {
					Evento event = new Evento("REGULA", e.getDeviceName(), tempo,
							cicle.getPower());
					relogio.addEvent(event);
					tempo = tempo + cicle.getDuration();
				}
				Evento event = new Evento("DESLIGA", e.getDeviceName(), tempo);
				relogio.addEvent(event);
			}
		}
	}
}
