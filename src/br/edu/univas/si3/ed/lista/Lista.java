package br.edu.univas.si3.ed.lista;

import br.edu.univas.si3.ed.entidade.Entidade;
import br.edu.univas.si3.ed.no.No;
import br.edu.univas.si3.ed.peca.Peca;

public class Lista {
	//Lista duplamente encadeada
	public No inicio;
	public No fim;
	
	private static int indice = 0;
	
	public Lista() {
		indice = 0;
	}
	public void inserir(Entidade entidade) {
		
		No novoNo = new No(entidade);
		
		novoNo.indice = indice;
		if(!estaVazia()) {
			novoNo.anterior = this.fim;
			this.fim.proximo = novoNo;
			this.fim = novoNo;
		}else {
			this.inicio = novoNo;
			this.fim = novoNo;
		}
		indice++;
	}
	public void inserirPorPosicao(int posicao, Entidade entidade) {
		No novoNo = new No(entidade);
		No encontrado = buscaPorPosicao(posicao);
		No anterior = encontrado.anterior;
		if(anterior == null) {
			novoNo.proximo = encontrado;
			encontrado.anterior = novoNo;
			this.inicio = novoNo;
		}else {
			novoNo.proximo = encontrado;
			novoNo.anterior = encontrado.anterior;
			encontrado.anterior = novoNo;
			anterior.proximo = novoNo;
			
		}
		
		atualizaIndice();
	}
	public boolean estaVazia() {
		return this.inicio == null;
	}
	public No buscaPorPosicao(int posicaoBuscada) {
		No noDaVez = this.inicio;
		int posicaoNoDaVez = 0;
		while(noDaVez != null) {
			if(posicaoNoDaVez == posicaoBuscada) {
				return noDaVez;
			}
			noDaVez = noDaVez.proximo;
			posicaoNoDaVez++;
		}
		return null;
	}
	public No remove(int posicaoARemover) {
		
		No aRemover = buscaPorPosicao(posicaoARemover); 
		//se for o primeiro
		if(aRemover != null) {
			if(aRemover.anterior == null) {//primeiro no
				if(aRemover.proximo == null) {//unico
					this.inicio = null;
					this.fim = null;
				}else if(aRemover.proximo != null && aRemover.proximo.proximo == null) {
					aRemover.proximo.anterior = null;
					this.inicio = aRemover.proximo;
					this.fim = aRemover.proximo;
				}else {
					this.inicio = aRemover.proximo;
					this.inicio.anterior = null;
				}
				
				
			}//se for o ultimo
			else if(aRemover.proximo == null){
				this.fim = aRemover.anterior;
				this.fim.proximo = null;
			}else {//se for do meio
				No anterior = aRemover.anterior;
				No proximo = aRemover.proximo;
				proximo.anterior = anterior;
				anterior.proximo = proximo;
				
				
			}
		}else {
			return null;
		}
		
		atualizaIndice();
		return aRemover;
	}
	public int comprimento() {
		No noDaVez = this.inicio;
		int c = 0;
		while(noDaVez != null) {
			noDaVez = noDaVez.proximo;
			c++;
			
		}
		return c;
	}
	public void atualizaIndice() {
		No noDaVez = this.inicio;
		for(int i = 0; i < comprimento();i++) {
			noDaVez.indice = i;
			noDaVez = noDaVez.proximo;
		}
	}
	public void imprimir() {
		No noDaVez = this.inicio;
		while(noDaVez != null) {
		System.out.print(noDaVez.entidade.toString()+" | ");
			noDaVez = noDaVez.proximo;
		}
	}
}
