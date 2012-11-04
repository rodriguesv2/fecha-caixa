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
	
	//Instanciar o objeto abaixarPlacas.
	private void incluirJogaDadoEPontosAoAbaixarPlacas(){
		abaixarPlacas.setJogaDado(jogaDado);
		abaixarPlacas.setPontos(pontos);
	}
	
	/**
	 * Caso seja a ultima placa e a jogada for errada, informa a tela se deve levantar
	 * a placa.
	 * @return boolean - Flag ultima placa dever ser levantada.
	 */
	public boolean isFlagLevantarPlacaSeUltima(){
		return abaixarPlacas.isFlagLevantarPlacaSeUltima();
	}
	
	/**
	 * Apenas identifica qual numero a ImageView de placa está mostrando
	 * @param valor
	 * @return Inteiro referente a placa.
	 */
	public int qualEhAPosicaoDaPlaca(int valor){
		return abaixarPlacas.qualEhAPosicaoDaPlaca(valor);
	}
	
	/**
	 * Flag que assinala se pode o dialogo sobre jogar com um dado aparecer.
	 * @return Se deve aparecer.
	 */
	public boolean isPerguntarSobreDado() {
		return abaixarPlacas.isPerguntarSobreDado();
	}
	
	/**
	 * Informa se a placa atual é a última em pé.
	 * @return boolean Ultima Placa.
	 */
	public boolean isUltimaPlaca() {
		return abaixarPlacas.isUltimaPlaca();
	}
	
	/**
	 * Assinala a flag para liberar a pergunta sobre jogar com um dado.
	 * @param perguntarSobreDado Flag a ser assinalada.
	 */
	public void setPerguntarSobreDado(boolean perguntarSobreDado) {
		abaixarPlacas.setPerguntarSobreDado(perguntarSobreDado);
	}
	
	/**
	 * Flag que libera os pontos de ranking serem mostrados na tela.
	 * @return Flag.
	 */
	public boolean isMostraRanking() {
		return abaixarPlacas.isMostraRanking();
	}
	
	/**
	 * Assinalar se deve ou não mostrar ranking.
	 * @param mostraRanking Flag a ser assinalada
	 */
	public void setMostraRanking(boolean mostraRanking) {
		abaixarPlacas.setMostraRanking(mostraRanking);
	}
	
	/**
	 * Acesso ao valor da primeira placa jogada.
	 * @return Primeira placa jogada.
	 */
	public int getPlacaAnterior() {
		return abaixarPlacas.getPlacaAnterior();
	}
	
	/**
	 * Marca qual foi a primeira placa abaixada quando ha à necessidade de 2 placas.
	 * @param int placa
	 */
	public void setPlacaAnterior(int placa){
		abaixarPlacas.setPlacaAnterior(placa);
	}
	
	/**
	 * Informa se as placas devem levantar mediante a jogada errada.
	 * @return Flag
	 */
	public boolean isLevantarPlacas() {
		return abaixarPlacas.isLevantarPlacas();
	}

	/**
	 * Faz a troca de ImageViews referentes as placas jogadas.
	 * @param levantarPlacas - id da ImageView referente a placa.
	 */
	public void setLevantarPlacas(boolean levantarPlacas) {
		abaixarPlacas.setLevantarPlacas(levantarPlacas);
	}
	
	/**
	 * Assinala se deve ou não calcular os pontos restantes
	 * @return boolean flag calcular pontos
	 */
	public boolean isCalcularPontosRestantes() {
		return abaixarPlacas.isCalcularPontosRestantes();
	}
	
	/**
	 * Marcar flag de calcular pontos restantes.
	 * @param boolean calcularPontosRestantes
	 */
	public void setCalcularPontosRestantes(boolean calcularPontosRestantes){
		abaixarPlacas.setCalcularPontosRestantes(calcularPontosRestantes);
	}
	
	/**
	 * Faz toda a gerência da jogada atual.
	 * @param int placa.
	 */
	public void gerenciaJogada(int placa){
		abaixarPlacas.gerenciaJogada(placa);
	}
	
	/**
	 * Identificar as ImageView de placaDown pelas id de ImageView de placa 
	 * @param view para a xml de tela
	 * @return o id da ImageView placaDown
	 */
	public int identificarPlacaDown(View view){
		return abaixarPlacas.identificarPlacaDown(view);
	}
	
	/**
	 * Embaralha as imagens e ordena para colocar nas ImageViews de placas.
	 * @return Array de inteiros referentes as IDs de imagens na R.drawable.
	 */
	public int[] embaralharPlacas(){
		return abaixarPlacas.embaralharPlacas();
	}
	
	/**
	 * Devolve a ordem que as placas se encontram. Só funciona se o metodo 
	 * embaralharPlacas() for usado primeiro.
	 * @return	Array de int referentes aos IDs de imagens.
	 */
	public int[] getOrdemDasPlacas(){
		return abaixarPlacas.getOrdemDasPlacas();
	}
	
	/**
	 * Identifica aonde está a ImageView na telaJogo.xml
	 * @param View view.
	 * @return Inteiro referente a imageViewPlaca.
	 */
	public int getPosicaoDaPlaca(View view){
		return abaixarPlacas.getPosicaoDaPlaca(view);
	}
	
	/**
	 * Seta a flag para informar se as 3 placas altas estão abaixadas.
	 * @return Flag para liberar mensagem.
	 */
	public boolean placasAltasAbaixadas(){
		return abaixarPlacas.placasAltasAbaixadas();
	}
	
	/**
	 * Marca a flag para informar se a placa atual é a primeira.
	 * @param boolean primeiraPlaca
	 */
	public void setPrimeiraPlaca(boolean primeiraPlaca){
		abaixarPlacas.setPrimeiraPlaca(primeiraPlaca);
	}
	
	/**
	 * Mudar valor do dado 1.
	 * @param int valorDado1
	 */
	public void setValorDado1(int valorDado1){
		jogaDado.setValorDado1(valorDado1);
	}
	
	/**
	 * Mudar valor do dado 2.
	 * @param valorDado2
	 */
	public void setValorDado2(int valorDado2){
		jogaDado.setValorDado2(valorDado2);
	}
	
	/**
	 * Devolve o valor gerado por sorteioDado1().
	 * @return int valor do dado 1.
	 */
	public int getValorDado1(){
		return jogaDado.getValorDado1();
	}
	
	/**
	 * Devolve o valor gerado por sorteioDado2().
	 * @return int valor do dado 2.
	 */
	public int getValorDado2(){
		return jogaDado.getValorDado2();
	}
	
	/**
	 * Soma dos 2 dados.
	 * @return int Soma.
	 */
	public int resultadoDaSoma(){
		return jogaDado.resultadoDaSoma();
	}
	
	/**
	 * Gera um numero aleatorio para trabalhar com o dado numero 1.
	 */
	public void sorteioDado1(){
		jogaDado.sorteioDado1();
	}
	
	/**
	 * Gera um numero aleatorio para trabalhar com o dado numero 2.
	 */
	public void sorteioDado2(){
		jogaDado.sorteioDado2();
	}
	
	/**
	 * Marcar uma flag para girar ou não o dado 1.
	 * @param boolean girar
	 */
	public void setGirarDado1(boolean girar){
		jogaDado.setGirarDado1(girar);
	}
	
	/**
	 * Marcar uma flag para girar ou não o dado 2.
	 * @param boolean girar
	 */
	public void setGirarDado2(boolean girar){
		jogaDado.setGirarDado2(girar);
	}
	
	/**
	 * Flag para a interface grafica girar ou não o dado 1.
	 * @return boolean boolean girar dado 1.
	 */
	public boolean getGirarDado1(){
		return jogaDado.getGirarDado1();
	}
	
	/**
	 * Flag para a interface grafica girar ou não o dado 2.
	 * @return boolean boolean girar dado 2.
	 */
	public boolean getGirarDado2(){
		return jogaDado.getGirarDado2();
	}
	
	/**
	 * Diz se os dado devem girar;
	 * @return Flag
	 */
	public boolean isGirarDados() {
		return abaixarPlacas.isGirarDados();
	}
	
	/**
	 * Assinalar se os dado devem girar;
	 * @param girarDados Flag
	 */
	public void setGirarDados(boolean girarDados){
		abaixarPlacas.setGirarDados(girarDados);
	}
	
	/**
	 * Muda flags gerenciando as jogadas de dados.
	 * @param View view
	 */
	public void acaoDado(View view){
		jogaDado.acaoDado(view);
	}
	
	/**
	 * Muda a flag de doa 1 parado
	 * @param boolean dado1Parado
	 */
	public void setDado1Parado(boolean dado1Parado){
		jogaDado.setDado1Parado(dado1Parado);
	}
	
	/**
	 * Muda a flag de doa 2 parado
	 * @param boolean dado2Parado
	 */
	public void setDado2Parado(boolean dado2Parado){
		jogaDado.setDado2Parado(dado2Parado);
	}
	
	/**
	 * Flag que informa se o dado 1 deve girar.
	 * @return boolean dado 1 parado.
	 */
	public boolean getDado1Parado(){
		return jogaDado.getDado1Parado();
	}
	
	/**
	 * Flag que informa se o dado 2 deve girar.
	 * @return boolean dado 2 parado.
	 */
	public boolean getDado2Parado(){
		return jogaDado.getDado2Parado();
	}
	
	/**
	 * Muda flag para informar se é dado.
	 * @param boolean ehUmDado. 
	 */
	public void setEhUmDado(boolean ehUmDado){
		jogaDado.setEhUmDado(ehUmDado);
	}
	
	/**
	 * informa se é um dado.
	 * @return boolean é um dado.
	 */
	public boolean getEhUmDado(){
		return jogaDado.getEhUmDado();
	}
	
	/**
	 * Soma os pontos correspondentes a jogada.
	 * @param int aSerSubtraido
	 * @param boolean ehUmaPlaca
	 */
	public void calculaJogada(int aSerSubtraido, boolean ehUmaPlaca){
		pontos.calculaJogada(aSerSubtraido, ehUmaPlaca);
	}
	
	/**
	 * Retorna os pontos que restaram.
	 * @return int pontos restantes.
	 */
	public int getPontosRestantes(){
		return pontos.getPontosRestantes();
	}
	
	/**
	 * Devolve os pontos de ranking
	 * @return int pontos de ranking
	 */
	public int getPontosRanking(){
		return pontos.getPontosRanking();
	}
	
	/**
	 * Modificar completamente os pontos de ranking
	 * @param int ranking
	 */
	public void setPontosRanking(int ranking){
		pontos.setPontosRanking(ranking);
	}
	
	/**
	 * Indica a quantidade de jogadores para o fluxo de jogadas.
	 * @param int qtdeJogadores
	 */
	public void setQuantidadeJodador(int qtdeJogadores){
		abaixarPlacas.setQuantidadeJodador(qtdeJogadores);
	}
	
	/**
	 * Informa o numero de players atualmente.
	 * @return Numero de jogadores atualmente.
	 */
	public int getQuantidadejogador(){
		return abaixarPlacas.getQuantidadejogador();
	}
	
	/**
	 * Insere a lista de jogadores para gerenciar o fluxo de jogadas
	 * @param ArrayList<String> listaJogadores
	 */
	public void setListaDeJogadores(ArrayList<String> listaJogadores){
		abaixarPlacas.setListaDeJogadores(listaJogadores);
	}
	
	/**
	 * Retorna a lista de jogadores
	 * @return ArrayList<String> de jogadores
	 */
	public ArrayList<String> getListaDeJogadores(){
		return abaixarPlacas.getListaDeJogadores();
	}
	
	/**
	 * Lista de pontuação referente a jogador.
	 * @return ArrayList<Integer> lista de pontuação.
	 */
	public ArrayList<Integer> getListaPontuacao() {
		return pontos.getListaPontuacao();
	}

	/**
	 * Inserir lista de pontuação
	 * @param ArrayList<Integer> listaPontuacao
	 */
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
		score = new Score(context);
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
	 * Chama o metodo da classe model.Score que busca a menor pontuacao armazenada no banco.
	 * @return um numero inteiro, a menor pontuacao armazenada no banco.
	 * @throws Exception possiveis erros ao buscar dados no banco.
	 */
	public boolean maiorPontuacaoGravada(int pontos) throws Exception{
		return score.maiorRegistro(pontos);
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
		opcoes.alterar(musica, efeito);
	}
	
	/**
	 * Faz a chamada do metodo da classe model.Opcoes que busca a flag no banco de dados.
	 * @return true caso a flag armazenada for 1 ou false caso seja 0.
	 */
	public boolean getMusica(){
		return opcoes.getMusica();
	}
	
	/**
	 * Faz a chamada do metodo da classe model.Opcoes que busca a flag no banco de dados.
	 * @return true caso a flag armazenada for 1 ou false caso seja 0.
	 */
	public boolean getEfeitos(){
		return opcoes.getEfeitos();
	}
	
}
