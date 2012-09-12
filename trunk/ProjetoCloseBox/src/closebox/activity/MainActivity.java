package closebox.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import closebox.audio.SoundManager;
import closebox.service.MusicaPrincipalService;
import closebox.service.MusicaPrincipalService.LocalBinder;

/**
 * Classe responsavel por mostrar a tela inicial do jogo e de acordo com o botao tocado pelo jogador, inicializa uma nova Activity
 * @author The EndGamers
 *
 */
public class MainActivity extends Activity {
    
    private Intent intent;
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
			mBound = true;
		}
	};
	
	@Override
    public void onCreate(Bundle savedInstanceState) { // CONSTRUTOR
        super.onCreate(savedInstanceState);
        //Coloca a tela main a frente.
        setContentView(R.layout.main);
        setVolumeControlStream(AudioManager.STREAM_MUSIC); 
        soundManager = SoundManager.getInstance(this);
        
        bindService(new Intent(MainActivity.this, MusicaPrincipalService.class), serviceConnection, Context.BIND_AUTO_CREATE);
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
    
    public void botaoInicio(View view){
    	soundManager.playSound(SoundManager.BOTAO_NAVEGACAO);
    	intent = new Intent(MainActivity.this, ControllerActivity.class); //determina a nova Activity
    	intent.putExtra("botao", "inicio"); // o nome do botao, na verdade uma referencia a ser tratada no controller
    	startActivity(intent); // inicializa a nova Activity, envia os dados ao controller
    }
    
    public void botaoOptions(View view){
    	soundManager.playSound(SoundManager.BOTAO_NAVEGACAO);
    	intent = new Intent(MainActivity.this, ControllerActivity.class); //determina a nova Activity
    	intent.putExtra("botao", "options"); // o nome do botao, na verdade uma referencia a ser tratada no controller
		startActivity(intent); // inicializa a nova Activity, envia os dados ao controller
		
    }
    
    public void botaoScore(View view){
    	soundManager.playSound(SoundManager.BOTAO_NAVEGACAO);
    	intent = new Intent(MainActivity.this, ControllerActivity.class); //determina a nova Activity
    	intent.putExtra("botao", "score"); // o nome do botao, na verdade uma referencia a ser tratada no controller
		startActivity(intent); // inicializa a nova Activity, envia os dados ao controller
		
    }
    
    public void botaoSobre(View view){
    	soundManager.playSound(SoundManager.BOTAO_NAVEGACAO);
    	intent = new Intent(MainActivity.this, ControllerActivity.class); //determina a nova Activity
    	intent.putExtra("botao", "sobre"); // o nome do botao, na verdade uma referencia a ser tratada no controller
    	startActivity(intent); // inicializa a nova Activity, envia os dados ao controller
    }
}
