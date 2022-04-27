package br.edu.univas.si3.ed.entidade;

import br.edu.univas.si3.ed.peca.Peca;

public class Entidade {
	private Peca peca;
	
	public Entidade(Peca peca) {
		this.peca = peca;
	}
	public Peca getPeca() {
		return this.peca;
	}

	@Override
	public String toString() {
		return "" + peca + "";
	}
}
