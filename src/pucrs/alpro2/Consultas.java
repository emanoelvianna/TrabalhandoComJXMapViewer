package pucrs.alpro2;

import org.jxmapviewer.viewer.GeoPosition;

import pucrs.alpro2.algoritmos.AlgoritmosGeograficos;

public class Consultas {

	private class Nodo {

		private Ponto ponto;
		private Nodo proximoCriminalidade;
		private Nodo proximoDistancia;
		private Nodo anteriorCriminalidade;
		private Nodo anteriorDistancia;
		private double distancia;

		public Nodo getProximoNodoCriminalidade() {
			return proximoCriminalidade;
		}

		public void setProximoNodoCriminalidade(Nodo nextCrim) {
			this.proximoCriminalidade = nextCrim;
		}

		public Nodo getProximoNodoDistancia() {
			return proximoDistancia;
		}

		public void setProximoNodoDistancia(Nodo nextDist) {
			this.proximoDistancia = nextDist;
		}

		public Nodo getNodoAnteriorCriminalidade() {
			return anteriorCriminalidade;
		}

		public void setNodoAnteriorCriminalidade(Nodo prevCrim) {
			this.anteriorCriminalidade = prevCrim;
		}

		public Nodo getNodoAnteriorDistancia() {
			return anteriorDistancia;
		}

		public void setNodoAnteriorDistancia(Nodo prevDist) {
			this.anteriorDistancia = prevDist;
		}

		public Ponto getPonto() {
			return ponto;
		}

		public void setPonto(Ponto ponto) {
			this.ponto = ponto;
		}

		public double getDistancia() {
			return distancia;
		}

		public void setDistancia(double distancia) {
			this.distancia = distancia;
		}

	}

	/*
	 * 1. Adicionar novo nodo. 2. Buscar nodo por index. 3. Buscar nodo por grau
	 * de criminalidade. 4. Buscar nodo por distancia. 5. buscar nodo 6.
	 * toString
	 */

	private Nodo primeiroNodoCriminalidade;
	private Nodo primeiroNodoDistancia;

	private Nodo ultimoNodoCriminalidade;
	private Nodo ultimoNodoDistancia;

	private int totalDistancia = 0;

	public void adicionar(Ponto ponto, double distancia) {
		try {
			Nodo novoNodo = new Nodo();
			novoNodo.setPonto(ponto);
			novoNodo.setDistancia(distancia);

			addCriminalidade(novoNodo);
			addDistancia(novoNodo);

		} catch (IllegalArgumentException argumentException) {
			System.out.println(argumentException.getMessage());
		}
	}

	private void addDistancia(Nodo novoNodo) {
		// está vazio, só adicionar e ir embora
		if (primeiroNodoDistancia == null) {
			primeiroNodoDistancia = novoNodo;
			return;
		}

		Nodo aux = primeiroNodoDistancia;
		Nodo anterior = null;
		// Procura a posição que o novo será adicionado
		while (aux != null) {
			if (aux.getDistancia() > novoNodo.getDistancia()) {
				break;
			}
			anterior = aux;
			aux = aux.getProximoNodoDistancia();
		}

		if (anterior == null) { // inserir no começo
			primeiroNodoDistancia.setNodoAnteriorDistancia(novoNodo);
			novoNodo.setProximoNodoDistancia(primeiroNodoDistancia);
			primeiroNodoDistancia = novoNodo;
		} else { // vai inserir entre o anterior e o aux (proximo)
			anterior.setProximoNodoDistancia(novoNodo);
			novoNodo.setNodoAnteriorDistancia(anterior);
			novoNodo.setProximoNodoDistancia(aux);

			if (aux != null) { // se for nulo ele é o ultimo
				aux.setNodoAnteriorDistancia(novoNodo);
			}
		}
	}

	private void addCriminalidade(Nodo novoNodo) {
		// está vazio, só adicionar e ir embora
		if (primeiroNodoCriminalidade == null) {
			primeiroNodoCriminalidade = novoNodo;
			return;
		}

		Nodo aux = primeiroNodoCriminalidade;
		Nodo anterior = null;
		// Procura a posição que o novo será adicionado
		while (aux != null) {
			if (aux.getPonto().getCriminalidade() > novoNodo.getPonto()
					.getCriminalidade()) {
				break;
			}
			anterior = aux;
			aux = aux.getProximoNodoCriminalidade();
		}

		if (anterior == null) { // inserir no começo
			primeiroNodoCriminalidade.setNodoAnteriorCriminalidade(novoNodo);
			novoNodo.setProximoNodoCriminalidade(primeiroNodoCriminalidade);
			primeiroNodoCriminalidade = novoNodo;
		} else { // vai inserir entre o anterior e o aux (proximo)
			anterior.setProximoNodoCriminalidade(novoNodo);
			novoNodo.setNodoAnteriorCriminalidade(anterior);
			novoNodo.setProximoNodoCriminalidade(aux);

			if (aux != null) { // se for nulo ele é o ultimo
				aux.setNodoAnteriorCriminalidade(novoNodo);
			}
		}
	}

	public Nodo buscarNodoDistancia(Nodo nodo) {
		for (Nodo i = primeiroNodoDistancia; i != null; i = i
				.getProximoNodoDistancia()) {
			if (i == nodo) {
				return i;
			}
		}

		return null;
	}

	public Nodo buscarNodoCriminalidade(Nodo nodo) {
		for (Nodo i = primeiroNodoCriminalidade; i != null; i = i
				.getProximoNodoCriminalidade()) {
			if (i == nodo) {
				return i;
			}
		}
		return null;
	}

	public String imprimirListaCriminalidade() {
		String elementos = "";

		for (Nodo i = primeiroNodoCriminalidade; i != null; i = i
				.getProximoNodoCriminalidade()) {
			elementos += " " + i.getPonto().getLogradouro();
		}

		return elementos;
	}

	public String imprimirListaDistancia() {
		String elementos = "";

		for (Nodo i = primeiroNodoDistancia; i != null; i = i
				.getProximoNodoDistancia()) {
			elementos += " " + i.getPonto().getExpesificacao();
		}

		return elementos;
	}

}
