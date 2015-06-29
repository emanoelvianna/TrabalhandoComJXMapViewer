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

	private int totalDistancia = 0;

	public void adicionar(Ponto ponto, double distancia) {
		try {
			Nodo novoNodo = new Nodo();
			novoNodo.setPonto(ponto);
			novoNodo.setDistancia(distancia);
			/**
			 * if (primeiroNodoCriminalidade == null) {
			 * novoNodo.setProximoNodoCriminalidade(null);
			 * novoNodo.setNodoAnteriorCriminalidade(null);
			 * ultimoNodoCriminalidade = novoNodo; primeiroNodoCriminalidade =
			 * novoNodo; } else { //
			 * adicionarNovoNodoPorCriminalidade(novoNodo);
			 * addCriminalidade(novoNodo); }
			 **/
			if (primeiroNodoDistancia == null) {
				novoNodo.setProximoNodoDistancia(null);
				novoNodo.setNodoAnteriorDistancia(null);
				ultimoNodoDistancia = novoNodo;
				primeiroNodoDistancia = novoNodo;
			} else {
				distancia(novoNodo);
			}
		} catch (IllegalArgumentException argumentException) {
			System.out.println(argumentException.getMessage());
		}
	}

	public void distancia(Nodo novoNodo) {

		Nodo aux = primeiroNodoDistancia;

		for (int i = 0; i < totalDistancia; i++) {

			if (aux != null) {

				if (novoNodo != primeiroNodoDistancia) {

					if (novoNodo.getDistancia() > aux.getDistancia()) {
						novoNodo.setNodoAnteriorDistancia(aux);
						if (aux.getProximoNodoDistancia() != null) {
							novoNodo.setProximoNodoDistancia(aux
									.getProximoNodoDistancia());
							aux.getProximoNodoDistancia()
									.setNodoAnteriorDistancia(novoNodo);
						}
						aux.setProximoNodoDistancia(novoNodo);
					}
					if (novoNodo.getDistancia() < aux.getDistancia()) {
						novoNodo.setProximoNodoDistancia(aux);
						if (aux.getNodoAnteriorDistancia() != null) {
							novoNodo.setNodoAnteriorDistancia(aux
									.getNodoAnteriorDistancia());
							aux.getNodoAnteriorDistancia()
									.setProximoNodoDistancia(novoNodo);
						}
						aux.setNodoAnteriorDistancia(novoNodo);

						if (aux == primeiroNodoDistancia) {
							primeiroNodoDistancia = novoNodo;
						}
					}
					aux = aux.getProximoNodoDistancia();
				}
			}
		}
		totalDistancia++;
	}

	/*
	 * Metodo para adicionar novos nodos já os ordenando [Importante, mais
	 * proximo para o mais distante.]
	 */

	public void addDistancia(Nodo novoNodo) {
		for (Nodo i = primeiroNodoDistancia; i != null; i = i
				.getProximoNodoDistancia()) {

			// nodo deve ficar no primeiro
			if (i.getDistancia() >= novoNodo.getDistancia()
					&& i == primeiroNodoDistancia) {

				novoNodo.setProximoNodoDistancia(i);
				novoNodo.setNodoAnteriorDistancia(null);
				i.setNodoAnteriorDistancia(novoNodo);
				primeiroNodoDistancia = novoNodo;

			}

			// nodo deve ficar no ultimo
			if (i.getDistancia() <= novoNodo.getDistancia()
					&& i == ultimoNodoDistancia) {

				i.setProximoNodoDistancia(novoNodo);
				novoNodo.setNodoAnteriorDistancia(i);
				novoNodo.setProximoNodoDistancia(null);
				ultimoNodoDistancia = novoNodo;

			}

			if (i.getDistancia() < novoNodo.getDistancia()
					&& i == primeiroNodoDistancia && i != ultimoNodoDistancia) {

				novoNodo.setProximoNodoDistancia(i.getProximoNodoDistancia());
				novoNodo.setNodoAnteriorDistancia(i);
				if (i.getProximoNodoDistancia() != null) {
					i.getProximoNodoDistancia().setNodoAnteriorDistancia(
							novoNodo);
				}
				i.setProximoNodoDistancia(novoNodo);
				primeiroNodoDistancia = i;

			}

			if (i.getDistancia() > novoNodo.getDistancia()
					&& i == ultimoNodoDistancia) {

				novoNodo.setNodoAnteriorDistancia(i.getNodoAnteriorDistancia());
				novoNodo.setProximoNodoDistancia(null);
				i.setNodoAnteriorDistancia(novoNodo);
				i.setProximoNodoDistancia(null);
				ultimoNodoDistancia = i;

			}

			if (i.getDistancia() <= novoNodo.getDistancia()
					&& i != primeiroNodoDistancia && i != ultimoNodoDistancia) {

				novoNodo.setNodoAnteriorDistancia(i);
				novoNodo.setProximoNodoDistancia(i.getProximoNodoDistancia());
				if (i.getProximoNodoDistancia() != null) {

					i.getProximoNodoDistancia().setNodoAnteriorDistancia(
							novoNodo);
				}
				i.setProximoNodoDistancia(novoNodo);

			}

			if (i.getDistancia() >= novoNodo.getDistancia()
					&& i != primeiroNodoDistancia && i != ultimoNodoDistancia) {

				novoNodo.setProximoNodoDistancia(i);
				novoNodo.setNodoAnteriorDistancia(i.getNodoAnteriorDistancia());
				i.getNodoAnteriorDistancia().setProximoNodoDistancia(novoNodo);
				i.setNodoAnteriorDistancia(novoNodo);

			}

		}
	}

	private void addCriminalidade(Nodo novoNodo) {

		Nodo i = null;
		for (i = primeiroNodoCriminalidade; i != null; i = i
				.getProximoNodoCriminalidade()) {
			// i MAIOR e ele é o primeiro.
			if (i.getPonto().getCriminalidade() >= novoNodo.getPonto()
					.getCriminalidade() && i == primeiroNodoCriminalidade) {
				novoNodo.setProximoNodoCriminalidade(i);
				novoNodo.setNodoAnteriorCriminalidade(null);
				i.setNodoAnteriorCriminalidade(novoNodo);

				primeiroNodoCriminalidade = novoNodo;

				// adicionarNoMeioPos(novoNodo, i);

				break;
			}
			// i MAIOR MAS ele não é o primeiro nem o ultimo,
			// ou seja elemento no meio da lista
			if (i.getPonto().getCriminalidade() >= novoNodo.getPonto()
					.getCriminalidade()
					&& i != primeiroNodoCriminalidade
					&& i != ultimoNodoCriminalidade) {

				novoNodo.setProximoNodoCriminalidade(i);
				novoNodo.setNodoAnteriorCriminalidade(i
						.getNodoAnteriorCriminalidade());
				i.getNodoAnteriorCriminalidade().setProximoNodoCriminalidade(
						novoNodo);
				i.setNodoAnteriorCriminalidade(novoNodo);

				// Nodo refAnt = i;
				// adicionarNoMeioPos(novoNodo, refAnt);
				break;
			}

			// i MENOR MAS ele não é o primeiro nem o ultimo,
			// ou seja elemento no meio da lista
			if (i.getPonto().getCriminalidade() <= novoNodo.getPonto()
					.getCriminalidade()
					&& i != primeiroNodoCriminalidade
					&& i != ultimoNodoCriminalidade) {

				novoNodo.setNodoAnteriorCriminalidade(i);
				novoNodo.setProximoNodoCriminalidade(i
						.getProximoNodoDistancia());
				i.getProximoNodoCriminalidade().setNodoAnteriorCriminalidade(
						novoNodo);
				i.setProximoNodoCriminalidade(novoNodo);

				// Nodo refProx = i;
				// Nodo refAnt = i.getNodoAnteriorCriminalidade();
				// adicionarNoMeioAnt(novoNodo, refAnt, refProx);
				break;
			}

			/*
			 * // i MENOR e NÃO igual if (i.getPonto().getCriminalidade() <
			 * novoNodo.getPonto() .getCriminalidade() && i !=
			 * primeiroNodoCriminalidade && i != ultimoNodoCriminalidade) {
			 * 
			 * 
			 * 
			 * 
			 * 
			 * Nodo refAnt = i.getNodoAnteriorCriminalidade(); Nodo refProx = i;
			 * adicionarNoMeioAnt(novoNodo, refAnt, refProx); break; }
			 */

			// i MENOR
			// i pode ser o primeiro ou o ultimo
			if (i.getPonto().getCriminalidade() <= novoNodo.getPonto()
					.getCriminalidade() && i == primeiroNodoCriminalidade) {
				novoNodo.setNodoAnteriorCriminalidade(i);
				novoNodo.setProximoNodoCriminalidade(i
						.getProximoNodoCriminalidade());
				i.getProximoNodoCriminalidade().setNodoAnteriorCriminalidade(
						novoNodo);
				i.setProximoNodoCriminalidade(novoNodo);

				// adicionarNoInicio(novoNodo);
				break;
			}
			if (i.getPonto().getCriminalidade() <= novoNodo.getPonto()
					.getCriminalidade() && i == ultimoNodoCriminalidade) {
				novoNodo.setNodoAnteriorCriminalidade(i);
				novoNodo.setProximoNodoCriminalidade(null);
				i.setProximoNodoCriminalidade(novoNodo);
				ultimoNodoCriminalidade = novoNodo;
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
