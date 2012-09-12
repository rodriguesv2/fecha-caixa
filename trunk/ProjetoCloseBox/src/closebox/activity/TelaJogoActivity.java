package closebox.activity;

import java.util.ArrayList;

import closebox.service.*;
import closebox.service.MusicaPrincipalService.LocalBinder;

import closebox.controle.*;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Classe responsavel pelo jogo.
 * @author THE ENDGAMERS
 *
 */
public class TelaJogoActivity extends Activity{
	private TextView jogador1;
	private TextView jogador2;
	private TextView jogador3;
	private TextView pontos1;
	private TextView pontos2;
	private TextView pontos3;
	private Intent dadosIntent;
	private Handler handler;
	private ImageView dado1;
	private ImageView dado2;
	private Runnable runnable1;
	private Runnable runnable2;
	private ImageView placa1;
	private ImageView placa2; 
	private ImageView placa3; 
	private ImageView placa4; 
	private ImageView placa5; 
	private ImageView placa6; 
	private ImageView placa7; 
	private ImageView placa8; 
	private ImageView placa9; 
	private ImageView placaDown1;
	private ImageView placaDown2;
	private ImageView placaDown3;
	private ImageView placaDown4;
	private ImageView placaDown5;
	private ImageView placaDown6;
	private ImageView placaDown7;
	private ImageView placaDown8;
	private ImageView placaDown9;
	private ImageView dadoLancado1;
	private ImageView dadoLancado2;
	private Intent intentOut;
	private int jogadorAtual;//
	private TextView apontador;
	private boolean jahFoiPerguntadoSobreDados = false;
	private TextView qualSomaDasPlacas;
	private EditText campoSomaPlacas;
	private Button okSomaPlacas;
	private TextView rodadaAtual;
	private ArrayList<Integer> listaRodadas;
	private boolean calcularPontos = false;
	private boolean jahDesistiu = false;
	private Controle controle;
	private boolean mBound = false;
	private MusicaPrincipalService musicaPrincipalService;
	
	private int[]listaDados = {R.drawable.dado_face1,R.drawable.dado_face2,R.drawable.dado_face3,
			R.drawable.dado_face4,R.drawable.dado_face5,R.drawable.dado_face6};
	
	private int[] arrayImageViewPlaca = {R.id.imageViewPlaca_1,R.id.imageViewPlaca_2,R.id.imageViewPlaca_3,
			R.id.imageViewPlaca_4,R.id.imageViewPlaca_5,R.id.imageViewPlaca_6,
			R.id.imageViewPlaca_7,R.id.imageViewPlaca_8,R.id.imageViewPlaca_9};
	
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
	public void onCreate(Bundle savedInstanceState){ // metodo CONSTRUTOR
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tela_jogo);
		try {
			controle = new Controle();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bindService(new Intent(this, MusicaPrincipalService.class), serviceConnection, Context.BIND_AUTO_CREATE);
		
		threadDado1(); // faz o dado 1 girar
		threadDado2(); // faz o dado 2 girar
		handler = new Handler();
		dadosIntent = getIntent();
		instanciarObjetos();
		mostrarJogadores();
		dadoLancado1.setVisibility(View.INVISIBLE);
		dadoLancado2.setVisibility(View.INVISIBLE);
		embaralharPlaca();
		
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
	
	/**
	 * Responsavel por alterar a posicao das placas, de maneira que elas nao sejam apresentadas
	 * de forma crescente de 1 a 9, mas sim de forma aleatoria, a cada nova tela (ao trocar de Jogador ou nova rodada).
	 */
	public void embaralharPlaca(){
		int[] arrayDeImagens = controle.embaralharPlacas();
		ImageView placa;
		
		for(int i = 0; i < 9; i++){
			placa = (ImageView)findViewById(arrayImageViewPlaca[i]);
			placa.setImageResource(arrayDeImagens[i]);
		}
	}
	
	/**
	 * Mostra apenas as TextViews com conteudo, de acordo com a quantidade de jogadores.
	 */
	private void mostrarJogadores(){
		//Recebe dados da activity que a chamou.
		controle.setQuantidadeJodador(dadosIntent.getIntExtra("numeroDeJogadores",2));
		controle.setListaDeJogadores(dadosIntent.getStringArrayListExtra("arrayJogadores"));
		controle.setListaPontuacao(dadosIntent.getIntegerArrayListExtra("pontuacaoJogadores"));
		jogadorAtual = dadosIntent.getIntExtra("jogadorAtual", 0);
		listaRodadas = dadosIntent.getIntegerArrayListExtra("listaRodadas");
		controle.setPontosRanking(listaRodadas.get(jogadorAtual));
		
		ArrayList<String> listaJogadores = controle.getListaDeJogadores();
		ArrayList<Integer> listaPontuacao = controle.getListaPontuacao();
		
		Toast toast = Toast.makeText(getBaseContext(), "Agora é a vez de "+ listaJogadores.get(jogadorAtual), Toast.LENGTH_SHORT);
		toast.show();
		
		if(controle.getQuantidadejogador() == 1){
			jogador2.setVisibility(View.INVISIBLE);
			jogador3.setVisibility(View.INVISIBLE);
			pontos2.setVisibility(View.INVISIBLE);
			pontos3.setVisibility(View.INVISIBLE);
					
			jogador1.setText(listaJogadores.get(0)+"  ");
			pontos1.setText(listaPontuacao.get(0)+"");
			
		
		}else if(controle.getQuantidadejogador() == 2){
			jogador3.setVisibility(View.INVISIBLE);
			pontos3.setVisibility(View.INVISIBLE);
			
			jogador1.setText(listaJogadores.get(0)+"  ");
			pontos1.setText(listaPontuacao.get(0)+"");
			jogador2.setText(listaJogadores.get(1)+"  ");
			pontos2.setText(listaPontuacao.get(1)+"");
		
		}else{
			jogador1.setText(listaJogadores.get(0)+"  ");
			pontos1.setText(listaPontuacao.get(0)+"");
			jogador2.setText(listaJogadores.get(1)+"  ");
			pontos2.setText(listaPontuacao.get(1)+"");
			jogador3.setText(listaJogadores.get(2)+"  ");
			pontos3.setText(listaPontuacao.get(2)+"");
		}
		rodadaAtual.setText(controle.getPontosRanking()+"");
		apontaJogador(jogadorAtual);
	}
	
	/**
	 * Inicializa os objetos, isto é, as imagens visiveis ao Jogador.
	 */
	private void instanciarObjetos(){
		jogador1 = (TextView)findViewById(R.id.jogadorText1);
		jogador2 = (TextView)findViewById(R.id.jogadorText2);
		jogador3 = (TextView)findViewById(R.id.jogadorText3);
		pontos1 = (TextView)findViewById(R.id.pontosText1);
		pontos2 = (TextView)findViewById(R.id.pontosText2);
		pontos3 = (TextView)findViewById(R.id.pontosText3);
		dado1 = (ImageView)findViewById(R.id.imageView1);
		dado2 = (ImageView)findViewById(R.id.imageView2);
		placa1 = (ImageView)findViewById(R.id.imageViewPlaca_1);
		placaDown1 = (ImageView)findViewById(R.id.imageViewPD1);
		placa2 = (ImageView)findViewById(R.id.imageViewPlaca_2);
		placaDown2 = (ImageView)findViewById(R.id.imageViewPD2);
		placa3 = (ImageView)findViewById(R.id.imageViewPlaca_3);
		placaDown3 = (ImageView)findViewById(R.id.imageViewPD3);
		placa4 = (ImageView)findViewById(R.id.imageViewPlaca_4);
		placaDown4 = (ImageView)findViewById(R.id.imageViewPD4);
		placa5 = (ImageView)findViewById(R.id.imageViewPlaca_5);
		placaDown5 = (ImageView)findViewById(R.id.imageViewPD5);
		placa6 = (ImageView)findViewById(R.id.imageViewPlaca_6);
		placaDown6 = (ImageView)findViewById(R.id.imageViewPD6);
		placa7 = (ImageView)findViewById(R.id.imageViewPlaca_7);
		placaDown7 = (ImageView)findViewById(R.id.imageViewPD7);
		placa8 = (ImageView)findViewById(R.id.imageViewPlaca_8);
		placaDown8 = (ImageView)findViewById(R.id.imageViewPD8);
		placa9 = (ImageView)findViewById(R.id.imageViewPlaca_9);
		placaDown9 = (ImageView)findViewById(R.id.imageViewPD9);
		dadoLancado1 = (ImageView)findViewById(R.id.imageViewDadoLancado1);
		dadoLancado2 = (ImageView)findViewById(R.id.imageViewDadoLancado2);
		qualSomaDasPlacas = (TextView)findViewById(R.id.textView1);
		campoSomaPlacas = (EditText)findViewById(R.id.campo_pontos_de_vida);
		okSomaPlacas = (Button)findViewById(R.id.okSomaPlacas);
		rodadaAtual = (TextView)findViewById(R.id.rodada);
	}
	
	/**
	 * Adiciona um # a frente do nome da rodada.
	 * @param jogador o indice do Jogador atual
	 */
	public void apontaJogador(int jogador){
		if(jogador == 0){
			apontador = (TextView)findViewById(R.id.checkedTextView1);
			apontador.setVisibility(View.VISIBLE);
		}else if(jogador == 1){
			apontador = (TextView)findViewById(R.id.checkedTextView2);
			apontador.setVisibility(View.VISIBLE);
		}else{
			apontador = (TextView)findViewById(R.id.checkedTextView3);
			apontador.setVisibility(View.VISIBLE);
		}
	}
	
	/**
	 * Thread responsavel por fazer o efeito do Dado 1.
	 * Faz o dado 1 girar.
	 */
	public void threadDado1() {
		runnable1 = new Runnable() {
			int i = 2;
			@Override
			public void run() {
				controle.setGirarDado1(true);
				while (controle.getGirarDado1()) {
					final int value = i;
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					handler.post(new Runnable() {
						@Override
						public void run() {
							if(value<6){
								dado1.setImageResource(listaDados[value]);
							}else{
								i = 0;
								dado1.setImageResource(listaDados[i]);
							}
						}
					});
					i++;
				}
			}
		};
		new Thread(runnable1).start();
		controle.setDado1Parado(false);
	}

	/**
	 * Thread responsavel por fazer o efeito do Dado 2.
	 * Faz o dado 2 girar.
	 */
	public void threadDado2() {
		// Do something long
		runnable2 = new Runnable() {
			int i = 4;
			@Override
			public void run() {
				controle.setGirarDado2(true);
				while (controle.getGirarDado2()) {

					final int value = i;
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					handler.post(new Runnable() {
						@Override
						public void run() {
							if(value<6){
								dado2.setImageResource(listaDados[value]);
							}else{
								i = 0;
								dado2.setImageResource(listaDados[i]);
							}
						}
					});
					i++;
				}
			}
		};
		new Thread(runnable2).start();
		controle.setDado2Parado(false);
	}
	
	
	/**
	 * Faz com que os dados voltem a posição de jogar.
	 */
	public void escondeDadoLancado(){
		if(!controle.getEhUmDado()){
			dadoLancado1.setVisibility(View.INVISIBLE);
			dadoLancado2.setVisibility(View.INVISIBLE);
			dado1.setVisibility(View.VISIBLE);
			dado2.setVisibility(View.VISIBLE);
		}else{
			dadoLancado1.setVisibility(View.INVISIBLE);
			dado1.setVisibility(View.VISIBLE);
		}
	}
	
	/**
	 * Ao tocar no dado esse metodo é disparado.
	 * Esconde a imagem do dado girando, faz o sorteio de um numero aleatorio entre 1 e 6 e
	 * apresenta o dado com a face relativa a esse numero.
	 * @param view a imagem do dado girando.
	 */
	public void acaoDado(View view){
		ImageView dado = (ImageView)findViewById(view.getId());
		
		dado.setVisibility(View.INVISIBLE);
		controle.acaoDado(view);
		
		if(view.getId() == R.id.imageView1)sortearDado1();
		else							   sortearDado2();
	}
	
	/**
	 * Faz com que o numero sorteado corresponda a imagem do dado.
	 */
	public void sortearDado1(){
		controle.sorteioDado1();
		dadoLancado1.setImageResource(listaDados[controle.getValorDado1()-1]);
		dadoLancado1.setVisibility(View.VISIBLE);
	}
	
	/**
	 * Faz com que o numero sorteado corresponda a imagem do dado.
	 */
	public void sortearDado2(){
		controle.sorteioDado2();
		dadoLancado2.setImageResource(listaDados[controle.getValorDado2()-1]);
		dadoLancado2.setVisibility(View.VISIBLE);
	}
	
	/**
	 * Esconde o dado 2.
	 */
	public void inutilizarDado2(){
		dado2.setVisibility(View.INVISIBLE);
	}
	
	//-------------------------------------------------------------------------------------------------------------//
	//caso de uso abaixar placas
	/**
	 * Chamado ao pressionar uma das placas do jogo.
	 */
	public void abaixarPlaca(View view){
		if(((controle.getDado1Parado() && controle.getDado2Parado()) 
				|| (controle.getDado1Parado() && controle.getEhUmDado())) && !calcularPontos){

			ImageView placa = (ImageView)findViewById(view.getId());
			ImageView placaDown = (ImageView)findViewById(controle.identificarPlacaDown(view));

			placa.setVisibility(View.INVISIBLE);
			placaDown.setVisibility(View.VISIBLE);

			int[] ordemDasPlacas = controle.getOrdemDasPlacas();
			int valorDaPlaca = ordemDasPlacas[(controle.getPosicaoDaPlaca(view)-1)];
			
			calculaJogada(valorDaPlaca);
		}
	}
	
	/**
	 * Calcula a soma dos dados e valida a ação levando em conta se uma ou duas
	 * placas foram abaixadas.
	 */
	public void calculaJogada(int placa){
		controle.gerenciaJogada(placa);
		
		if(controle.isLevantarPlacas()){
			int placa1 = controle.qualEhAPosicaoDaPlaca(placa);
			int placa2 = controle.qualEhAPosicaoDaPlaca(controle.getPlacaAnterior());
			levantar2Placas(placa1, placa2, placa, controle.getPlacaAnterior());
			controle.setLevantarPlacas(false);
		}
		if(controle.isGirarDados()){
			threadDado1();
			threadDado2();
			controle.setGirarDados(false);
			escondeDadoLancado();
		}
		if(controle.isMostraRanking()){
			rodadaAtual.setText(""+controle.getPontosRanking());
			controle.setMostraRanking(false);
		}
		if(controle.isCalcularPontosRestantes()){
			calculaPontosRestantes();
		}
		if(controle.isPerguntarSobreDado()){
			subirDialogoSobreDados();
			controle.setPerguntarSobreDado(false);
		}
		if(controle.isUltimaPlaca() && !controle.isCalcularPontosRestantes()){
			mensagemJogadaErrada(placa, 0);
			levantarPlaca(controle.qualEhAPosicaoDaPlaca(placa));
			threadDado1();
			threadDado2();
			controle.setGirarDados(false);
			escondeDadoLancado();
		}
	}
	
	/**
	 * Faz a chamada dos metodos responsaveis pelo efeito dos dados girando.
	 */
	public void girarDados(){
		if(controle.isGirarDados()){
			threadDado1();
			threadDado2();
		}
	}
	
	/**
	 * Chamado quando ha uma jogada errada e se faz necessario levanta as placas que foram abaixadas
	 * @param placa a ultima placa abaixada
	 * @param placaAnterior a placa anteriormente abaixada
	 * @param placaMensagem1 ??
	 * @param placaMensagem2 ??
	 */
	private void levantar2Placas(int placa, int placaAnterior, int placaMensagem1, int placaMensagem2) {
		levantarPlaca(placa);
		levantarPlaca(placaAnterior);
		controle.setDado1Parado(true);
		controle.setDado2Parado(true);
		controle.setPrimeiraPlaca(true);
		controle.setPlacaAnterior(0);
		
		mensagemJogadaErrada(placaMensagem1, placaMensagem2);
	}

	/**
	 * Levanta uma placa.
	 * @param placa a placa a ser levantada.
	 */
	private void levantarPlaca(int placa) {
		switch (placa) {
		case 1:
			placa1.setVisibility(View.VISIBLE);
			placaDown1.setVisibility(View.INVISIBLE);
			break;
		
		case 2:
			placa2.setVisibility(View.VISIBLE);
			placaDown2.setVisibility(View.INVISIBLE);
			break;
			
		case 3:
			placa3.setVisibility(View.VISIBLE);
			placaDown3.setVisibility(View.INVISIBLE);
			break;
			
		case 4:
			placa4.setVisibility(View.VISIBLE);
			placaDown4.setVisibility(View.INVISIBLE);
			break;
			
		case 5:
			placa5.setVisibility(View.VISIBLE);
			placaDown5.setVisibility(View.INVISIBLE);
			break;
			
		case 6:
			placa6.setVisibility(View.VISIBLE);
			placaDown6.setVisibility(View.INVISIBLE);
			break;
			
		case 7:
			placa7.setVisibility(View.VISIBLE);
			placaDown7.setVisibility(View.INVISIBLE);
			break;
			
		case 8:
			placa8.setVisibility(View.VISIBLE);
			placaDown8.setVisibility(View.INVISIBLE);
			break;
			
		case 9:
			placa9.setVisibility(View.VISIBLE);
			placaDown9.setVisibility(View.INVISIBLE);
			break;

		default:
			break;
		}
	}
		
	/**
	 * Verifica se já foi perguntado se deseja jogar com apenas 1 dado.
	 */
	public void subirDialogoSobreDados(){
		if(controle.placasAltasAbaixadas() && !jahFoiPerguntadoSobreDados){
			determinarQuantidadeDeDados();
			jahFoiPerguntadoSobreDados = true;
		}
	}
	
	/**
	 * Detemina se deve haver dois dados.
	 */
	public void determinarQuantidadeDeDados(){
		if(controle.placasAltasAbaixadas()){
			AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
			dialogo.setTitle("Sugestão");
			dialogo.setMessage("As placas 7, 8 e 9 foram abaixadas.\n Deseja jogar " +
					"com 1 dado?");
			
			dialogo.setPositiveButton("Sim", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					controle.setEhUmDado(true);
					inutilizarDado2();
				}
			});
			dialogo.setNegativeButton("Não", null);
			dialogo.show();
			
		}
	}
	
	/**
	 * Dispara uma mensagem dizendo que a jogada é invalida.
	 * @param placa1 placa abaixada 
	 * @param placa2 placa abaixada
	 */
	private void mensagemJogadaErrada(int placa1, int placa2){
		AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
		dialogo.setTitle("JOGADA ERRADA");
		
		if(!controle.isUltimaPlaca())
			dialogo.setMessage("A soma de "+ placa2 +" e "+ placa1 + " não corresponde á soma dos dados!");
		else
			dialogo.setMessage("" + placa1 + " não corresponde á soma dos dados!");
		
		dialogo.setPositiveButton("OK", new OnClickListener() {	
			@Override
			public void onClick(DialogInterface dialogo, int qualBotao) {
				try {
					this.finalize();
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		});
    	dialogo.show();
	}
	
	/**
	 * Exibe caixa de dialogo para o caso do jogador não conseguir mais jogar.
	 * @param view o proprio botao "desistir"
	 */
	public void desistir(View view){
		if(!jahDesistiu){
			jahDesistiu = true;
			AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
			dialogo.setTitle("NÃO É POSSÍVEL PROSSEGUIR!");
			dialogo.setMessage("Tem certeza que não há jogadas possíveis?");

			dialogo.setPositiveButton("CONFIRMAR", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					mostrarCalcularPontos();
				}
			});

			dialogo.setNegativeButton("TENTAR NOVAMENTE", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					try {
						jahDesistiu = false;
						this.finalize();
					} catch (Throwable e) {
						e.printStackTrace();
					}
				}
			});
			
			dialogo.setOnCancelListener(new DialogInterface.OnCancelListener() {
				
				@Override
				public void onCancel(DialogInterface dialog) {
					jahDesistiu = false;
				}
			});
			dialogo.show();
		}
	}
	/**
	 * Coloca os dados em um Intent e os envia ao ControllerActivity para ser tratado.
	 */
	public void calculaPontosRestantes(){
		// inserir ou enviar para o Activity CALCULAR PONTOS DE VIDA
		
		intentOut = new Intent(this, ControllerActivity.class);
		
		intentOut.putExtra("botao", "calcularPontosDeVida");
		intentOut.putExtra("numeroDeJogadores", controle.getQuantidadejogador());//quantidade de jogadores (int)
		intentOut.putStringArrayListExtra("arrayJogadores", controle.getListaDeJogadores());//lista de nomes dos jogadores (String)
		intentOut.putIntegerArrayListExtra("pontuacaoJogadores", controle.getListaPontuacao());//lista de pontuacao (int)
		intentOut.putExtra("jogadorAtual", jogadorAtual);// o jogador atual (int)
		intentOut.putExtra("pontosRodada", controle.getPontosRestantes());// a soma das placas nao abaixadas (int)
		listaRodadas.set(jogadorAtual, controle.getPontosRanking());
		intentOut.putIntegerArrayListExtra("listaRodadas", listaRodadas);//lista das rodadas (int)
		super.finish();
		startActivity(intentOut);
	}
	/**
	 * Chamado ao pressionar o botao "voltar" nativo do Android.
	 */
	@Override
	public void onBackPressed(){
		
		AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
		dialogo.setTitle("Sair do Jogo");
		dialogo.setMessage("Deseja realmente sair?");
		
		dialogo.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		});
		
		dialogo.setNegativeButton("Não", null);
		
		dialogo.show();
	}
	
	/**
	 * Mostra um editText para que seja inserido a soma das placas que nao foram abaixadas.
	 */
	public void mostrarCalcularPontos(){
		campoSomaPlacas.setVisibility(View.VISIBLE);
		qualSomaDasPlacas.setVisibility(View.VISIBLE);
		okSomaPlacas.setVisibility(View.VISIBLE);
		calcularPontos = true;
		dado1.setVisibility(View.INVISIBLE);
		dado2.setVisibility(View.INVISIBLE);
		
	}
	
	/**
	 * Verifica se o Jogador inseriu os valores corretos na EditText do metodo mostrarCalcularPontos().
	 * @param view o botao "ok" ao lado da EditText.
	 */
	public void validarCalculoDoUsuario(View view){
		try {
			int somaDasPlacas = Integer.parseInt(campoSomaPlacas.getText().toString());
			
			if(somaDasPlacas == controle.getPontosRestantes()){
				if((controle.getListaPontuacao().get(jogadorAtual) - controle.getPontosRestantes())
						>= 0)					
					dialogoCalculaPontosRestantes();
				else
					calculaPontosRestantes();
				
			}else{
				dialogoErroDeCalculo(null);
			}
		} catch (NumberFormatException e) {
			dialogoErroDeCalculo(e);
		}		
	}
	
	/**
	 * Mensagem exibida ao inserir valores incorretos na EditText do calculo de pontos restantes.
	 * @param e a Exception do erro
	 */
	public void dialogoErroDeCalculo(NumberFormatException e){
		AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
		
		dialogo.setTitle("Erro");
		if(e == null) dialogo.setMessage("Calculo não está correto.");
		else          dialogo.setMessage("O campo está vazio ou preenchido errado.");
		
		dialogo.setNegativeButton("Ok", null);
		dialogo.show();
	}
	
	/**
	 * Mensagem exibida ao inserir valores incorretos na EditText do calculo de pontos restantes.
	 * @param e a Exception do erro
	 */
	public void dialogoErroDeCalculoRestante(NumberFormatException e){
		AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
		
		dialogo.setTitle("Erro");
		if(e == null) dialogo.setMessage("Calculo não está correto.");
		else          dialogo.setMessage("O campo está vazio ou preenchido errado.");
		
		dialogo.setPositiveButton("Ok", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialogoCalculaPontosRestantes();
			}
		});
		dialogo.show();
	}
	
	/**
	* Mensagem exibida para que seja inserido o valor da subtração dos pontos.
	*/
	public void dialogoCalculaPontosRestantes(){
		AlertDialog.Builder dialogo = new AlertDialog.Builder(this);

		dialogo.setTitle("Subtrair");
		dialogo.setMessage("Você deve subtrair o valor das placas com seus pontos de vida\n\n" +
				controle.getListaPontuacao().get(jogadorAtual)+" - "+controle.getPontosRestantes()+" =");

		final EditText inserirNumero = new EditText(this);
		dialogo.setView(inserirNumero);

		dialogo.setPositiveButton("Calcular", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				try{
					int numero = Integer.parseInt(inserirNumero.getText().toString());
					if(numero != (controle.getListaPontuacao().get(jogadorAtual) -
							controle.getPontosRestantes())){
						dialogoErroDeCalculoRestante(null);
					}else{
						calculaPontosRestantes();
					}
				}catch (NumberFormatException e) {
					dialogoErroDeCalculoRestante(e);
				}
			}
		});
		dialogo.show();
	}
	
}