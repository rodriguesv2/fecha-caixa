package closebox.model;

/**
 * Classe modelo do sistema responsavel
 * Atributos nome, que é o proprio nome do Jogador e pontosDeVida, que é a pontuacao do Jogador.
 * @author Reinaldo
 *
 */
public class Jogador {
	private String nome; // o nome do Jogador
	private int pontosDeVida; // a pontuacao do Jogador
	
	/**
	 * Construtor da classe
	 * @param nome o proprio nome
	 * @param pontosDeVida os pontos de vida restantes do Jogador
	 */
	public Jogador(String nome, int pontosDeVida){
		this.nome = nome;
		this.pontosDeVida = pontosDeVida;
	}
	
	/**
	 * Construtor da classe, sem parametros
	 * o nome comeca vazio e pontosDeVida com zero.
	 */
	public Jogador(){
		nome = "";
		pontosDeVida = 0;
	}
	
	/**
	 * Modificador do nome
	 * @param nome o nome do Jogador
	 */
	public void setNome(String nome){
		this.nome = nome;
	}
	
	/**
	 * Metodo get do nome do Jogador
	 * @return nome do Jogador
	 */
	public String getNome(){
		return nome;
	}
	
	/**
	 * Metodo get dos pontosDeVida do Jogador
	 * @return os pontosDeVida do Jogador
	 */
	public int getPontosDeVida(){
		return pontosDeVida;
	}
	
	/**
	 * Metodo modificador dos pontosDeVida do Jogador
	 * @param pontosDeVida os pontosDeVida do Jogador
	 */
	public void setPontosDeVida(int pontosDeVida){
		this.pontosDeVida = pontosDeVida;
	}
}
