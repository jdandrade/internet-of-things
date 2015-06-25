package pt.iscte.poo.instalacao;


public class Tomada {
	
	private Ligavel device;
	
	public Ligavel getDevice(){
		return this.device;
	}

	public void connectDevice(Ligavel ligavel) {
		this.device = ligavel;
	}

}
