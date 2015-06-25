package pt.iscte.poo.instalacao.aparelhos.utility;

import org.json.simple.JSONObject;

import pt.iscte.poo.instalacao.Aparelho;
import pt.iscte.poo.instalacao.Instalacao;
import pt.iscte.poo.instalacao.aparelhos.AparelhoPotenciaVariavel;
import pt.iscte.poo.instalacao.aparelhos.Computador;

/**
 * This class was made to store Evento Objects. An Evento Object consists in an
 * Object which is used in event scheduling over time. Contains information on
 * when to start, what type of action to execute, on what device should it
 * operate and in some cases, the power change value and program name
 * 
 * @author jdandrade @ ISCTE-IUL
 */

public class Evento implements Comparable<Evento> {
	/**
	 * Variables stored in this class
	 */
	private String action;
	private String device;
	private int time;
	private double increase;
	private String program;

	/**
	 * @param action
	 *            is the query that is executed on this event.
	 * @param device
	 *            is the name of the device where the action will operate.
	 * @param time
	 *            it's when the Event must start.
	 */
	public Evento(String action, String device, int time) {
		this.action = action;
		this.device = device;
		this.time = time;
	}

	/**
	 * @param action
	 *            is the query that is executed on this event.
	 * @param device
	 *            is the name of the device where the action will operate.
	 * @param time
	 *            it's when the Event must start.
	 * @param increase
	 *            the power change value.
	 */
	public Evento(String action, String device, int time, double increase) {
		this.action = action;
		this.device = device;
		this.time = time;
		this.increase = increase;
	}

	/**
	 * @param obj
	 *            JSONObject obj that contains all the information needed to
	 *            create an Event, it was read from eventos.json.
	 */
	public Evento(JSONObject obj) {
		this.action = obj.get("accao").toString();
		this.device = obj.get("idAparelho").toString();
		this.time = Integer.parseInt(obj.get("tempo").toString());

		if (obj.containsKey("valor"))
			this.increase = Double.parseDouble(obj.get("valor").toString());
		if (obj.containsKey("programa"))
			this.program = obj.get("programa").toString();
	}

	/**
	 * Getter for the name of the operation in an Evento.
	 * 
	 * @return a String, can be null.
	 */
	public String getAction() {
		return this.action;
	}

	/**
	 * Getter for the device name in an Evento.
	 * 
	 * @return String.
	 */
	public String getDeviceName() {
		return this.device;
	}

	/**
	 * Variable holding the starting time of an Evento.
	 * 
	 * @return positive or zero integer.
	 */
	public int getTime() {
		return this.time;
	}

	/**
	 * Getter for the increase variable in an Evento.
	 * 
	 * @return a positive or zero double value.
	 */
	public double getIncrease() {
		return this.increase;
	}

	/**
	 * @return a String containing the name of the program, null if Evento
	 *         doesn't have a program.
	 */
	public String getProgramName() {
		return this.program;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Evento e) {
		return this.time - e.getTime();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (increase != 0)
			return "[" + time + "] Event " + action + " device " + device
					+ " CHANGE TO -> " + increase + "W";
		else if (program != null)
			return "[" + time + "] Event " + action + " device " + device
					+ " - Program : " + program;
		else if (Instalacao.getInstanciaUnica().getAparelho(this.device) instanceof AparelhoPotenciaVariavel) {
			if (!(Instalacao.getInstanciaUnica().getAparelho(this.device) instanceof Computador))
				return "["
						+ time
						+ "] Event "
						+ action
						+ " device "
						+ device
						+ " - "
						+ "MAX POW -> "
						+ ((AparelhoPotenciaVariavel) Instalacao
								.getInstanciaUnica().getAparelho(device))
								.getMaxPower()
						+ "W - ACTUAL POWER -> "
						+ ((Aparelho) Instalacao.getInstanciaUnica()
								.getAparelho(device)).potenciaMaxima() + "W";
			else
				return "["
						+ time
						+ "] Event "
						+ action
						+ " device "
						+ device
						+ " - "
						+ "MAX POW -> "
						+ ((AparelhoPotenciaVariavel) Instalacao
								.getInstanciaUnica().getAparelho(device))
								.getMaxPower()
						+ "W - ACTUAL POWER -> "
						+ ((Computador) Instalacao.getInstanciaUnica()
								.getAparelho(device)).getSinusoidalPower()
						+ "W";
		}
		return "["
				+ time
				+ "] Event "
				+ action
				+ " device "
				+ device
				+ " - "
				+ ((Aparelho) Instalacao.getInstanciaUnica()
						.getAparelho(device)).potenciaMaxima() + "W";
	}
}
