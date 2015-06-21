package pucrs.alpro2;

public class Consultas {

	private class Nodo {

		private Ponto ponto;
		private Nodo nextCrim;
		private Nodo nextDist;
		private Nodo prevCrim;
		private Nodo prevDist;

		public Nodo getProximoNodoCriminalidade() {
			return nextCrim;
		}

		public void setProximoNodoCriminalidade(Nodo nextCrim) {
			this.nextCrim = nextCrim;
		}

		public Nodo getProximoNodoDistancia() {
			return nextDist;
		}

		public void setProximoNodoEmDistancia(Nodo nextDist) {
			this.nextDist = nextDist;
		}

		public Nodo getAnteriorCriminalidade() {
			return prevCrim;
		}

		public void setNodoAnteriorCriminalidade(Nodo prevCrim) {
			this.prevCrim = prevCrim;
		}

		public Nodo getNodoAnteriorDistancia() {
			return prevDist;
		}

		public void setNodoAnteriorDistancia(Nodo prevDist) {
			this.prevDist = prevDist;
		}

		public Ponto getPonto() {
			return ponto;
		}

		public void setPonto(Ponto ponto) {
			this.ponto = ponto;
		}

	}

	/*
	 * Ira precisar de uma classe privada nodo, contendo as referencias para:
	 * nextDist, nextCrim, prevCrim, prevDist Lembrar o nodo ira ser do tipo
	 * Ponto ( ponto de taxi ), na classe ListaConsultas então ira precisar ter
	 * os metodo para gerar a interação com nodo exemplo: adicionar, buscarNodo,
	 * aparentemente não ira precisar ter um remover
	 * 
	 * 
	 * Inicialmente a proposta é ordenar do maior para o menor
	 */

	private int totalElementosNaLista;

	// faz sentido eles serem do tipo nodos?!
	private Nodo primeiroNodoCriminalidade;
	private Nodo primeiroNodoDistancia;

	private Nodo ultimoNodoCriminalidade;
	private Nodo ultimoNodoDistancia;

	public void adicionar(Ponto ponto) {
		/*
		 * A ideia aqui é deixar generico, ou seja quando um ponto for
		 * adicionado, ele ira ser adicionado na ordem de criminalidade e de
		 * distancia também.
		 */
		if (ponto != null) {
			Nodo novoNodo = new Nodo();
			novoNodo.setPonto(ponto);

			if (primeiroNodoCriminalidade == null) {

				primeiroNodoCriminalidade = novoNodo;
				ultimoNodoCriminalidade = novoNodo;
				novoNodo.setProximoNodoCriminalidade(null);
				novoNodo.setNodoAnteriorCriminalidade(null);

				totalElementosNaLista++;
			} else {
				adicionarNovoNodoPorCriminalidade(novoNodo);
			}
			/**
			 * Tenho que fazer da distancia aqui também :D E vou precisar
			 * calcular a distancia aqui...
			 */
			if (primeiroNodoDistancia == null) {

			}

		} else {
			// seila o cara é loco mandou um null aqui! trata isso malandro..
		}

	}

	/*
	 * Metodo para adicionar um novo nodo por indice de criminalidade
	 * 
	 * @returns true caso adicionado
	 * 
	 * @Param Nodo novoNodo
	 */
	public void adicionarNovoNodoPorCriminalidade(Nodo novoNodo) {

		for (Nodo i = primeiroNodoCriminalidade; i != null; i = i.getProximoNodoCriminalidade()) {
			if (i.getProximoNodoCriminalidade() == null) {
				if (novoNodo.getPonto().getCriminalidade() >= i.getPonto().getCriminalidade()) {

					if (i.getAnteriorCriminalidade() != null) {
						novoNodo.setNodoAnteriorCriminalidade(i.getAnteriorCriminalidade());
						novoNodo.setProximoNodoCriminalidade(i);
						i.getAnteriorCriminalidade().setProximoNodoCriminalidade(novoNodo);
						i.setNodoAnteriorCriminalidade(novoNodo);

						totalElementosNaLista++;

					} else {
						novoNodo.setNodoAnteriorCriminalidade(null);
						novoNodo.setProximoNodoCriminalidade(i);
						i.setNodoAnteriorCriminalidade(novoNodo);

						primeiroNodoCriminalidade = novoNodo;
						totalElementosNaLista++;
					}

				}

			}
		}
	}

	public Nodo buscarPontoPorDistancia(Nodo ponto) {
		if (primeiroNodoDistancia != null && ponto != null) {
			for (Nodo i = primeiroNodoDistancia; i != null; i = i.getNodoAnteriorDistancia()) {
				if (i == ponto) {
					return i;
				}
			}
		}
		return null;
	}

	public Nodo buscarPontoPorCriminalidade(Nodo ponto) {
		return new Nodo();
	}

	public int getTotalElementosNaLista() {
		return totalElementosNaLista;
	}

	public String imprimirListaCriminalidade() {
		String elementos = "";

		for (Nodo i = primeiroNodoCriminalidade; i != null; i = i.getProximoNodoCriminalidade()) {
			elementos += i.getPonto().getLogradouro();
		}

		return elementos;
	}

}
