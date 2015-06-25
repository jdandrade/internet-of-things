package pt.iscte.poo.instalacao;

import org.json.simple.JSONObject;

import pt.iscte.poo.instalacao.aparelhos.Computador;
import pt.iscte.poo.instalacao.aparelhos.Frigorifico;
import pt.iscte.poo.instalacao.aparelhos.Lampada;
import pt.iscte.poo.instalacao.aparelhos.LampadaVariavel;
import pt.iscte.poo.instalacao.aparelhos.MaqLavar;
import pt.iscte.poo.instalacao.aparelhos.MicroOndas;
import pt.iscte.poo.instalacao.aparelhos.Torradeira;
import pt.iscte.poo.instalacao.aparelhos.Tripla;

public class Aparelho implements Ligavel {
	private double power;
	private String name;
	private boolean on;

	public Aparelho(String name, double power) {
		this.power = power;
		this.name = name;
	}

	@Override
	public void liga() {
		this.on = true;

	}

	@Override
	public void desliga() {
		this.on = false;

	}

	@Override
	public boolean estaLigado() {
		return this.on;
	}

	public void setPower(double power) {
		this.power = power;
	}

	public double potenciaMaxima() {
		return this.power;
	}

	public double potenciaAtual() {
		if (this.estaLigado())
			return this.power;
		return 0.0;
	}

	public String getId() {
		return this.name;
	}

	public static Ligavel novoAparelho(JSONObject obj) {

		String type = obj.get("tipo").toString();
		Ligavel aparelho = null;

		if (type.equals("frigorifico"))
			aparelho = new Frigorifico(obj);
		else if (type.equals("computador"))
			aparelho = new Computador(obj);
		else if (type.equals("tripla"))
			aparelho = new Tripla(obj);
		else if (type.equals("maqLavarRoupa"))
			aparelho = new MaqLavar(obj);
		else if (type.equals("microOndas"))
			aparelho = new MicroOndas(obj);
		else if (type.equals("lampada"))
			aparelho = new Lampada(obj);
		else if (type.equals("lampadaVariavel"))
			aparelho = new LampadaVariavel(obj);
		else if (type.equals("torradeira"))
			aparelho = new Torradeira(obj);
		return aparelho;
	}
}
