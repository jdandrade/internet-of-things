package pt.iscte.poo.instalacao.aparelhos;

import org.json.simple.JSONObject;

public class MicroOndas extends AparelhoPotenciaVariavel {

	public MicroOndas(String name, int maxPower) {
		super(name, maxPower);
	}

	public MicroOndas(JSONObject obj) {
		super(obj.get("id").toString(), Double.parseDouble(obj.get("potenciaMaxima").toString()));
	}
	
}
