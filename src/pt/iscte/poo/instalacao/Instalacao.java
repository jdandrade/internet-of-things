package pt.iscte.poo.instalacao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import pt.iscte.poo.instalacao.aparelhos.AparelhoPotenciaVariavel;
import pt.iscte.poo.instalacao.aparelhos.MaqLavar;
import pt.iscte.poo.instalacao.aparelhos.Tripla;
import pt.iscte.poo.instalacao.aparelhos.utility.Evento;

public class Instalacao extends Observable {

	private static Instalacao instance;
	private static ArrayList<Linha> myLines = new ArrayList<Linha>();
	private Map<String, Double> potencias = new LinkedHashMap<String, Double>();

	public static Instalacao getInstanciaUnica() {
		if (instance == null)
			instance = new Instalacao();
		return instance;
	}

	public void novaLinha(String lineName, int plugs) {
		Linha line = new Linha(lineName, plugs);
		myLines.add(line);

	}
																																																																																																													
	public void ligaAparelhoATomadaLivre(String name, Ligavel ligavel) {
		searchLine(name).searchFreePlug().connectDevice(ligavel);
	}

	private Linha searchLine(String name) {

		for (Linha line : myLines) {
			if (name.equals(line.getName()))
				return line;
		}
		return null;
	}

	@Override
	public String toString() {
		String ts = "";

		ts += "t = " + (Relogio.getInstanciaUnica()).getTempoAtual() + "\n";

		for (Linha l : myLines) {
			ts += "\t" + l.getName() + " " + l.getLineTotalPower() + "W\n";
		}
		return ts;
	}

	public void init(JSONArray objectos) {

		Iterator<?> iterator = objectos.listIterator();

		while (iterator.hasNext()) {
			JSONObject obj = (JSONObject) iterator.next();
			String lineName = obj.get("nome").toString();
			int plugs = Integer.parseInt(obj.get("tomadas").toString());

			Linha line = new Linha(lineName, plugs);
			myLines.add(line);
		}
	}

	public List<Ligavel> lerAparelhos(JSONArray listaAparelhos) {
		List<Ligavel> devices = new ArrayList<Ligavel>();
		Iterator<?> iterator = listaAparelhos.listIterator();

		while (iterator.hasNext()) {
			JSONObject obj = (JSONObject) iterator.next();

			Ligavel device = Aparelho.novoAparelho(obj);
			devices.add(device);
		}

		return devices;
	}

	public void lerLigacoes(JSONArray listaLigacoes, List<Ligavel> aparelhos) {

		Iterator<?> iterator = listaLigacoes.listIterator();

		while (iterator.hasNext()) {
			JSONObject obj = (JSONObject) iterator.next();
			String deviceName = obj.get("aparelho").toString();

			for (Ligavel device : aparelhos) {
				if (device instanceof Tripla) {
					if (deviceName.equals(((Tripla)device).getId())) {
						this.ligaAparelhoATomadaLivre(obj.get("ligadoA")
								.toString(), device);
					}
				} else if (((Aparelho) device).getId().equals(deviceName)) {
					this.ligaAparelhoATomadaLivre(
							obj.get("ligadoA").toString(), device);
				}
			}
		}
		showInstalationMap();

	}

	public void lerEventos(JSONArray listaEventos) {

		Iterator<?> iterator = listaEventos.listIterator();

		while (iterator.hasNext()) {
			JSONObject obj = (JSONObject) iterator.next();

			Evento event = new Evento(obj);

			Relogio.getInstanciaUnica().addEvent(event);

		}
	}

	public void simula(long fim) {

		Relogio relogio = Relogio.getInstanciaUnica();

		for (; relogio.getTempoAtual() < fim; relogio.tique()) {

			while (checkAndRunEvents(relogio))
				;

			checkTotalPowerLines();
			setChanged();
			notifyObservers(potencias);
		}

	}

	private void checkTotalPowerLines() {
		potencias.clear();
		for (Linha l : myLines) {
			potencias.put(l.getName(), this.potenciaNaLinha(l.getName()));
		}
	}

	private boolean checkAndRunEvents(Relogio relogio) {

		if (relogio.getMySortedEvents().isEmpty()) {
			return false;
		}

		Evento firstEvent = relogio.getMySortedEvents().get(0);

		if (relogio.getTempoAtual() == firstEvent.getTime()) {
			System.out.println(firstEvent.toString());
			if (firstEvent.getAction().equals("LIGA")) {
				this.getAparelho(firstEvent.getDeviceName()).liga();
			} else if (firstEvent.getAction().equals("DESLIGA")) {
				this.getAparelho(firstEvent.getDeviceName()).desliga();
			} else if (firstEvent.getAction().equals("AUMENTA")) {
				((AparelhoPotenciaVariavel) this.getAparelho(firstEvent
						.getDeviceName())).aumenta(firstEvent.getIncrease());
			} else if (firstEvent.getAction().equals("PROGRAMA")) {
				((MaqLavar)this.getAparelho(firstEvent.getDeviceName())).setProgram(firstEvent,relogio);				
			} else if (firstEvent.getAction().equals("REGULA")) {
				((AparelhoPotenciaVariavel) this.getAparelho(firstEvent
						.getDeviceName())).setPower(firstEvent.getIncrease());
			}

			relogio.removeFirstEvent();
			return true;
		}
		return false;
	}

	public void removeTodasAsLinhas() {
		myLines.clear();
	}

	public double potenciaNaLinha(String lineName) {
		return this.searchLine(lineName).getLineTotalPower();
	}

	public Tomada getTomadaLivre(String lineName) {
		return this.searchLine(lineName).searchFreePlug();
	}

	public Ligavel getAparelho(String deviceName) {
		for (Linha line : myLines) {
			for (Tomada plug : line.getMyPlugs()) {
				if (plug.getDevice() instanceof Tripla) {
					if (((Tripla) plug.getDevice()).searchDevice(deviceName) == null)
						continue;
					return ((Tripla) plug.getDevice()).searchDevice(deviceName);
				} else if (plug.getDevice() != null
						&& deviceName.equals(((Aparelho) plug.getDevice())
								.getId())) {
					return plug.getDevice();
				}

			}

		}
		return null;
	}

	public void showInstalationMap() {

		System.out.println("\n-------------- INSTALATION ----------------");
		for (Linha line : myLines) {
			System.out.println(line.getName() + " - "
					+ line.getMyPlugs().size());
			for (Tomada plug : line.getMyPlugs()) {
				if (plug.getDevice() instanceof Tripla) {
					System.out.println("\t "
							+ ((Tripla) plug.getDevice()).getId() + " - "
							+ ((Tripla) plug.getDevice()).getNrPlugs());
					for (Tomada entry : ((Tripla) plug.getDevice())
							.getTriplaPlugs()) {
						if(entry.getDevice()!= null)
						System.out.println("\t\t "
								+ ((Aparelho) entry.getDevice()).getId());

					}
				} else if (plug.getDevice() != null) {
					System.out.println("\t "
							+ ((Aparelho) plug.getDevice()).getId());
				}

			}

		}
		System.out.println("--------------     END     ----------------\n");
	}

}
