package pucrs.alpro2;

import org.jxmapviewer.viewer.GeoPosition;

import pucrs.alpro2.algoritmos.AlgoritmosGeograficos;

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

		public void setProximoNodoDistancia(Nodo nextDist) {
			this.nextDist = nextDist;
		}

		public Nodo getNodoAnteriorCriminalidade() {
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

	/*
	 * 1. Adicionar novo nodo. 2. Buscar nodo por index. 3. Buscar nodo por grau
	 * de criminalidade. 4. Buscar nodo por distancia. 5. buscar nodo 6.
	 * toString
	 */

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
				novoNodo.setProximoNodoCriminalidade(null);
				novoNodo.setNodoAnteriorCriminalidade(null);
				ultimoNodoCriminalidade = novoNodo;
				primeiroNodoCriminalidade = novoNodo;
			} else {
				// adicionarNovoNodoPorCriminalidade(novoNodo);
				addCriminalidade(novoNodo);
			}
			if (primeiroNodoDistancia == null) {
				novoNodo.setProximoNodoDistancia(null);
				novoNodo.setNodoAnteriorDistancia(null);
				ultimoNodoDistancia = novoNodo;
				primeiroNodoDistancia = novoNodo;
			} else {
				addDistancia(novoNodo);
			}

		} else {
			// seila o cara é loco mandou um null aqui! trata isso malandro..
		}

	}

	private void addDistancia(Nodo novoNodo) {
		Nodo i = null;
		for (i = primeiroNodoDistancia; i != null; i = i.getProximoNodoDistancia()) {

			GeoPosition geoPositionI = new GeoPosition(i.getPonto().getLatitude(), i.getPonto().getLongitude());
			GeoPosition geoPositionNodo = new GeoPosition(novoNodo.getPonto().getLatitude(), novoNodo.getPonto().getLongitude());

			double distancia = AlgoritmosGeograficos.calcDistancia(geoPositionI, geoPositionNodo);
			
			
		}
	}

	private void addCriminalidade(Nodo novoNodo) {

		Nodo i = null;
		for (i = primeiroNodoCriminalidade; i != null; i = i.getProximoNodoCriminalidade()) {
			// i MAIOR e ele é o primeiro.
			if (i.getPonto().getCriminalidade() >= novoNodo.getPonto().getCriminalidade() && i == primeiroNodoCriminalidade) {
				adicionarNoMeioPos(novoNodo, i);
				break;
			}
			// i MAIOR MAS ele não é o primeiro nem o ultimo,
			// ou seja elemento no meio da lista
			if (i.getPonto().getCriminalidade() >= novoNodo.getPonto().getCriminalidade() && i != primeiroNodoCriminalidade && i != ultimoNodoCriminalidade) {
				Nodo refAnt = i;
				adicionarNoMeioPos(novoNodo, refAnt);
				break;
			}

			// i MENOR MAS ele não é o primeiro nem o ultimo,
			// ou seja elemento no meio da lista
			if (i.getPonto().getCriminalidade() <= novoNodo.getPonto().getCriminalidade() && i != primeiroNodoCriminalidade && i != ultimoNodoCriminalidade) {
				Nodo refProx = i;
				Nodo refAnt = i.getNodoAnteriorCriminalidade();
				adicionarNoMeioAnt(novoNodo, refAnt, refProx);
			}

			// i MENOR e NÃO igual
			if (i.getPonto().getCriminalidade() < novoNodo.getPonto().getCriminalidade() && i != primeiroNodoCriminalidade && i != ultimoNodoCriminalidade) {
				Nodo refAnt = i.getNodoAnteriorCriminalidade();
				Nodo refProx = i;
				adicionarNoMeioAnt(novoNodo, refAnt, refProx);
			}

			// i MENOR
			// i pode ser o primeiro ou o ultimo
			if (i.getPonto().getCriminalidade() <= novoNodo.getPonto().getCriminalidade() && i == primeiroNodoCriminalidade || i == ultimoNodoCriminalidade) {
				adicionarNoInicio(novoNodo);
				break;
			}

		}

	}

	/*
	 * Adicionar antes do i
	 * 
	 * @param Nodo, novo nodo a ser adicionado
	 */
	private void adicionarNoMeioAnt(Nodo novoNodo, Nodo refAnt, Nodo refProx) {
		novoNodo.setNodoAnteriorCriminalidade(refAnt);
		novoNodo.setProximoNodoCriminalidade(refProx);
		refAnt.setProximoNodoCriminalidade(novoNodo);
		refProx.setNodoAnteriorCriminalidade(novoNodo);
	}

	/*
	 * Adiciona depois do i.
	 * 
	 * @param Nodo, novo nodo a ser adicionado
	 */
	private void adicionarNoMeioPos(Nodo novoNodo, Nodo refI) {
		novoNodo.setNodoAnteriorCriminalidade(refI);
		novoNodo.setProximoNodoCriminalidade(refI.getProximoNodoCriminalidade());
		refI.setProximoNodoCriminalidade(novoNodo);
	}

	/*
	 * Adiciona no inicio da lista
	 * 
	 * @param Nodo, novo nodo a ser adicionado
	 */
	private void adicionarNoInicio(Nodo novoNodo) {
		novoNodo.setProximoNodoCriminalidade(primeiroNodoCriminalidade);
		novoNodo.setNodoAnteriorCriminalidade(null);
		primeiroNodoCriminalidade.setNodoAnteriorCriminalidade(novoNodo);
		primeiroNodoCriminalidade = novoNodo;
	}

	/*
	 * Adicionar no fim da lista
	 * 
	 * @param Nodo, novo nodo a ser adicionado
	 */
	private void adicionarNoFim(Nodo novoNodo) {
		novoNodo.setNodoAnteriorCriminalidade(ultimoNodoCriminalidade);
		novoNodo.setProximoNodoCriminalidade(null);
		ultimoNodoCriminalidade.setProximoNodoCriminalidade(novoNodo);
		ultimoNodoCriminalidade = novoNodo;
	}

	public String imprimirListaCriminalidade() {
		String elementos = "";

		for (Nodo i = primeiroNodoCriminalidade; i != null; i = i.getProximoNodoCriminalidade()) {
			elementos += " " + i.getPonto().getLogradouro();
		}

		return elementos;
	}


}
