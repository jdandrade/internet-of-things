package pt.iscte.poo.instalacao.aparelhos;

import pt.iscte.poo.instalacao.Aparelho;

public class AparelhoPotenciaVariavel extends Aparelho {
	
	private double maxPower;

	public AparelhoPotenciaVariavel(String name, double maxPower) {
		super(name, 0.0);
		this.maxPower = maxPower;
	}
	
	public double getMaxPower(){
		return this.maxPower;
	}
	
	public void aumenta(double power){
		if((super.potenciaMaxima()+power)>this.maxPower)
			super.setPower(maxPower);
		else
			super.setPower(super.potenciaMaxima()+power);
	}

	public void setPotencia(double power){
		if(power<this.maxPower){
			super.setPower(power);
		}
	}
}
