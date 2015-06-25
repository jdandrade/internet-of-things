package pt.iscte.poo.instalacao.aparelhos;

import org.json.simple.JSONObject;

public class Frigorifico extends AparelhoPotenciaFixa {

	public Frigorifico(String name, double power) {
		super(name, power);
	}

	public Frigorifico(JSONObject obj) {
		super(obj.get("id").toString(), Double.parseDouble(obj.get("potencia")
				.toString()));
	}

}
