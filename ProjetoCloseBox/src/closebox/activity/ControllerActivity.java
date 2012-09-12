package closebox.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
/**
 * Classe que gerencia o fluxo entre as Activities
 * @author The EndGamers
 *
 */
public class ControllerActivity extends Activity{
	private Intent intentIn; // Intent responsavel por receber dados enviados por outras Actuvities
	private Intent intentOut; // Intent responsavel por enviar dados  para outras Actuvities
	
	/**
	 * Construtor da classe que ao inicializar chama o unico metodo dessa classe
	 */
	@Override 
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		selecionaBotao();
		
	}
	
	/**
	 * Recebe via Intent uma String com chave "botao" e de acordo com essa String chama outra Activity.
	 */
	private void selecionaBotao(){
		intentIn = getIntent();
		
		String botao = intentIn.getStringExtra("botao");
		
		
		if(botao.equals("inicio")){ // chamada do botao "inicio",
			
			intentOut = new Intent(this, NumeroDeJogadoresActivity.class); // faz a chamada da Activity NumeroDeJogadoresActivity.
			super.finish();													// nao envia dado algum.
			startActivity(intentOut);
			
		}else if(botao.equals("options")){//chamada do botao "opcoes"
			intentOut = new Intent(this, OptionsActivity.class);// faz a chamada da Activity OptionsActivity.
			super.finish();													// nao envia dado algum.
			startActivity(intentOut);
			
		}else if(botao.equals("score")){//chamada do botao "score"
			intentOut = new Intent(this, ScoreActivity.class);// faz a chamada da Activity ScoreActivity
			intentOut.putExtra("botao", "lista"); // envia uma String "lista" com chave "botao"
			super.finish();
			startActivity(intentOut);
			
		}else if(botao.equals("sobre")){//chamada do botao "sobre"
			intentOut = new Intent(this, SobreActivity.class);// faz a chamada da Activity SobreActivity
			super.finish();										// nao envia dado algum.
			startActivity(intentOut);
			
		}else if(botao.equals("botaoOkInserirNome")){//chamada apos inserir o(s) nome(s) e pressionar "OK"
			intentOut = new Intent(this, TelaJogoActivity.class);// faz a chamada da Activity SobreActivity
			intentOut.putExtra("numeroDeJogadores", intentIn.getIntExtra("numeroDeJogadores",2));//quantidade de jogadores
			intentOut.putStringArrayListExtra("arrayJogadores", intentIn.getStringArrayListExtra("arrayJogadores"));//lista de jogadores
			intentOut.putIntegerArrayListExtra("pontuacaoJogadores", intentIn.getIntegerArrayListExtra("pontuacaoJogadores"));//lista de pontosDeVida
			intentOut.putExtra("JogadorAtual", intentIn.getIntExtra("JogadorAtual", 0));// o jogador atual
			intentOut.putIntegerArrayListExtra("listaRodadas", intentIn.getIntegerArrayListExtra("listaRodadas"));//lista da pontuacao do score
			super.finish();
			startActivity(intentOut);
			
		}else if(botao.equals("botaoCancelarDialogo")){//chamada do botao "cancelar"
			intentOut = new Intent(this, MainActivity.class);// faz a chamada da Activity MainActivity, que e a tela inicial
			super.finish();										// nao envia dado algum.
			startActivity(intentOut);
			
		}else if(botao.equals(null)){//chamada em caso de falha / erro
			intentOut = new Intent(this, MainActivity.class);// faz a chamada da Activity MainActivity, que e a tela inicial
			super.finish();										// nao envia dado algum.
			startActivity(intentOut);
		
		}else if(botao.equals("botaoCredito")){//chamada do botao "mostrar creditos"
			intentOut = new Intent(this, CreditosActivity.class);// faz a chamada da Activity CreditosActivity
			super.finish();										// nao envia dado algum.
			startActivity(intentOut);
			
		}else if(botao.equals("botaoHistoria")){//chamada do botao "historia do jogo"
			intentOut = new Intent(this, HistoriaActivity.class);//faz a chamada da Activity HistoriaActivity
			super.finish();										// nao envia dado algum.
			startActivity(intentOut);
			
		}else if(botao.equals("botaoComoJogar")){//chamada do botao "como jogar"
			intentOut = new Intent(this, ComoJogarActivity.class);//faz a chamada da Activity ComoJogarActivity
			intentOut.putExtra("tela", 0);//envia uma String "tela"
			super.finish();
			startActivity(intentOut);
			
		}else if(botao.equals("calcularPontosDeVida")){//chamada quando abaixam-se todas as placas ou quando o Jogador pressiona "desistir"
			int jogAtual = intentIn.getIntExtra("jogadorAtual", 0);// o Jogador atual / usado como indice para todas as listas
			int pontosVida = intentIn.getIntegerArrayListExtra("pontuacaoJogadores").get(jogAtual); // os pontos de vida
			int pontosRestantes = intentIn.getIntExtra("pontosRodada", 0);//os pontos restantes
			int numJogadores = intentIn.getIntExtra("numeroDeJogadores", 0);// a quantidade de jogadores
			
			if(pontosVida - pontosRestantes <1){//caso nao haja mais pontos restantes para o Jogador atual
				intentOut = new Intent(this, ScoreActivity.class);// faz a chamada da Activity ScoreActivity
				intentOut.putExtra("botao", "gravarPontuacao");//envia uma String "gravarPontuacao"
				intentOut.putExtra("numeroDeJogadores", intentIn.getIntExtra("numeroDeJogadores",2));//quantidade de jogadores(int)
				intentOut.putStringArrayListExtra("arrayJogadores", intentIn.getStringArrayListExtra("arrayJogadores"));//lista de jogadores (String)
				intentOut.putIntegerArrayListExtra("pontuacaoJogadores", intentIn.getIntegerArrayListExtra("pontuacaoJogadores"));//lista de pontos de vida restantes(int)
				intentOut.putExtra("jogadorAtual", intentIn.getIntExtra("jogadorAtual", 0));// o jogador atual (int)
				intentOut.putIntegerArrayListExtra("listaRodadas", intentIn.getIntegerArrayListExtra("listaRodadas"));// a lista com pontuacao total de cada Jogador
				super.finish();
				startActivity(intentOut);
				}else{// caso contrario
				ArrayList<Integer> pontuacoes = intentIn.getIntegerArrayListExtra("pontuacaoJogadores");//a lista com a pontuacao total de cada Jogador
				pontuacoes.set(jogAtual, pontosVida - pontosRestantes); // os pontos de vida restantes
				jogAtual++; 											// atualiza o Jogador atual
				if(jogAtual>=numJogadores){ 							//tratamento para nao estourar o indice da lista
					jogAtual = 0;
				}
				intentOut = new Intent(this, TelaJogoActivity.class);// faz a chamada da Activity TelaJogoActivity
				intentOut.putExtra("numeroDeJogadores", intentIn.getIntExtra("numeroDeJogadores",2));//quantidade de jogadores(int)
				intentOut.putStringArrayListExtra("arrayJogadores", intentIn.getStringArrayListExtra("arrayJogadores"));//lista de jogadores (String)
				intentOut.putIntegerArrayListExtra("pontuacaoJogadores", pontuacoes);//lista de pontos de vida (int)
				intentOut.putExtra("jogadorAtual", jogAtual);// o jogador atual (int)
				intentOut.putIntegerArrayListExtra("listaRodadas", intentIn.getIntegerArrayListExtra("listaRodadas"));// lista com a pontuacao total dos jogadores
				super.finish();
				startActivity(intentOut);
			}
			
		}else if(botao.equals("novaRodada")){// chamado em caso de o Jogador abaixar todas as placas
			intentOut = new Intent(this, TelaJogoActivity.class);// faz a chamada da Activity TelaJogoActivity
			intentOut.putExtra("numeroDeJogadores", intentIn.getIntExtra("numeroDeJogadores",2));//quantidade de jogadores
			intentOut.putStringArrayListExtra("arrayJogadores", intentIn.getStringArrayListExtra("arrayJogadores"));//lista de jogadores
			intentOut.putIntegerArrayListExtra("pontuacaoJogadores", intentIn.getIntegerArrayListExtra("pontuacaoJogadores"));//lista de pontos de vida
			intentOut.putExtra("jogadorAtual", intentIn.getIntExtra("jogadorAtual", 0));// o jogador atual
			intentOut.putIntegerArrayListExtra("listaRodadas", intentIn.getIntegerArrayListExtra("listaRodadas"));// lista com a pontuacao total dos jogadores
			super.finish();
			startActivity(intentOut);
			
		}else if(botao.equals("gameOver")){// chamado em caso de o Jogador nao possuir mais pontos de vida
			intentOut = new Intent(this, GameOverActivity.class);// faz a chamada da Activity GameOverActivity
			super.finish();
			startActivity(intentOut);
		
		}else if(botao.equals("botaoNumeroDeJogadores")){// chamado apos o Jogador escolher a quantidade de jogadores
			intentOut = new Intent(this, InserirNomeActivity.class);// faz a chamada da Activity InserirNomeActivity
			intentOut.putExtra("numeroDeJogadores", intentIn.getIntExtra("numeroDeJogadores", 1));//a quantidade de jogadores.
			super.finish();
			startActivity(intentOut);
		}
	}

}
