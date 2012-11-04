package closebox.model;

import java.util.ArrayList;

public class Pontos {
	
	private int pontosRestantes;
	private int pontosRanking;
	private ArrayList<Integer> listaPontuacao;
	
	public Pontos(){
		pontosRestantes = 45;
		pontosRanking = 0;
	}
	
	/**
	 * Soma os pontos correspondentes a jogada.
	 * @param int aSerSubtraido
	 * @param boolean ehUmaPlaca
	 */
	public void calculaJogada(int aSerSubtraido, boolean ehUmaPlaca){
		pontosRestantes -= aSerSubtraido;
		if(ehUmaPlaca)pontosRanking += 10;
		else          pontosRanking += 5;
	}
	
	/**
	 * Escolher um valor para ser somado ao ranking
	 * @param int valor
	 */
	public void adicionarPontosRanking(int valor){
		pontosRanking += valor;
	}
	
	/**
	 * Retorna os pontos que restaram.
	 * @return int pontos restantes.
	 */
	public int getPontosRestantes(){
		return pontosRestantes;
	}
	
	/**
	 * Subtrai pontos de vida.
	 * @param int placa
	 */
	public void subtrairPontos(int placa){
		pontosRestantes -= placa;
	}
	
	/**
	 * Devolve os pontos de ranking
	 * @return int pontos de ranking
	 */
	public int getPontosRanking(){
		return pontosRanking;
	}
	
	/**
	 * Modificar completamente os pontos de ranking
	 * @param int ranking
	 */
	public void setPontosRanking(int ranking){
		pontosRanking = ranking;
	}

	/**
	 * Lista de pontuação referente a jogador.
	 * @return ArrayList<Integer> lista de pontuação.
	 */
	public ArrayList<Integer> getListaPontuacao() {
		return listaPontuacao;
	}

	/**
	 * Inserir lista de pontuação
	 * @param ArrayList<Integer> listaPontuacao
	 */
	public void setListaPontuacao(ArrayList<Integer> listaPontuacao) {
		this.listaPontuacao = listaPontuacao;
	}
	
	

	
	
	
}
