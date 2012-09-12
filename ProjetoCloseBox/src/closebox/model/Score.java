package closebox.model;

import java.util.ArrayList;
import android.content.Context;
import closebox.databaseAcess.ScoreDao;

/**
 * Classe da camada modelo que contem os metodos necessarios para a persistencia no banco de dados.
 * @author EndGamers
 */
public class Score {
	private ScoreDao dao;
	private Context context;
	
	/**
	 * Chamado nos casos em que e necessario passar um Context como parametro, 
	 * como no caso do banco de dados SQLite.
	 * @param context a Activity que chama o metodo.
	 * @throws Exception possiveis erros de conexao.
	 */
	public Score(Context context) throws Exception{
		this.context = context;
		dao = new ScoreDao(this.context);
	}
	
	/**
	 * Chama o metodo da classe databaseAcess.Score que insere um registro no banco.
	 * @param jogador um objeto da classe model.Jogador.
	 * @throws Exception possiveis erros de conexao com o banco de dados.
	 */
	public void insere(Jogador jogador) throws Exception{
		dao.gravarJogador(jogador.getNome(), jogador.getPontosDeVida());
	}
	
	/**
	 * Chama o metodo da classe databaseAcess.Score que apaga um registro do banco de dados.
	 * @param chave um numero inteiro, a primary key do registro que se quer apagar.
	 * @throws Exception possiveis erros de conexao com o banco de dados.
	 */
	public void apagarJogador(int chave) throws Exception{
		dao.apagarRegistro(chave);
	}
	
	/**
	 * Chama o metodo da classe databaseAcess.Score que retorna uma lista dos registros armazenados no banco de dados.
	 * @return um ArrayList, uma lista dos Jogadores armazenados no banco de dados.
	 * @throws Exception possiveis erros de conexao com o banco de dados.
	 */
	public ArrayList<Jogador> obterList() throws Exception{
		return dao.obterLista();
	}
	
	/**
	 * Chama o metodo da classe databaseAcess.Score que retorna a menor pontuacao armazenada no banco de dados.
	 * @return um numero inteiro, a menor pontuacao armazenada no banco de dados.
	 * @throws Exception possiveis erros de conexao com o banco de dados.
	 */
	public int menorRegistro() throws Exception{
		return dao.getMenorPonto();
	}
	
	/**
	 * Chama o metodo da classe databaseAcess.Score que retorna o total de registros armazenados no banco de dados.
	 * @return um numero inteiro, o total de registros armazenados no banco de dados.
	 * @throws Exception possiveis erros de conexao com o banco de dados.
	 */
	public int numRegistros() throws Exception{
		return dao.numRegistros();
	}
	
	/**
	 * Chama o metodo da classe databaseAcess.Score que mantem o banco de dados com 
	 * no maximo 10 registros.
	 * @throws Exception possiveis erros de conexao com o banco de dados.
	 */
	public void apagarMaisQueDez() throws Exception{
		dao.apagarMaisQueDez();
	}
}
