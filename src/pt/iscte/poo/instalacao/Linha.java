package pt.iscte.poo.instalacao;

import java.util.ArrayList;

import pt.iscte.poo.instalacao.aparelhos.Computador;
import pt.iscte.poo.instalacao.aparelhos.Tripla;

public class Linha {

	private String name;
	private int plugs;
	private ArrayList<Tomada> myPlugs = new ArrayList<Tomada>();

	public Linha(String lineName, int plugs) {
		this.name = lineName;
		this.plugs = plugs;

		createPlugs();
	}

	private void createPlugs() {
		for (int i = 0; i != this.plugs; i++) {
			Tomada plug = new Tomada();
			myPlugs.add(plug);
		}

	}

	public String getName() {
		return this.name;
	}

	public Tomada searchFreePlug() {
		for (Tomada plug : myPlugs) {
			if (plug.getDevice() == null)
				return plug;
			else if (plug.getDevice() instanceof Tripla) {
				return ((Tripla) plug.getDevice()).searchFreeTriplaEntry();
			}
		}
		return null;
	}

	public double getLineTotalPower() {
		double totalPower = 0.0;
		for (Tomada plug : myPlugs) {
			if (plug.getDevice() instanceof Tripla) {
				totalPower += ((Tripla) plug.getDevice()).getTotalPowerTripla();
			} else if (plug.getDevice() != null
					&& plug.getDevice().estaLigado()) {
				if (plug.getDevice() instanceof Computador) {
					totalPower += ((Computador)plug.getDevice()).getSinusoidalPower();
				} else
					totalPower += ((Aparelho) plug.getDevice())
							.potenciaMaxima();
			}
		}
		return totalPower;
	}

	public ArrayList<Tomada> getMyPlugs() {
		return this.myPlugs;
	}

}
