package pt.iscte.poo.instalacao.aparelhos;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;

import pt.iscte.poo.instalacao.Aparelho;
import pt.iscte.poo.instalacao.Ligavel;
import pt.iscte.poo.instalacao.Tomada;

public class Tripla implements Ligavel {

	private List<Tomada> entradasDaTripla = new ArrayList<Tomada>();
	private String name;
	private double maxPower;
	private int plugs;
	private boolean on;

	public Tripla(JSONObject obj) {
		name = obj.get("id").toString();
		maxPower = Double.parseDouble(obj.get("potenciaMaxima").toString());
		plugs = Integer.parseInt(obj.get("nTomadas").toString());
		createTriplaEntries();
	}

	private void createTriplaEntries() {
		for (int i = 0; i != this.plugs; i++) {
			Tomada plug = new Tomada();
			entradasDaTripla.add(plug);
		}
	}

	public String getId() {
		return this.name;
	}

	public double getMaxPower() {
		return this.maxPower;
	}

	public int getNrPlugs() {
		return this.plugs;
	}

	public List<Tomada> getTriplaPlugs() {
		return entradasDaTripla;
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

	public Tomada searchFreeTriplaEntry() {

		for (Tomada plug : entradasDaTripla) {
			if (plug.getDevice() == null) {
				return plug;
			}
		}
		return null;

	}

	public Ligavel searchDevice(String deviceName) {
		for (Tomada entry : entradasDaTripla) {
			if (entry.getDevice() != null && ((Aparelho) entry.getDevice()).getId().equals(deviceName)) {
				return ((Aparelho) entry.getDevice());
			}
		}
		return null;
	}

	public double getTotalPowerTripla() {
		double total = 0.0;
		for (Tomada entry : entradasDaTripla) {
			if (entry.getDevice() != null && entry.getDevice().estaLigado()) {
				if (entry.getDevice() instanceof Computador) {
					total += ((Computador) entry.getDevice())
							.getSinusoidalPower();
				} else
					total += ((Aparelho) entry.getDevice()).potenciaMaxima();
			}
		}
		return total;
	}

}
