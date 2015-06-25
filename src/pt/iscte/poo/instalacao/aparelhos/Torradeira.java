package pt.iscte.poo.instalacao.aparelhos;

import org.json.simple.JSONObject;

public class Torradeira extends AparelhoPotenciaFixa {

	public Torradeira(String name, double power) {
		super(name, power);
	}

	public Torradeira(JSONObject obj) {
		super(obj.get("id").toString(), Double.parseDouble(obj.get("potencia")
				.toString()));
	}

}
