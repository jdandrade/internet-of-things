package pt.iscte.poo.instalacao.aparelhos.utility;

import org.json.simple.JSONObject;

public class Ciclo {
	
	private int duration;
	private double power;
	

	public Ciclo(JSONObject cicleobj) {
		this.duration = Integer.parseInt(cicleobj.get("duracao").toString());
		this.power = Double.parseDouble(cicleobj.get("potencia").toString());
	}

	public int getDuration(){
		return this.duration;
	}
	
	public double getPower(){
		return this.power;
	}

	@Override
	public String toString() {
		return "Ciclo - Duracao : " + duration + " power : " + power;
	}
}
