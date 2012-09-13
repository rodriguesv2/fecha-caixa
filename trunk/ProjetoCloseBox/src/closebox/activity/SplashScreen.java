package closebox.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

public class SplashScreen extends Activity implements Runnable{
	
	private ProgressBar mProgress; // a barra de estatus
    private int mProgressStatus = 0; // inteiro usado para fazer o preenchimento da barra.
    private Handler progressBarHandler = new Handler(); // thread
	private long size = 0; // usado para as porções da barra.
	
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.splash);

		mProgress = (ProgressBar) findViewById(R.id.progressBar1);
		mProgress.setProgress(20); // começa com 20% preenchido
		mProgress.setMax(3500);
		new Thread(new Runnable() {
			public void run() {
				//determina a barra para ser preenchida em 3 segundos.
				while (mProgressStatus < 3000) {

					mProgressStatus = progresso();

					// aguarda 1 segundo.
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					// Update the progress bar
					progressBarHandler.post(new Runnable() {
						public void run() {
							mProgress.setProgress(mProgressStatus);
						}
					});
				}

				if (mProgressStatus >= 3000) {

					// espera 4 centesimos de segundo.
					try {
						Thread.sleep(400);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
		
		/**
		 * O thread do Splash
		 */
		Handler handler = new Handler();
		handler.postDelayed(this, 3500);
	}

	public void run() {
		startActivity(new Intent(this, TelaInicial.class));
		finish();
	}

	/**
	 * Chamado para mostrar o progresso da barra.
	 * @return um numero, 800, 1600 ou 2400.
	 */
	public int progresso() {

		while (size <= 3000) {

			size++;

			if (size == 1000) {
				return 800;
			} else if (size == 2000) {
				return 1600;
			} else if (size == 3000) {
				return 2400;
			}
		}
		return 3500;
	}
	
}
