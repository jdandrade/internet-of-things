package pt.iscte.poo.instalacao.aparelhos;

import org.json.simple.JSONObject;


public class LampadaVariavel extends AparelhoPotenciaVariavel {

	public LampadaVariavel(JSONObject obj) {
		super(obj.get("id").toString(), Double.parseDouble(obj.get(
				"potenciaMaxima").toString()));
	}

}
