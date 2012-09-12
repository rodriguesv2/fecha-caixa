package closebox.databaseAcess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Classe responsavel por consultar e alterar as flags de opcoes de som do jogo
 * @author EndGamers
 *
 */
public class OptionsDao extends SQLiteOpenHelper{

	private static final String NOME_BANCO = "closebox"; //String com o nome do database a ser utilizado
	private static final String TABELA = "opcoes"; //String com o nome da tabela
	private static final String ID_TABELA = "_id"; //String com o nome do campo _id da tabela
	//o campo SOUND armazenará um flag no bd, 1 = true e 0 = false;
	private static final String CAMPO_SOUND = "musica"; //String com o nome do campo musica
	// o campo FX armazenará um flag no bd, 1 = true e 0 = false;
	private static final String CAMPO_FX = "efeitos"; //String com o nome do campo efeitos
	private SQLiteDatabase bancoDados = null; // instancia do banco de dados
	private Cursor cursor;// cursor usado para manipular os dados provenientes do banco de dados
	
	/**
	 * Construtor usado quando necessario utilizar
	 * @param context O contexto que chama o metodo, uma Activity por exemplo.
	 */
	public OptionsDao(Context context){
		super(context, NOME_BANCO, null, 1);
		onCreate(this.getReadableDatabase());
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE IF NOT EXISTS "+TABELA+
		"("+ ID_TABELA +" INTEGER PRIMARY KEY AUTOINCREMENT, "+ CAMPO_SOUND +
		" INTEGER, "+ CAMPO_FX +" INTEGER);";
		db.execSQL(sql);
		//verifica se há uma linha inserida e se nao houver insere.
		// os campos serao inseridos com valor 1, isto é "true";
		if(!getCount()){
			criarLinhaOpcoes();
		}
	}
	
	/**
	 * Faz uma query na tabela para verificar se ha registros
	 * @return true se houver uma linha.
	 */
	public boolean getCount(){
		boolean existe = false;
		bancoDados = getReadableDatabase();
		cursor = bancoDados.rawQuery("select * from "+ TABELA +";", null); //select * from opcoes;
		if(cursor.getCount()==1){ // obtem a contagem
			existe = true;
		}
		cursor.close();
		bancoDados.close();
		return existe;
	}
	
	/**
	 * Faz a contagem do total de registros no banco de dados.
	 * @return o total de linhas
	 */
	public int contaRegistros(){
		bancoDados = getReadableDatabase();
		cursor = bancoDados.rawQuery("select * from "+ TABELA +";", null); //select * from opcoes;
		int total = cursor.getCount();
		cursor.close();
		bancoDados.close();
		return total;
	}
	
	/**
	 * Insere uma linha na tabela OPCOES - 
	 * POR PADRAO COMECA COM 1 no campo SOUND e 1 no campo FX
	 * o que significa as duas opcoes habilitadas (true).
	 */
	public void criarLinhaOpcoes(){
		bancoDados = getReadableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(CAMPO_SOUND, 1);
		cv.put(CAMPO_FX, 1);
		bancoDados.insert(TABELA, null, cv);
		bancoDados.close();
	}
	
	/**
	 * Sobreescrita para atender os requisitos de variaveis do tipo boolean.
	 * @param musica a musica do jogo.
	 * @param efeitos o efeito do jogo.
	 */
	public void alterarOpcoes(boolean musica, boolean efeitos){
		int sound = 0, fx = 0;
		if(musica) sound = 1;
		if(efeitos) fx = 1;
		alterarOpcoes(sound, fx);
	}
	
	/**
	 * Altera as colunas na unica linha armazenada.
	 * @param musica campo SOUND; 1 = true, 0 = false.
	 * @param efeito campo FX;  1 = true, 0 = false.
	 */
	public void alterarOpcoes(int musica, int efeito){
		bancoDados = getReadableDatabase();
		
		ContentValues cv = new ContentValues();
		cv.put(CAMPO_SOUND, musica);
		cv.put(CAMPO_FX, efeito);
		bancoDados.update(TABELA, cv, ID_TABELA, null);
		gerenciaTabela();
		bancoDados.close();
	}
	
	/**
	 * Mantem apenas uma linha na tabela.
	 */
	public void gerenciaTabela(){
		if(contaRegistros()>1){
			apagarTabela();
		}
	}
	
	/**
	 * Remove a tabela do banco de dados.
	 */
	public void apagarTabela(){
		bancoDados = getReadableDatabase();
		bancoDados.execSQL("DROP TABLE IF EXISTS " + TABELA + ";");
		bancoDados.close();
	}
	
	/**
	 * Executa uma query na tabela, buscando o campo "SOUND".
	 * @return true se a query retornar 1 e false caso retorne 0;
	 */
	public boolean getMusica(){
		bancoDados = getReadableDatabase();
		cursor = bancoDados.rawQuery("select "+ CAMPO_SOUND +" from "+ TABELA, null); //select * from opcoes;
		cursor.moveToFirst();
		int resultado = cursor.getInt(cursor.getColumnIndex(CAMPO_SOUND));
		if(resultado == 1) return true;
		else return false;
	}
	
	/**
	 *  Executa uma query na tabela, buscando o campo "FX".
	 * @return true se a query retornar 1 e false caso retorne 0;
	 */
	public boolean getEfeitos(){
		bancoDados = getReadableDatabase();
		cursor = bancoDados.rawQuery("select "+ CAMPO_FX +" from "+ TABELA, null); //select * from opcoes;
		cursor.moveToFirst();
		int resultado = cursor.getInt(cursor.getColumnIndex(CAMPO_FX));
		if(resultado == 1) return true;
		else return false;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
