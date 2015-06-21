package pucrs.alpro2.qa;

import pucrs.alpro2.Consultas;
import pucrs.alpro2.Ponto;

public class Principal {

	public static void main(String[] args) {

		Ponto ponto1 = new Ponto();
		ponto1.setLogradouro("Maria");
		ponto1.somaCriminalidade();
		ponto1.somaCriminalidade();

		Ponto ponto2 = new Ponto();
		ponto2.setLogradouro("Pedro");
		ponto2.somaCriminalidade();

		Consultas consultas = new Consultas();
		consultas.adicionar(ponto1);
		consultas.adicionar(ponto2);

		System.out.println(consultas.imprimirListaCriminalidade());

	}

}
