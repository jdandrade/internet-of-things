package pt.iscte.poo.instalacao.aparelhos;

import org.json.simple.JSONObject;

public class Computador extends AparelhoPotenciaVariavel {

	private double current_power;

	public Computador(String name, double maxPower) {
		super(name, maxPower);
		current_power = maxPower;
	}

	public Computador(JSONObject obj) {
		super(obj.get("id").toString(), Double.parseDouble(obj.get(
				"potenciaMaxima").toString()));
		current_power = Double.parseDouble(obj.get("potenciaMaxima").toString());
	}

	public double getSinusoidalPower() {
		return  Math.random()*current_power;
	}

}
