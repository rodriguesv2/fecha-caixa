package closebox.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TelaInicial extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tela_inicial);
	}
	public void chamarMain(View view){
    	Intent intent = new Intent(this, MainActivity.class); //determina a nova Activity
    	startActivity(intent); // inicializa a nova Activity, envia os dados ao controller
    	finish();
	}
	
}
