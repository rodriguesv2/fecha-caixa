package closebox.model;

import closebox.activity.R;
import android.view.View;

public class JogaDado {
	
	private boolean girar1;
	private boolean girar2;
	private boolean dado1Parado = false;//garante que o dado foi acionado
	private boolean dado2Parado = false;//garante que o dado foi acionado
	private int valorDado1;
	private int valorDado2;
	private boolean ehUmDado = false;
	
	public void sorteioDado1(){
		valorDado1 = (int)Math.ceil((Math.random()*6));
	}
	
	public void sorteioDado2(){
		valorDado2 = (int)Math.ceil((Math.random()*6));
	}
	
	public void setValorDado1(int valorDado1){
		this.valorDado1 = valorDado1;
	}
	
	public void setValorDado2(int valorDado2){
		this.valorDado2 = valorDado2;
	}
	
	public int resultadoDaSoma(){
		return valorDado1 + valorDado2;
	}
	
	public int getValorDado1(){
		return valorDado1;
	}
	
	public int getValorDado2(){
		return valorDado2;
	}
	
	public void setGirarDado1(boolean girar){
		girar1 = girar;
	}
	
	public void setGirarDado2(boolean girar){
		girar2 = girar;
	}
	
	public boolean getGirarDado1(){
		return girar1;
	}
	
	public boolean getGirarDado2(){
		return girar2;
	}
	
	public void setDado1Parado(boolean dado1Parado){
		this.dado1Parado = dado1Parado;
	}
	
	public void setDado2Parado(boolean dado2Parado){
		this.dado2Parado = dado2Parado;
	}
	
	public boolean getDado1Parado(){
		return dado1Parado;
	}
	
	public boolean getDado2Parado(){
		return dado2Parado;
	}
	
	public void setEhUmDado(boolean ehUmDado){
		this.ehUmDado = ehUmDado;
	}
	
	public boolean getEhUmDado(){
		return ehUmDado;
	}
	
	public void acaoDado(View view){
		if(view.getId() == R.id.imageView1){
			girar1 = false;
			dado1Parado = true;
		}else{
			girar2 = false;
			dado2Parado = true;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
