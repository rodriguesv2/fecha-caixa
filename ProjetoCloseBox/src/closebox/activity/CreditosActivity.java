package closebox.activity;

import closebox.audio.SoundManager;
import closebox.model.Creditos;
import closebox.service.MusicaPrincipalService;
import closebox.service.MusicaPrincipalService.LocalBinder;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
/**
 * Classe responsavel por mostrar um texto na tela, com as informações dos desenvolvedores e todo os artefatos utilizados no projeto.
 * @author The EndGamers
 *
 */
public class CreditosActivity extends Activity{
	private TextView credito; // TextView responsavel por mostrar o texto "creditos"
	private Handler handler;
	private ScrollView scroll;
	private int indice = 0; // usado como indice na rolagem da tela
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
	
	@Override 
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.credito);
		
		credito = (TextView)findViewById(R.id.textView1);
		scroll = (ScrollView)findViewById(R.id.scrollView1);
		handler = new Handler();
		mostraCredito();
		passarCreditos();
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
	 * Insere um texto na TextView da tela, proveniente do método getCredito() da classe Creditos do pacote Model.
	 */
	public void mostraCredito(){
		credito.setText(Creditos.getCredito());
	}
	
	/**
	 * Thread que faz o scroll do texto,  quando o texto chega ao fim, volta ao começo sucessivamente.
	 */
	public void passarCreditos(){
		Runnable runnable = new Runnable() {
			 	
			@Override
			public void run() {
				
				while(true){
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					handler.post(new Runnable() {
						
						@Override
						public void run() {
							int rolar = indice;
							scroll.scrollTo(0, rolar);
							if(indice < 900){
								indice += 3;
							}else{
								indice = 0;
							}
						}
					});
				}
			}
		};
		new Thread(runnable).start();
	}
	
	/**
	 * Implementa o botao voltar do Android
	 * @param view o proprio botao voltar
	 */
	public void botaoVoltar(View view){
		soundManager.playSound(SoundManager.BOTAO_NAVEGACAO);
		onBackPressed();
	}
}
