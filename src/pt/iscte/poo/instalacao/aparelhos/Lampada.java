package pt.iscte.poo.instalacao.aparelhos;

import org.json.simple.JSONObject;

public class Lampada extends AparelhoPotenciaFixa {

	public Lampada(String name, double power) {
		super(name, power);
	}

	public Lampada(JSONObject obj) {
		super(obj.get("id").toString(), Double.parseDouble(obj.get("potencia")
				.toString()));
	}

}
