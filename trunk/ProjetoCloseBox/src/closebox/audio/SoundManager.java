package closebox.audio;

import java.util.ArrayList;
import java.util.Stack;

import closebox.activity.R;
import closebox.controle.Controle;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundManager {  
	// Total de sons no pool  
	private static final int MAXSTREAMS = 10;  
	// Inst�ncia �nica   
	private static SoundManager instance;  

	// Pool de sons  
	private SoundPool mSoundPool;  
	// AudioManager para controlar o volume do som  
	private AudioManager mAudioManager;  
	// Lista com os ids dos sons adicionados  
	private ArrayList<Integer> mSoundPoolMap;  
	// Pilha que armazena as transa��es   
	// de execu��o dos sons  
	private Stack<Integer> mSongsTransactions;      
	private Context mContext;  
	public static final int BOTAO_NAVEGACAO = 0;
	public static final int PLACA_ABAIXANDO = 1;
	public static final int PLACA_LEVANTANDO = 5;
	public static final int PLACAS_ERRADAS = 3;
	public static final int CALCULO_ERRADO = 4;
	public static final int DADO_GIRANDO = 2;
	public static final int DADO_JOGANDO = 6;
	public static final int DIALOGO_ERRO = 7;
	public static final int ABAIXOU_TODAS_AS_PLACAS = 8;
	public Controle controle;

	// Construtor privado pra implementar o   
	// Singleton Design Pattern  
	private SoundManager(Context ct) {  
		mContext = ct;  
		mSoundPoolMap = new ArrayList<Integer>();  
		mSongsTransactions = new Stack<Integer>();  

		// Criando o pool de sons  
		mSoundPool = new SoundPool(  
				MAXSTREAMS, AudioManager.STREAM_MUSIC, 0);  

		// AudioManager � um servi�o de sistema  
		mAudioManager = (AudioManager)   
		mContext.getSystemService(  
				Context.AUDIO_SERVICE);  
		sonsFixos();

		try {
			controle = new Controle(ct);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}  

	public void sonsFixos(){
		addSound(R.raw.botao_4);
		addSound(R.raw.placa_abaixando);
		addSound(R.raw.dado_girando);
	}

	// M�todo est�tico para obter a inst�ncia �nica  
	public static SoundManager getInstance(Context ct) {  
		if (instance == null){  
			instance = new SoundManager(ct);  
		}  
		return instance;  
	}  

	// Adiciona um som ao pool  
	public void addSound(int soundId) {  
		mSoundPoolMap.add(  
				/* Carrega e obt�m o id do som no pool 
				 * O segundo par�metro o id do recurso 
				 * E o terceiro n�o serve pra nada :) , 
				 * Mas na documenta��o diz pra colocar 1 */  
				mSoundPool.load(mContext, soundId, 1));  
	}  

	// Manda tocar um determinado som  
	public void playSound(int index) { 
		if(controle.getEfeitos()){
			/* O AudioManager � usado aqui pra obter 
			 * o valor atual do volume do aparelho para 
			 * n�o tocar o som nem baixo nem alto demais. 
			 * A divis�o que � feita aqui � pq o m�todo  
			 * requer um valor entre 0.0 e 1.0. */  
			float streamVolume =   
				mAudioManager.getStreamVolume(  
						AudioManager.STREAM_MUSIC);  
			streamVolume /=   
				mAudioManager.getStreamMaxVolume(  
						AudioManager.STREAM_MUSIC);  

			/* playId, armazena o id da requisi��o do som  
			 * a ser tocado. Ele � usado para parar um  
			 * determinado som a qualquer momento. */  
			int playId = mSoundPool.play(  
					mSoundPoolMap.get(index), // ID do som  
					streamVolume, // volume da esquerda  
					streamVolume, // volume da direita  
					1, // prioridade   
					0, // -1 toca repetidamente,   
					// n = n�mero de repeti��es)  
					1  // pitch. 0.5f metade da velocidade  
					// 1 = normal e 2 = dobro da velocidade  
			);  

			// adiciona o id da transa��o na pilha  
			mSongsTransactions.push(playId);  
		}
	}  

	public void stopSounds() {  
		// Percorre todos os ids da pilha e manda  
		// parar todos os sons  
		while (mSongsTransactions.size() > 0)  
			mSoundPool.stop(mSongsTransactions.pop());  
	}  

	// Libera os recursos alocados  
	public void cleanup() {  
		mSoundPool.release();  
		mSoundPool = null;  
		mSoundPoolMap.clear();  
		mSongsTransactions.clear();  
		mAudioManager.unloadSoundEffects();  
	}  
}  
