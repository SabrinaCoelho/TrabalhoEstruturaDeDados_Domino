package br.edu.univas.si3.ed.no;

import br.edu.univas.si3.ed.entidade.Entidade;
import br.edu.univas.si3.ed.peca.Peca;

public class No {
	public No proximo;
	public No anterior;
	public int indice;
	public Entidade entidade;
	
	
	public No(Entidade entidade) {
		this.entidade = entidade;
	}
	
}
