package closebox.activity;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.*;

import closebox.audio.SoundManager;
import closebox.model.Jogador;
import closebox.service.MusicaPrincipalService;
import closebox.service.MusicaPrincipalService.LocalBinder;

/**
 * Classe responsavel pela exibição e pelo preenchimento dos campos relativos ao nome do Jogador
 * @author The EndGamers
 */
public class InserirNomeActivity extends Activity {
	private Intent intentDados; // Intent responsavel por receber dados
	private Intent intentOut; // Intent responsavel por enviar dados
	private EditText editJogador1; //campo de texto para digitar o nome do Jogador
	private EditText editJogador2;
	private EditText editJogador3;
	private TextView textJogador1; // o nome de exibição do Jogador
	private TextView textJogador2;
	private TextView textJogador3;
	private ArrayList<String> arrayJogadores; // lista com os nomes dos jogadores
	private ArrayList<Integer> pontosJogador; // lista com pontos de vida dos jogadores
	private ArrayList<Integer> listaRodadas; // lista com a pontuação (score) dos jogadores
	private int indice = 0; //variavel usada para determinar a quantidade de campos de acordo com a quantidade de jogadores
	private boolean mBound = false;
	private MusicaPrincipalService musicaPrincipalService;
	private SoundManager soundManager;
	
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
	
	//Para o Android, essa eh a classe main.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Faz uma nova tela entrar na frente.
        setContentView(R.layout.inserir_nomes);
        instanciarObjetos();
        setNovaIntent();
        setQuantidadeDeCamposDeTextos();
        soundManager = SoundManager.getInstance(this);
        
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
	
	@Override
	public void finish(){
		//soundManager.cleanup();
		super.finish();
	}
    
	/**
	 * Metodo chamado ao pressionar o botao "OK"
	 * Insere a quantidade de jogadores e as listas de nomes, pontos de vida e pontuacao em um Intent e os envia ao Controller
	 * @param view o proprio botao "OK"
	 */
	public void botaoOk(View view) {
		soundManager.playSound(SoundManager.BOTAO_NAVEGACAO);
		// Instancia a intent com a activity destino"
		intentOut = new Intent(this, ControllerActivity.class);

		if (confirmaPreenchimento(indice)) { // caso o campo de texto nao esteja vazio
			addJogadores(); // adiciona os nomes na lista
			populaPontos(indice); // adiciona os pontos de vida na lista

			// Iserindo os dados no Intent
			intentOut.putExtra("numeroDeJogadores", indice);
			intentOut.putExtra("botao", "botaoOkInserirNome");
			intentOut.putStringArrayListExtra("arrayJogadores", arrayJogadores);
			intentOut.putExtra("jogadorAtual", 0);
			intentOut.putIntegerArrayListExtra("pontuacaoJogadores",pontosJogador);
			intentOut.putIntegerArrayListExtra("listaRodadas", listaRodadas);

			finish();
			// Vai para nova activity
			startActivity(intentOut);
		}
	}
	
	/**
	 * Metodo chamado ao pressionar o botao "CANCELAR"
	 * Volta para a tela anterior
	 * @param view o proprio botao "CANCELAR"
	 */
	public void botaoCancelar(View view){	
		soundManager.playSound(SoundManager.BOTAO_NAVEGACAO);
		onBackPressed();
	}
	
	/**
	 * Metodo que verifica se os EditTexts foram preenchidos, caso contrario retorna falso
	 * e aguarda o preenchimento correto ou que se pressione o botao "CANCELAR"
	 * @param indice a quantidade de jogadores
	 * @return true se os campos nao estao vazios e false caso estejam vazios
	 */
	private boolean confirmaPreenchimento(int indice){
		String nome1 = editJogador1.getText().toString();
		String nome2 = editJogador2.getText().toString();
		String nome3 = editJogador3.getText().toString();
		boolean preenchimentoOk = false;
		this.indice = indice;
		
		switch (indice) {
		case 1:
			if(nome1.length() == 0){ //se os campos nao estao vazios
				preenchimentoOk = false;
			}else{
				preenchimentoOk = true;
			}
			break;
			
		case 2:
			if(nome1.length() == 0 || nome2.length() == 0){ //se os campos nao estao vazios
				preenchimentoOk = false;
			}else{
				preenchimentoOk = true;
				}
			break;
			
		case 3:
			if(nome1.length() == 0 || nome2.length() == 0 || nome3.length() == 0){ //se os campos nao estao vazios
				preenchimentoOk = false;
			}else{
				preenchimentoOk = true;
			}
			break;

		default :
			preenchimentoOk = false;
			break;
		}
		return preenchimentoOk;
		
	}
	
	/**
	 * Metodo que preenche os pontos de vida com 45 e os pontos com 0 para cada Jogador
	 * @param index a quantidade de jogadores
	 */
	private void populaPontos(int index){
		pontosJogador = new ArrayList<Integer>();
		listaRodadas = new ArrayList<Integer>();
		for(int i = 0; i<index; i++){
			pontosJogador.add(i, 45);
			listaRodadas.add(i, 0);
		}
	}
	
	 
	/**
	 * Metodo que cria a lista de Jogadores para a proxima activity montar a lista na view.
	 */
	private void addJogadores(){
		String string1 = editJogador1.getText().toString(); // variavel recebe o nome digitado na caixa de texto
		String string2 = editJogador2.getText().toString();
		String string3 = editJogador3.getText().toString();
		
		arrayJogadores = new ArrayList<String>();
		arrayJogadores.add(string1); // adiciona o nome na lista
		arrayJogadores.add(string2);
		arrayJogadores.add(string3);
	}
	
	/**
	 * Metodo que inicializa os objetos da tela
	 */
	private void instanciarObjetos(){
		editJogador1 = (EditText)findViewById(R.id.editText1);
		editJogador2 = (EditText)findViewById(R.id.editText2);
		editJogador3 = (EditText)findViewById(R.id.editText3);
		textJogador1 = (TextView)findViewById(R.id.textView1);
		textJogador2 = (TextView)findViewById(R.id.textView2);
		textJogador3 = (TextView)findViewById(R.id.textView3);
		
	}
	
	/**
	 * Metodo que recebe os dados via Intent
	 */
	private void setNovaIntent(){
		intentDados = getIntent();
	}
	
	/**
	 * Metodo que torna os campos visiveis, de acordo com a quantidade de jogadores escolhidos.
	 */
	private void setQuantidadeDeCamposDeTextos(){
		indice = intentDados.getIntExtra("numeroDeJogadores", 1);
		
		switch (indice) { // a quantidade de jogadores
		case 1:
			editJogador1.setVisibility(View.VISIBLE);
			textJogador1.setVisibility(View.VISIBLE);
			break;
		case 2:
			editJogador1.setVisibility(View.VISIBLE);
			textJogador1.setVisibility(View.VISIBLE);
			editJogador2.setVisibility(View.VISIBLE);
			textJogador2.setVisibility(View.VISIBLE);
			break;
		case 3:
			editJogador1.setVisibility(View.VISIBLE);
			textJogador1.setVisibility(View.VISIBLE);
			editJogador2.setVisibility(View.VISIBLE);
			textJogador2.setVisibility(View.VISIBLE);
			editJogador3.setVisibility(View.VISIBLE);
			textJogador3.setVisibility(View.VISIBLE);
			break;

		default:
			editJogador1.setVisibility(View.VISIBLE);
			textJogador1.setVisibility(View.VISIBLE);
			break;
		}
	}
}

