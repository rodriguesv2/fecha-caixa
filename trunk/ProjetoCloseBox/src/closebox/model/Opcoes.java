package closebox.model;

import closebox.databaseAcess.OptionsDao;
import android.content.Context;

/**
 * Classe da camada model, responsavel por chamar os metodos de acesso ao banco de dados
 * Manipula a tabela "OPCOES" do banco de dados "closebox".
 * @author EndGamers
 *
 */
public class Opcoes {
	private Context context;
	private OptionsDao dao;
	private boolean musica;	
	private boolean efeitos;
	/**
	 * Construtor da classe com o contexto da classe que o chamou como parametro.
	 * @param context O context que chamou esse metodo, como uma Activity por exemplo.
	 */
	public Opcoes(Context context){
		this.context = context;
		dao = new OptionsDao(context);
		musica = getMusica();
		efeitos = getEfeitos();
	}
	
	/**
	 * Faz a chamada do metodo da classe dataBaseAcess.OpcoesDao que altera as flags no banco de dados.
	 * @param musica a flag que sera alterada no campo "SOUND" no banco de dados; 1 = true, 0 = false;
	 * @param efeito a flag que sera alterada no campo "FX" no banco de dados; 1 = true, 0 = false;
	 */
	public void alterar(boolean music, boolean fx){
		dao.alterarOpcoes(music, fx);
	}
	
	/**
	 * Faz a chamada do metodo da classe dataBaseAcess.OpcoesDao que busca a flag no banco de dados.
	 * @return true caso a flag armazenada for 1 ou false caso seja 0.
	 */
	public boolean getMusica(){
		return dao.getMusica();
	}
	
	/**
	 * Faz a chamada do metodo da classe dataBaseAcess.OpcoesDao que busca a flag no banco de dados.
	 * @return true caso a flag armazenada for 1 ou false caso seja 0.
	 */
	public boolean getEfeitos(){
		return dao.getEfeitos();
	}
}
