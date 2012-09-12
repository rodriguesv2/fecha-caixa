package closebox.activity;

import java.util.ArrayList;
import java.util.List;
import closebox.activity.ControllerActivity;
import closebox.activity.R;
import closebox.controle.Controle;
import closebox.model.Jogador;
import closebox.service.MusicaPrincipalService;
import closebox.service.MusicaPrincipalService.LocalBinder;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



/**
 * Classe responsavel pela persistencia dos dados do jogo, especificamente o Score, isto é, o Ranking com os jogadores
 * com as melhores pontuações.
 * @author The EndGamers
 *
 */
public class ScoreActivity extends Activity {

	private Intent intentIn; //intent responsável por receber as informaçoes necessárias 
	private Intent intentOut; //intent responsável por enviar as informaçoes necessárias 
	private String botao = ""; // Srting usada para comparar o botão que chamou essa Activity
	private int numJogadores; // quantidade de jogadores
	private int jogAtual, pontoJog; // o indice do jogador atual e a sua pontuação
	private String nomeJog = ""; //o nome do jogador
	private Controle controle;
	private boolean mBound = false;
	private MusicaPrincipalService musicaPrincipalService;
	//Atributo sobrescrito para conexão com o serviço de musica.
	private ServiceConnection serviceConnection = new ServiceConnection() {
		@Override
		public void onServiceDisconnected(ComponentName name) {
			mBound = false;
			
		}
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			LocalBinder localBinder = (LocalBinder)service;
			musicaPrincipalService = localBinder.getService();
			musicaPrincipalService.playMusic();
			mBound = true;
		}
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) { // metodo CONSTRUTOR
		super.onCreate(savedInstanceState);
		intentIn = getIntent();
		defineAcaoOrigem();
		
		bindService(new Intent(this, MusicaPrincipalService.class), serviceConnection, Context.BIND_AUTO_CREATE);
	}
	
	@Override
	public void onResume(){
		if(mBound)
			musicaPrincipalService.playMusic();
		super.onResume();
	}
	
	@Override
	public void onPause(){
		if(mBound)
			musicaPrincipalService.pauseMusic();
		super.onPause();
	}
	
	@Override
	public void onStart(){
		if(mBound)
			musicaPrincipalService.playMusic();
		super.onStart();
	}
	
	@Override
	public void onDestroy(){
		if(mBound)
			unbindService(serviceConnection);
		super.onDestroy();
	}
	
	/**
	 * Metodo que define a Activity que chamou essa classe, definindo assim o comportamento esperado. (gravar ou exibir a lista)
	 * De acordo com a origem grava, solicita nova rodada ou mostra a view "GAME OVER"
	 */
	private void defineAcaoOrigem(){
		botao = intentIn.getStringExtra("botao");
		
		if (botao.equals("gravarPontuacao")) {
			numJogadores = intentIn.getIntExtra("numeroDeJogadores", 0);//a quantidade dos jogadores
			jogAtual = intentIn.getIntExtra("jogadorAtual", 0);//indice do jogador atual na lista
			pontoJog = intentIn.getIntegerArrayListExtra("listaRodadas").get(jogAtual); //a pontuacao do jogador atual
			nomeJog = intentIn.getStringArrayListExtra("arrayJogadores").get(jogAtual); // o nome do jogador atual
			gerenciarTabela(nomeJog, pontoJog);//grava os dados se obedecerem os criterios para tal
			voltaParaJogo();//inicia uma nova rodada
		}else if(botao.equals("lista")){
			populaTabela();//mostra a lista Score
		
		}else {
			intentOut = new Intent(this, ControllerActivity.class);
			intentOut.putExtra("botao", "gameOver");
			super.finish();
			startActivity(intentOut);
		}
	}
	
	/**
	 * Metodo responsavel por atualizar os dados e envia-los para o Controller que iniciará uma nova rodada.
	 * Esse metodo é chamado após armazenar a pontuação de um Jogador no Banco de Dados
	 */
	private void voltaParaJogo(){
		numJogadores --; //Atualiza a quantidade de jogadores
		if(numJogadores<1){//Caso não haja mais Jogadores: GAME OVER
	    	intentOut = new Intent(this, ControllerActivity.class); //envia os dados ao controller
	    	intentOut.putExtra("botao", "gameOver");// o nome do botao, na verdade uma referencia a ser tratada no controller
	    	super.finish();
			startActivity(intentOut);
		}else{
			ArrayList<String> jogadores = intentIn.getStringArrayListExtra("arrayJogadores");
			jogadores.remove(jogAtual);//remove o jogador atual da lista de nomes
			ArrayList<Integer> listaPontuacao = intentIn.getIntegerArrayListExtra("pontuacaoJogadores");
			listaPontuacao.remove(jogAtual);//remove a pontuação do jogador atual
			ArrayList<Integer> rodadas = intentIn.getIntegerArrayListExtra("listaRodadas");
			rodadas.remove(jogAtual);//remove as rodadas do jogador atual
			if(jogAtual>=numJogadores){ //evita que o indice fora dos limites da lista
				jogAtual = 0;
			}
			intentOut = new Intent(this, ControllerActivity.class); //envia os dados ao controller
			intentOut.putExtra("botao", "novaRodada"); // o nome do botao, na verdade uma referencia a ser tratada no controller
			intentOut.putExtra("numeroDeJogadores", numJogadores);//quantidade de jogadores
			intentOut.putStringArrayListExtra("arrayJogadores", jogadores);//lista de jogadores
			intentOut.putIntegerArrayListExtra("pontuacaoJogadores", listaPontuacao);//lista de pontuacao
			intentOut.putExtra("jogadorAtual", jogAtual);// o jogador atual
			intentOut.putIntegerArrayListExtra("listaRodadas", rodadas);
			super.finish();
			startActivity(intentOut);
		}
	}

	/**
	 * Metodo responsavel por preencher a ListView que apresenta a lista dos jogadores gravados no Banco de Dados
	 */
	private void populaTabela() {
		setContentView(R.layout.score_listview);
		ListView listView = (ListView) findViewById(R.id.list_view);
		ArrayList<Jogador> listaJogadores; //lista para receber os dados, se existirem no Banco de Dados
		try {
			controle = new Controle(this.getApplicationContext());
			listaJogadores = (ArrayList<Jogador>)controle.obterLista();
			if (listaJogadores.size() != 0){
				   //Criação do Adapter e passamos a nossa lista de Jogadores para ele
			       JogadorAdapter adapter2 = new JogadorAdapter(this, listaJogadores);
			       listView.setAdapter(adapter2);//passamos o Adapter como parametro para a ListVIEW
			} else {
				Toast toast = Toast.makeText(getBaseContext(),"Não há jogadores no Score", Toast.LENGTH_SHORT);
				toast.show();
			}
		} catch (Exception erro) {
			mensagemExibir("Erro Banco","Erro buscar dados no banco: " + erro.getMessage());
		}
	}
	
	/**
	 * Metodo que define se a pontuação é suficiente para ser gravada no Banco de Dados de acordo com a quantidade de dados armazenados
	 * e o valor da pontuação
	 * @param nomE o nome do Jogador
	 * @param rodadaS os pontos do Jogador
	 */
	private void gerenciarTabela(String nomE, int rodadaS){
		if(numRegistros()<10){//se houver até 9 jogadores gravados no BD grava direto
			gravarJogador(nomE, rodadaS);
		}else if(rodadaS>getMenorPonto()){//caso haja 10, compara a pontuação com o que há gravado no BD
			gravarJogador(nomE, rodadaS);//grava
			apagarUltimo();//apaga o pior resultado gravado anteriormente
		}else{
			Toast toast = Toast.makeText(getBaseContext(),"Não atingiu score!", Toast.LENGTH_SHORT);
			toast.show();
		}
	}
	
	/**
	 * Metodo que busca no Banco de Dados a quantidade de registros
	 * @return a quantidade de registros no BD, caso ocorra erros retorna 10.(para que seja utilizado o critério de maior pontuação)
	 */
	private int numRegistros(){
		int registros = 10;
		try {
			controle = new Controle(this.getApplicationContext());
			return controle.numRegistrosGravados();
		} catch (Exception e) {
			mensagemExibir("Erro Banco", "Erro buscar dados no banco: " + e.getMessage());
			return registros;
		}
	}
	
	/**
	 * Metodo que busca a menor pontuação gravada no BD
	 * @return a menor pontuação
	 */
	private int getMenorPonto(){
		int menor = 0;
		try {
			controle = new Controle(this.getApplicationContext());
			return controle.menorPontuacaoGravada();
		} catch (Exception e) {
			mensagemExibir("Erro Banco", "Erro buscar dados no banco: " + e.getMessage());
			return menor;
		}
	}
	/**
	 * Metodo utilizado para guardar os valores passados por parametro no banco de dados
	 * @param valorNome o nome do Jogador
	 * @param valorPontos os pontos do Jogador
	 */
	private void gravarJogador(String valorNome, int valorPontos){
		try {
			controle = new Controle(this.getApplicationContext());   
			controle.insereNoBanco(valorNome, valorPontos);			   		   
		   }
		   catch(Exception erro) {
			   mensagemExibir("Erro Banco", "Erro ao gravar dados no banco: "+erro.getMessage());
			  
		   }
	}
	
	/**
	 * Metodo que busca o valor mais baixo do campo rodadas(integer) e o apaga caso haja mais de dez registros no banco de dados. 
	 */
	private void apagarUltimo() {
		
		try {
			controle = new Controle(this.getApplicationContext());
			if (controle.numRegistrosGravados() > 10) {// atende a condição de só apagar caso haja mais de dez registros
														// no banco de dados.
				int menorValor; // variavel que será usada para armazenar o retorno da busca
				/**
				 * Essa query busca o id da linha que tem o menor valor no campo
				 * rodada, que foi adicionado mais recente, ou seja se dois
				 * jogadores com a mesma pontuacao forem encontrados a busca
				 * retornara o que foi armazenado por ultimo. (MAIOR ID)
				 */
				menorValor = controle.menorPontuacaoGravada();
				controle.apagarMaisQueDez();
			}
		} catch (Exception erro) {
			mensagemExibir("Erro Banco",
					"Erro buscar dados no banco: " + erro.getMessage());
		}

	}
	
	/**
	 * Metodo usado para mostrar uma mensagem de alerta
	 * @param titulo O titulo da mensagem
	 * @param texto o texto objetivo da mensagem
	 */
	private void mensagemExibir(String titulo, String texto) {
		AlertDialog.Builder mensagem = new AlertDialog.Builder(
				ScoreActivity.this);
		mensagem.setTitle(titulo);
		mensagem.setMessage(texto);
		mensagem.setNeutralButton("OK", null);
		mensagem.show();

	}
	
	/**
	 * CLASSE INTERNA USADA PARA ADAPTAR O ARRAYLIST DE JOGADORES À LISTA EXIBIDA NO SCORE
	 */
	public static class JogadorAdapter extends BaseAdapter{
	    private List<Jogador> listJogadores;
	 
	    //Classe utilizada para instanciar os objetos do XML
	    private LayoutInflater inflater;
	     
	    public JogadorAdapter(Context context, List<Jogador> plistJogadores) {
	        this.listJogadores = plistJogadores;
	        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    }
	    
	    @Override
	    public int getCount() {
	        return listJogadores.size();
	    }
	 
	    @Override
	    public Object getItem(int position) {
	        return listJogadores.get(position);
	    }
	 
	    @Override
	    public long getItemId(int position) {
	        return position;
	    }
	 
	    @Override
	    public View getView(int position, View convertView, ViewGroup viewGroup) {
	        //Pega o registro da lista e transfere para o objeto JogadorLV
	        Jogador jogadorLV = listJogadores.get(position);
	         
	        //Utiliza o XML score_campos para apresentar na tela
	        convertView = inflater.inflate(R.layout.score_campos, null);
	         
	        //Instância os objetos do XML
	        TextView tvNome = (TextView)convertView.findViewById(R.id.nome2);
	        TextView tvPontos = (TextView)convertView.findViewById(R.id.nome4);
	             
	        //pega os dados que estão no objeto jogadorLV e transfere para os objetos do XML
	        tvNome.setText(jogadorLV.getNome());
	        tvPontos.setText(jogadorLV.getPontosDeVida()+"");
	         
	        return convertView;
	    }
	}
	
	
}
