package closebox.model;

import java.util.ArrayList;

import closebox.activity.R;
import android.view.View;

public class AbaixarPlacas {
	
	private int[] ordemDasPlacas;
	private boolean placa7abaixada;
	private boolean placa8abaixada;
	private boolean placa9abaixada;
	private boolean primeiraPlaca;
	private int placaAnterior;
	private int qtdePlacas;
	private int qtdeJogadores;
	private int diferenca;
	private boolean girarDados;
	private boolean levantarPlacas;
	private boolean calcularPontosRestantes;
	private boolean mostraRanking;
	private boolean perguntarSobreDado;
	private boolean ultimaPlaca;
	private JogaDado jogaDado;
	private Pontos pontos;
	private ArrayList<String> listaJogadores;
	
	public AbaixarPlacas(){
		ordemDasPlacas = new int[9];
		placa7abaixada = false;
		placa8abaixada = false;
		placa9abaixada = false;
		qtdePlacas = 9;
		primeiraPlaca = true;
		girarDados = false;
		levantarPlacas = false;
		calcularPontosRestantes = false;
		mostraRanking = false;
		perguntarSobreDado = false;
		ultimaPlaca = false;
	}
		
	/**
	 * Flag que assinala se pode o dialogo sobre jogar com um dado aparecer.
	 * @return Se deve aparecer.
	 */
	public boolean isPerguntarSobreDado() {
		return perguntarSobreDado;
	}

	/**
	 * Assinala a flag para liberar a pergunta sobre jogar com um dado.
	 * @param perguntarSobreDado Flag a ser assinalada.
	 */
	public void setPerguntarSobreDado(boolean perguntarSobreDado) {
		this.perguntarSobreDado = perguntarSobreDado;
	}

	/**
	 * Flag que libera os pontos de ranking serem mostrados na tela.
	 * @return Flag.
	 */
	public boolean isMostraRanking() {
		return mostraRanking;
	}

	/**
	 * Assinalar se deve ou não mostrar ranking.
	 * @param mostraRanking Flag a ser assinalada
	 */
	public void setMostraRanking(boolean mostraRanking) {
		this.mostraRanking = mostraRanking;
	}

	/**
	 * Acesso ao valor da primeira placa jogada.
	 * @return Primeira placa jogada.
	 */
	public int getPlacaAnterior() {
		return placaAnterior;
	}

	/**
	 * Assinala se deve ou não calcular os pontos restantes
	 * @return
	 */
	public boolean isCalcularPontosRestantes() {
		return calcularPontosRestantes;
	}

	/**
	 * Recebe o objeto JogaDado para manter o controle do mesmo.
	 * @param jogaDado Classe JogaDado.
	 */
	public void setJogaDado(JogaDado jogaDado){
		this.jogaDado = jogaDado;
	}
	
	/**
	 * Recebe o objeto Pontos para manter o controle do mesmo.
	 * @param pontos Classe Pontos.
	 */
	public void setPontos(Pontos pontos){
		this.pontos = pontos;
	}
	
	/**
	 * Diz se os dado devem girar;
	 * @return Flag
	 */
	public boolean isGirarDados() {
		return girarDados;
	}
	
	/**
	 * Assinalar se os dado devem girar;
	 * @param girarDados Flag
	 */
	public void setGirarDados(boolean girarDados){
		this.girarDados = girarDados;
	}
	
	/**
	 * Informa se as placas devem levantar mediante a jogada errada.
	 * @return Flag
	 */
	public boolean isLevantarPlacas() {
		return levantarPlacas;
	}

	/**
	 * 
	 * @param levantarPlacas
	 */
	public void setLevantarPlacas(boolean levantarPlacas) {
		this.levantarPlacas = levantarPlacas;
	}

	public int identificarPlacaDown(View view){
		int idPlacaDown;
		
		switch (view.getId()) {
		case R.id.imageViewPlaca_1:
			idPlacaDown = R.id.imageViewPD1;
			break;
			
		case R.id.imageViewPlaca_2:
			idPlacaDown = R.id.imageViewPD2;
			break;
			
		case R.id.imageViewPlaca_3:
			idPlacaDown = R.id.imageViewPD3;
			break;
			
		case R.id.imageViewPlaca_4:
			idPlacaDown = R.id.imageViewPD4;
			break;
			
		case R.id.imageViewPlaca_5:
			idPlacaDown = R.id.imageViewPD5;
			break;
			
		case R.id.imageViewPlaca_6:
			idPlacaDown = R.id.imageViewPD6;
			break;
			
		case R.id.imageViewPlaca_7:
			idPlacaDown = R.id.imageViewPD7;
			break;
			
		case R.id.imageViewPlaca_8:
			idPlacaDown = R.id.imageViewPD8;
			break;
			
		case R.id.imageViewPlaca_9:
			idPlacaDown = R.id.imageViewPD9;
			break;

		default:
			idPlacaDown =  1;
			break;
		}
		
		return idPlacaDown;
	}
	
	public int[] embaralharPlacas(){
		int[] arrayImagens  = {R.drawable.placa_1, R.drawable.placa_2, R.drawable.placa_3, R.drawable.placa_4
				,R.drawable.placa_5, R.drawable.placa_6, R.drawable.placa_7, R.drawable.placa_8, R.drawable.placa_9};

		int[] novoArrayImagens = new int[9];
		int i = 0;

		while(i < 9){
			if(i == 0){
				int sorteio = (int)Math.ceil((Math.random()*9) - 1);
				novoArrayImagens[i] = arrayImagens[sorteio];
				ordemDasPlacas[i] = sorteio+1;
				i++;
			}else{
				int j;
				int sorteio = (int)Math.ceil((Math.random()*9) - 1);
				
				for(j = 0; j < i; j++){
					if(novoArrayImagens[j] == arrayImagens[sorteio])
						break;
				}
				if(j == i){
					novoArrayImagens[i] = arrayImagens[sorteio];
					ordemDasPlacas[i] = sorteio+1;
					i++;
				}
			}
		}
		return novoArrayImagens;
	}
	
	public int[] getOrdemDasPlacas(){
		return ordemDasPlacas;
	}
	
	public int qualEhAPosicaoDaPlaca(int valor){
		int i = 0;
		for(i = 0; i < 9; i++){
			if(valor == ordemDasPlacas[i])break;
		}
		
		return i+1;
	}
	
	public int getPosicaoDaPlaca(View view){
		int posicao;
		
		switch (view.getId()) {
		case R.id.imageViewPlaca_1:
			posicao = 1;
			break;
			
		case R.id.imageViewPlaca_2:
			posicao = 2;
			break;
			
		case R.id.imageViewPlaca_3:
			posicao = 3;
			break;
			
		case R.id.imageViewPlaca_4:
			posicao = 4;
			break;
			
		case R.id.imageViewPlaca_5:
			posicao = 5;
			break;
			
		case R.id.imageViewPlaca_6:
			posicao = 6;
			break;
			
		case R.id.imageViewPlaca_7:
			posicao = 7;
			break;
			
		case R.id.imageViewPlaca_8:
			posicao = 8;
			break;
			
		case R.id.imageViewPlaca_9:
			posicao = 9;
			break;

		default:
			posicao =  1;
			break;
		}
		
		return posicao;
	}
	
	public boolean placasAltasAbaixadas(){
		if(placa7abaixada && placa8abaixada && placa9abaixada) return true;
		else												   return false;
	}
	
	public void setFlagPlacasAltasFalse(int placa){
		if(placa == 7) 	   placa7abaixada = false;
		else if(placa == 8)placa8abaixada = false;
		else if(placa == 9)placa9abaixada = false;
	}
	
	public void setFlagPlacasAltasTrue(int valor){
		if(valor == 7)     placa7abaixada = true;
		else if(valor == 8)placa8abaixada = true;
		else if(valor == 9)placa9abaixada = true;
	}
	
	public void setQuantidadeJodador(int qtdeJogadores){
		this.qtdeJogadores = qtdeJogadores;
	}
	
	public int getQuantidadejogador(){
		return qtdeJogadores;
	}
	
	public void setListaDeJogadores(ArrayList<String> listaJogadores){
		this.listaJogadores = listaJogadores;
	}
	
	public ArrayList<String> getListaDeJogadores(){
		return listaJogadores;
	}
	
	public void setPlacaAnterior(int placa){
		placaAnterior = placa;
	}
	
	public void setPrimeiraPlaca(boolean primeiraPlaca){
		this.primeiraPlaca = primeiraPlaca;
	}
	
	public boolean isUltimaPlaca() {
		return ultimaPlaca;
	}

	public void gerenciaJogada(int placa){
		int somaDados;
		if(!jogaDado.getEhUmDado())somaDados = jogaDado.resultadoDaSoma();
		else		               somaDados = jogaDado.getValorDado1();

		if(primeiraPlaca){
			if(placa == somaDados){
				qtdePlacas --;
				pontos.calculaJogada(placa, primeiraPlaca);
				girarDados = true;
				levantarPlacas = false;
				mostraRanking = true;
				perguntarSobreDado = true;
				setFlagPlacasAltasTrue(placa);
			}else{
				if(qtdePlacas != 1){
					primeiraPlaca = false;
					diferenca = somaDados - placa;
					placaAnterior = placa;
				}else{
					ultimaPlaca = true;
				}
			}
		}else{
			if(placa==diferenca){
				qtdePlacas -= 2;
				pontos.calculaJogada((placa+placaAnterior), primeiraPlaca);
				primeiraPlaca = true;
				girarDados = true;
				mostraRanking = true;
				perguntarSobreDado = true;
				setFlagPlacasAltasTrue(placa);
				setFlagPlacasAltasTrue(placaAnterior);
			}else{
				levantarPlacas = true;
				setFlagPlacasAltasFalse(placa);
				setFlagPlacasAltasFalse(placaAnterior);
			}
		}
		if(qtdePlacas == 0){
			pontos.adicionarPontosRanking(30);
			jogaDado.setGirarDado1(false);
			jogaDado.setGirarDado2(false);
			calcularPontosRestantes = true;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
