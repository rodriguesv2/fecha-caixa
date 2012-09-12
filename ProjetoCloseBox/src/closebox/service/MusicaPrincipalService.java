package closebox.service;

import closebox.activity.R;
import closebox.controle.Controle;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

public class MusicaPrincipalService extends Service{
	
	private MediaPlayer mediaPlayer;
    private IBinder myBinder = new LocalBinder();
    private Controle controle;
    
    public class LocalBinder extends Binder {
        public MusicaPrincipalService getService() {
            return MusicaPrincipalService.this;
        }
    }
    
    public void pauseMusic(){
    	if(mediaPlayer.isPlaying())
    		mediaPlayer.pause();
    }
    
    public void playMusic(){
    	if(controle.getMusica())
    		mediaPlayer.start();
    }
    
    @Override  
    public IBinder onBind(Intent i) {
        return myBinder;  
    }  
    
    @Override  
    public void onCreate() {  
    	try {
			controle = new Controle(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	mediaPlayer = MediaPlayer.create(this, R.raw.yoho);
    	if(controle.getMusica())
    		mediaPlayer.start();
    	mediaPlayer.setLooping(true);
    }  
      
    @Override  
    public void onDestroy() {  
    	mediaPlayer.stop();
    	mediaPlayer.release();
    }
}
