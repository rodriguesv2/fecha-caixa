package closebox.activity;

import closebox.controle.Controle;
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
import android.widget.CheckBox;
import android.widget.TextView;
/**
 * PROVISORIO
 * @author 
 *
 */
public class OptionsActivity extends Activity{
	
	private CheckBox musicaCheck;
	private CheckBox somCheck;
	private Controle controle;
	private boolean mBound = false;
	private MusicaPrincipalService musicaPrincipalService;
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
		setContentView(R.layout.opcoes);
		try {
			controle = new Controle(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		musicaCheck = (CheckBox)findViewById(R.id.checkMusica);
		somCheck = (CheckBox)findViewById(R.id.checkSom);
		
		musicaCheck.setChecked(controle.getMusica());
		somCheck.setChecked(controle.getEfeitos());
		
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
	
	public void acao(View view){
	    controle.alterarOpcoes(musicaCheck.isChecked(), somCheck.isChecked());
	}
	
	public void voltar(View view){
		super.onBackPressed();
	}
}