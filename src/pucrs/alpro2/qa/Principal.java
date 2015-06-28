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
		ponto2.setLogradouro("Joao");
		ponto2.somaCriminalidade();
		ponto2.somaCriminalidade();
		ponto2.somaCriminalidade();
		ponto2.somaCriminalidade();
		ponto2.somaCriminalidade();
		ponto2.somaCriminalidade();

		Ponto ponto3 = new Ponto();
		ponto3.setLogradouro("Pedro");
		ponto3.somaCriminalidade();


		Consultas consultas = new Consultas();
		consultas.adicionar(ponto1, 10);
		consultas.adicionar(ponto2, 13);
		consultas.adicionar(ponto3, 4);

		System.out.println(consultas.imprimirListaCriminalidade());

	}

}
