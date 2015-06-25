package pt.iscte.poo.instalacao.aparelhos.utility;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Programa {
	
	private String id;
	private List<Ciclo> myCicles = new ArrayList<Ciclo>();

	public Programa(JSONObject jProgram) {
	
		this.id = jProgram.get("id").toString();
		
		JSONArray jCicles = (JSONArray)jProgram.get("ciclos");
		Iterator<?> iterCicles = jCicles.listIterator();
		
		while(iterCicles.hasNext()){
			JSONObject cicleobj = (JSONObject)iterCicles.next();
			
			Ciclo cicle = new Ciclo(cicleobj);
			myCicles.add(cicle);
		}
	}
	
	public String getId(){
		return this.id;
	}
	
	public List<Ciclo> getCicles(){
		return this.myCicles;
	}
}
