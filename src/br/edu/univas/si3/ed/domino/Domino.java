package br.edu.univas.si3.ed.domino;

import java.util.Random;
import java.util.Scanner;


import br.edu.univas.si3.ed.entidade.Entidade;
import br.edu.univas.si3.ed.lista.Lista;
import br.edu.univas.si3.ed.no.No;
import br.edu.univas.si3.ed.peca.Peca;

public class Domino {
	private Lista pecas;//para comprar
	private Lista pecasComputador;
	private Lista pecasJogador;
	private Lista pecasJogadas = new Lista();
	
	private static Lista inicioFim = new Lista();
	private static boolean onOff = true;
	private static boolean vez; //true -> computador | false -> jogador
	private static int contadorCompraComputador = 0;
	private static int contadorCompraJogador = 0;
	private static boolean vencedor = false;
	
	private static boolean controleEmpateComputador = false;
	private static boolean controleEmpateJogador = false;
	
	public Domino() {
		System.out.println("------------------------------------- DOMINÓ -------------------------------------");
		//cria as peças
		Lista listaPecas = new Lista();//add as pecas a lista
		for(int i = 0; i <= 6; i++) {
			for(int j = i; j <= 6; j++) {
				Peca peca = new Peca(i, j);
				Entidade entidade = new Entidade(peca);
				listaPecas.inserir(entidade);
			}
		}
		this.pecas = listaPecas;
		
		inicio();
	}
	public void inicio() {
		separaPecas();
		primeiraPeca();
		mesa();
	}
	
	//SEPARAR AS PEÇAS
	public void separaPecas() {
		Random numeroAleatorio = new Random();
		int posicaoNDaLista;
		Lista pecasComputador = new Lista();
		for(int i = 0; i < 7;i++) {//VOLTAR PARA 7
			int t = this.pecas.comprimento();
			posicaoNDaLista = numeroAleatorio.nextInt(t);
			Entidade acessoPeca = this.pecas.buscaPorPosicao(posicaoNDaLista).entidade;
			
			pecasComputador.inserir(acessoPeca);
			Entidade pecaRemovida = this.pecas.remove(posicaoNDaLista).entidade;
			
		}
		this.pecasComputador = pecasComputador;
		Lista pecasJogador = new Lista();
		for(int i = 0; i < 7;i++) {
			posicaoNDaLista = numeroAleatorio.nextInt(this.pecas.comprimento());
			Entidade acessoPeca = this.pecas.buscaPorPosicao(posicaoNDaLista).entidade;
			pecasJogador.inserir(acessoPeca);
			this.pecas.remove(posicaoNDaLista);
		}
		this.pecasJogador = pecasJogador;
		
	}

	public Peca primeiraPeca() {//quem tem a maior peca para começar o jogo
		No noDaVez = this.pecasComputador.inicio;
		Peca pecaDaVez = noDaVez.entidade.getPeca();
		
		Peca maiorPeca = this.pecasComputador.inicio.entidade.getPeca();
		
		No noMaiorPecaComputador = this.pecasComputador.inicio;
		Peca maiorPecaComputador = this.pecasComputador.inicio.entidade.getPeca();
		
		No noMaiorPecaJogador = this.pecasJogador.inicio;;
		Peca maiorPecaJogador = this.pecasJogador.inicio.entidade.getPeca();
		
		while(noDaVez != null) {
			if(pecaDaVez.getN1() == pecaDaVez.getN2() && pecaDaVez.getN2() > maiorPeca.getN2()) {
				maiorPeca = pecaDaVez;
				noMaiorPecaComputador = noDaVez;
			}else if(pecaDaVez.getN2() > maiorPeca.getN2()){
				maiorPeca = pecaDaVez;
				noMaiorPecaComputador = noDaVez;
			}else if(pecaDaVez.getN1() > maiorPeca.getN1() && pecaDaVez.getN2() == maiorPeca.getN2()){
				maiorPeca = pecaDaVez;
				noMaiorPecaComputador = noDaVez;
			}
			noDaVez = noDaVez.proximo;
			if(noDaVez != null) {
				pecaDaVez = noDaVez.entidade.getPeca();
			}
			
		}
		maiorPecaComputador = maiorPeca;
		
		noDaVez = this.pecasJogador.inicio;
		pecaDaVez = noDaVez.entidade.getPeca();
		maiorPeca = this.pecasJogador.inicio.entidade.getPeca();
		while(noDaVez != null) {
			if(pecaDaVez.getN1() == pecaDaVez.getN2() && pecaDaVez.getN2() > maiorPeca.getN2()) {
				maiorPeca = pecaDaVez;
				noMaiorPecaJogador = noDaVez;
			}else if(pecaDaVez.getN2() > maiorPeca.getN2()){
				maiorPeca = pecaDaVez;
				noMaiorPecaJogador = noDaVez;
			}else if(pecaDaVez.getN1() > maiorPeca.getN1() && pecaDaVez.getN2() == maiorPeca.getN2()){
				maiorPeca = pecaDaVez;
				noMaiorPecaJogador = noDaVez;
			}
			noDaVez = noDaVez.proximo;
			if(noDaVez != null) {
				pecaDaVez = noDaVez.entidade.getPeca();
			}
		}
		maiorPecaJogador = maiorPeca;
		
		if(maiorPecaComputador.getN1() > maiorPecaJogador.getN1() && maiorPecaComputador.getN2() >= maiorPecaJogador.getN2()) {
			System.out.println("------------------------------ Computador começa ---------------------------------");
			vez = true;//pc
			atualizaPecas(noMaiorPecaComputador);
			
			Peca p = new Peca(maiorPecaComputador.getN1(), maiorPecaComputador.getN2());//ponta do inicio e ponta do fim. respectivamente numero da esquerda e numero da direta
			Entidade e = new Entidade(p);
			inicioFim.inserir(e);
			
			return maiorPecaComputador;//pega os duplos e de mesmo nivel
		}else if(maiorPecaComputador.getN1() < maiorPecaJogador.getN1() && maiorPecaComputador.getN2() <= maiorPecaJogador.getN2()) {
			System.out.println("--------------------------------- Jogador começa ---------------------------------");
			vez = false;//hum
			atualizaPecas(noMaiorPecaJogador);
			
			Peca p = new Peca(maiorPecaJogador.getN1(), maiorPecaJogador.getN2());//ponta do inicio e ponta do fim. respectivamente numero da esquerda e numero da direta
			Entidade e = new Entidade(p);
			inicioFim.inserir(e);
			
			return maiorPecaJogador;//coleta os duplos e de mesmo nivel
		}else if(maiorPecaComputador.getN2() > maiorPecaJogador.getN2()){
			System.out.println("------------------------------ Computador começa ---------------------------------");
			vez = true;//pc
			atualizaPecas(noMaiorPecaComputador);
			
			Peca p = new Peca(maiorPecaComputador.getN1(), maiorPecaComputador.getN2());//ponta do inicio e ponta do fim. respectivamente numero da esquerda e numero da direta
			Entidade e = new Entidade(p);
			inicioFim.inserir(e);
			
			return maiorPecaComputador;
		}else {
			System.out.println("--------------------------------- Jogador começa ---------------------------------");
			vez = false;//hum
			atualizaPecas(noMaiorPecaJogador);
			
			Peca p = new Peca(maiorPecaJogador.getN1(), maiorPecaJogador.getN2());//ponta do inicio e ponta do fim. respectivamente numero da esquerda e numero da direta
			Entidade e = new Entidade(p);
			inicioFim.inserir(e);
			
			return maiorPecaJogador;
		}
		
	}
	//atualiza peças
	public void atualizaPecas(No noPecaARemover) {
		No pecaJogada;
		if(vez) {
			pecaJogada = this.pecasComputador.remove(noPecaARemover.indice);
		}else {
			pecaJogada = this.pecasJogador.remove(noPecaARemover.indice);
		}
		vez = !vez;
		
		this.pecasJogadas.inserir(pecaJogada.entidade);
	}
	
	public void mesa() {
		Scanner in = new Scanner(System.in);
		do {
			System.out.println("====================================== MESA ======================================");
			System.out.print("Peças do computador:	");
			for(int i = 0; i < this.pecasComputador.comprimento(); i++) {
				System.out.print("  -  |");
			}
			
			System.out.println("\n");
			System.out.print("Peças   na    mesa:	");
			this.pecasJogadas.imprimir();
			System.out.println("\n");
			
			System.out.print("Peças   do jogador:	");
			this.pecasJogador.imprimir();
			System.out.println("\n==================================================================================");
			
			if(!vencedor) {
				if(vez) {
					System.out.println("-------------------------------- Vez do computador ------------------------------");
					computador();
					vez = !vez;
					
				}else {
					System.out.println("Sua vez, jogador: ");
					if(menu() == 9) {
						break;
					}else {
						vez = !vez;
					}
				}
			}
			
			
		}while( !haUmVencedor() && !haEmpate() && onOff);
		
	}
	public int menu() {
		
		Scanner in = new Scanner(System.in);
		int op = 0;
		
			System.out.print(""
					+ "	[1] Passar a vez\n"
					+ "	[2] Comprar\n"
					+ "	[3] Inserir posição da peça\n"
					+ "	[9] Encerrar jogo\n>>");
			op = in.nextInt();
			switch(op) {
				case 1:{
					controleEmpateJogador = true;
					break;
				}
				case 2:{
					if(contadorCompraJogador != 0) {
						System.out.println("Não é possível comprar peças em seguida. Você passou sua vez.");
					}else {
						compraPeca();
						mesa();
						if(onOff) {
							op = menu();
						}
					}
					break;
				}
				case 3:{
					inserirPeca(in);
					break;
				}
				case 9:{
					System.out.println("Até a próxima!");
					onOff = false;
					break;
				}
				default:{
					System.out.println("Escolha uma opção do menu, por favor.");
				}
			}
		
		return op;
		
	}
	
	public void inserirPeca(Scanner in) {
			System.out.println("Qual a posição da peça que deseja jogar?");
			int pos = in.nextInt();
			No noPecaJogada = this.pecasJogador.buscaPorPosicao(pos - 1);
			
			
			if(validacaoPeca(noPecaJogada.entidade.getPeca())) {
				controleEmpateJogador = false;
				compatibilidadePeca(noPecaJogada.entidade.getPeca());
				this.pecasJogador.remove(pos - 1);
			}else {
				System.out.println("Esta peça não é elegível para o jogo.");
			}
			
	}
	public boolean validacaoPeca(Peca peca) {
		int com = this.pecasJogadas.comprimento();
		
		Peca peca1Mesa = this.pecasJogadas.inicio.entidade.getPeca();
		Peca peca2Mesa = this.pecasJogadas.fim.entidade.getPeca();
		
		inicioFim.remove(0);
		Peca p = new Peca(peca1Mesa.getN1(), peca2Mesa.getN2());//ultima peças, respectivamente: numero da esquerda e numero da direta
		Entidade e = new Entidade(p);
		
		inicioFim.inserir(e);
		
		Peca inicioFimAux = inicioFim.buscaPorPosicao(0).entidade.getPeca();
		
			if(peca.getN1() == inicioFimAux.getN1() || peca.getN2() == inicioFimAux.getN1() ) {
				return true;
			}
			else if(peca.getN1() == inicioFimAux.getN2() || peca.getN2() == inicioFimAux.getN2()) {
				return true;
			}
		return false;
	}
	public void compatibilidadePeca(Peca peca) {
		if(peca.getN1() == inicioFim.buscaPorPosicao(0).entidade.getPeca().getN1()) {//Peça da esquerda
			//"gira" a peca
			Peca pAux = new Peca(peca.getN2(), peca.getN1());
			Entidade e = new Entidade(pAux);
			
			this.pecasJogadas.inserirPorPosicao(0, e);//INSERE NO INICIO
		}else if(peca.getN2() == inicioFim.buscaPorPosicao(0).entidade.getPeca().getN1()) {//Peça da esquerda
			Entidade e = new Entidade(peca);
			this.pecasJogadas.inserirPorPosicao(0, e);//INSERE NO INICIO
		}else if(peca.getN1() == inicioFim.buscaPorPosicao(0).entidade.getPeca().getN2()) {//Peça da direita
			Entidade e = new Entidade(peca);
			this.pecasJogadas.inserir(e);//INSERE NO FIM
		}else if(peca.getN2() == inicioFim.buscaPorPosicao(0).entidade.getPeca().getN2()) {//Peça da direita
			//"gira" a peca
			Peca pAux = new Peca(peca.getN2(), peca.getN1());
			
			Entidade e = new Entidade(pAux);
			this.pecasJogadas.inserir(e);//INSERE NO FIM
		}
		
		
		Peca peca1Mesa = this.pecasJogadas.inicio.entidade.getPeca();
		Peca peca2Mesa = this.pecasJogadas.fim.entidade.getPeca();
		
		inicioFim.remove(0);
		Peca p = new Peca(peca1Mesa.getN1(), peca2Mesa.getN2());
		Entidade e = new Entidade(p);
		inicioFim.inserir(e);
		
	}
	
	public void compraPeca() {
		Random r = new Random();
		if(this.pecas.comprimento() > 0) {
			int posSorteada = r.nextInt(this.pecas.comprimento());
			No pecaSorteada = this.pecas.buscaPorPosicao(posSorteada);
			if(this.pecas.comprimento() > 0) {
				if(vez && contadorCompraComputador == 0) {
					this.pecasComputador.inserir(pecaSorteada.entidade);
					contadorCompraComputador++;
				}else if(!vez && contadorCompraJogador == 0){
					this.pecasJogador.inserir(pecaSorteada.entidade);
					contadorCompraJogador++;
				}
				this.pecas.remove(posSorteada);
			}
		}else {
			System.out.println("Não há mais peças para comprar.");
		}
		
	}
	public void computador() {
		Peca jogoAtual = this.inicioFim.buscaPorPosicao(0).entidade.getPeca();
		Peca pecaDaVez;
		boolean temPeca = false;
		
		for(int i = 0; i < this.pecasComputador.comprimento(); i++) {
			pecaDaVez = this.pecasComputador.buscaPorPosicao(i).entidade.getPeca();
			if(pecaDaVez.getN1() == jogoAtual.getN1() || pecaDaVez.getN1() == jogoAtual.getN2()) {
				compatibilidadePeca(pecaDaVez);
				controleEmpateComputador = false;
				
				this.pecasComputador.remove(i);
				temPeca = true;
				break;
			}else if(pecaDaVez.getN2() == jogoAtual.getN1() || pecaDaVez.getN2() == jogoAtual.getN2()) {
				compatibilidadePeca(pecaDaVez);
				controleEmpateComputador = false;
				
				this.pecasComputador.remove(i);
				temPeca = true;
				break;
			}
		}
		if(!temPeca && contadorCompraComputador == 0 && this.pecas.comprimento() > 0) {
			compraPeca();
			computador();
		}else if(!temPeca){
			System.out.println("O Computador passa a vez.");
			controleEmpateComputador = true;
			contadorCompraComputador = 0;
			contadorCompraJogador = 0;
		}
		
	}
	
	public boolean haUmVencedor() {
		if(this.pecasComputador.comprimento() == 0) {
			System.out.println("-------------------------------- Computador venceu! --------------------------------");
			onOff = false;
			vencedor = true;
			return true;
		}else if(this.pecasJogador.comprimento() == 0) {
			System.out.println("-------------------------------- Você venceu!!! --------------------------------");
			onOff = false;
			vencedor = true;
			return true;
		}
		return false;
	}
	
	public boolean haEmpate() {
		if(this.pecas.comprimento() == 0 && controleEmpateJogador && controleEmpateComputador) {
			System.out.println("------------------------------------------ EMPATE ------------------------------------------");
			onOff = false;
			return true;
		}
		return false;
	}
}
