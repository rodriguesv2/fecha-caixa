package closebox.activity;

import closebox.audio.SoundManager;
import closebox.service.MusicaPrincipalService;
import closebox.service.MusicaPrincipalService.LocalBinder;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

/**
 * Classe responsavel por determinar a quantidade de jogadores que participarao do jogo
 * @author The EndGamers
 *
 */
public class NumeroDeJogadoresActivity extends Activity{
	
	public boolean mBound = false;
	public MusicaPrincipalService musicaPrincipalService;
	public SoundManager soundManager;
	
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
	public void onCreate(Bundle savedInstanceState){ // CONSTRUTOR
		super.onCreate(savedInstanceState);
		setContentView(R.layout.numero_de_jogadores);	
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
	 * Determina a quantidade de jogadores
	 * @param view o proprio botao selecionado pelo Jogador, que apresenta as imagens '1', '2' ou '3'.
	 */
	public void escolherNumero(View view){
		int numeroDeJogadores = 0;
		soundManager.playSound(SoundManager.BOTAO_NAVEGACAO);
		
		if(view.getId() == R.id.imageView2)		numeroDeJogadores = 1; // caso seja selecionada a imagem 1
		else if(view.getId() == R.id.imageView3)numeroDeJogadores = 2; // caso seja selecionada a imagem 2
		else 									numeroDeJogadores = 3; // caso seja selecionada a imagem 3
			
		Intent intent = new Intent(this, ControllerActivity.class); // faz a chamada da Activity ControllerActivity.
		intent.putExtra("numeroDeJogadores", numeroDeJogadores); // envia a quantidade selecionada pelo Jogador
		intent.putExtra("botao", "botaoNumeroDeJogadores"); // envia uma String "botaoNumeroDeJogadores", com chave "botao"
		startActivity(intent);
		finish();
	}
	
	/**
	 * Ao clicar no botao "Voltar" ou pressionar o botao "voltar" nativo do aparelho
	 * @param view o proprio botao voltar
	 */
	public void voltar(View view){
		soundManager.playSound(SoundManager.BOTAO_NAVEGACAO);
		onBackPressed();
	}
}
