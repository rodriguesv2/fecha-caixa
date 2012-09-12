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
	
	public void calculaJogada(int aSerSubtraido, boolean ehUmaPlaca){
		pontosRestantes -= aSerSubtraido;
		if(ehUmaPlaca)pontosRanking += 10;
		else          pontosRanking += 5;
	}
	
	public void adicionarPontosRanking(int valor){
		pontosRanking += valor;
	}
	
	public int getPontosRestantes(){
		return pontosRestantes;
	}
	
	public void subtrairPontos(int placa){
		pontosRestantes -= placa;
	}
	
	public int getPontosRanking(){
		return pontosRanking;
	}
	
	public void setPontosRanking(int ranking){
		pontosRanking = ranking;
	}

	public ArrayList<Integer> getListaPontuacao() {
		return listaPontuacao;
	}

	public void setListaPontuacao(ArrayList<Integer> listaPontuacao) {
		this.listaPontuacao = listaPontuacao;
	}
	
	

	
	
	
}
