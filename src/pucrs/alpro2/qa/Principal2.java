package pucrs.alpro2.qa;

import pucrs.alpro2.Consultas;
import pucrs.alpro2.Ponto;

public class Principal2 {

	public static void main(String[] args) {
		Ponto joao = new Ponto();
		joao.setExpesificacao("Joao");

		Ponto pedro = new Ponto();
		pedro.setExpesificacao("Pedro");
		
		Ponto maria = new Ponto();
		maria.setExpesificacao("Maria");

		Consultas c = new Consultas();

		c.adicionar(pedro, 10);
		System.out.println(c.imprimirListaDistancia());
		
		c.adicionar(joao, 50);
		System.out.println(c.imprimirListaDistancia());

		c.adicionar(maria, 30);
		System.out.println(c.imprimirListaDistancia());

		
	}

}
