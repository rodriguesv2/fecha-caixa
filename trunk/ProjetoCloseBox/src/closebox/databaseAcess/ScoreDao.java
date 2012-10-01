package closebox.databaseAcess;

import java.util.ArrayList;
import closebox.model.Jogador;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Classe responsavel pela persistencia dos dados do jogo, especificamente o Score, isto �, o Ranking com os jogadores
 * com as melhores pontua��es.
 * @author The EndGamers
 */
public class ScoreDao extends SQLiteOpenHelper{

	private static final String NOME_BANCO = "closebox"; //String com o nome do database a ser utilizado
	private static final String TABELA = "jogador"; //String com o nome da tabela
	private static final String ID_TABELA = "_id"; //String com o nome do campo _id da tabela
	private static final String CAMPO_NOME = "nome"; //String com o nome do campo nome da tabela
	private static final String CAMPO_RODADAS = "rodadas"; //String com o nome do campo rodadas
	private SQLiteDatabase bancoDados = null; // instancia do banco de dados
	private Cursor cursor;// cursor usado para manipular os dados provenientes do banco de dados
	
	/**
	 * Construtor usado quando necessario utilizar
	 * @param context
	 */
	public ScoreDao(Context context){
		super(context, NOME_BANCO, null, 1);
		onCreate(this.getWritableDatabase());
	}

	/**
	 * Metodo responsavel por preencher a ListView que apresenta a lista dos jogadores gravados no Banco de Dados
	 */
	public ArrayList<Jogador> obterLista() throws Exception{
		bancoDados = getWritableDatabase();
		ArrayList<Jogador> listaJogadores = new ArrayList<Jogador>();; //lista para receber os dados, se existirem no Banco de Dados
		Jogador jogador;
		try {
			cursor = bancoDados.query(TABELA, new String[] {"nome", "rodadas"}, 
					null,// selection,
					null,// selectionArgs,
					null,// groupBy,
					null,// having,
					"rodadas desc",// "order by (nome coluna modo)
					"10"); // Limite de registros retornados(String)
			if (cursor.getCount() > 0){ //faz a contagem dos registros
				 while(cursor.moveToNext()){//enquanto apontar para a proxima (ou primeira) linha do resultado da pesquisa
					   jogador = new Jogador();
					   jogador.setNome(cursor.getString(cursor.getColumnIndex("nome")));//grava o nome na instancia do joogador
					   jogador.setPontosDeVida(Integer.parseInt(cursor.getString(cursor.getColumnIndex("rodadas"))));//grava a pontuacao na instancia do joogador
					   listaJogadores.add(jogador);//adiciona a instancia de Jogador na lista
				   }
			} else {
			}
		} catch (Exception erro) {
			throw erro;
		}
		cursor.close();
		bancoDados.close();
		return listaJogadores;
	}

	/**
	 * Metodo que busca no Banco de Dados a quantidade de registros
	 * @return a quantidade de registros no BD, caso ocorra erros retorna 10.(para que seja utilizado o crit�rio de maior pontua��o)
	 */
	public int numRegistros() throws Exception{
		bancoDados = getWritableDatabase();
		int registros = 0;
		try {
			Cursor c = bancoDados.rawQuery("select * from "+ TABELA +";", null); //select * from jogador;
			registros = c.getCount(); // obtem a contagem
			c.close();
		} catch (Exception erro) {
			throw erro;
		}
		bancoDados.close();
		return registros;
	}
	
	/**
	 * Metodo que busca a menor pontua��o gravada no BD
	 * @return a menor pontua��o
	 */
	public int getMenorPonto()throws Exception{
		bancoDados = getWritableDatabase();
		int menor = 0;
		try { 
			cursor = bancoDados.rawQuery("select min("+ CAMPO_RODADAS +") from jogador;", null); //select min(rodadas) from jogador
			cursor.moveToFirst();
			menor = cursor.getInt(cursor.getColumnIndex("min(" + CAMPO_RODADAS + ")" ));
		} catch (Exception erro) {
			throw erro;
			
		}
		cursor.close();
		bancoDados.close();
		return menor;
	}
	
	/**
	 * Metodo que busca a maior pontua��o gravada no BD
	 * @return a maior pontua��o
	 * @throws Exception
	 */
	public boolean getMaiorPonto(int pontos)throws Exception{
		bancoDados = getWritableDatabase();
		boolean ehMaior = false;
		int maior = 0;
		try { 
			cursor = bancoDados.rawQuery("select max("+ CAMPO_RODADAS +") from jogador;", null); //select min(rodadas) from jogador
			cursor.moveToFirst();
			maior = cursor.getInt(cursor.getColumnIndex("max(" + CAMPO_RODADAS + ")" ));
		} catch (Exception erro) {
			throw erro;
		}
		if(pontos>maior)ehMaior = true;
		cursor.close();
		bancoDados.close();
		return ehMaior;
	}
	
	/**
	 * Metodo utilizado para guardar os valores passados por parametro no banco de dados
	 * @param valorNome o nome do Jogador
	 * @param valorPontos os pontos do Jogador
	 */
	public void gravarJogador(String nome, int pontuacao)throws Exception{
		bancoDados = getWritableDatabase();
		
		ContentValues cv = new ContentValues();
		cv.put(CAMPO_NOME, nome);
		cv.put(CAMPO_RODADAS, pontuacao);
		bancoDados.insert(TABELA, null, cv);
		bancoDados.close();
	}
	
	/**
	 * Apaga o registro da tabela com a chave promaria passada por parametro
	 * @param a chave primario da linha que se quer apagar
	 * @throws Exception
	 */
	public void apagarRegistro(int chave)throws Exception{
		bancoDados = getWritableDatabase();
		try{
			bancoDados.execSQL("delete from "+TABELA+" where "+ID_TABELA+" = "+chave+";");
		}catch (Exception erro) {
			throw erro;
		}
		bancoDados.close();
	}
	
	/**
	 * Metodo que busca o valor mais baixo do campo rodadas(integer) e o apaga caso haja mais de dez registros no banco de dados. 
	 */
	public void apagarMaisQueDez() throws Exception{
		bancoDados = getWritableDatabase();
		while(numRegistros()>10){//atende a condi��o de s� apagar caso haja mais de dez registros no banco de dados.
			try {
				int menorValor; //variavel que ser� usada para armazenar o retorno da busca
				/**
				 * Essa query busca o id da linha que tem o menor valor no campo rodada,
				 *  que foi adicionado mais recente, ou seja se dois jogadores com a mesma pontuacao forem encontrados
				 *   a busca retornara o que foi armazenado por ultimo. (MAIOR ID)
				 */
				String sql = ID_TABELA+" = (select max("+ID_TABELA+") from "+TABELA+
				"where "+CAMPO_RODADAS+" = (select min("+CAMPO_RODADAS+") from "+TABELA+"));";
				
				cursor = bancoDados.query(TABELA, new String[] { ID_TABELA, CAMPO_NOME }, 
						sql, null, null, null, null);
				cursor.moveToFirst();
				menorValor = cursor.getInt(cursor.getColumnIndex(ID_TABELA));
				bancoDados.execSQL("delete from "+TABELA+" where "+ID_TABELA+" = "+menorValor+";");
			} catch (Exception erro) {
				throw erro;
			}
		}
		cursor.close();
		bancoDados.close();
	}
	
	/**
	 * Chamado quando necessario pesquisar a menor pontuacao armazenada no banco de dados.
	 * @return o indice da linha que tem o menor valor na coluna "rodadas"
	 * @throws Exception Possiveis erros ao acessar o banco de dados
	 */
	public int obtemChaveMenorPontuacao()throws Exception{
		bancoDados = getWritableDatabase();
		int indiceMenorPontuacao = 0;
		try {
			/**
			 * Essa query busca o id da linha que tem o menor valor no campo rodada,
			 *  que foi adicionado mais recente, ou seja se dois jogadores com a mesma pontuacao forem encontrados
			 *   a busca retornara o que foi armazenado por ultimo. (MAIOR ID)
			 */
			String sql = ID_TABELA+" = (select max("+ID_TABELA+") from "+TABELA+
			"where "+CAMPO_RODADAS+" = (select min("+CAMPO_RODADAS+") from "+TABELA+"));";
			
			cursor = bancoDados.query(TABELA, new String[] { ID_TABELA, CAMPO_NOME }, 
					sql, null, null, null, null);
			cursor.moveToFirst();
			indiceMenorPontuacao = cursor.getInt(cursor.getColumnIndex(ID_TABELA));
		} catch (Exception e) {
			throw e;
		}
		cursor.close();
		bancoDados.close();
		return indiceMenorPontuacao;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//create table if not exists jogador(_id integer primary key autoincrement, nome text, rodadas integer)
		String sql = "CREATE TABLE IF NOT EXISTS "+TABELA+
			"("+ ID_TABELA +" INTEGER PRIMARY KEY AUTOINCREMENT, "+ CAMPO_NOME +
			" TEXT, "+ CAMPO_RODADAS +" INTEGER);";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
}



