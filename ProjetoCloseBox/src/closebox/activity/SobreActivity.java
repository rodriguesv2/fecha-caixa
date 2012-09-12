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
 * Classe que inicializa as classes especificas: HistoriaActivity, CreditosActivity e ComoJogarActivity 
 * de acordo com o botao tocado pelo jogador
 * @author THE ENDGAMERS
 *
 */
public class SobreActivity extends Activity{
	
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
	
	public void onCreate(Bundle savedInstanceState){ // metodo CONSTRUTOR
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sobre_novo);
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
	
	public void botaoCredito(View view){
		soundManager.playSound(SoundManager.BOTAO_NAVEGACAO);
		Intent intent = new Intent(this, ControllerActivity.class); //determina a nova Activity
		intent.putExtra("botao", "botaoCredito"); // o nome do botao, na verdade uma referencia a ser tratada no controller
		startActivity(intent); // inicializa a nova Activity, envia os dados ao controller
	}
	
	public void botaoHistoria(View view){
		soundManager.playSound(SoundManager.BOTAO_NAVEGACAO);
		Intent intent = new Intent(this, ControllerActivity.class); //determina a nova Activity
		intent.putExtra("botao", "botaoHistoria"); // o nome do botao, na verdade uma referencia a ser tratada no controller
		startActivity(intent); // inicializa a nova Activity, envia os dados ao controller
	}
	
	public void botaoComoJogar(View view){
		soundManager.playSound(SoundManager.BOTAO_NAVEGACAO);
		Intent intent = new Intent(this, ControllerActivity.class); //determina a nova Activity
		intent.putExtra("botao", "botaoComoJogar"); // o nome do botao, na verdade uma referencia a ser tratada no controller
		startActivity(intent); // inicializa a nova Activity, envia os dados ao controller
	}
}

