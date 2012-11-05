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
	
	/**
	 * Gera um numero aleatorio para trabalhar com o dado numero 1.
	 */
	public void sorteioDado1(){
		valorDado1 = (int)Math.ceil((Math.random()*6));
	}
	
	/**
	 * Gera um numero aleatorio para trabalhar com o dado numero 2.
	 */
	public void sorteioDado2(){
		valorDado2 = (int)Math.ceil((Math.random()*6));
	}
	
	/**
	 * Mudar valor do dado 1.
	 * @param int valorDado1
	 */
	public void setValorDado1(int valorDado1){
		this.valorDado1 = valorDado1;
	}
	
	/**
	 * Mudar valor do dado 2.
	 * @param valorDado2
	 */
	public void setValorDado2(int valorDado2){
		this.valorDado2 = valorDado2;
	}
	
	/**
	 * Soma dos 2 dados.
	 * @return int Soma.
	 */
	public int resultadoDaSoma(){
		return valorDado1 + valorDado2;
	}
	
	/**
	 * Devolve o valor gerado por sorteioDado1().
	 * @return int valor do dado 1.
	 */
	public int getValorDado1(){
		return valorDado1;
	}
	
	/**
	 * Devolve o valor gerado por sorteioDado2().
	 * @return int valor do dado 2.
	 */
	public int getValorDado2(){
		return valorDado2;
	}
	
	/**
	 * Marcar uma flag para girar ou não o dado 1.
	 * @param boolean girar
	 */
	public void setGirarDado1(boolean girar){
		girar1 = girar;
	}
	
	/**
	 * Marcar uma flag para girar ou não o dado 2.
	 * @param boolean girar
	 */
	public void setGirarDado2(boolean girar){
		girar2 = girar;
	}
	
	/**
	 * Flag para a interface grafica girar ou não o dado 1.
	 * @return boolean boolean girar dado 1.
	 */
	public boolean getGirarDado1(){
		return girar1;
	}
	
	/**
	 * Flag para a interface grafica girar ou não o dado 2.
	 * @return boolean boolean girar dado 2.
	 */
	public boolean getGirarDado2(){
		return girar2;
	}
	
	/**
	 * Muda a flag de doa 1 parado
	 * @param boolean dado1Parado
	 */
	public void setDado1Parado(boolean dado1Parado){
		this.dado1Parado = dado1Parado;
	}
	
	/**
	 * Muda a flag de doa 2 parado
	 * @param boolean dado2Parado
	 */
	public void setDado2Parado(boolean dado2Parado){
		this.dado2Parado = dado2Parado;
	}
	
	/**
	 * Flag que informa se o dado 1 deve girar.
	 * @return boolean dado 1 parado.
	 */
	public boolean getDado1Parado(){
		return dado1Parado;
	}
	
	/**
	 * Flag que informa se o dado 2 deve girar.
	 * @return boolean dado 2 parado.
	 */
	public boolean getDado2Parado(){
		return dado2Parado;
	}
	
	/**
	 * Muda flag para informar se é dado.
	 * @param boolean ehUmDado. 
	 */
	public void setEhUmDado(boolean ehUmDado){
		this.ehUmDado = ehUmDado;
	}
	
	/**
	 * informa se é um dado.
	 * @return boolean é um dado.
	 */
	public boolean getEhUmDado(){
		return ehUmDado;
	}
	
	/**
	 * Muda flags gerenciando as jogadas de dados.
	 * @param View view
	 */
	public void acaoDado(View view){
		if(view.getId() == R.id.imageViewDG1){
			girar1 = false;
			dado1Parado = true;
		}else{
			girar2 = false;
			dado2Parado = true;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
