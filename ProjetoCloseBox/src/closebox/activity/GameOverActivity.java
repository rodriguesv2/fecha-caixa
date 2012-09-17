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
 * Classe responsavel por mostrar a tela "GameOver" ao jogador
 * @author The EndGamers
 *
 */
public class GameOverActivity extends Activity{ // CONSTRUTOR
    
	private SoundManager soundManager;
	private boolean mBound = false;
	private MusicaPrincipalService musicaPrincipalService;
	//Atributo sobrescrito para conex�o com o servi�o de musica.
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Coloca a tela main a frente.
        setContentView(R.layout.game_over);
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
	
	public void menuPrincipal(View view){
		soundManager.playSound(SoundManager.BOTAO_NAVEGACAO);
		finish();
	}
	
	public void jogarNovamente(View view){
		soundManager.playSound(SoundManager.BOTAO_NAVEGACAO);
		startActivity(new Intent(this, NumeroDeJogadoresActivity.class));
		finish();
	}
}
