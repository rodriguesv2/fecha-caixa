package closebox.controle;

import java.util.ArrayList;
import android.content.Context;
import android.view.View;
import closebox.model.*;

public class Controle{
	
	private AbaixarPlacas abaixarPlacas;
	private JogaDado jogaDado;
	private Pontos pontos;
	private Score score;
	private Opcoes opcoes;
	private Context context;
	
	public Controle(){
		abaixarPlacas = new AbaixarPlacas();
		jogaDado = new JogaDado();
		pontos = new Pontos();
		incluirJogaDadoEPontosAoAbaixarPlacas();
	}
	
	
	private void incluirJogaDadoEPontosAoAbaixarPlacas(){
		abaixarPlacas.setJogaDado(jogaDado);
		abaixarPlacas.setPontos(pontos);
	}
	
	public int qualEhAPosicaoDaPlaca(int valor){
		return abaixarPlacas.qualEhAPosicaoDaPlaca(valor);
	}
	
	public boolean isPerguntarSobreDado() {
		return abaixarPlacas.isPerguntarSobreDado();
	}
	
	public boolean isUltimaPlaca() {
		return abaixarPlacas.isUltimaPlaca();
	}

	public void setPerguntarSobreDado(boolean perguntarSobreDado) {
		abaixarPlacas.setPerguntarSobreDado(perguntarSobreDado);
	}
	
	public boolean isMostraRanking() {
		return abaixarPlacas.isMostraRanking();
	}

	public void setMostraRanking(boolean mostraRanking) {
		abaixarPlacas.setMostraRanking(mostraRanking);
	}

	
	public int getPlacaAnterior() {
		return abaixarPlacas.getPlacaAnterior();
	}
	
	public void setPlacaAnterior(int placa){
		abaixarPlacas.setPlacaAnterior(placa);
	}
	
	public boolean isLevantarPlacas() {
		return abaixarPlacas.isLevantarPlacas();
	}

	public void setLevantarPlacas(boolean levantarPlacas) {
		abaixarPlacas.setLevantarPlacas(levantarPlacas);
	}
	
	public boolean isCalcularPontosRestantes() {
		return abaixarPlacas.isCalcularPontosRestantes();
	}
	
	public void gerenciaJogada(int placa){
		abaixarPlacas.gerenciaJogada(placa);
	}
	
	public int identificarPlacaDown(View view){
		return abaixarPlacas.identificarPlacaDown(view);
	}
	
	public int[] embaralharPlacas(){
		return abaixarPlacas.embaralharPlacas();
	}
	
	public int[] getOrdemDasPlacas(){
		return abaixarPlacas.getOrdemDasPlacas();
	}
	
	public int getPosicaoDaPlaca(View view){
		return abaixarPlacas.getPosicaoDaPlaca(view);
	}
	
	public boolean placasAltasAbaixadas(){
		return abaixarPlacas.placasAltasAbaixadas();
	}
	
	public void setPrimeiraPlaca(boolean primeiraPlaca){
		abaixarPlacas.setPrimeiraPlaca(primeiraPlaca);
	}
	
	public void setValorDado1(int valorDado1){
		jogaDado.setValorDado1(valorDado1);
	}
	
	public void setValorDado2(int valorDado2){
		jogaDado.setValorDado2(valorDado2);
	}
	
	public int getValorDado1(){
		return jogaDado.getValorDado1();
	}
	
	public int getValorDado2(){
		return jogaDado.getValorDado2();
	}
	
	public int resultadoDaSoma(){
		return jogaDado.resultadoDaSoma();
	}
	
	public void sorteioDado1(){
		jogaDado.sorteioDado1();
	}
	
	public void sorteioDado2(){
		jogaDado.sorteioDado2();
	}
	
	public void setGirarDado1(boolean girar){
		jogaDado.setGirarDado1(girar);
	}
	
	public void setGirarDado2(boolean girar){
		jogaDado.setGirarDado2(girar);
	}
	
	public boolean getGirarDado1(){
		return jogaDado.getGirarDado1();
	}
	
	public boolean getGirarDado2(){
		return jogaDado.getGirarDado2();
	}
	
	public boolean isGirarDados() {
		return abaixarPlacas.isGirarDados();
	}
	
	public void setGirarDados(boolean girarDados){
		abaixarPlacas.setGirarDados(girarDados);
	}
	
	public void acaoDado(View view){
		jogaDado.acaoDado(view);
	}
	
	public void setDado1Parado(boolean dado1Parado){
		jogaDado.setDado1Parado(dado1Parado);
	}
	
	public void setDado2Parado(boolean dado2Parado){
		jogaDado.setDado2Parado(dado2Parado);
	}
	
	public boolean getDado1Parado(){
		return jogaDado.getDado1Parado();
	}
	
	public boolean getDado2Parado(){
		return jogaDado.getDado2Parado();
	}
	
	public void setEhUmDado(boolean ehUmDado){
		jogaDado.setEhUmDado(ehUmDado);
	}
	
	public boolean getEhUmDado(){
		return jogaDado.getEhUmDado();
	}
	
	public void calculaJogada(int aSerSubtraido, boolean ehUmaPlaca){
		pontos.calculaJogada(aSerSubtraido, ehUmaPlaca);
	}
	
	public int getPontosRestantes(){
		return pontos.getPontosRestantes();
	}
	
	public int getPontosRanking(){
		return pontos.getPontosRanking();
	}
	
	public void setPontosRanking(int ranking){
		pontos.setPontosRanking(ranking);
	}
	
	public void setQuantidadeJodador(int qtdeJogadores){
		abaixarPlacas.setQuantidadeJodador(qtdeJogadores);
	}
	
	public int getQuantidadejogador(){
		return abaixarPlacas.getQuantidadejogador();
	}
	
	public void setListaDeJogadores(ArrayList<String> listaJogadores){
		abaixarPlacas.setListaDeJogadores(listaJogadores);
	}
	
	public ArrayList<String> getListaDeJogadores(){
		return abaixarPlacas.getListaDeJogadores();
	}
	
	public ArrayList<Integer> getListaPontuacao() {
		return pontos.getListaPontuacao();
	}

	public void setListaPontuacao(ArrayList<Integer> listaPontuacao) {
		pontos.setListaPontuacao(listaPontuacao);
	}
	//---------------------------------------------------------------------------------------------------------------------
	//metodos SCORE
	/**
	 * Chamado nos casos em que e necessario passar um Context como parametro, 
	 * como no caso do banco de dados SQLite.
	 * @param context a Activity que chama o metodo.
	 * @throws Exception Possiveis erros relacionados ao banco de dados
	 */
	public Controle(Context context)throws Exception{
		this.context = context;
		score = new Score(this.context);
		opcoes = new Opcoes(context);
	}
	
	/**
	 * Chama o metodo da classe model.Score que insere o registro no banco de dados.
	 */
	public void insereNoBanco(String nome, int pontos) throws Exception{
		Jogador jogador = new Jogador(nome, pontos);
		score.insere(jogador);
	}
	
	/**
	 * Chama o metodo da classe model.Score que apaga um registro no banco de dados.
	 * @param chave um numero inteiro, a primary key do registro que se quer apagar.
	 * @throws Exception possiveis erros ao tentar apagar.
	 */
	public void apagarScore(int chave) throws Exception{
		score.apagarJogador(chave);
	}
	
	/**
	 * Chama o metodo da classe model.Score que mantem o banco de dados com no maximo 10 registros.
	 * @throws Exception possiveis erros ao tentar apagar.
	 */
	public void apagarMaisQueDez() throws Exception{
		score.apagarMaisQueDez();
	}
	
	/**
	 * Chama o metodo da classe model.Score que pesquisa os registros do banco de dados e retorna uma lista.
	 * @return um ArrayList, a lista de Jogadores armazenados no banco, em ordem decrescente.
	 * @throws Exception possiveis erros ao buscar os dados.
	 */
	public ArrayList<Jogador> obterLista() throws Exception{
		return score.obterList();
	}
	
	/**
	 * Chama o metodo da classe model.Score que busca a menor pontuacao armazenada no banco.
	 * @return um numero inteiro, a menor pontuacao armazenada no banco.
	 * @throws Exception possiveis erros ao buscar dados no banco.
	 */
	public int menorPontuacaoGravada() throws Exception{
		return score.menorRegistro();
	}
	
	/**
	 * Chama o metodo da classe model.Score que busca a quantidade de registros armazenados no banco.
	 * @return um num inteiro, a quantidade de registros armazenados no banco.
	 * @throws Exception possiveis erros ao buscar dados no banco.
	 */
	public int numRegistrosGravados() throws Exception{
		return score.numRegistros();
	}
	//-----------------------------------------------------------------------------------------------------------
	//metodos OPCOES
	
	/**
	 * Chama o metodo da classe model.Opcoes que faz a alteracao da opcoes do jogo.
	 * @param musica a flag que sera alterada no banco de dados no campo "SOUND"; 1 = true, 0 = false.
	 * @param efeito a flag que sera alterada no banco de dados no campo "FX"; 1 = true, 0 = false.
	 */
	public void alterarOpcoes(boolean musica, boolean efeito){
		opcoes = new Opcoes(context);
		opcoes.alterar(musica, efeito);
	}
	
	/**
	 * Faz a chamada do metodo da classe model.Opcoes que busca a flag no banco de dados.
	 * @return true caso a flag armazenada for 1 ou false caso seja 0.
	 */
	public boolean getMusica(){
		opcoes = new Opcoes(context);
		return opcoes.getMusica();
	}
	
	/**
	 * Faz a chamada do metodo da classe model.Opcoes que busca a flag no banco de dados.
	 * @return true caso a flag armazenada for 1 ou false caso seja 0.
	 */
	public boolean getEfeitos(){
		opcoes = new Opcoes(context);
		return opcoes.getEfeitos();
	}
	
}
