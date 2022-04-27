package br.edu.univas.si3.ed.peca;

public class Peca {
	//n1 | n2
	private int n1;
	private int n2;
	
	public Peca(int n1, int n2) {
		this.n1 = n1;
		this.n2 = n2;
	}

	public int getN1() {
		return this.n1;
	}
	public int getN2() {
		return this.n2;
	}
	public void setN1(int n1) {
		this.n1 = n1;
	}
	public void setN2(int n2) {
		this.n2 = n2;
	}
	@Override
	public String toString() {
		return "" + n1 + "/" + n2 + "";
	}
	
}
